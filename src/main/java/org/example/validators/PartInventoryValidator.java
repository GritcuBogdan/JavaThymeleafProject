package org.example.validators;


import org.example.domain.Part;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PartInventoryValidator implements ConstraintValidator<ValidPartInventory, Part> {
    @Override
    public void initialize(ValidPartInventory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Part part, ConstraintValidatorContext constraintValidatorContext) {
        if(part.getInv() < part.getMin() || part.getInv() > part.getMax()){
            return false;
        }
        return true;
    }
}
