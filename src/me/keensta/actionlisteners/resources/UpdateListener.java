package me.keensta.actionlisteners.resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import me.keensta.AppWindow;
import me.keensta.util.Notification;
import me.keensta.xmleditting.EditResources;

@SuppressWarnings("unused")
public class UpdateListener implements ActionListener {

    private AppWindow app;

    public UpdateListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EditResources er = app.getEditResources();
        String[] data = app.getRes().getData();
        int index = app.getRes().getResSelectedIndex();
        
        if(index == 0 || app.getRes().getStackCount() == "" || data == null || data.length == 1) {
            Notification.createWarningNotification("Please select a resource first", 2000);
            return;
        } else {
            er.activateCode(data[index - 1].split(":")[2], app.getRes().getStackCount());
        }
    }

}
