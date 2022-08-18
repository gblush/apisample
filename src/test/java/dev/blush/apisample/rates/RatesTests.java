package dev.blush.apisample.rates;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RatesTests {

    @Test
    public void test_empty_rates_constructor() {
        Rates rates = new Rates();
        assertEquals(new ArrayList<>(), rates.getRates());
    }

    @Test
    public void test_primary_constructor() {
        Rates rates = new Rates(List.of(
                new Rate()
        ));

        assertEquals(1, rates.getRates().size());
    }
}
