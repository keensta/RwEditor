 package me.keensta.UI;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.text.WebTextField;

import me.keensta.AppWindow;
import me.keensta.actionlisteners.pawn.HealAllListener;
import me.keensta.actionlisteners.pawn.ListListener;
import me.keensta.actionlisteners.pawn.SavePawnListener;
import me.keensta.colonists.ColonistWindow;
import me.keensta.colonists.Pawn;
import me.keensta.colonists.enums.Skill;

public class ColonistView {

    private AppWindow app;
    private ColonistWindow cw;
    
    String[] pawns;
    List<Pawn> listPawns = new ArrayList<Pawn>();
    
    private WebLabel borderLabel = new WebLabel();
    private WebLabel colonistEditor = new WebLabel("Colonist(s) Editor");
    private WebLabel colonistInfo = new WebLabel("ColonistInfo");
    private WebList list;
    private JScrollPane sp;
    
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
    private WebLabel labelHealth = new WebLabel("Health");
    private WebTextField fieldHealth = new WebTextField();
    private WebLabel labelHappiness = new WebLabel("Happiness");
    private WebTextField fieldHappiness = new WebTextField();
    private WebLabel labelLoyalty = new WebLabel("Loyalty");
    private WebTextField fieldLoyalty = new WebTextField();
    private WebLabel labelFear = new WebLabel("Fear");
    private WebTextField fieldFear = new WebTextField();
    private WebLabel labelFood = new WebLabel("Food");
    private WebTextField fieldFood = new WebTextField();
    private WebLabel labelRest = new WebLabel("Rest");
    private WebTextField fieldRest = new WebTextField();
    private WebLabel labelWeapon = new WebLabel("Weapon");
    private WebComboBox fieldWeapon;
    
    //Skills BEGIN **SkillNameTYPE(Label/Field)** Different to other sets to stop big list dispalying while typeing any skill related stuff.
    private WebLabel Skills = new WebLabel("Skills");
    private WebLabel constructionLabel = new WebLabel("Construction");
    private WebSpinner constructionField = new WebSpinner();
    private WebLabel growingLabel = new WebLabel("Growing");
    private WebSpinner growingField = new WebSpinner();
    private WebLabel researchLabel = new WebLabel("Research");
    private WebSpinner researchField = new WebSpinner();
    private WebLabel miningLabel = new WebLabel("Mining");
    private WebSpinner miningField = new WebSpinner();
    private WebLabel shootingLabel = new WebLabel("Shooting");
    private WebSpinner shootingField = new WebSpinner();
    private WebLabel meleeLabel = new WebLabel("Melee");
    private WebSpinner meleeField = new WebSpinner();
    private WebLabel socialLabel = new WebLabel("Social");
    private WebSpinner socialField = new WebSpinner();
    private WebLabel cookingLabel = new WebLabel("Cooking");
    private WebSpinner cookingField = new WebSpinner();
    private WebLabel medicineLabel = new WebLabel("Medicine");
    private WebSpinner medicineField = new WebSpinner();
    private WebLabel artisticLabel = new WebLabel("Artistic");
    private WebSpinner artisticField = new WebSpinner();
    private WebLabel craftingLabel = new WebLabel("Crafting");
    private WebSpinner craftingField = new WebSpinner();
    //Skill END
    //ColonistInfo Components END
    private WebButton savePawn = new WebButton("Save");
    private WebButton healAll = new WebButton("Heal All");
    
    private ListListener ll;
    private SavePawnListener spl;
    private HealAllListener hal;

    public ColonistView(AppWindow app, ColonistWindow cw) {
        this.app = app;
        this.cw = cw;
        
        ll = new ListListener(cw, app);
        spl = new SavePawnListener(cw);
        hal = new HealAllListener(cw);
    }

