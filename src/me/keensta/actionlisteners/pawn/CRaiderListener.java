package me.keensta.actionlisteners.pawn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.AppWindow;
import me.keensta.xmleditting.ConvertRaiders;

public class CRaiderListener implements ActionListener {

    private AppWindow app;

    public CRaiderListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ConvertRaiders cr = app.getConvertRaiders();
        cr.activateCode(100);
    }

}
