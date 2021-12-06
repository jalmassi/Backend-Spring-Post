package com.justinalmassi.backend.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class SortByValidator implements ConstraintValidator<SortByValidation, String> {

    private List<String> validSortOptions;

    public SortByValidator() {
        validSortOptions = new ArrayList<String>() {{
            add("id");
            add("reads");
            add("likes");
            add("popularity");
        }};
    }

    @Override
    public boolean isValid(String direction, ConstraintValidatorContext context) {
        if (direction == null) return false;
        return validSortOptions.contains(direction);
    }

    private static boolean isValidString(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return true;
    }
}
