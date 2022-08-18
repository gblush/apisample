package dev.blush.apisample.rates;


import org.springframework.data.annotation.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

public class Rate {


    @NotNull(message = "days is required")
    @Pattern(regexp="^[a-zA-Z,]+$", message = "days must be a string")
    private String days;

    @NotNull(message = "times is required")
    @Pattern(regexp="^[a-zA-Z-a0-9-zA-Z0-9]+$", message = "times must be a string")
    @Size(min = 9, max = 9, message = "Times must be exactly 9 characters (i.e. 0900-1500)")
    private String times;

    @NotNull(message = "timezone is required")
    @Pattern(regexp="^[a-zA-Z/a-zA-Z]+$", message = "timezone must be a string")
    private String tz;

    @NotNull(message = "price is required")
    @Positive(message = "price must be a positive number")
    private Long price;

    private final Long id;

    public Rate() {
        this.id = new Date().getTime();
        this.days = "";
        this.times = "";
        this.tz = "";
        this.price = 0l;
    }

    public Rate(Long id, String days, String times, String tz, Long price) {
        this.id = id;
        this.days = days;
        this.times = times;
        this.tz = tz;
        this.price = price;
    }

    public String getDays() {
        return days;
    }

    public String getTimes() {
        return times;
    }

    public String getTz() {
        return tz;
    }

    public Long getPrice() {
        return price;
    }

    @Id
    public Long getId() {
        return id;
    }


}
