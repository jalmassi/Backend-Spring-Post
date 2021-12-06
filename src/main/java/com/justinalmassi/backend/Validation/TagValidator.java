package com.justinalmassi.backend.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class TagValidator implements ConstraintValidator<TagValidation, List<String>> {
    @Override
    public boolean isValid(List<String> tagsList, ConstraintValidatorContext context) {
        if (tagsList == null || tagsList.size() == 0) return false;

        for (String str : tagsList) {
            if (!isValidString(str)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidString(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return true;
    }
}
