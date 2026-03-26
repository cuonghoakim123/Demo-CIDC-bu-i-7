package fit.hutech.NguyenDaiKimCuong.controllers;

import fit.hutech.NguyenDaiKimCuong.daos.Item;
import fit.hutech.NguyenDaiKimCuong.entities.Book;
import fit.hutech.NguyenDaiKimCuong.services.BookService;
import fit.hutech.NguyenDaiKimCuong.services.CartService;
import fit.hutech.NguyenDaiKimCuong.services.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final CartService cartService;

    @GetMapping
    public String showAllBooks(
            @NotNull Model model,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        model.addAttribute("books", bookService.getAllBooks(pageNo, pageSize, sortBy));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", bookService.getAllBooks(pageNo, pageSize, sortBy).size() / pageSize);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "book/list";
    }

    @GetMapping("/search")
    public String searchBook(
            @NotNull Model model,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        model.addAttribute("books", bookService.searchBook(keyword));
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", bookService
                .getAllBooks(pageNo, pageSize, sortBy)
                .size() / pageSize);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/list";
    }

    @GetMapping("/add")
    public String addBookForm(@NotNull Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/add";
    }

    @PostMapping("/add")
    public String addBook(
            @Valid @ModelAttribute("book") Book book,
            @RequestParam(value = "imageUrlInput", required = false) String imageUrlInput,
            @NotNull BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/add";
        }

        book.setImageUrl(normalizeImageUrl(imageUrlInput));

        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@NotNull Model model, @PathVariable long id) {
        var book = bookService.getBookById(id);
        model.addAttribute("book", book.orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sách")));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "book/edit";
    }

    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("book") Book book,
                          @RequestParam(value = "imageUrlInput", required = false) String imageUrlInput,
                          @RequestParam(value = "existingImageUrl", required = false) String existingImageUrl,
                          @NotNull BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toArray(String[]::new);
            model.addAttribute("errors", errors);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/edit";
        }

        String normalizedImageUrl = normalizeImageUrl(imageUrlInput);
        book.setImageUrl(normalizedImageUrl != null ? normalizedImageUrl : existingImageUrl);

        bookService.updateBook(book);
        return "redirect:/books";
    }

    private String normalizeImageUrl(String imageUrlInput) {
        if (imageUrlInput == null) {
            return null;
        }

        String trimmed = imageUrlInput.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        bookService.getBookById(id)
                .ifPresentOrElse(
                        book -> bookService.deleteBookById(id),
                        () -> {
                            throw new IllegalArgumentException("Không tìm thấy sách");
                        });
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        var book = bookService.getBookById(id)
            .orElseThrow(() -> new IllegalArgumentException("Mã sách không hợp lệ: " + id));
        model.addAttribute("book", book);
        return "book/detail";
    }

    @PostMapping("/add-to-cart")
public String addToCart(
        HttpSession session,
    Authentication authentication,
        @RequestParam long id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Double price,
        @RequestParam(defaultValue = "1") int quantity,
        @RequestHeader(value = "Referer", required = false) String referer,
        RedirectAttributes redirectAttributes
) {
    var book = bookService.getBookById(id)
            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sách"));

    var username = authentication != null ? authentication.getName() : null;

    var cart = cartService.getCart(session, username);

    cart.addItems(
            new Item(book.getId(), book.getTitle(), book.getPrice(), quantity, book.getImageUrl())
    );

    cartService.updateCart(session, cart, username);

    redirectAttributes.addFlashAttribute(
            "successMessage",
            quantity > 1
                    ? "Đã thêm " + quantity + " cuốn \"" + book.getTitle() + "\" vào giỏ hàng."
                    : "Đã thêm \"" + book.getTitle() + "\" vào giỏ hàng thành công."
    );

    return resolveRedirectTarget(referer);
}

    private String resolveRedirectTarget(String referer) {
        if (referer == null || referer.isBlank()) {
            return "redirect:/books";
        }

        if (referer.contains("localhost:8080")) {
            int hostIndex = referer.indexOf("localhost:8080");
            String relative = referer.substring(hostIndex + "localhost:8080".length());
            if (relative.isBlank()) {
                return "redirect:/books";
            }
            return "redirect:" + relative;
        }

        if (referer.startsWith("/")) {
            return "redirect:" + referer;
        }

        return "redirect:/books";
    }

}
