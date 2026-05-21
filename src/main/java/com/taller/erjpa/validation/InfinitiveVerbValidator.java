package com.taller.erjpa.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InfinitiveVerbValidator implements ConstraintValidator<InfinitiveVerb, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true; // @NotBlank handles null/empty
        String trimmed = value.trim().toLowerCase();
        if (trimmed.isEmpty()) return true;
        // Consider valid if first token ends with spanish infinitive endings
        String firstToken = trimmed.split("\\s+", 2)[0];
        return firstToken.endsWith("ar") || firstToken.endsWith("er") || firstToken.endsWith("ir");
    }
}
