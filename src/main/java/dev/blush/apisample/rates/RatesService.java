package dev.blush.apisample.rates;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.blush.apisample.price.Price;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Service
@EnableMapRepositories
public class RatesService {
    private final CrudRepository<Rate, Long> repository;

    Logger log = LogManager.getLogger(RatesService.class);

    public RatesService(CrudRepository<Rate, Long> repository) {
        this.repository = repository;
        Rates defaultRates = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File file = ResourceUtils.getFile("classpath:static\\rates.json");
            defaultRates = objectMapper.readValue(file, Rates.class);
        } catch (IOException e) {
            log.error("Failed to properly load rates.json file, please load manually. " + e.getLocalizedMessage());
        }

        if (null != defaultRates) {
            this.create(defaultRates);
        }
    }

    public List<Rate> findAll() {
        List<Rate> list = new ArrayList<>();
        Iterable<Rate> rates = repository.findAll();
        rates.forEach(list::add);
        return list;
    }

    public Optional<Rate> find(Long id) {
        return repository.findById(id);
    }

    public Rates create(Rates rates) {
        Random rand = new Random(new Date().getTime());
        List<Rate> addedRates = new ArrayList<>();
        repository.deleteAll();
        for (Rate r : rates.getRates()) {
            Rate copy = new Rate(
                   Math.abs(rand.nextLong()),
                    r.getDays(),
                    r.getTimes(),
                    r.getTz(),
                    r.getPrice()
            );
            addedRates.add(repository.save(copy));
        }
        return new Rates(addedRates);
    }

    public Price getPriceForTime(ZonedDateTime startTime, ZonedDateTime endTime) {
        List<Rate> currentRates = this.findAll();
        ZonedDateTime rateStart;
        ZonedDateTime rateEnd;

        for (Rate r : currentRates) {
            if (r.getDays().toLowerCase().contains(startTime.getDayOfWeek().toString().substring(0, 3).toLowerCase())) {
                rateStart = ZonedDateTime.of(
                        startTime.getYear(),
                        startTime.getMonthValue(),
                        startTime.getDayOfMonth(),
                        Integer.valueOf(r.getTimes().substring(0,2)).intValue(),
                        Integer.valueOf(r.getTimes().substring(2,4)).intValue(),
                        0,
                        0,
                        ZoneId.of(r.getTz()));

                rateEnd = ZonedDateTime.of(
                        endTime.getYear(),
                        endTime.getMonthValue(),
                        endTime.getDayOfMonth(),
                        Integer.valueOf(r.getTimes().substring(5,7)).intValue(),
                        Integer.valueOf(r.getTimes().substring(7,9)).intValue(),
                        0,
                        0,
                        ZoneId.of(r.getTz()));

                if (startTime.withZoneSameInstant(ZoneId.of(r.getTz())).isAfter(rateStart) &&
                    endTime.withZoneSameInstant(ZoneId.of(r.getTz())).isBefore(rateEnd)) {
                    return new Price(r.getPrice().toString());
                }
            }
        }
        return new Price();
    }

    public boolean getOverlappingTime(Integer start1, Integer start2, Integer end1, Integer end2) {
        if ((start2 >= start1 && start2 < end1) ||
                (start1 >= start2 && start1 < end2)
        ) {
            return true;
        }
        return false;
    }

    public boolean getOverLappingRange(List<Reservation> reservations) {
        for(int i = 0; i < reservations.size(); i++ ) {
            for (int j = 0; j < reservations.size(); j++ ) {
                if ((getOverlappingTime(reservations.get(i).getStartTime(),
                        reservations.get(j).getStartTime(),
                        reservations.get(i).getEndTime(),
                        reservations.get(j).getEndTime())) &&
                        (i != j)
                ) {
                    return true;
                }
            }
        }
        return false;
    }
}
