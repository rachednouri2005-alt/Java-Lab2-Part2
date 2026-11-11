package app;

import model.AudioGuide;
import model.BusTicket;
import model.CorporateBusTicket;
import model.UserBusTicket;
import notification.imp.EmailNotification;
import notification.NotificationService;
import notification.imp.SmsNotification;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

public class TourAgency {
    private final NotificationService notificationService;

    private static int totalOrdersCount;
    private static double totalRevenue;

    // Receive the notification service from outside.
    public TourAgency(NotificationService notificationService) {
        this.notificationService = Objects.requireNonNull(
                notificationService,
                "Notification service must not be null.");
    }

    public static int getTotalOrdersCount() {
        return totalOrdersCount;
    }

    public static double getTotalRevenue() {
        return totalRevenue;
    }

    public void processPayment(
            BusTicket ticket, String contact, double finalPrice) {

        if (ticket == null) {
            System.out.println(
                    "[WARNING] Ticket is missing. Payment was not recorded.");
            return;
        }

        if (!Double.isFinite(finalPrice) || finalPrice <= 0) {
            System.out.println(
                    "[WARNING] Invalid payment amount. Payment was not recorded.");
            return;
        }

        double payment = BusTicket.roundMoney(
                BigDecimal.valueOf(finalPrice));

        if (payment <= 0) {
            System.out.println(
                    "[WARNING] Payment rounds to zero. Payment was not recorded.");
            return;
        }

        double updatedRevenue = BusTicket.roundMoney(
                BigDecimal.valueOf(totalRevenue)
                        .add(BigDecimal.valueOf(payment)));

        if (totalOrdersCount == Integer.MAX_VALUE) {
            System.out.println(
                    "[WARNING] Order limit reached. Payment was not recorded.");
            return;
        }

        totalRevenue = updatedRevenue;
        totalOrdersCount++;

        String message = String.format(
                Locale.ROOT,
                "Оплата тура \"%s\" принята. Сумма: %.2f RUB.",
                ticket.getName(),
                payment);

        notificationService.sendNotification(message, contact);
    }

    public static void printGlobalStats() {
        System.out.println("\n--- Tour agency statistics ---");
        System.out.println("Paid orders: " + getTotalOrdersCount());

        System.out.printf(
                Locale.ROOT,
                "Total revenue: %.2f RUB%n",
                getTotalRevenue());
    }

}