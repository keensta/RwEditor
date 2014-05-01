package me.keensta.colonists;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import me.keensta.AppWindow;
import me.keensta.UI.ColonistView;
import me.keensta.colonists.enums.Skill;
import me.keensta.colonists.enums.Trait;
import me.keensta.util.Notification;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

@SuppressWarnings("unused")
public class Pawn {

    private ColonistView cv;
    private AppWindow app;
    
    // Store basic vars as strings.
    // Psychology store as double

    private String name = "keensta :)";
    private String id = "01101011 01100101 01100101 01101110 01110011 01110100 01100001";

    private String age = "19";
    private String health = "100";
    private String sex = "----";
    private Weapon currentWeapon;
    // Psychology vars BEGIN
    private double loyalty;
    private double happiness;
    private double fear;
    private double food;
    private double rest;
    // Psychology Vars END
    private List<Trait> traits = new ArrayList<Trait>();
    private HashMap<Skill, String> skills = new HashMap<Skill, String>();

    public Pawn(Element pawnE, ColonistView cv, AppWindow app) {
        this.cv = cv;
        this.app = app;
        
        setupClass(pawnE);
    }

    private void setupClass(Element e) {
        setName(e.getChild("story").getChildText("name.nick"));
        setId(e.getChildText("id"));

        setAge(e.getChildText("age"));
        setHealth(e.getChild("healthTracker").getChildText("pawnHealth"));
        setSex(determinSex(e));
        setCurrentWeapon(hasWeapon(e));

        setLoyalty(Double.parseDouble(e.getChild("psychology").getChild("loyaltyBase").getChildText("curLevel")));
        setHappiness(Double.parseDouble(e.getChild("psychology").getChild("pieceHappiness").getChildText("curLevel")));
        setFear(Double.parseDouble(e.getChild("psychology").getChild("pieceFear").getChildText("curLevel")));
        setFood(Double.parseDouble(e.getChild("food").getChild("pieceFood").getChildText("curLevel")));
        setRest(Double.parseDouble(e.getChild("rest").getChild("pieceRest").getChildText("curLevel")));
        
        getSkills(e);
    }

    private void getSkills(Element e) {
        Element s = e.getChild("skills");
        Element s2 = s.getChild("skills");
        Iterator<Element> skills = s2.getDescendants(new ElementFilter("li"));
        
        while(skills.hasNext()) {
            Element skill = skills.next();
            Element skillDef = skill.getChild("def");
            String skillLevel = skill.getChildText("level");
            
            if(skillLevel == null)
                skillLevel = "0";
            
            this.skills.put(Skill.valueOf(skillDef.getText().toUpperCase()), skillLevel);
        }
    }

    private String determinSex(Element e) {
        if(e.getChild("sex") == null)
            return "Male";
        return "Female";
    }

    private Weapon hasWeapon(Element e) {
        Element p = e.getChild("equipment").getChild("primary");

        if(p.hasAttributes()) {
            if(p.getAttributeValue("IsNull").equalsIgnoreCase("True")) {
                return getWeapon("isNull");
            } else {
                return getWeapon(p.getChildText("def"));
            }
        } else
            return getWeapon(p.getChildText("def"));
    }

    private Weapon getWeapon(String weaponCode) {
       return app.getWeaponHandler().getWeaponByTag(weaponCode);
    }

