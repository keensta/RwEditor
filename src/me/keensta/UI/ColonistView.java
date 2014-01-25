package me.keensta.UI;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.text.WebTextField;

import me.keensta.AppWindow;
import me.keensta.actionlisteners.pawn.ListListener;
import me.keensta.colonists.ColonistWindow;
import me.keensta.colonists.Pawn;
import me.keensta.colonists.enums.Weapon;

public class ColonistView {

    private AppWindow app;
    private ColonistWindow cw;
    
    String[] pawns;
    List<Pawn> listPawns = new ArrayList<Pawn>();
    
    private WebLabel borderLabel = new WebLabel();
    private WebLabel colonistEditor = new WebLabel("Colonist(s) Editor");
    private WebLabel colonistInfo = new WebLabel("ColonistInfo");
    private WebList list;
    
    //ColonistInfo Components BEGIN
    private WebLabel colonistBorder = new WebLabel();
    private WebLabel labelName = new WebLabel("Name");
    private WebTextField fieldName = new WebTextField();
    private WebLabel labelId = new WebLabel("Id");
    private WebTextField fieldId = new WebTextField();
    private WebLabel labelAge = new WebLabel("Age");
    private WebTextField fieldAge = new WebTextField();
    private WebLabel labelSex = new WebLabel("Sex");
    private WebTextField fieldSex = new WebTextField();
    private WebLabel labelWeapon = new WebLabel("Weapon");
    private WebComboBox fieldWeapon;
    
    //Skills BEGIN
    
    //Skill END
    //ColonistInfo Components END
    
    private ListListener ll;

    public ColonistView(AppWindow app, ColonistWindow cw) {
        this.app = app;
        this.cw = cw;
        
        ll = new ListListener(cw, app);
    }

    public void createWindow() {
        // Components
        listPawns = loadPawns();
        list = getPawns();
        
        setUpWeapons();
        
        colonistEditor.setFontSize(14);
        colonistEditor.setDrawShade(true);
        colonistEditor.setShadeColor(new Color(20, 210, 230));
        colonistInfo.setDrawShade(true);
        colonistInfo.setShadeColor(new Color(20, 210, 230));
        
        //Temp
        fieldSex.setEditable(false);
        
        // Add to main window
        createBorder();
        createBorder2();
        cw.add(colonistEditor);
        cw.add(list);
        cw.add(colonistInfo);
        cw.add(labelName);
        cw.add(fieldName);
        cw.add(labelId);
        cw.add(fieldId);
        cw.add(labelAge);
        cw.add(fieldAge);
        cw.add(labelSex);
        cw.add(fieldSex);
        cw.add(labelWeapon);
        cw.add(fieldWeapon);
        
        // Positioning
        colonistEditor.setBounds(5, 0, 105, 25);
        list.setBounds(5, 35, 150, 260);
        colonistInfo.setBounds(165, 0, 105, 25);
        
        labelName.setBounds(165, 25, 45, 25);
        fieldName.setBounds(215, 25, 130, 25);
        labelId.setBounds(360, 25, 45, 25);
        fieldId.setBounds(410, 25, 130, 25);
        labelAge.setBounds(165, 60, 45, 25);
        fieldAge.setBounds(215, 60, 130, 25);
        labelSex.setBounds(360, 60, 45, 25);
        fieldSex.setBounds(410, 60, 130, 25);
        labelWeapon.setBounds(165, 95, 50, 25);
        fieldWeapon.setBounds(215, 95, 325, 25);
        
        list.addListSelectionListener(ll);
    }
    
    @SuppressWarnings("rawtypes")
    private void createBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(Color.GRAY);
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        cw.add(borderLabel);

        borderLabel.setBounds(0, 0, 550, 300);
    }
    
    @SuppressWarnings("rawtypes")
    private void createBorder2() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(new Color(20, 210, 230));
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        colonistBorder.setPainter(dbp);

        cw.add(colonistBorder);

        colonistBorder.setBounds(155, 20, 390, 105);
    }
    
    private void setUpWeapons() {
        String[] weaponNames = new String[10];
        for(int i = 0; i < Weapon.values().length; i++) {
            weaponNames[i] = Weapon.values()[i].getName();
        }
        fieldWeapon = new WebComboBox(weaponNames);
    }
    
    public void updateCompnents(Pawn p) {
        fieldName.setText(p.getName());
        fieldId.setText(p.getId());
        fieldAge.setText(p.getAge());
        fieldSex.setText(p.getSex());
        //System.out.println(p.getCurrentWeapon().getName());
        fieldWeapon.setSelectedItem(p.getCurrentWeapon().getName());
    }

    private List<Pawn> loadPawns() {
        File xmlFile = app.getFile();
        SAXBuilder builder = app.getBuilder();
        List<Pawn> pawns = new ArrayList<Pawn>();
        
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("Team"));

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getValue().equalsIgnoreCase("Colonist")) {
                    if(e.getParentElement().getName().equalsIgnoreCase("Thing")) {
                        if(e.getParentElement().getAttributeValue("Class").equalsIgnoreCase("Pawn"))
                        pawns.add(new Pawn(e.getParentElement()));
                    }
                }
            }
            
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
        
        return pawns;
    }

    private WebList getPawns() {
        pawns = new String[listPawns.size()];
        for(int i = 0; i < listPawns.size(); i++) {
            pawns[i] = listPawns.get(i).getName() + "(" + listPawns.get(i).getId() + ")";
        }
        
        return new WebList(pawns);
    }
    
    public WebList getList() {
        return list;
    }
    
    public List<Pawn> getPawnList() {
        return listPawns;
    }

}
