import app.TourAgency;
import model.AudioGuide;
import model.BusTicket;
import model.CorporateBusTicket;
import model.UserBusTicket;
import notification.imp.EmailNotification;
import notification.imp.SmsNotification;

public static void main(String[] args) {
    AudioGuide guide = new AudioGuide(
            "City audio guide", 200.0);

    // Individual customer: SMS notification.
    TourAgency smsAgency = new TourAgency(
            new SmsNotification());

    BusTicket userTicket = new UserBusTicket(
            "Individual city tour", 1000.0, "Panoramic");

    double userPrice = userTicket.addGuide(guide, "FR");

    System.out.printf(
            Locale.ROOT,
            "Individual ticket + FR guide: %.2f RUB%n",
            userPrice);

    smsAgency.processPayment(
            userTicket, "+79991234567", userPrice);

    // Corporate customer: email notification.
    TourAgency emailAgency = new TourAgency(
            new EmailNotification());

    BusTicket corporateTicket = new CorporateBusTicket(
            "Corporate city tour", 1000.0, "Panoramic", 5);

    double corporatePrice =
            corporateTicket.calculateBillingPrice();

    System.out.printf(
            Locale.ROOT,
            "%nCorporate booking for 5 seats: %.2f RUB%n",
            corporatePrice);

    emailAgency.processPayment(
            corporateTicket, "company@example.com", corporatePrice);

}