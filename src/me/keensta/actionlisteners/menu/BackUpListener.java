package me.keensta.actionlisteners.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.AppWindow;
import me.keensta.util.Notification;

public class BackUpListener implements ActionListener {

    private AppWindow app;

    public BackUpListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        app.getBackupHandler().createBackup();
        
        Notification.createInfoNotification(app.getBackupHandler().getBackupFile().getName() + " has been backed up.", 5000);
    }

}
