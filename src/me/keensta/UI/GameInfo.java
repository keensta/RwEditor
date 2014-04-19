package me.keensta.UI;

import java.awt.Color;

import me.keensta.AppWindow;
import me.keensta.util.AppPosition;

import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;

public class GameInfo {

    private AppWindow app;

    private WebLabel gameInfo = new WebLabel("GameInfo");
    private WebLabel version = new WebLabel("FileVersion ");
    private WebLabel colonyName = new WebLabel("ColonyName ");
    
    private WebLabel borderLabel = new WebLabel();
    
    private WebTextField cn;
    private WebTextField sv;

    public GameInfo(AppWindow app) {
        this.app = app;
    }

    public void BuildComponents() {

        cn = new WebTextField(app.getDataHandler().getDataString("colonyInfo/colonyName", app.getFile().getName()));
        sv = new WebTextField(app.getDataHandler().getDataString("gameVersion", app.getFile().getName()));

        gameInfo.setDrawShade(true);
        gameInfo.setShadeColor(new Color(140, 30, 185));
        
        sv.setEditable(false);
        
        createBorder();
        app.add(gameInfo);
        app.add(version);
        app.add(colonyName);
        app.add(cn);
        app.add(sv);

        gameInfo.setBounds(AppPosition.GAMEINFO_X, AppPosition.GAMEINFO_Y, 75, 25);
        version.setBounds(AppPosition.VERSION_X, AppPosition.VERSION_Y, 75, 25);
        sv.setBounds(AppPosition.SV_X, AppPosition.SV_Y, 175, 25);
        colonyName.setBounds(AppPosition.COLONYNAME_X, AppPosition.COLONYNAME_Y, 80, 25);
        cn.setBounds(AppPosition.CN_X, AppPosition.CN_Y, 175, 25);
        
        app.revalidate();
    }
    
    public void updateComponents() {
        cn.setText(app.getDataHandler().getDataString("ColonyInfo/ColonyName", app.getFile().getName()));
        sv.setText(app.getDataHandler().getDataString("GameVersion", app.getFile().getName()));
        
        app.revalidate();
    }
    
    @SuppressWarnings("rawtypes")
    private void createBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(new Color(140, 30, 185));
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        app.add(borderLabel);

        borderLabel.setBounds(AppPosition.GIBORDER_X, AppPosition.GIBORDER_Y, 520, 35);
    }
    
    public WebTextField getColonyName() {
        return cn;
    }

}
