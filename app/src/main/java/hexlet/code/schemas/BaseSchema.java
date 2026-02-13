package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public sealed class BaseSchema<T>
        permits MapSchema, NumberSchema, StringSchema {

    protected final Map<String, Predicate<T>> validationRules = new HashMap<>();
    protected boolean isRequired = false;
    protected Predicate<T> requiredValidationRule = value -> value != null;

    /**
     * Validates an object using rules from the validationRules map.
     *
     * @param value object to validate
     * @return true if all checks from the schema passed
     */
    public boolean isValid(T value) {
        if (!requiredValidationRule.test(value)) {
            return !isRequired;
        }

        for (Predicate<T> validationRule : validationRules.values()) {
            if (!validationRule.test(value)) {
                return false;
            }
        }

        return true;
    }
}
