package fit.hutech.NguyenDaiKimCuong.controllers;

import fit.hutech.NguyenDaiKimCuong.services.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public String showCart(HttpSession session, Authentication authentication, @NotNull Model model) {
        var username = authentication != null ? authentication.getName() : null;
        var cart = cartService.getCart(session, username);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cartService.getSumPrice(session));
        model.addAttribute("totalQuantity", cartService.getSumQuantity(session));
        model.addAttribute("cartAutoSaved", username != null && !username.isBlank() && !cart.getCartItems().isEmpty());
        return "book/cart";
    }

    @GetMapping("/removeFromCart/{id}")
    public String removeFromCart(HttpSession session, Authentication authentication, @PathVariable Long id) {
        var username = authentication != null ? authentication.getName() : null;
        var cart = cartService.getCart(session, username);
        cart.removeItems(id);
        cartService.updateCart(session, cart, username);
        return "redirect:/cart";
    }

    @GetMapping("/updateCart/{id}/{quantity}")
    public String updateCart(HttpSession session, Authentication authentication, @PathVariable Long id, @PathVariable int quantity) {
        var username = authentication != null ? authentication.getName() : null;
        var cart = cartService.getCart(session, username);
        cart.updateItems(id, quantity);
        cartService.updateCart(session, cart, username);
        return "redirect:/cart";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session, Authentication authentication) {
        var username = authentication != null ? authentication.getName() : null;
        cartService.removeCart(session, username);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Authentication authentication, @NotNull Model model) {
        var username = authentication != null ? authentication.getName() : null;
        var cart = cartService.getCart(session, username);
        
        if (cart.getCartItems().isEmpty()) {
            return "redirect:/cart";
        }
        
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cartService.getSumPrice(session));
        model.addAttribute("totalQuantity", cartService.getSumQuantity(session));
        model.addAttribute("cartAutoSaved", username != null && !username.isBlank() && !cart.getCartItems().isEmpty());
        return "book/checkout";
    }

    @PostMapping("/process-checkout")
    public String processCheckout(
            HttpSession session,
            Authentication authentication,
            @RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String zipCode,
            @RequestParam(required = false) String notes,
            @RequestParam String paymentMethod,
            Model model) {
        
        var cart = cartService.getCart(session, authentication.getName());
        String orderNumber = "ORD-" + System.currentTimeMillis();
        String shippingAddress = address + ", " + city + " " + zipCode;
        double totalAmount = cartService.getSumPrice(session) * 1.1; // Including 10% tax
        
        // Get payment method display name
        String paymentMethodDisplay = switch (paymentMethod) {
            case "cod" -> "Thanh toán khi nhận hàng";
            case "card" -> "Thẻ tín dụng/Ghi nợ";
            case "bank" -> "Chuyển khoản ngân hàng";
            case "wallet" -> "Ví điện tử";
            default -> "Không xác định";
        };
        
        // Save cart to database with order details
        String username = authentication.getName();
        cartService.saveCartWithDetails(
            session, 
            username, 
            orderNumber, 
            paymentMethodDisplay, 
            shippingAddress,
            fullName,
            phone,
            email
        );
        
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("orderDate", new Date());
        model.addAttribute("paymentMethod", paymentMethodDisplay);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("shippingAddress", shippingAddress);
        model.addAttribute("orderItems", cart.getCartItems());
        
        // Clear cart after successful checkout
        cartService.removeCart(session, username);
        
        return "book/order-success";
    }
}
