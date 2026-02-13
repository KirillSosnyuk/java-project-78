package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema() {
        requiredValidationRule = Objects::nonNull;
    }

    public NumberSchema required() {
        this.isRequired = true;
        return this;
    }

    public NumberSchema positive() {
        Predicate<Integer> positiveRule = value -> value > 0;

        validationRules.put("positive", positiveRule);
        return this;
    }

    public NumberSchema range(Integer lowerBound, Integer upperBound) {
        Predicate<Integer> rangeRule = value -> value >= lowerBound && value <= upperBound;

        validationRules.put("range", rangeRule);
        return this;
    }
}

