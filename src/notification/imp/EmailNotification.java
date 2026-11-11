
package notification.imp;

import notification.NotificationService;

public class EmailNotification implements NotificationService {

    @Override
    public void sendNotification(String message, String recipient) {
        if (recipient == null
                || !recipient.contains("@")
                || !recipient.contains(".")) {

            System.out.println(
                    "[WARNING] Invalid email address. Notification was not sent.");
            return;
        }

        System.out.println(
                "[EMAIL] На адрес " + recipient
                        + " отправлено письмо: " + message);
    }
}