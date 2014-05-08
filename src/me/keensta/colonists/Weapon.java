package me.keensta.colonists;

public class Weapon {
    
    private String tagName;
    private String gunName;
    
    public Weapon(String gunName, String tagName) {
        this.gunName = gunName;
        this.tagName = tagName;
    }
    
    public String getGunName() {
        return gunName;
    }
    
    public String getTagName() {
        return tagName;
    }

}
