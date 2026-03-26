package fit.hutech.NguyenDaiKimCuong.services;

import fit.hutech.NguyenDaiKimCuong.daos.Cart;
import fit.hutech.NguyenDaiKimCuong.daos.Item;
import fit.hutech.NguyenDaiKimCuong.entities.Invoice;
import fit.hutech.NguyenDaiKimCuong.entities.ItemInvoice;
import fit.hutech.NguyenDaiKimCuong.repositories.IBookRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.IInvoiceRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.IItemInvoiceRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.IUserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(
        isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class}
)
public class CartService {

    private static final String CART_SESSION_KEY = "cart";
    private static final String CART_STATUS = "CART";
    private static final String PENDING_STATUS = "PENDING";

    private final IInvoiceRepository invoiceRepository;
    private final IItemInvoiceRepository itemInvoiceRepository;
    private final IBookRepository bookRepository;
    private final IUserRepository userRepository;

    public Cart getCart(@NotNull HttpSession session) {
        var cart = Optional.ofNullable(
                (Cart) session.getAttribute(CART_SESSION_KEY)
        ).orElseGet(() -> {
            Cart newCart = new Cart();
            session.setAttribute(CART_SESSION_KEY, newCart);
            return newCart;
        });

        refreshCartItems(cart);
        session.setAttribute(CART_SESSION_KEY, cart);
        return cart;
    }

    public Cart getCart(@NotNull HttpSession session, String username) {
        var sessionCart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (sessionCart != null) {
            var changed = refreshCartItems(sessionCart);
            session.setAttribute(CART_SESSION_KEY, sessionCart);
            if (changed) {
                syncCartWithDatabase(sessionCart, username);
            }
            return sessionCart;
        }

        if (username != null && !username.isBlank()) {
            var draftInvoice = findDraftInvoice(username);
            if (draftInvoice.isPresent()) {
                var cart = mapInvoiceToCart(draftInvoice.get());
                session.setAttribute(CART_SESSION_KEY, cart);
                return cart;
            }
        }

        return getCart(session);
    }

