package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public abstract class BusTicket {
    private static final double MINIMUM_PRICE = 100.0;
    private static final double STANDARD_MULTIPLIER = 1.0;
    private static final double PANORAMIC_MULTIPLIER = 1.25;
    private static final double TABLE_MULTIPLIER = 1.40;
    private static final double OTHER_LANGUAGE_MULTIPLIER = 1.30;

    private final String name;
    private double basePrice;
    private String tariff;
    private AudioGuide appliedOption = null;

    public BusTicket(String name, double basePrice, String tariff) {
        if (name == null || name.isBlank()) {
            System.out.println(
                    "[WARNING] Empty ticket name. Using Unnamed tour.");
            this.name = "Unnamed tour";
        } else {
            this.name = name;
        }

        setBasePrice(basePrice);
        setTariff(tariff);
    }

    public BusTicket(String name, double basePrice) {
        this(name, basePrice, "Standard");
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getTariff() {
        return tariff;
    }

    public AudioGuide getAppliedOption() {
        return appliedOption;
    }

    public void setBasePrice(double basePrice) {
        if (!Double.isFinite(basePrice) || basePrice <= 0) {
            System.out.println(
                    "[WARNING] Invalid base price. Using 100.0 RUB.");
            this.basePrice = MINIMUM_PRICE;
        } else {
            this.basePrice = basePrice;
        }
    }

    public void setTariff(String tariff) {
        if (tariff == null || tariff.isBlank()) {
            System.out.println(
                    "[WARNING] Empty tariff. Using Standard.");
            this.tariff = "STANDARD";
        } else {
            this.tariff = tariff.strip().toUpperCase(Locale.ROOT);
        }
    }

    protected double getTariffMultiplier() {
        return switch (tariff) {
            case "STANDARD" -> STANDARD_MULTIPLIER;
            case "PANORAMIC" -> PANORAMIC_MULTIPLIER;
            case "TABLE" -> TABLE_MULTIPLIER;

            default -> {
                System.out.println(
                        "[WARNING] Unknown tariff: " + tariff
                                + ". Using the Standard multiplier.");
                yield STANDARD_MULTIPLIER;
            }
        };
    }

    // Each child class must provide its own implementation.
    public abstract double calculateBillingPrice();

    public double calculateFinalPrice() {
        return calculateBillingPrice();
    }

    public double calculateFinalPrice(AudioGuide guide) {
        return calculateFinalPrice(guide, "EN");
    }

    public double calculateFinalPrice(AudioGuide guide, String lang) {
        if (guide == null) {
            System.out.println(
                    "[WARNING] Missing audio guide. Returning ticket price.");
            return calculateBillingPrice();
        }

        if (lang == null || lang.isBlank()) {
            System.out.println(
                    "[WARNING] Empty language. Using EN.");
            lang = "EN";
        }

        double guideMultiplier;

        if (lang.strip().equalsIgnoreCase("EN")) {
            guideMultiplier = STANDARD_MULTIPLIER;
        } else {
            guideMultiplier = OTHER_LANGUAGE_MULTIPLIER;
        }

        BigDecimal guideCost = BigDecimal.valueOf(guide.getPrice())
                .multiply(BigDecimal.valueOf(guideMultiplier));

        BigDecimal total = BigDecimal.valueOf(calculateBillingPrice())
                .add(guideCost);

        appliedOption = guide;

        return roundMoney(total);
    }

    public double addGuide(AudioGuide guide) {
        return calculateFinalPrice(guide);
    }

    public double addGuide(AudioGuide guide, String lang) {
        return calculateFinalPrice(guide, lang);
    }

    public static double roundMoney(BigDecimal amount) {
        double result = amount
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException(
                    "Calculated price is too large.");
        }

        return result;
    }
}