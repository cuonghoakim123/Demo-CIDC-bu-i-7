package fit.hutech.NguyenDaiKimCuong.controllers;

import fit.hutech.NguyenDaiKimCuong.repositories.IBookRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.ICategoryRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.IInvoiceRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.IUserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class DashboardController {

        private static final String CART_STATUS = "CART";

    private final IBookRepository bookRepository;
    private final ICategoryRepository categoryRepository;
    private final IUserRepository userRepository;
    private final IInvoiceRepository invoiceRepository;

    @GetMapping("/dashboard")
    public String dashboard(@NotNull Model model) {
        // Count statistics
        long totalBooks = bookRepository.count();
        long totalCategories = categoryRepository.count();
        long totalUsers = userRepository.count();
                long totalOrders = invoiceRepository.countByStatusNot(CART_STATUS);
        
        // Order statistics by status
                var allOrders = invoiceRepository.findAllByStatusNotOrderByInvoiceDateDesc(CART_STATUS);
        long pendingOrders = allOrders.stream().filter(o -> "PENDING".equals(o.getStatus())).count();
        long processingOrders = allOrders.stream().filter(o -> "PROCESSING".equals(o.getStatus())).count();
        long shippedOrders = allOrders.stream().filter(o -> "SHIPPED".equals(o.getStatus())).count();
        long deliveredOrders = allOrders.stream().filter(o -> "DELIVERED".equals(o.getStatus())).count();
        long cancelledOrders = allOrders.stream().filter(o -> "CANCELLED".equals(o.getStatus())).count();
        
        // Revenue statistics
        double totalRevenue = allOrders.stream()
                .filter(o -> !"CANCELLED".equals(o.getStatus()))
                .mapToDouble(o -> o.getPrice() != null ? o.getPrice() : 0.0)
                .sum();
        
        // Today's orders
        LocalDate today = LocalDate.now();
        Date todayStart = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        long todayOrders = allOrders.stream()
                .filter(o -> o.getInvoiceDate() != null && !o.getInvoiceDate().before(todayStart))
                .count();
        
        // Recent orders (last 5)
        var recentOrders = invoiceRepository.findAllByStatusNotOrderByInvoiceDateDesc(CART_STATUS)
                .stream()
                .limit(5)
                .toList();
        
        // Add to model
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("pendingOrders", pendingOrders);
        model.addAttribute("processingOrders", processingOrders);
        model.addAttribute("shippedOrders", shippedOrders);
        model.addAttribute("deliveredOrders", deliveredOrders);
        model.addAttribute("cancelledOrders", cancelledOrders);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("todayOrders", todayOrders);
        model.addAttribute("recentOrders", recentOrders);
        
        return "dashboard/index";
    }
}
