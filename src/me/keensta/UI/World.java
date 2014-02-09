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

public class World {
    
    private AppWindow app;
    
    private WebLabel borderLabel = new WebLabel();
    
    private WebLabel world = new WebLabel("WorldEdit");
    
    private WebButton CCButton = new WebButton("ClearCorpses");
    //private WebButton CWButton = new WebButton("ClearWeapons");
    private WebButton SGButton = new WebButton("SpawnGeyser");
    private WebButton CRButton = new WebButton("ClearRubbish");
    
    private CCorpseListener ccl;
    //private CWeaponsListener cwl;
    private DRubbishListener drl;
    private SGeyserListener sgl;
    
    public World(AppWindow app) {
        this.app = app;
        
        ccl = new CCorpseListener(app);
        //cwl = new CWeaponsListener(app);
        drl = new DRubbishListener(app);
        sgl = new SGeyserListener(app);
    }
    
    public void BuildComponents() {
        world.setDrawShade(true);
        world.setShadeColor(new Color(30, 231, 43));
        
        TooltipManager.addTooltip(CCButton, "Removes all Corpses, Including blood.");
        TooltipManager.addTooltip(CRButton, "Removes all rubbish from the map, include slag and rock debris.");
        //TooltipManager.addTooltip(CWButton, "Gets rid of all weapons not in a wepaon rack", TooltipWay.trailing);
        TooltipManager.addTooltip(SGButton, "Converts any stockpiles called sg to SteamGeysers");
        
        //Temp (Looking for Fix due to game update)
        //CWButton.setEnabled(false);
        
        createBorder();
        app.add(world);
        app.add(CCButton);
        app.add(CRButton);
        //app.add(CWButton);
        app.add(SGButton);
        
        world.setBounds(40, 225, 80, 25);
        CCButton.setBounds(40, 250, 90, 25);
        CRButton.setBounds(130, 250, 90, 25);
        //CWButton.setBounds(40, 275, 100, 25);
        SGButton.setBounds(40, 275, 100, 25);
        
        CCButton.addActionListener(ccl);
        CRButton.addActionListener(drl);
       // CWButton.addActionListener(cwl);
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

        borderLabel.setBounds(35, 245, 190, 60);
    }

}
