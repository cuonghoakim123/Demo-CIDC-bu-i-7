package fit.hutech.NguyenDaiKimCuong.validators;

import fit.hutech.NguyenDaiKimCuong.entities.Category;
import fit.hutech.NguyenDaiKimCuong.validators.annotations.ValidCategoryId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ValidCategoryIdValidator
        implements ConstraintValidator<ValidCategoryId, Category> {

    @Override
    public boolean isValid(
            Category category,
            ConstraintValidatorContext context
    ) {
        return category != null && category.getId() != null;
    }
}
