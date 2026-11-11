package model;

import java.math.BigDecimal;

public class CorporateBusTicket extends BusTicket {
    private final int activeLicensesCount;

    public CorporateBusTicket(
            String name,
            double basePrice,
            String tariff,
            int activeLicensesCount) {

        super(name, basePrice, tariff);

        if (activeLicensesCount < 1) {
            System.out.println(
                    "[WARNING] Seat count must be at least 1. Using 1.");
            this.activeLicensesCount = 1;
        } else {
            this.activeLicensesCount = activeLicensesCount;
        }
    }

    public int getActiveLicensesCount() {
        return activeLicensesCount;
    }

    @Override
    public double calculateBillingPrice() {
        BigDecimal price = BigDecimal.valueOf(getBasePrice())
                .multiply(BigDecimal.valueOf(getTariffMultiplier()))
                .multiply(BigDecimal.valueOf(activeLicensesCount));

        return roundMoney(price);
    }
}