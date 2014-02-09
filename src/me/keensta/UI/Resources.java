package me.keensta.UI;

import java.awt.Color;
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
        
        resourcesTitle.setBounds(40, 120, 80, 25);
        stockpiles.setBounds(42, 140, 80, 25);
        stockpileList.setBounds(40, 160, 130, 25);
        resources.setBounds(192, 140, 80, 25);
        resourcesList.setBounds(190, 160, 130, 25);
        stackCount.setBounds(342, 140, 80, 25);
        fieldStackCount.setBounds(340, 160, 80, 25);
        setAllSpinner.setBounds(190, 195, 75, 25);
        setAll.setBounds(265, 195, 55, 25);
        update.setBounds(340, 195, 80, 25);
        
        update.addActionListener(ul);
        stockpileList.addActionListener(sl);
        resourcesList.addActionListener(rl);
        setAll.addActionListener(asl);
        
        app.revalidate();
    }

    public void updateComponents() {
        
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

        borderLabel.setBounds(35, 140, 390, 85);
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
