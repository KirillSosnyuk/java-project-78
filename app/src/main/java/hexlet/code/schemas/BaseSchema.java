package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema<T> {

    protected final Map<String, Predicate<T>> validationRules = new HashMap<>();
    protected Predicate<T> requiredValidationRule =
            value -> validationRules.isEmpty() || value != null;

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
