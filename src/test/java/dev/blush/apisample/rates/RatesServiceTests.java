package dev.blush.apisample.rates;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@SpringBootTest
public class RatesServiceTests {

    Logger log = LogManager.getLogger(RatesServiceTests.class);

    @Autowired
    private RatesService ratesService;

    private static final String TEST_DAYS = "mon,tues,wed,thurs";
    private static final String TEST_TIMES = "0500-1300";
    private static final String TEST_TZ = "America/Chicago";
    private static final Long TEST_PRICE = 1500l;

    private List<Rate> expectedRates;

    @BeforeEach
    void init() {
        assertThat(ratesService).isNotNull();
        expectedRates = List.of(
                new Rate(1l, TEST_DAYS, TEST_TIMES, TEST_TZ, TEST_PRICE)
        );
        Rates defaultRates = new Rates(expectedRates);
        ratesService.create(defaultRates);
    }

    @Test
    void testGetPriceForTime() {
        ZonedDateTime startTime = ZonedDateTime.parse("2015-07-01T07:00:00-05:00");
        ZonedDateTime endTime = ZonedDateTime.parse("2015-07-01T12:00:00-05:00");
        assertThat(ratesService.getPriceForTime(startTime, endTime).getPrice())
                .isEqualTo(expectedRates.get(0).getPrice().toString());
    }

    @Test
    void test_getOverlappingTime() {
        //1, 5; 3, 7
        assertTrue(ratesService.getOverlappingTime(1, 3, 5, 7));
        //1, 5; 7, 9
        assertFalse(ratesService.getOverlappingTime(1, 7, 5, 9));
        //1, 3; 1, 5
        assertTrue(ratesService.getOverlappingTime(1, 1, 3, 5));
        //3, 5, 1, 5
        assertTrue(ratesService.getOverlappingTime(3, 1, 5, 5));
        //1 , 5; 1, 5
        assertTrue(ratesService.getOverlappingTime(1, 1, 5,5 ));
        //1, 5, 5, 10
        assertFalse(ratesService.getOverlappingTime(1, 5, 5, 10));
    }

    @Test
    void test_getOverlappingRange() {
        Reservation test1First = new Reservation(1, 3);
        Reservation test1Second = new Reservation(5, 7);
        Reservation test1Third = new Reservation(2,4);
        Reservation test1Fourth = new Reservation(6,8);
        ArrayList<Reservation> test1 = new ArrayList<>();
        test1.add(test1First);
        test1.add(test1Second);
        test1.add(test1Third);
        test1.add(test1Fourth);

        assertTrue(ratesService.getOverLappingRange(test1));

        Reservation test2First = new Reservation(1, 3);
        Reservation test2Second = new Reservation(7, 9);
        Reservation test2Third = new Reservation(4,6);
        Reservation test2Fourth = new Reservation(10,13);
        ArrayList<Reservation> test2 = new ArrayList<>();
        test2.add(test2First);
        test2.add(test2Second);
        test2.add(test2Third);
        test2.add(test2Fourth);

        assertFalse(ratesService.getOverLappingRange(test2));
    }
}