    public void createWindow() {
        // Components
        listPawns = loadPawns();
        list = getPawns();
        sp = new JScrollPane(list);
        
        setUpWeapons();
        
        colonistEditor.setFontSize(14);
        colonistEditor.setDrawShade(true);
        colonistEditor.setShadeColor(new Color(20, 210, 230));
        colonistInfo.setDrawShade(true);
        colonistInfo.setShadeColor(new Color(20, 210, 230));
        
        fieldId.setEditable(false);
        fieldName.setEditable(false);
        fieldSex.setEditable(false);
        
        // Add to main window
        createBorder();
        createBorder2();
        cw.add(colonistEditor);
        cw.add(sp);
        cw.add(colonistInfo);
        cw.add(labelName);
        cw.add(fieldName);
        cw.add(labelId);
        cw.add(fieldId);
        cw.add(labelAge);
        cw.add(fieldAge);
        cw.add(labelSex);
        cw.add(fieldSex);
        cw.add(labelHealth);
        cw.add(fieldHealth);
        cw.add(labelHappiness);
        cw.add(fieldHappiness);
        cw.add(labelLoyalty);
        cw.add(fieldLoyalty);
        cw.add(labelFear);
        cw.add(fieldFear);
        cw.add(labelFood);
        cw.add(fieldFood);
        cw.add(labelRest);
        cw.add(fieldRest);
        cw.add(labelWeapon);
        cw.add(fieldWeapon);
        cw.add(savePawn);
        cw.add(healAll);
        
        // Positioning
        colonistEditor.setBounds(5, 0, 105, 25);
        sp.setBounds(5, 25, 150, 335);
        colonistInfo.setBounds(165, 0, 105, 25);
        
        labelName.setBounds(170, 25, 45, 25);
        fieldName.setBounds(225, 25, 130, 25);
        labelId.setBounds(360, 25, 45, 25);
        fieldId.setBounds(420, 25, 130, 25);
        labelAge.setBounds(170, 60, 45, 25);
        fieldAge.setBounds(225, 60, 130, 25);
        labelSex.setBounds(360, 60, 45, 25);
        fieldSex.setBounds(420, 60, 130, 25);
        labelHealth.setBounds(170, 95, 45, 25);
        fieldHealth.setBounds(225, 95, 130, 25);
        labelHappiness.setBounds(360, 95, 55, 25);
        fieldHappiness.setBounds(420, 95, 130, 25);
        labelLoyalty.setBounds(170, 125, 55, 25);
        fieldLoyalty.setBounds(225, 125, 130, 25);
        labelFear.setBounds(360, 125, 45, 25);
        fieldFear.setBounds(420, 125, 130, 25);
        labelFood.setBounds(170, 160, 45, 25);
        fieldFood.setBounds(225, 160, 130, 25);
        labelRest.setBounds(360, 160, 45, 25);
        fieldRest.setBounds(420, 160, 130, 25);
        labelWeapon.setBounds(170, 195, 55, 25);
        fieldWeapon.setBounds(225, 195, 325, 25);
        savePawn.setBounds(470, 365, 80, 25);
        healAll.setBounds(5, 365, 150, 25);
        
        list.addListSelectionListener(ll);
        savePawn.addActionListener(spl);
        healAll.addActionListener(hal);

        //Skills
        cw.add(Skills);
        cw.add(constructionLabel);
        cw.add(constructionField);
        cw.add(growingLabel);
        cw.add(growingField);
        cw.add(researchLabel);
        cw.add(researchField);
        cw.add(miningLabel);
        cw.add(miningField);
        cw.add(shootingLabel);
        cw.add(shootingField);
        cw.add(meleeLabel);
        cw.add(meleeField);
        cw.add(socialLabel);
        cw.add(socialField);
        cw.add(cookingLabel);
        cw.add(cookingField);
        cw.add(medicineLabel);
        cw.add(medicineField);
        cw.add(artisticLabel);
        cw.add(artisticField);
        cw.add(craftingLabel);
        cw.add(craftingField);
        
        Skills.setBounds(170, 230, 45, 25);
        constructionLabel.setBounds(170, 250, 70, 25);
        constructionField.setBounds(170, 270, 70, 25);
        growingLabel.setBounds(245, 250, 70, 25);
        growingField.setBounds(245, 270, 70, 25);
        researchLabel.setBounds(320, 250, 70, 25);
        researchField.setBounds(320, 270, 70, 25);
        miningLabel.setBounds(395, 250, 70, 25);
        miningField.setBounds(395, 270, 70, 25);
        shootingLabel.setBounds(170, 290, 70, 25);
        shootingField.setBounds(170, 310, 70, 25);
        meleeLabel.setBounds(245, 290, 70, 25);
        meleeField.setBounds(245, 310, 70, 25);
        socialLabel.setBounds(320, 290, 70, 25);
        socialField.setBounds(320, 310, 70, 25);
        cookingLabel.setBounds(395, 290, 70, 25);
        cookingField.setBounds(395, 310, 70, 25);
        medicineLabel.setBounds(170, 330, 70, 25);
        medicineField.setBounds(170, 350, 70, 25);
        artisticLabel.setBounds(245, 330, 70, 25);
        artisticField.setBounds(245, 350, 70, 25);
        craftingLabel.setBounds(320, 330, 70, 25);
        craftingField.setBounds(320, 350, 70, 25);
    }
    
