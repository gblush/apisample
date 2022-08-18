package dev.blush.apisample.price;

public class Price {
    private String price;

    public Price() {
        price = "unavailable";
    }

    public Price(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public Price updateWith(Price price) {
        return new Price(
                price.price
        );
    }
}
