package design_problems.push_notification_system.channel;

import design_problems.social_media_notification_system.domain.Notification;
import design_problems.social_media_notification_system.domain.User;

public class AndroidPushNotificationChannel implements NotificationChannel {
    @Override
    public void sendNotification(User user, Notification notification) {
        System.out.println("Sent notification to " + user + " via android channel.");
    }
}
