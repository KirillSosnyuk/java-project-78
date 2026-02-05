package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema required() {
        requiredValidationRule = Objects::nonNull;
        return this;
    }

    public MapSchema sizeof(int expectedSize) {
        Predicate<Map<?, ?>> sizeRule = map -> map.size() == expectedSize;
        validationRules.put("sizeof", sizeRule);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> fieldSchemas) {
        Predicate<Map<?, ?>> shapeRule = map -> {
            Set<Map.Entry<String, BaseSchema<String>>> schemaEntries = fieldSchemas.entrySet();

            for (Map.Entry<String, BaseSchema<String>> schemaEntry : schemaEntries) {
                String fieldName = schemaEntry.getKey();
                BaseSchema<String> fieldSchema = schemaEntry.getValue();

                String fieldValue = (String) map.get(fieldName);
                if (!fieldSchema.isValid(fieldValue)) {
                    return false;
                }
            }

            return true;
        };

        validationRules.put("shape", shapeRule);
        return this;
    }
}

