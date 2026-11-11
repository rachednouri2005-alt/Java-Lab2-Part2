package notification.imp;

import notification.NotificationService;

public class SmsNotification implements NotificationService {

    @Override
    public void sendNotification(String message, String recipient) {
        if (recipient == null) {
            System.out.println(
                    "[WARNING] Phone number is missing. SMS was not sent.");
            return;
        }

        if (!(recipient.startsWith("+7") || recipient.startsWith("8"))
                || recipient.length() < 11) {
            System.out.println(
                    "[WARNING] Invalid phone number. SMS was not sent.");
            return;
        }

        System.out.println(
                "[SMS] На номер " + recipient
                        + " отправлено сообщение: " + message);
    }
}