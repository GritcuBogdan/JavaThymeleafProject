package org.example.validators;

import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PartInventoryValidator.class)
public @interface ValidPartInventory {
    String message() default "Inventory must be between your max and min";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

