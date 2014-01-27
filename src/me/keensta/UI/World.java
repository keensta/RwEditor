package me.keensta.UI;

import java.awt.Color;

import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;

import me.keensta.AppWindow;
import me.keensta.actionlisteners.world.CCorpseListener;
import me.keensta.actionlisteners.world.CWeaponsListener;
import me.keensta.actionlisteners.world.DRubbishListener;

public class World {
    
    private AppWindow app;
    
    private WebLabel borderLabel = new WebLabel();
    
    private WebLabel world = new WebLabel("WorldEdit");
    
    private WebButton CCButton = new WebButton("ClearCorpses");
    private WebButton CWButton = new WebButton("ClearWeapons");
    private WebButton CRButton = new WebButton("ClearRubbish");
    
    private CCorpseListener ccl;
    private CWeaponsListener cwl;
    private DRubbishListener drl;
    
    public World(AppWindow app) {
        this.app = app;
        
        ccl = new CCorpseListener(app);
        cwl = new CWeaponsListener(app);
        drl = new DRubbishListener(app);
    }
    
    public void BuildComponents() {
        world.setDrawShade(true);
        world.setShadeColor(new Color(30, 231, 43));
        
        TooltipManager.addTooltip(CCButton, "Removes all Corpses, Including blood.", TooltipWay.trailing);
        TooltipManager.addTooltip(CWButton, "Gets rid of all weapons not in a wepaon rack", TooltipWay.trailing);
        TooltipManager.addTooltip(CRButton, "Removes all rubbish from the map, include slag and rock debris.", TooltipWay.trailing);
        
        //Temp (Looking for Fix due to game update)
        CWButton.setEnabled(false);
        
        createBorder();
        app.add(world);
        app.add(CCButton);
        app.add(CWButton);
        app.add(CRButton);
        
        world.setBounds(40, 255, 80, 25);
        CCButton.setBounds(40, 280, 90, 25);
        CRButton.setBounds(130, 280, 90, 25);
        CWButton.setBounds(40, 305, 100, 25);
        
        CCButton.addActionListener(ccl);
        CRButton.addActionListener(drl);
        CWButton.addActionListener(cwl);
    }
    
    @SuppressWarnings("rawtypes")
    private void createBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(new Color(30, 231, 43));
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        app.add(borderLabel);

        borderLabel.setBounds(35, 275, 190, 60);
    }

}
