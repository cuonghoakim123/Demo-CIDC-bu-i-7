package fit.hutech.NguyenDaiKimCuong.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        
        log.info("Authentication successful for user: {}", authentication.getName());
        log.info("User authorities: {}", authentication.getAuthorities());
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        // Check if user has ADMIN role
        boolean isAdmin = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        
        log.info("Is Admin: {}", isAdmin);
        
        if (isAdmin) {
            log.info("Redirecting to /dashboard");
            response.sendRedirect("/dashboard");
        } else {
            log.info("Redirecting to /");
            response.sendRedirect("/");
        }
    }
}
