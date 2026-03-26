package fit.hutech.NguyenDaiKimCuong.validators;

import fit.hutech.NguyenDaiKimCuong.services.UserService;
import fit.hutech.NguyenDaiKimCuong.validators.annotations.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidUsernameValidator implements
        ConstraintValidator<ValidUsername, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) return true;
        return userService.findByUsername(username).isEmpty();
    }
}
