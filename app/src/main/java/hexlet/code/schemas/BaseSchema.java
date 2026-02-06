package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public sealed class BaseSchema<T>
        permits MapSchema, NumberSchema, StringSchema {

    protected final Map<String, Predicate<T>> validationRules = new HashMap<>();
    protected Predicate<T> requiredValidationRule = value -> true;

    /**
     * Validates an object using rules from the validationRules map.
     *
     * @param value object to validate
     * @return true if all checks from the schema passed
     */
    public boolean isValid(T value) {
        if (!requiredValidationRule.test(value)) {
            return false;
        }

        for (Predicate<T> validationRule : validationRules.values()) {
            if (!validationRule.test(value)) {
                return false;
            }
        }

        return true;
    }
}
