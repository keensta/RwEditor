package me.keensta.colonists;

import java.io.File;
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
import me.keensta.colonists.enums.Weapon;
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
    private Weapon currentWeapon = Weapon.NOGUN;
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
        setupClass(pawnE);
        this.cv = cv;
        this.app = app;
    }

    public Pawn(String pawnName) {
        this.name = pawnName;
        this.id = pawnName;

        this.age = "20";
        this.health = "100";
        this.sex = "male";
        this.currentWeapon = Weapon.LEEENFIELD;

        this.loyalty = 100;
        this.happiness = 100;
        this.fear = 100;
    }

    private void setupClass(Element e) {
        setName(e.getChild("story").getChildText("name.nick"));
        setId(e.getChildText("ID"));

        setAge(e.getChildText("Age"));
        setHealth(e.getChild("healthTracker").getChildText("PawnHealth"));
        setSex(determinSex(e));
        setCurrentWeapon(hasWeapon(e));

        setLoyalty(Double.parseDouble(e.getChild("psychology").getChild("LoyaltyBase").getChildText("CurLevel")));
        setHappiness(Double.parseDouble(e.getChild("psychology").getChild("PieceHappiness").getChildText("CurLevel")));
        setFear(Double.parseDouble(e.getChild("psychology").getChild("PieceFear").getChildText("CurLevel")));
        setFood(Double.parseDouble(e.getChild("food").getChild("PieceFood").getChildText("CurLevel")));
        setRest(Double.parseDouble(e.getChild("rest").getChild("PieceRest").getChildText("CurLevel")));
        
        getSkills(e);
    }

    private void getSkills(Element e) {
        Element s = e.getChild("skills");
        
        for(Skill skill : Skill.values()) {
            skills.put(skill, s.getChildText(skill.getTag()));
        }
    }

    private String determinSex(Element e) {
        if(e.getChild("Sex") == null)
            return "Male";
        return "Female";
    }

    private Weapon hasWeapon(Element e) {
        Element p = e.getChild("equipment").getChild("Primary");

        if(p.hasAttributes()) {
            if(p.getAttributeValue("IsNull").equalsIgnoreCase("True")) {
                return Weapon.NOGUN;
            } else {
                return getWeapon(p.getChildText("Def"));
            }
        } else
            return getWeapon(p.getChildText("Def"));
    }

    private Weapon getWeapon(String weaponCode) {
        for(Weapon w : Weapon.values()) {
            if(weaponCode.equalsIgnoreCase(w.getWeaponCode()))
                return w;
        }

        return Weapon.NOGUN;
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
    
    public void savePawn(Pawn p) {
        File xmlFile = app.getFile();
        SAXBuilder builder = app.getBuilder();
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("Team"));

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getValue().equalsIgnoreCase("Colonist")) {
                    Element ep = e.getParentElement();
                    if(ep.getName().equalsIgnoreCase("Thing")) {
                        if(ep.getAttributeValue("Class").equalsIgnoreCase("Pawn")) {
                            if(ep.getChild("story").getChildText("name.nick").equalsIgnoreCase(p.getName())) {
                                //Update File
                                ep.getChild("Age").setText(cv.getAge());
                                ep.getChild("healthTracker").getChild("PawnHealth").setText(cv.getHealth());
                                ep.getChild("psychology").getChild("LoyaltyBase").getChild("CurLevel").setText(cv.getLoyalty());
                                ep.getChild("psychology").getChild("PieceHappiness").getChild("CurLevel").setText(cv.getHappiness());
                                ep.getChild("psychology").getChild("PieceFear").getChild("CurLevel").setText(cv.getFear());
                                ep.getChild("food").getChild("PieceFood").getChild("CurLevel").setText(cv.getFood());
                                ep.getChild("rest").getChild("PieceRest").getChild("CurLevel").setText(cv.getRest());
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
                                p.setCurrentWeapon(Weapon.valueOf(cv.getWeapon().replace("-", "").replaceAll(" ", "").toUpperCase()));
                            }
                        }
                    }
                }
            }
            
            Notification.createInfoNotification("Colonist " + p.getName() + " changes have been saved", 4000);
            
            XMLOutputter xmlOutput = new XMLOutputter();
            FileWriter fw = new FileWriter(xmlFile);

            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, fw);

            fw.close();
            
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
    }

    private void saveSkills(Element ep) {
        Element e = ep.getChild("skills");
        
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
        
        for(Skill s : Skill.values()) {
            e.getChild(s.getTag()).setText(skills.get(s));
        }
    }

    private void setWeapon(Element ep) {
        String weapon = cv.getWeapon();
        String weaponCode = determineWeapon(weapon);
        
        if(weaponCode.length() == 0) {
            Element equipment = ep.getChild("equipment");
            equipment.removeContent(equipment.getChild("Primary"));
            
            Element primary = new Element("Primary");
            
            primary.setAttribute(new Attribute("IsNull", "True"));
            equipment.addContent(primary);
        } else {
            Element equipment = ep.getChild("equipment");
            Element prim = new Element("Primary");
            Element def = new Element("Def");
            Element id = new Element("ID");
            Element health = new Element("Health");
            
            def.setText(weaponCode);
            id.setText(weaponCode+"0");
            health.setText("100");
            prim.addContent(def);
            prim.addContent(id);
            prim.addContent(health);
            
            equipment.removeContent(equipment.getChild("Primary"));
            equipment.addContent(prim);
        }
    }

    private String determineWeapon(String weapon) {
        for(Weapon w : Weapon.values()) {
            if(w.getName().equalsIgnoreCase(weapon))
                return w.getWeaponCode();
        }
        return Weapon.NOGUN.getWeaponCode();
    }

}
