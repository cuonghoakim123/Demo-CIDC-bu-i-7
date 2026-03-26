package fit.hutech.NguyenDaiKimCuong.services;

import fit.hutech.NguyenDaiKimCuong.entities.User;
import fit.hutech.NguyenDaiKimCuong.repositories.IRoleRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService extends DefaultOAuth2UserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        
        log.info("OAuth2 login attempt - Email: {}, Name: {}", email, name);
        
        // Check if user exists by email
        User user = userRepository.findByEmail(email);
        
        if (user == null) {
            log.info("Creating new OAuth2 user: {}", email);
            // Create new user if doesn't exist
            user = new User();
            user.setUsername(name != null ? name : email);
            user.setEmail(email);
            user.setProvider("Google");
            user.setPassword("OAUTH2_USER");
            
            // Assign USER role
            fit.hutech.NguyenDaiKimCuong.entities.Role userRole = roleRepository.findRoleById(
                    fit.hutech.NguyenDaiKimCuong.constants.Role.USER.value
            );
            if (userRole != null) {
                user.setRoles(new HashSet<>());
                user.getRoles().add(userRole);
                log.info("Assigned USER role to new OAuth2 user");
            } else {
                log.error("USER role not found in database!");
            }
            
            userRepository.save(user);
        } else {
            log.info("Existing OAuth2 user found: {}, Roles: {}", user.getUsername(), user.getRoles());
        }
        
        // Return custom OAuth2User with username and authorities from database
        return new CustomOAuth2User(oauth2User, user);
    }
}
