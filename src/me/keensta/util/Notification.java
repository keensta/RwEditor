package me.keensta.util;

import com.alee.managers.notification.NotificationIcon;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotificationPopup;
import com.alee.managers.popup.PopupStyle;

public class Notification {

    public static void createTipNotification(String message, int time) {
        WebNotificationPopup notificationPopup = new WebNotificationPopup(PopupStyle.gray);
        notificationPopup.setIcon(NotificationIcon.tip);
        notificationPopup.setDisplayTime(time);

        notificationPopup.setContent(message);
        NotificationManager.showNotification(notificationPopup);
    }

    public static void createInfoNotification(String message, int time) {
        WebNotificationPopup notificationPopup = new WebNotificationPopup(PopupStyle.light);
        notificationPopup.setIcon(NotificationIcon.information);
        notificationPopup.setDisplayTime(time);

        notificationPopup.setContent(message);
        NotificationManager.showNotification(notificationPopup);
    }

    public static void createWarningNotification(String message, int time) {
        WebNotificationPopup notificationPopup = new WebNotificationPopup(PopupStyle.light);
        notificationPopup.setIcon(NotificationIcon.warning);
        notificationPopup.setDisplayTime(time);
        notificationPopup.setContent(message);
        NotificationManager.showNotification(notificationPopup);
    }

}
