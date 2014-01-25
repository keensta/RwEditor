package me.keensta.colonists;

import java.util.ArrayList;
import java.util.List;

import me.keensta.colonists.enums.Skill;
import me.keensta.colonists.enums.Trait;
import me.keensta.colonists.enums.Weapon;

import org.jdom2.Element;

@SuppressWarnings("unused")
public class Pawn {

    // Store basic vars as strings.
    // Psychology store as double

    private String name = "keensta :)";
    private String id = "01101011 01100101 01100101 01101110 01110011 01110100 01100001";

    private String age = "19";
    private String health = "100";
    private String sex = "----";
    private Weapon currentWeapon = Weapon.NONE;
    // Psychology vars BEGIN
    private double loyalty;
    private double happiness;
    private double fear;
    // Psychology Vars END
    private List<Trait> traits = new ArrayList<Trait>();
    private List<Skill> skills = new ArrayList<Skill>();

    public Pawn(Element pawnE) {
        setupClass(pawnE);
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
        setName(e.getChildText("CharacterName"));
        setId(e.getChildText("ID"));

        setAge(e.getChildText("Age"));
        setHealth(e.getChild("HealthTracker").getChildText("PawnHealth"));
        setSex(determinSex(e));
        setCurrentWeapon(hasWeapon(e));

        setLoyalty(Double.parseDouble(e.getChild("Psychology").getChild("LoyaltyBase").getChildText("CurLevel")));
        setHappiness(Double.parseDouble(e.getChild("Psychology").getChild("PieceHappiness").getChildText("CurLevel")));
        setFear(Double.parseDouble(e.getChild("Psychology").getChild("PieceFear").getChildText("CurLevel")));
    }

    private String determinSex(Element e) {
        if(e.getChild("Sex") == null) {
            return "Male";
        }
        return "Female";
    }

    private Weapon hasWeapon(Element e) {
        Element p = e.getChild("Equipment").getChild("Primary");

        if(p.hasAttributes()) {
            if(p.getAttributeValue("IsNull").equalsIgnoreCase("True")) {
                return Weapon.NONE;
            } else {
                return getWeapon(p.getChildText("Def"));
            }
        } else {
            return getWeapon(p.getChildText("Def"));
        }
    }

    private Weapon getWeapon(String weaponCode) {
        for(Weapon w : Weapon.values()) {
            if(weaponCode.equalsIgnoreCase(w.getWeaponCode()))
                return w;
        }
        
        return Weapon.NONE;
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

}
