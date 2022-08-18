package dev.blush.apisample.price;

import dev.blush.apisample.rates.RatesService;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;


@SpringBootTest
public class PriceControllerTests {

    @Autowired
    private PriceController priceController;

    @Autowired
    private RatesService ratesService;

    @Test
    public void test_controller_is_instantiated() throws Exception {
        assertThat(priceController).isNotNull();
    }

    @Test
    public void test_compare_price_against_service_layer() throws Exception {
        ZonedDateTime start = ZonedDateTime.parse("2015-07-01T07:00:00-05:00");
        ZonedDateTime end = ZonedDateTime.parse("2015-07-01T12:00:00-05:00");

        assertThat(ratesService).isNotNull();

        assertThat(priceController
                .computePrice("2015-07-01T07:00:00-05:00", "2015-07-01T12:00:00-05:00").getBody().getPrice())
                .isEqualTo(ratesService.getPriceForTime(start, end).getPrice());
    }
}
