package me.keensta.actionlisteners.pawn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.AppWindow;
import me.keensta.xmleditting.DeleteRaiders;

public class RRaidersListener implements ActionListener {

    private AppWindow app;

    public RRaidersListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DeleteRaiders dr = app.getDeleteRaiders();
        dr.activateCode();
    }

}
