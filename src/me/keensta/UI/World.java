package me.keensta.UI;

import java.awt.Color;

import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.managers.tooltip.TooltipManager;
import me.keensta.AppWindow;
import me.keensta.actionlisteners.world.CCorpseListener;
import me.keensta.actionlisteners.world.DRubbishListener;
import me.keensta.actionlisteners.world.SGeyserListener;
import me.keensta.util.AppPosition;

public class World {
    
    private AppWindow app;
    
    private WebLabel borderLabel = new WebLabel();
    
    private WebLabel world = new WebLabel("WorldEdit");
    
    private WebButton CCButton = new WebButton("ClearCorpses");
    private WebButton SGButton = new WebButton("SpawnGeyser");
    private WebButton CRButton = new WebButton("ClearRubbish");
    
    private CCorpseListener ccl;
    private DRubbishListener drl;
    private SGeyserListener sgl;
    
    public World(AppWindow app) {
        this.app = app;
        
        ccl = new CCorpseListener(app);
        drl = new DRubbishListener(app);
        sgl = new SGeyserListener(app);
    }
    
    public void BuildComponents() {
        world.setDrawShade(true);
        world.setShadeColor(new Color(30, 231, 43));
        
        TooltipManager.addTooltip(CCButton, "Removes all Corpses, including blood.");
        TooltipManager.addTooltip(CRButton, "Removes all rubbish from the map, including slag and rock debris.");
        TooltipManager.addTooltip(SGButton, "Converts any stockpiles called SG to SteamGeysers");
        
        createBorder();
        app.add(world);
        app.add(CCButton);
        app.add(CRButton);
        app.add(SGButton);
        
        world.setBounds(AppPosition.WORLD_X, AppPosition.WORLD_Y, 80, 25);
        CCButton.setBounds(AppPosition.CLEARCORPSES_X, AppPosition.CLEARCORPSES_Y, 90, 25);
        CRButton.setBounds(AppPosition.CLEARRUBBISH_X, AppPosition.CLEARRUBBISH_Y, 90, 25);
        SGButton.setBounds(AppPosition.SPAWNGEYSER_X, AppPosition.SPAWNGEYSER_Y, 95, 25);
        
        CCButton.addActionListener(ccl);
        CRButton.addActionListener(drl);
        SGButton.addActionListener(sgl);
    }
    
    @SuppressWarnings("rawtypes")
    private void createBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(new Color(30, 231, 43));
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        app.add(borderLabel);

        borderLabel.setBounds(AppPosition.WBORDER_X, AppPosition.WBORDER_Y, 190, 60);
    }

}
