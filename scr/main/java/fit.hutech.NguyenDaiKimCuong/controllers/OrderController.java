package fit.hutech.NguyenDaiKimCuong.controllers;

import fit.hutech.NguyenDaiKimCuong.repositories.IInvoiceRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.IUserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final IInvoiceRepository invoiceRepository;
    private final IUserRepository userRepository;

    @GetMapping
    public String showMyOrders(Authentication authentication, @NotNull Model model) {
        var username = authentication.getName();
        var user = userRepository.findByUsername(username);
        if (user == null) {
            return "redirect:/login";
        }
        var orders = invoiceRepository.findByUserAndStatusNotOrderByInvoiceDateDesc(user, "CART");
        model.addAttribute("orders", orders);
        return "order/my-orders";
    }

    @GetMapping("/{id}")
    public String showOrderDetail(@PathVariable Long id, @NotNull Model model) {
        var order = invoiceRepository.findById(id).orElseThrow();
        model.addAttribute("order", order);
        return "order/order-detail";
    }

    @GetMapping("/admin/all")
    public String showAllOrders(@NotNull Model model) {
        var orders = invoiceRepository.findAllByStatusNotOrderByInvoiceDateDesc("CART");
        
        // Calculate statistics
        long pendingCount = orders.stream().filter(o -> "PENDING".equals(o.getStatus())).count();
        long processingCount = orders.stream().filter(o -> "PROCESSING".equals(o.getStatus())).count();
        long shippedCount = orders.stream().filter(o -> "SHIPPED".equals(o.getStatus())).count();
        long deliveredCount = orders.stream().filter(o -> "DELIVERED".equals(o.getStatus())).count();
        
        model.addAttribute("orders", orders);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("processingCount", processingCount);
        model.addAttribute("shippedCount", shippedCount);
        model.addAttribute("deliveredCount", deliveredCount);
        
        return "order/admin-orders";
    }

    @PostMapping("/admin/update-status/{id}")
    public String updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        var order = invoiceRepository.findById(id).orElseThrow();
        order.setStatus(status);
        invoiceRepository.save(order);
        return "redirect:/orders/admin/all";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        invoiceRepository.deleteById(id);
        return "redirect:/orders/admin/all";
    }
}
