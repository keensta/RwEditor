package me.keensta.actionlisteners.resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.alee.laf.combobox.WebComboBox;

import me.keensta.AppWindow;
import me.keensta.util.Notification;
import me.keensta.xmleditting.EditResources;

public class SetAllListener implements ActionListener{
    
    private AppWindow app;
    
    public SetAllListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {       
        int stackCount = app.getRes().getSetAllStackCount();
        String[] data = app.getRes().getData();
        
        if(data == null || app.getRes().getSetAllStackCount() == 0 || data.length == 0) {
            Notification.createWarningNotification("Select a stockpile first", 2000);
            return;
        }
        
        WebComboBox cb = app.getRes().getResourceList();
        cb.setSelectedIndex(0);
        
        EditResources er = app.getEditResources();
        List<String> pos = new ArrayList<String>();
        
        for(int i = 0; i < data.length; i++) {
            pos.add(data[i].split(":")[2]);
        }
        
        er.setAllCode(pos, Integer.toString(stackCount));
    }

}
