package dev.blush.apisample.rates;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Random;

@SpringBootTest
public class RateTests {

    @Test
    public void test_rate_empty_constructor() {
        Rate rate = new Rate();
        assertEquals("",rate.getDays());
        assertEquals("",rate.getTimes());
        assertEquals("",rate.getTz());
    }

    @Test
    public void test_rate_parameterized_constructor() {
        Random rand = new Random(new Date().getTime());
        Long id = Math.abs(rand.nextLong());
        String times = "0900-1300";
        String days = "mon,tue,thurs";
        String tz = "America/Chicago";
        Long price = 1000l;

        Rate rate = new Rate(id, days, times, tz, price);

        assertEquals(id, rate.getId());
        assertEquals(days, rate.getDays());
        assertEquals(times, rate.getTimes());
        assertEquals(tz, rate.getTz());
        assertEquals(price, rate.getPrice());
    }

}
