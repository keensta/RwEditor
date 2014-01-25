package me.keensta.actionlisteners.pawn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.AppWindow;
import me.keensta.colonists.ColonistWindow;

public class ColonistEditListener implements ActionListener {

    private AppWindow app;

    public ColonistEditListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ColonistWindow.main(app);
    }

}
