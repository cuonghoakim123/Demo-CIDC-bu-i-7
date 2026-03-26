package fit.hutech.NguyenDaiKimCuong.controllers;

import fit.hutech.NguyenDaiKimCuong.repositories.IRoleRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.IUserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @GetMapping
    public String listUsers(@NotNull Model model) {
        var users = userRepository.findAll();
        var roles = roleRepository.findAll();
        
        // Calculate statistics
        long adminCount = users.stream()
                .filter(u -> u.getRoles().stream()
                        .anyMatch(r -> "ADMIN".equals(r.getName())))
                .count();
        
        long regularUserCount = users.stream()
                .filter(u -> u.getRoles().stream()
                        .noneMatch(r -> "ADMIN".equals(r.getName())))
                .count();
        
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        model.addAttribute("adminCount", adminCount);
        model.addAttribute("regularUserCount", regularUserCount);
        
        return "admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        // Don't allow deleting the admin user (id = 1 or username = hutech)
        var user = userRepository.findById(id).orElseThrow();
        if (!"hutech".equals(user.getUsername())) {
            userRepository.deleteById(id);
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/toggle-role/{userId}/{roleId}")
    public String toggleUserRole(
            @PathVariable Long userId,
            @PathVariable Long roleId) {
        
        var user = userRepository.findById(userId).orElseThrow();
        var role = roleRepository.findById(roleId).orElseThrow();
        
        if (user.getRoles().contains(role)) {
            // Don't allow removing ADMIN role from hutech user
            if ("hutech".equals(user.getUsername()) && "ADMIN".equals(role.getName())) {
                return "redirect:/admin/users?error=cannot-remove-admin";
            }
            user.getRoles().remove(role);
        } else {
            user.getRoles().add(role);
        }
        
        userRepository.save(user);
        return "redirect:/admin/users";
    }
}
