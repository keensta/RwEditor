package me.keensta.UI;

import java.awt.Color;

import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.spinner.WebSpinner;

import me.keensta.AppWindow;
import me.keensta.actionlisteners.UpdateListener;
import me.keensta.xmleditting.DataHandler;

public class Resources {

    private AppWindow app;
    
    private WebLabel borderLabel = new WebLabel();

    private WebLabel resources = new WebLabel("Resources");
    private WebLabel money = new WebLabel("Money");
    private WebLabel food = new WebLabel("Food");
    private WebLabel metal = new WebLabel("Metal");
    private WebLabel uranium = new WebLabel("Uranium");
    private WebLabel medicine = new WebLabel("Medicine");
    private WebLabel missiles = new WebLabel("Missiles");

    private WebSpinner moneyS = new WebSpinner();
    private WebSpinner foodS = new WebSpinner();
    private WebSpinner metalS = new WebSpinner();
    private WebSpinner uraniumS = new WebSpinner();
    private WebSpinner medicineS = new WebSpinner();
    private WebSpinner missilesS = new WebSpinner();

    private WebButton update = new WebButton("Update");

    private UpdateListener ul;

    public Resources(AppWindow app) {
        this.app = app;

        ul = new UpdateListener(app);
    }

    public void BuildComponents() {
        moneyS.setValue(app.getDataHandler().getDataInt("Resources/Money"));
        foodS.setValue(app.getDataHandler().getDataInt("Resources/Food"));
        metalS.setValue(app.getDataHandler().getDataInt("Resources/Metal"));
        uraniumS.setValue(app.getDataHandler().getDataInt("Resources/Uranium"));
        medicineS.setValue(app.getDataHandler().getDataInt("Resources/Medicine"));
        missilesS.setValue(app.getDataHandler().getDataInt("Resources/Missiles"));

        resources.setDrawShade(true);
        resources.setShadeColor(Color.BLUE);

        createBorder();
        app.add(resources);
        app.add(money);
        app.add(moneyS);
        app.add(food);
        app.add(foodS);
        app.add(metal);
        app.add(metalS);
        app.add(uranium);
        app.add(uraniumS);
        app.add(medicine);
        app.add(medicineS);
        app.add(missiles);
        app.add(missilesS);
        app.add(update);

        resources.setBounds(40, 120, 80, 25);
        money.setBounds(84, 140, 80, 25);
        moneyS.setBounds(82, 160, 80, 25);
        food.setBounds(222, 140, 80, 25);
        foodS.setBounds(220, 160, 80, 25);
        metal.setBounds(372, 140, 80, 25);
        metalS.setBounds(370, 160, 80, 25);
        uranium.setBounds(84, 180, 80, 25);
        uraniumS.setBounds(82, 200, 80, 25);
        medicine.setBounds(222, 180, 80, 25);
        medicineS.setBounds(220, 200, 80, 25);
        missiles.setBounds(372, 180, 80, 25);
        missilesS.setBounds(370, 200, 80, 25);
        update.setBounds(220, 225, 80, 25);

        update.addActionListener(ul);

        app.revalidate();
    }

    public void updateComponents() {
        moneyS.setValue(app.getDataHandler().getDataInt("Resources/Money"));
        foodS.setValue(app.getDataHandler().getDataInt("Resources/Food"));
        metalS.setValue(app.getDataHandler().getDataInt("Resources/Metal"));
    }

    public int[] getValues() {
        // Used to set currently unhandled res just incase they have any
        DataHandler dh = app.getDataHandler();

        int[] values = { (int) moneyS.getValue(), (int) foodS.getValue(), (int) metalS.getValue(),
                (int) medicineS.getValue(), (int) uraniumS.getValue(), dh.getDataInt("Resources/Shells"),
                (int) missilesS.getValue()};

        return values;
    }
    
    @SuppressWarnings("rawtypes")
    private void createBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(Color.BLUE);
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        app.add(borderLabel);

        borderLabel.setBounds(35, 140, 460, 115);
    }

}