    @SuppressWarnings("rawtypes")
    private void createBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(Color.GRAY);
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        cw.add(borderLabel);

        borderLabel.setBounds(0, 0, 560, 400);
    }
    
    @SuppressWarnings("rawtypes")
    private void createBorder2() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(new Color(20, 210, 230));
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        colonistBorder.setPainter(dbp);

        cw.add(colonistBorder);

        colonistBorder.setBounds(160, 20, 395, 375);
    }
    
    private void setUpWeapons() {
        fieldWeapon = new WebComboBox(app.getWeaponHandler().getWeaponNames());
    }
    
    public void updateCompnents(Pawn p) {
        fieldName.setText(p.getName());
        fieldId.setText(p.getId());
        fieldAge.setText(p.getAge());
        fieldSex.setText(p.getSex());
        fieldHealth.setText(p.getHealth());
        fieldHappiness.setText(Double.toString(p.getHappiness()));
        fieldLoyalty.setText(Double.toString(p.getLoyalty()));
        fieldFear.setText(Double.toString(p.getFear()));
        fieldFood.setText(Double.toString(p.getFood()));
        fieldRest.setText(Double.toString(p.getRest()));
        fieldWeapon.setSelectedItem(p.getCurrentWeapon().getGunName());
        
        constructionField.setValue(p.getSkillValue(Skill.CONSTRUCTION));
        growingField.setValue(p.getSkillValue(Skill.GROWING));
        researchField.setValue(p.getSkillValue(Skill.RESEARCH));
        miningField.setValue(p.getSkillValue(Skill.MINING));
        shootingField.setValue(p.getSkillValue(Skill.SHOOTING));
        meleeField.setValue(p.getSkillValue(Skill.MELEE));
        socialField.setValue(p.getSkillValue(Skill.SOCIAL));
        cookingField.setValue(p.getSkillValue(Skill.COOKING));
        medicineField.setValue(p.getSkillValue(Skill.MEDICINE));
        artisticField.setValue(p.getSkillValue(Skill.ARTISTIC));
        craftingField.setValue(p.getSkillValue(Skill.CRAFTING));
    }
    
    private List<Pawn> loadPawns() {
        File xmlFile = app.getFile();
        SAXBuilder builder = app.getBuilder();
        List<Pawn> pawns = new ArrayList<Pawn>();
        
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element things = rootNode.getChild("things");

            Iterator<Element> c = things.getDescendants(new ElementFilter("faction"));

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getValue().equalsIgnoreCase("Colony")) {
                    if(e.getParentElement().getName().equalsIgnoreCase("thing")) {
                        if(e.getParentElement().getAttributeValue("Class").equalsIgnoreCase("Pawn"))
                        pawns.add(new Pawn(e.getParentElement(), this, app));
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
    
    public String getAge() {
        return fieldAge.getText();
    }
    
    public String getHealth() {
        return fieldHealth.getText();
    }
    
    public String getLoyalty() {
        return fieldLoyalty.getText();
    }
    
    public String getHappiness() {
        return fieldHappiness.getText();
    }
    
    public String getFear() {
        return fieldFear.getText();
    }
    
    public String getFood() {
        return fieldFood.getText();
    }
    
    public String getRest() {
        return fieldRest.getText();
    }
    
    public String getWeapon() {
        return (String) fieldWeapon.getSelectedItem();
    }
    
    public String getConstructionLevel() {
        return Integer.toString((int) constructionField.getValue());
    }
    
    public String getGrowingLevel() {
        return Integer.toString((int) growingField.getValue());
    }
    
    public String getResearchLevel() {
        return Integer.toString((int) researchField.getValue());
    }
    
    public String getMiningLevel() {
        return Integer.toString((int) miningField.getValue());
    }
    
    public String getShootingLevel() {
        return Integer.toString((int) shootingField.getValue());
    }
    
    public String getMeleeLevel() {
        return Integer.toString((int) meleeField.getValue());
    }
    
    public String getSocialLevel() {
        return Integer.toString((int) socialField.getValue());
    }
    
    public String getCookingLevel() {
        return Integer.toString((int) cookingField.getValue());
    }
    
    public String getMedicineLevel() {
        return Integer.toString((int) medicineField.getValue());
    }
    
    public String getArtisticLevel() {
        return Integer.toString((int) artisticField.getValue());
    }
    
    public String getCraftingLevel() {
        return Integer.toString((int) craftingField.getValue());
    }
}
