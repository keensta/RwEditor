package me.keensta.actionlisteners.world;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.AppWindow;
import me.keensta.xmleditting.DeleteRubbish;

public class DRubbishListener implements ActionListener {

    private AppWindow app;

    public DRubbishListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DeleteRubbish dr = app.getDeleteRubbish();
        dr.activateCode();
    }

}
