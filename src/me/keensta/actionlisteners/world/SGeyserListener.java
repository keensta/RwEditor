package me.keensta.actionlisteners.world;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.AppWindow;
import me.keensta.xmleditting.SpawnGeyser;

public class SGeyserListener implements ActionListener{
    
    private AppWindow app;
    
    public SGeyserListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SpawnGeyser sg = app.getSpawnGeyser();
        sg.activateCode();
    }
    
}
