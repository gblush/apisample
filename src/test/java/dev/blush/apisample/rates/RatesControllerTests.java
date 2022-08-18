package dev.blush.apisample.rates;

import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class RatesControllerTests {
    @Autowired
    private RateController rateController;

    @Autowired
    private RatesService ratesService;

    @Test
    public void test_controller_is_instantiated() throws Exception {
        assertThat(rateController).isNotNull();
    }

    @Test
    public void test_create_list_against_service() throws Exception {
        assertThat(ratesService).isNotNull();
        Random rand = new Random(new Date().getTime());
        ResponseEntity<Rates> rates = rateController.createList(new Rates(List.of(
                new Rate(Math.abs(rand.nextLong()),"mon","1000-1600","America/Chicago", 1000l)
        )));
        Rates serviceRates = ratesService.create(new Rates(List.of(
                new Rate(Math.abs(rand.nextLong()),"mon","1000-1600","America/Chicago", 1000l)
        )));
        assertThat(rates.getBody().getRates().get(0).getPrice())
                .isEqualTo(serviceRates.getRates().get(0).getPrice());
        assertThat(rates.getBody().getRates().get(0).getDays())
                .isEqualTo(serviceRates.getRates().get(0).getDays());
        assertThat(rates.getBody().getRates().get(0).getTimes())
                .isEqualTo(serviceRates.getRates().get(0).getTimes());
        assertThat(rates.getBody().getRates().get(0).getTz())
                .isEqualTo(serviceRates.getRates().get(0).getTz());
    }

}
