package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema() {
        requiredValidationRule = value -> true;
    }

    public NumberSchema required() {
        requiredValidationRule = Objects::nonNull;
        return this;
    }

    public NumberSchema positive() {
        Predicate<Integer> positiveRule =
                value -> value == null || value > 0;

        validationRules.put("positive", positiveRule);
        return this;
    }

    public NumberSchema range(Integer lowerBound, Integer upperBound) {
        Predicate<Integer> rangeRule =
                value -> value == null
                        || (lowerBound <= value && value <= upperBound);

        validationRules.put("range", rangeRule);
        return this;
    }
}

