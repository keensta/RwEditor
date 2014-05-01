package me.keensta.UI;

import java.awt.Color;
import java.awt.Rectangle;

import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;

import me.keensta.AppWindow;
import me.keensta.actionlisteners.resources.RSelectionListener;
import me.keensta.actionlisteners.resources.SSelectionListener;
import me.keensta.actionlisteners.resources.SetAllListener;
import me.keensta.actionlisteners.resources.UpdateListener;
import me.keensta.util.AppPosition;

public class Resources {

    private AppWindow app;

    private WebLabel borderLabel = new WebLabel();

    private WebLabel resourcesTitle = new WebLabel("Resources");
    private WebLabel stockpiles = new WebLabel("Stockpiles");
    private WebComboBox stockpileList;
    private WebLabel resources = new WebLabel("Resources");
    private WebComboBox resourcesList;
    private WebLabel stackCount = new WebLabel("StackCount");
    private WebTextField fieldStackCount = new WebTextField();
    private WebButton setAll = new WebButton("SetAll");
    private WebSpinner setAllSpinner = new WebSpinner();

    private WebButton update = new WebButton("Update");
    
    private UpdateListener ul;
    private SSelectionListener sl;
    private RSelectionListener rl;
    private SetAllListener asl;
    
    private String[] data;

    public Resources(AppWindow app) {
        this.app = app;

        ul = new UpdateListener(app);
        sl = new SSelectionListener(app);
        rl = new RSelectionListener(app);
        asl = new SetAllListener(app);
    }

    public void BuildComponents() {
        resourcesTitle.setDrawShade(true);
        resourcesTitle.setShadeColor(Color.BLUE);
        stockpileList = new WebComboBox(app.getEditResources().getStockpileList().toArray());
        resourcesList = new WebComboBox();

        createBorder();
        app.add(resourcesTitle);
        app.add(stockpiles);
        app.add(stockpileList);
        app.add(resources);
        app.add(resourcesList);
        app.add(stackCount);
        app.add(fieldStackCount);
        app.add(setAllSpinner);
        app.add(setAll);
        app.add(update);
        
        resourcesTitle.setBounds(AppPosition.RESOURCETITLE_X, AppPosition.RESOURCETITLE_Y, 80, 25);
        stockpiles.setBounds(AppPosition.STOCKPILES_X, AppPosition.STOCKPILES_Y, 80, 25);
        stockpileList.setBounds(AppPosition.STOCKPILELIST_X, AppPosition.STOCKPILELIST_Y, 130, 25);
        resources.setBounds(AppPosition.RESOURCES_X, AppPosition.RESOURCES_Y, 80, 25);
        resourcesList.setBounds(AppPosition.RESOURCESLIST_X, AppPosition.RESOURCESLIST_Y, 130, 25);
        stackCount.setBounds(AppPosition.STACKCOUNT_X, AppPosition.STACKCOUNT_Y, 80, 25);
        fieldStackCount.setBounds(AppPosition.FIELDSTACKCOUNT_X, AppPosition.FIELDSTACKCOUNT_Y, 80, 25);
        setAllSpinner.setBounds(AppPosition.SETALLSPINNER_X, AppPosition.SETALLSPINNER_Y, 75, 25);
        setAll.setBounds(AppPosition.SETALL_X, AppPosition.SETALL_Y, 55, 25);
        update.setBounds(AppPosition.UPDATE_X, AppPosition.UPDATE_Y, 80, 25);
        
        update.addActionListener(ul);
        stockpileList.addActionListener(sl);
        resourcesList.addActionListener(rl);
        setAll.addActionListener(asl);
    }

    public void updateComponents() {
        //This is the only current fix I have for not loading new stockpilelist..
        //If anyone has better please tell..
        Rectangle r = stockpileList.getBounds();
        app.remove(stockpileList);
        stockpileList = new WebComboBox(app.getEditResources().getStockpileList().toArray());
        app.add(stockpileList);
        stockpileList.setBounds(r);
        stockpileList.addActionListener(sl);
        
        resourcesList.removeAllItems();
        setStackCount("");
    }
    
    @SuppressWarnings("unchecked")
    public void setResourcesList(String zoneName) {
        String[] s = new String[app.getEditResources().getResources(zoneName).length + 1];
        String[] s1 = app.getEditResources().getResources(zoneName);
        setData(s1);
        resourcesList.removeAllItems();
        
        for(int i = 0; i < s1.length; i++) {
            s[i + 1] = s1[i].split(":")[0];
            
            //HotFix for not putting space in
            if(i == 0)
                resourcesList.addItem(s[0]);
            
            resourcesList.addItem(s[i + 1]);
        }      
    }

    @SuppressWarnings("rawtypes")
    private void createBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(Color.BLUE);
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        app.add(borderLabel);

        borderLabel.setBounds(AppPosition.RBORDER_X, AppPosition.RBORDER_Y, 390, 85);
    }
    
    public WebComboBox getStockpileList() {
        return stockpileList;
    }
    
    public int getResSelectedIndex() {
        return resourcesList.getSelectedIndex();
    }
    
    public WebComboBox getResourceList() {
        return resourcesList;
    }
    
    public int getSetAllStackCount() {
        return (int) setAllSpinner.getValue();
    }

    public String[] getData() {
        return this.data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
    
    public void setStackCount(String sc) {
        fieldStackCount.setText(sc);
    }
    
    public String getStackCount() {
        return fieldStackCount.getText();
    }

}
