package dev.blush.apisample.rates;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Rates {

    private Random rand = new Random(new Date().getTime());

    @NotNull(message = "list of rates is required")
    private List<Rate> rates;

    public Rates() {
        this.rates = new ArrayList<>();
    }

    public Rates(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Rate> getRates() {
        return rates;
    }

}
