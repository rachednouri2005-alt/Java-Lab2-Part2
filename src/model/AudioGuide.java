package model;

public class AudioGuide {
    private final String name;
    private double price;

    public AudioGuide(String name, double price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Guide name must not be blank.");
        }

        this.name = name;
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (!Double.isFinite(price) || price < 0) {
            throw new IllegalArgumentException(
                    "Guide price must be finite and non-negative.");
        }

        this.price = price;
    }
}