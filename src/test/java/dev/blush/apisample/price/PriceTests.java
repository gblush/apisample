package dev.blush.apisample.price;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class PriceTests {

    @Test
    public void test_price_empty_constructor() {
        Price price = new Price();
        assertEquals("unavailable", price.getPrice());
    }

    @Test
    public void test_price_parameterized_constructor() {
        Price price = new Price("1000");
        assertEquals("1000", price.getPrice());
    }
}
