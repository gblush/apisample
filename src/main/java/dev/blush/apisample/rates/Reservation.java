package dev.blush.apisample.rates;

public class Reservation {
    private Integer startTime;
    private Integer endTime;

    public Reservation(Integer startTime, Integer endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
}
