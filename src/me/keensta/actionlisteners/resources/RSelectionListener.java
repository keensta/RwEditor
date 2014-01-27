package me.keensta.actionlisteners.resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.laf.combobox.WebComboBox;

import me.keensta.AppWindow;

public class RSelectionListener implements ActionListener{
    
    private AppWindow app;
    
    public RSelectionListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WebComboBox cb = (WebComboBox) e.getSource();
        int index = (int) (cb.getSelectedIndex());
        String[] data = app.getRes().getData();
       
        if(index <= 0 || data.length == 1)
            return;
        
        app.getRes().setStackCount(data[index - 1].split(":")[1]);
    }

}
