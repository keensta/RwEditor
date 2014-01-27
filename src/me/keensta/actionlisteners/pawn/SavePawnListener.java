package me.keensta.actionlisteners.pawn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.keensta.colonists.ColonistWindow;
import me.keensta.colonists.Pawn;

public class SavePawnListener implements ActionListener{
    
    private ColonistWindow cw;
    
    public SavePawnListener(ColonistWindow cw) {
        this.cw = cw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int i = cw.getColonistView().getList().getSelectedIndex();
        Pawn p = cw.getColonistView().getPawnList().get(i);
        
        cw.getColonistView().savePawn(p);
    }

}
