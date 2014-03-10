package me.keensta.actionlisteners.pawn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.colonists.ColonistWindow;
import me.keensta.colonists.Pawn;
import me.keensta.util.Notification;

public class HealAllListener implements ActionListener{
    
    private ColonistWindow cw;
    
    public HealAllListener(ColonistWindow cw) {
        this.cw = cw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        for(Pawn p : cw.getColonistView().getPawnList()) {
            p.maxPawnHealth();
        }
        
        Notification.createInfoNotification("All colonists have been healed", 5000);
    }
}
