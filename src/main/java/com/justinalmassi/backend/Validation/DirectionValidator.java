package com.justinalmassi.backend.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class DirectionValidator implements ConstraintValidator<DirectionValidation, String> {

    private List<String> validSortOptions;
    private List<String> validDirections;

    public DirectionValidator() {
        validDirections = new ArrayList<String>() {{
            add("asc");
            add("desc");
        }};
    }

    @Override
    public boolean isValid(String direction, ConstraintValidatorContext context) {
        if (direction == null) return false;
        return validDirections.contains(direction);
    }

    private static boolean isValidString(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return true;
    }
}