    public void updateCart(@NotNull HttpSession session, Cart cart) {
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void updateCart(@NotNull HttpSession session, Cart cart, String username) {
        updateCart(session, cart);
        syncCartWithDatabase(cart, username);
    }

    public void removeCart(@NotNull HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    public void removeCart(@NotNull HttpSession session, String username) {
        removeCart(session);
        deleteDraftInvoice(username);
    }

    public int getSumQuantity(@NotNull HttpSession session) {
        return getCart(session)
                .getCartItems()
                .stream()
                .mapToInt(Item::getQuantity)
                .sum();
    }

    public double getSumPrice(@NotNull HttpSession session) {
        return getCart(session)
                .getCartItems()
                .stream()
                .mapToDouble(item ->
                        item.getPrice() * item.getQuantity()
                )
                .sum();
    }

    public void saveCart(@NotNull HttpSession session) {
        var cart = getCart(session);
        if (cart.getCartItems().isEmpty())
            return;

        var invoice = new Invoice();
        invoice.setInvoiceDate(new Date(new Date().getTime()));
        invoice.setPrice(getSumPrice(session));
        invoice.setStatus("PENDING");
        invoiceRepository.save(invoice);

        cart.getCartItems().forEach(item -> {
            var items = new ItemInvoice();
            items.setInvoice(invoice);
            items.setQuantity(item.getQuantity());
            items.setBook(bookRepository.findById(item.getBookId()).orElseThrow());
            itemInvoiceRepository.save(items);
        });

        removeCart(session);
    }

    public void saveCartWithDetails(
            @NotNull HttpSession session,
            String username,
            String orderNumber,
            String paymentMethod,
            String shippingAddress,
            String customerName,
            String customerPhone,
            String customerEmail) {

        var cart = getCart(session, username);
        if (cart.getCartItems().isEmpty())
            return;

        var user = userRepository.findByUsername(username);

        var invoice = findDraftInvoice(username).orElseGet(Invoice::new);
        invoice.setInvoiceDate(new Date());
        invoice.setPrice(getSumPrice(session) * 1.1); // Including 10% tax
        invoice.setOrderNumber(orderNumber);
        invoice.setStatus(PENDING_STATUS);
        invoice.setPaymentMethod(paymentMethod);
        invoice.setShippingAddress(shippingAddress);
        invoice.setCustomerName(customerName);
        invoice.setCustomerPhone(customerPhone);
        invoice.setCustomerEmail(customerEmail);
        invoice.setUser(user);
        var savedInvoice = invoiceRepository.save(invoice);
        replaceInvoiceItems(savedInvoice, cart);
    }

    private Optional<Invoice> findDraftInvoice(String username) {
        if (username == null || username.isBlank()) {
            return Optional.empty();
        }

        var user = userRepository.findByUsername(username);
        if (user == null) {
            return Optional.empty();
        }

        return invoiceRepository.findFirstByUserAndStatusOrderByInvoiceDateDesc(user, CART_STATUS);
    }

    private void syncCartWithDatabase(Cart cart, String username) {
        if (username == null || username.isBlank()) {
            return;
        }

        var user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        if (cart.getCartItems().isEmpty()) {
            deleteDraftInvoice(username);
            return;
        }

        var invoice = invoiceRepository.findFirstByUserAndStatusOrderByInvoiceDateDesc(user, CART_STATUS)
                .orElseGet(Invoice::new);

        invoice.setInvoiceDate(new Date());
        invoice.setPrice(cart.getTotalPrice());
        invoice.setStatus(CART_STATUS);
        invoice.setPaymentMethod(null);
        invoice.setShippingAddress(null);
        invoice.setOrderNumber(null);
        invoice.setCustomerName(user.getUsername());
        invoice.setCustomerPhone(user.getPhone());
        invoice.setCustomerEmail(user.getEmail());
        invoice.setUser(user);

        var savedInvoice = invoiceRepository.save(invoice);
        replaceInvoiceItems(savedInvoice, cart);
    }

    private void deleteDraftInvoice(String username) {
        findDraftInvoice(username).ifPresent(invoice -> {
            itemInvoiceRepository.deleteByInvoice(invoice);
            invoiceRepository.delete(invoice);
        });
    }

    private Cart mapInvoiceToCart(Invoice invoice) {
        var cart = new Cart();

        invoice.getItemInvoices().forEach(itemInvoice -> {
            var book = itemInvoice.getBook();
            if (book == null) {
                return;
            }

            cart.addItems(new Item(
                    book.getId(),
                    book.getTitle(),
                    book.getPrice(),
                    itemInvoice.getQuantity(),
                    book.getImageUrl()
            ));
        });

        return cart;
    }

    private boolean refreshCartItems(Cart cart) {
        boolean changed = false;
        var refreshedItems = new ArrayList<Item>();

        for (var item : cart.getCartItems()) {
            var bookOptional = bookRepository.findById(item.getBookId());
            if (bookOptional.isEmpty()) {
                changed = true;
                continue;
            }

            var book = bookOptional.get();
            var refreshedItem = new Item(
                    book.getId(),
                    book.getTitle(),
                    book.getPrice(),
                    item.getQuantity(),
                    book.getImageUrl()
            );
            refreshedItems.add(refreshedItem);

            if (!book.getTitle().equals(item.getBookName())
                    || !book.getPrice().equals(item.getPrice())
                    || !java.util.Objects.equals(book.getImageUrl(), item.getImageUrl())) {
                changed = true;
            }
        }

        if (changed || refreshedItems.size() != cart.getCartItems().size()) {
            cart.setCartItems(refreshedItems);
        }

        return changed || refreshedItems.size() != cart.getCartItems().size();
    }

    private void replaceInvoiceItems(Invoice invoice, Cart cart) {
        itemInvoiceRepository.deleteByInvoice(invoice);

        cart.getCartItems().forEach(item -> {
            var itemInvoice = new ItemInvoice();
            itemInvoice.setInvoice(invoice);
            itemInvoice.setQuantity(item.getQuantity());
            itemInvoice.setBook(bookRepository.findById(item.getBookId()).orElseThrow());
            itemInvoiceRepository.save(itemInvoice);
        });
    }
}
