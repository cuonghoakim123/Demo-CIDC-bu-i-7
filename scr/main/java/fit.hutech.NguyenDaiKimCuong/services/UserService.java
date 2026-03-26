package fit.hutech.NguyenDaiKimCuong.services;

import fit.hutech.NguyenDaiKimCuong.constants.Role;
import fit.hutech.NguyenDaiKimCuong.entities.User;
import fit.hutech.NguyenDaiKimCuong.repositories.IRoleRepository;
import fit.hutech.NguyenDaiKimCuong.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public void save(@NotNull User user) {
        user.setPassword(
                new BCryptPasswordEncoder()
                        .encode(user.getPassword())
        );
        user.setProvider("Local");
        userRepository.save(user);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public void setDefaultRole(String username) {
        User user = userRepository.findByUsername(username);
        user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
        userRepository.save(user);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            rollbackFor = {Exception.class, Throwable.class}
    )
    public void saveOauthUser(String email, String name) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setUsername(email);
            user.setEmail(email);
            user.setProvider("Google");
            user.setPassword("OAUTH2_USER");
            userRepository.save(user);
            
            // Set default role for OAuth user
            user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
            userRepository.save(user);
        }
    }

    public Optional<User> findByUsername(String username)
            throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        
        var user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found: {}", username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        log.info("User found: {}, Roles: {}", user.getUsername(), user.getRoles());
        log.info("Authorities: {}", user.getAuthorities());
        
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
