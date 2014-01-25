package me.keensta.actionlisteners.pawn;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.keensta.AppWindow;
import me.keensta.colonists.ColonistWindow;
import me.keensta.colonists.Pawn;

public class ListListener implements ListSelectionListener{
    
    private ColonistWindow cw;
    @SuppressWarnings("unused")
    private AppWindow app;
    
    public ListListener(ColonistWindow cw, AppWindow app) {
        this.cw = cw;
        this.app = app;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int i = cw.getColonistView().getList().getSelectedIndex();
        Pawn p = cw.getColonistView().getPawnList().get(i);
        
        cw.getColonistView().updateCompnents(p);
    }

}
