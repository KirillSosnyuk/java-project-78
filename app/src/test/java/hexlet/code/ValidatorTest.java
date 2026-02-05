package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @Test
    void testStringValidationBasic() {
        var v = new Validator();
        var stringRule = v.string();


        assertTrue(stringRule.isValid("Hexlet"));
        assertTrue(stringRule.isValid(""));
        assertTrue(stringRule.isValid(null));
    }

    @Test
    void testStringRequired() {
        var v = new Validator();
        var stringRule = v.string().required();

        assertTrue(stringRule.isValid("Java"));
        assertTrue(stringRule.isValid("Space   "));
        assertFalse(stringRule.isValid(""));
        assertFalse(stringRule.isValid(null));
    }

    @Test
    void testStringMinLength() {
        var v = new Validator();
        var schema = v.string().minLength(5);

        assertTrue(schema.isValid("House"));
        assertTrue(schema.isValid("Mountain"));
        assertFalse(schema.isValid("Cat"));

        schema.minLength(2);
        assertTrue(schema.isValid("Cat"));
    }

    @Test
    void testStringContains() {
        var v = new Validator();
        var schema = v.string().contains("Java");

        assertTrue(schema.isValid("Java code"));
        assertTrue(schema.isValid("I love Java"));
        assertFalse(schema.isValid("javascript"));

        assertFalse(schema.isValid("Python code"));


        schema.contains("va");
        assertTrue(schema.isValid("Java"));
    }

    @Test
    void testStringComplexChain() {
        // Тестируем цепочку вызовов
        var v = new Validator();
        var complexSchema = v.string()
                .required()
                .minLength(4)
                .contains("fox");

        assertTrue(complexSchema.isValid("The quick brown fox"));
        assertFalse(complexSchema.isValid("fox"));
        assertFalse(complexSchema.isValid("The quick brown dog"));
        assertFalse(complexSchema.isValid(null));
    }

    @Test
    void testNumberValidationBasic() {
        var v = new Validator();
        var numberSchema = v.number();

        assertTrue(numberSchema.isValid(null));
        assertTrue(numberSchema.isValid(100));
        assertTrue(numberSchema.isValid(-100));
    }

    @Test
    void testNumberRequiredAndPositive() {
        var v = new Validator();
        var numSchema = v.number().required().positive();

        assertTrue(numSchema.isValid(10));
        assertFalse(numSchema.isValid(null));
        assertFalse(numSchema.isValid(-5));
        assertFalse(numSchema.isValid(0));
    }

    @Test
    void testNumberRange() {
        var v = new Validator();
        var rangeSchema = v.number().range(10, 20);

        assertTrue(rangeSchema.isValid(10));
        assertTrue(rangeSchema.isValid(20));
        assertTrue(rangeSchema.isValid(15));
        assertFalse(rangeSchema.isValid(9));
        assertFalse(rangeSchema.isValid(21));

        // Тест переопределения диапазона
        rangeSchema.range(5, 10);
        assertTrue(rangeSchema.isValid(5));
        assertFalse(rangeSchema.isValid(15));
    }

    @Test
    void testNumberConstraintsIndependence() {
        var v = new Validator();
        var schema = v.number().positive();

        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(-42));
    }


    @Test
    void testMapBaseValidation() {
        var v = new Validator();
        var mapSchema = v.map();

        assertTrue(mapSchema.isValid(null));
        assertTrue(mapSchema.isValid(new HashMap<>()));

        mapSchema.required();
        assertFalse(mapSchema.isValid(null));
        assertTrue(mapSchema.isValid(new HashMap<>()));
    }

    @Test
    void testMapSize() {
        var v = new Validator();
        var mapSchema = v.map().required().sizeof(2);

        Map<String, String> data = new HashMap<>();
        data.put("key1", "val1");
        assertFalse(mapSchema.isValid(data));

        data.put("key2", "val2");
        assertTrue(mapSchema.isValid(data));

        data.put("key3", "val3");
        assertFalse(mapSchema.isValid(data));
    }

    @Test
    void testMapShapeNested() {
        var v = new Validator();
        var schema = v.map();

        Map<String, BaseSchema<String>> carSchema = new HashMap<>();

        carSchema.put("model", v.string().required().minLength(3));
        carSchema.put("vin", v.string().required().contains("X"));

        schema.shape(carSchema);


        Map<String, String> bmw = new HashMap<>();
        bmw.put("model", "BMW X5");
        bmw.put("vin", "XX12345");
        assertTrue(schema.isValid(bmw));


        Map<String, String> audi = new HashMap<>();
        audi.put("model", "Audi");
        audi.put("vin", "123456");
        assertFalse(schema.isValid(audi));


        Map<String, String> lada = new HashMap<>();
        lada.put("model", "Lada");
        assertFalse(schema.isValid(lada));


        Map<String, String> toyCar = new HashMap<>();
        toyCar.put("model", "M"); // < 3
        toyCar.put("vin", "X1");
        assertFalse(schema.isValid(toyCar));
    }


    @Test
    void testInvalidRangeLogic() {
        var v = new Validator();

        var impossibleSchema = v.number().range(50, 40);

        assertFalse(impossibleSchema.isValid(45));
        assertFalse(impossibleSchema.isValid(50));
        assertFalse(impossibleSchema.isValid(40));
    }

    @Test
    void testDoubleConfigurationOverride() {

        var v = new Validator();
        var schema = v.string().minLength(100).minLength(1);

        assertTrue(schema.isValid("Hi"));
    }
}
