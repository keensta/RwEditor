package me.keensta.actionlisteners.world;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.AppWindow;
import me.keensta.xmleditting.ClearCorpses;

public class CCorpseListener implements ActionListener {

    private AppWindow app;

    public CCorpseListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClearCorpses cc = app.getClearCorpses();
        cc.activateCode();
    }

}
