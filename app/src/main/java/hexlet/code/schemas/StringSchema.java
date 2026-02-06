package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        requiredValidationRule = value -> value != null && !value.isBlank();
        return this;
    }

    public StringSchema minLength(int minimumLength) {
        Predicate<String> minLengthRule =
                value -> value == null || value.isEmpty() || value.length() >= minimumLength;

        validationRules.put("minLength", minLengthRule);
        return this;
    }

    public StringSchema contains(String substring) {
        Predicate<String> containsRule =
                value -> value == null || value.isEmpty() || value.contains(substring);

        validationRules.put("contains", containsRule);
        return this;
    }
}
