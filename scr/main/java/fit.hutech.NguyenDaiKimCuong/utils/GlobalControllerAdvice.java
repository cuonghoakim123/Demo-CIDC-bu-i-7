package fit.hutech.NguyenDaiKimCuong.utils;

import fit.hutech.NguyenDaiKimCuong.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    
    private final IUserRepository userRepository;
    
    @ModelAttribute("currentUsername")
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || 
            "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }
        
        String authName = authentication.getName();
        
        // Try to find user by username first
        var user = userRepository.findByUsername(authName);
        if (user != null) {
            return user.getUsername();
        }
        
        // If not found, try to parse as ID and find by ID
        try {
            Long userId = Long.parseLong(authName);
            user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                return user.getUsername();
            }
        } catch (NumberFormatException e) {
            // Not a number, return the auth name as is
        }
        
        return authName;
    }

    @ModelAttribute("statusLabels")
    public Map<String, String> getStatusLabels() {
        return Map.of(
                "PENDING", "Chờ xử lý",
                "PROCESSING", "Đang xử lý",
                "SHIPPED", "Đang giao",
                "DELIVERED", "Đã giao",
                "CANCELLED", "Đã hủy"
        );
    }

    @ModelAttribute("paymentLabels")
    public Map<String, String> getPaymentLabels() {
        return Map.ofEntries(
                Map.entry("cod", "Thanh toán khi nhận hàng"),
                Map.entry("card", "Thẻ tín dụng/Ghi nợ"),
                Map.entry("bank", "Chuyển khoản ngân hàng"),
                Map.entry("wallet", "Ví điện tử"),
                Map.entry("Cash on Delivery", "Thanh toán khi nhận hàng"),
                Map.entry("Credit/Debit Card", "Thẻ tín dụng/Ghi nợ"),
                Map.entry("Bank Transfer", "Chuyển khoản ngân hàng"),
                Map.entry("E-Wallet", "Ví điện tử")
        );
    }

    @ModelAttribute("providerLabels")
    public Map<String, String> getProviderLabels() {
        return Map.of(
                "Local", "Tài khoản hệ thống",
                "Google", "Google"
        );
    }

    @ModelAttribute("roleLabels")
    public Map<String, String> getRoleLabels() {
        return Map.of(
                "ADMIN", "Quản trị viên",
                "USER", "Người dùng"
        );
    }

    @ModelAttribute("categoryLabels")
    public Map<String, String> getCategoryLabels() {
        return Map.of(
                "Technology", "Công nghệ",
                "Literature", "Văn học",
                "Science", "Khoa học",
                "Business", "Kinh doanh",
                "Children", "Thiếu nhi"
        );
    }
}
