package me.keensta.actionlisteners.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.AppWindow;
import me.keensta.UI.AboutView;

public class AboutListener implements ActionListener {

    private AppWindow app;

    public AboutListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AboutView.loadAboutPage(app);
    }

}
