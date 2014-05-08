package me.keensta.UI;

import java.awt.Color;

import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.managers.tooltip.TooltipManager;
import me.keensta.AppWindow;
import me.keensta.actionlisteners.pawn.CRaiderListener;
import me.keensta.actionlisteners.pawn.ColonistEditListener;
import me.keensta.actionlisteners.pawn.RRaidersListener;
import me.keensta.util.AppPosition;

public class PawnEditting {
    
    private AppWindow app;
    
    private WebLabel borderLabel = new WebLabel();
    
    private WebLabel pawnEdit = new WebLabel("PawnEdit");
    
    private WebButton RRButton = new WebButton("RemoveRaiders");
    private WebButton CRButton = new WebButton("ConvertRaiders");
    private WebButton CEButton = new WebButton("ColonistEditor");
    
    private RRaidersListener rrl;
    private CRaiderListener crl;
    private ColonistEditListener cel;
    
    public PawnEditting(AppWindow app) {
        this.app = app;
        
        rrl = new RRaidersListener(app);
        crl = new CRaiderListener(app);
        cel = new ColonistEditListener(app);
    }
    
    public void BuildComponents() {
        pawnEdit.setDrawShade(true);
        pawnEdit.setShadeColor(new Color(221, 20, 20));
        
        TooltipManager.addTooltip(RRButton, "Clears map of all raiders");
        TooltipManager.addTooltip(CRButton, "Converts all raiders to colonists. This is unstable use at own risk");
        TooltipManager.addTooltip(CEButton, "Edit certain colonist attributes");
        
        createBorder();
        app.add(pawnEdit);
        app.add(RRButton);
        app.add(CRButton);
        app.add(CEButton);
        
        pawnEdit.setBounds(AppPosition.PAWNEDIT_X, AppPosition.PAWNEDIT_Y, 80, 25);
        RRButton.setBounds(AppPosition.REMOVERAIDERS_X, AppPosition.REMOVERAIDERS_Y, 100, 25);
        CRButton.setBounds(AppPosition.CONVERTRAIDERS_X, AppPosition.CONVERTRAIDERS_Y, 100, 25);
        CEButton.setBounds(AppPosition.COLONISTEDIT_X, AppPosition.COLONISTEDIT_Y, 100, 25);
        
        RRButton.addActionListener(rrl);
        CRButton.addActionListener(crl);
        CEButton.addActionListener(cel);
    
        CRButton.setEnabled(false);
    }

    @SuppressWarnings("rawtypes")
    private void createBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(new Color(221, 20, 20));
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        app.add(borderLabel);

        borderLabel.setBounds(AppPosition.PEBORDER_X, AppPosition.PEBORDER_Y, 210, 60);
    }
}
