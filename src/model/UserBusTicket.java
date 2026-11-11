package model;

import java.math.BigDecimal;

public class UserBusTicket extends BusTicket {

    public UserBusTicket(
            String name, double basePrice, String tariff) {
        super(name, basePrice, tariff);
    }

    public UserBusTicket(String name, double basePrice) {
        this(name, basePrice, "Standard");
    }

    @Override
    public double calculateBillingPrice() {
        BigDecimal price = BigDecimal.valueOf(getBasePrice())
                .multiply(BigDecimal.valueOf(getTariffMultiplier()));

        return roundMoney(price);
    }
}