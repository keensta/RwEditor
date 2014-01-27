package me.keensta.actionlisteners.resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.laf.combobox.WebComboBox;

import me.keensta.AppWindow;

public class SSelectionListener implements ActionListener{
    
    private AppWindow app;

    public SSelectionListener(AppWindow app) {
        this.app = app;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        WebComboBox cb = (WebComboBox) e.getSource();
        String zoneName = (String) cb.getSelectedItem();
        
        app.getRes().setResourcesList(zoneName);
        app.getRes().setStackCount("");
    }

}
