package me.keensta.actionlisteners.world;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.AppWindow;
import me.keensta.xmleditting.ClearWeapons;

public class CWeaponsListener implements ActionListener {

    private AppWindow app;

    public CWeaponsListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClearWeapons cw = app.getClearWeapons();
        cw.activeCode();
    }

}
