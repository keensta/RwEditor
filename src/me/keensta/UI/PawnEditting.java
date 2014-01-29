package me.keensta.UI;

import java.awt.Color;

import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;

import me.keensta.AppWindow;
import me.keensta.actionlisteners.pawn.CRaiderListener;
import me.keensta.actionlisteners.pawn.ColonistEditListener;
import me.keensta.actionlisteners.pawn.RRaidersListener;

public class PawnEditting {
    
    private AppWindow app;
    
    private WebLabel borderLabel = new WebLabel();
    
    private WebLabel pawnEdit = new WebLabel("PawnEdit");
    
    private WebButton RRButton = new WebButton("RemoveRaiders");
    private WebButton CRButton = new WebButton("ConvertRaiders");
    private WebButton CEButton = new WebButton("Open ColonistEditor");
    
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
        
        TooltipManager.addTooltip(RRButton, "Clears map of all raiders", TooltipWay.trailing);
        TooltipManager.addTooltip(CRButton, "Converts all raiders to colonists. This is unstable use at own risk", TooltipWay.left);
        TooltipManager.addTooltip(CEButton, "Not completed, minimal features implemented", TooltipWay.left);
        
        createBorder();
        app.add(pawnEdit);
        app.add(RRButton);
        app.add(CRButton);
        app.add(CEButton);
        
        pawnEdit.setBounds(260, 225, 80, 25);
        RRButton.setBounds(260, 250, 100, 25);
        CRButton.setBounds(360, 250, 100, 25);
        CEButton.setBounds(260, 275, 140, 25);
        
        RRButton.addActionListener(rrl);
        CRButton.addActionListener(crl);
        CEButton.addActionListener(cel);
    }

    @SuppressWarnings("rawtypes")
    private void createBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(new Color(221, 20, 20));
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        app.add(borderLabel);

        borderLabel.setBounds(255, 245, 210, 60);
    }
}