    // Setters and Getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public double getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(double loyalty) {
        this.loyalty = loyalty;
    }

    public double getHappiness() {
        return happiness;
    }

    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    public double getFear() {
        return fear;
    }

    public void setFear(double fear) {
        this.fear = fear;
    }
    
    public double getFood() {
        return food;
    }
    
    public void setFood(double food) {
        this.food = food;
    }
    
    public double getRest() {
        return rest;
    }
    
    public void setRest(double rest) {
        this.rest = rest;
    }
    
    public int getSkillValue(Skill s) {
        return Integer.parseInt(this.skills.get(s));
    }
    
    public void maxPawnHealth() {
        File xmlFile = app.getFile();
        SAXBuilder builder = app.getBuilder();
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("faction"));

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getValue().equalsIgnoreCase("Colony")) {
                    Element ep = e.getParentElement();
                    if(ep.getName().equalsIgnoreCase("thing")) {
                        if(ep.getAttributeValue("Class").equalsIgnoreCase("Pawn")) {
                            if(ep.getChild("story").getChildText("name.nick").equalsIgnoreCase(this.getName())) {
                                //Update File
                                ep.getChild("healthTracker").getChild("pawnHealth").setText("100");
                                //Update Pawn
                                this.setHealth("100");
                            }
                        }
                    }
                }
            }
            
            XMLOutputter xmlOutput = new XMLOutputter();
            FileOutputStream fos = new FileOutputStream(xmlFile);

            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, fos);

            fos.close();
            
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
    }
    
    public void savePawn(Pawn p) {
        File xmlFile = app.getFile();
        SAXBuilder builder = app.getBuilder();
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("faction"));

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getValue().equalsIgnoreCase("Colony")) {
                    Element ep = e.getParentElement();
                    if(ep.getName().equalsIgnoreCase("thing")) {
                        if(ep.getAttributeValue("Class").equalsIgnoreCase("Pawn")) {
                            if(ep.getChild("story").getChildText("name.nick").equalsIgnoreCase(p.getName())) {
                                //Update File
                                ep.getChild("age").setText(cv.getAge());
                                ep.getChild("healthTracker").getChild("pawnHealth").setText(cv.getHealth());
                                ep.getChild("psychology").getChild("loyaltyBase").getChild("curLevel").setText(cv.getLoyalty());
                                ep.getChild("psychology").getChild("pieceHappiness").getChild("curLevel").setText(cv.getHappiness());
                                ep.getChild("psychology").getChild("pieceFear").getChild("curLevel").setText(cv.getFear());
                                ep.getChild("food").getChild("pieceFood").getChild("curLevel").setText(cv.getFood());
                                ep.getChild("rest").getChild("pieceRest").getChild("curLevel").setText(cv.getRest());
                                setWeapon(ep);
                                saveSkills(ep); //Updates Pawns Values as it saves..
                                
                                //Update Pawn
                                p.setAge(cv.getAge());
                                p.setHealth(cv.getHealth());
                                p.setHappiness(Double.parseDouble(cv.getHappiness()));
                                p.setLoyalty(Double.parseDouble(cv.getLoyalty()));
                                p.setFear(Double.parseDouble(cv.getFear()));
                                p.setFood(Double.parseDouble(cv.getFood()));
                                p.setRest(Double.parseDouble(cv.getRest()));
                                p.setCurrentWeapon(app.getWeaponHandler().getWeaponByTag(cv.getWeapon()));
                            }
                        }
                    }
                }
            }
            
            Notification.createInfoNotification("Colonist " + p.getName() + " changes have been saved", 4000);
            
            XMLOutputter xmlOutput = new XMLOutputter();
            FileOutputStream fos = new FileOutputStream(xmlFile);

            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, fos);

            fos.close();
            
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
    }

    private void saveSkills(Element ep) {
        Element s = ep.getChild("skills");
        Element s2 = s.getChild("skills");
        Iterator<Element> skillRecord = s2.getDescendants(new ElementFilter("li"));
        
        skills.put(Skill.CONSTRUCTION, cv.getConstructionLevel());
        skills.put(Skill.GROWING, cv.getGrowingLevel());
        skills.put(Skill.RESEARCH, cv.getResearchLevel());
        skills.put(Skill.MINING, cv.getMiningLevel());
        skills.put(Skill.SHOOTING, cv.getShootingLevel());
        skills.put(Skill.MELEE, cv.getMeleeLevel());
        skills.put(Skill.SOCIAL, cv.getSocialLevel());
        skills.put(Skill.COOKING, cv.getCookingLevel());
        skills.put(Skill.MEDICINE, cv.getMedicineLevel());
        skills.put(Skill.ARTISTIC, cv.getArtisticLevel());
        skills.put(Skill.CRAFTING, cv.getCraftingLevel());
        
        while(skillRecord.hasNext()) {
            Element skill = skillRecord.next();
            Element skillDef = skill.getChild("def");
            
            if(skill.getChild("level") == null)
                continue;
            
            skill.getChild("level").setText(skills.get(Skill.valueOf(skillDef.getText().toUpperCase())));
        }
    }

    private void setWeapon(Element ep) {
        String weapon = cv.getWeapon();
        String weaponCode = determineWeapon(weapon);
        
        if(weaponCode.length() == 0 || weaponCode.equalsIgnoreCase("isNull")) {
            Element equipment = ep.getChild("equipment");
            equipment.removeContent(equipment.getChild("primary"));
            
            Element primary = new Element("primary");
            
            primary.setAttribute(new Attribute("IsNull", "True"));
            equipment.addContent(primary);
        } else {
            int thingId = app.getDataHandler().getDataInt("mapInfo/maxThingIDIndex", app.getFile().getName()) + 1;
            Element equipment = ep.getChild("equipment");
            Element prim = new Element("primary");
            Element def = new Element("def");
            Element id = new Element("id");
            Element health = new Element("health");
            Element faction = new Element ("faction");
            
            def.setText(weaponCode);
            id.setText(weaponCode+thingId);
            health.setText("100");
            faction.setText("Colony");
            prim.addContent(def);
            prim.addContent(id);
            prim.addContent(health);
            prim.addContent(faction);
            
            equipment.removeContent(equipment.getChild("primary"));
            equipment.addContent(prim);
            app.getDataHandler().setData("mapInfo/maxThingIDIndex", Integer.toString(thingId), app.getFile().getName());
        }
    }

    private String determineWeapon(String weapon) {
        return app.getWeaponHandler().getWeaponByName(weapon).getTagName();
    }

}
