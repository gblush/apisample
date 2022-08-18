package dev.blush.apisample.price;

import dev.blush.apisample.rates.RatesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/price")
public class PriceController {
    private final RatesService ratesService;

    Logger log = LogManager.getLogger(PriceController.class);

    public PriceController(RatesService ratesService) {this.ratesService = ratesService;}

    @GetMapping()
    public ResponseEntity<Price> computePrice(@RequestParam final String start, @RequestParam final String end) {
        ZonedDateTime startTime = null;
        ZonedDateTime endTime = null;
        try {
            startTime = ZonedDateTime.parse(start);
            endTime = ZonedDateTime.parse(end);
        } catch (DateTimeParseException e) {
            log.error(e.getLocalizedMessage()+ ", please ensure all request parameters have been HTML encoded.");
        }
        if (null != startTime && null != endTime) {
            return ResponseEntity.ok().body(ratesService.getPriceForTime(startTime, endTime));
        }
        return ResponseEntity.ok().body(new Price());
    }


}
