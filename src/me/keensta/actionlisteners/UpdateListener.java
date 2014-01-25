package me.keensta.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import me.keensta.AppWindow;
import me.keensta.xmleditting.EditResources;

@SuppressWarnings("unused")
public class UpdateListener implements ActionListener {

    private AppWindow app;
    String[] values = {"money", "food", "metal", "medicine", "uranium", "shells", "missiles"};
    String money = "0", food = "0", metal = "0", medicine = "0", uranium = "0", shells = "0", missiles = "0";

    public UpdateListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resetValues();
        
        EditResources er = app.getEditResources();
        setValues(app.getRes().getValues());
        er.activateCode(values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
    }
    
    private void setValues(int[] values) {
        for(int i = 0; i < this.values.length; i++) {
            this.values[i] = Integer.toString(values[i]);
        }
    }
    
    private void resetValues() {
        money = "0";
        food = "0";
        metal = "0";
        medicine = "0";
        uranium = "0";
        shells = "0";
        missiles = "0";
    }

}
