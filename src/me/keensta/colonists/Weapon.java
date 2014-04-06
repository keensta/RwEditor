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
    
    //OLD ENUM CODE NO LONGER NEED DUE TO ALPHA 2 MODDING BEING ADDED
    
    /*    NOGUN("", "No Gun"), PISTOL("Gun_Pistol", "Pistol"), SHOTGUN("Gun_Pump Shotgun", "Pump Shotgun"), T9INCENDIARYL("Gun_T-9 Incendiary Launcher",
            "T-9 Incendairy Launcher"), UZI("Gun_Uzi", "Uzi"), LEEENFIELD("Gun_Lee-Enfield", "Lee Enfield"), M16RIFLE(
            "Gun_M-16Rifle", "M16 Rifle"), R4CHARGERIFLE("Gun_R-4 charge rifle", "R-4 Charge Rifle"), M24RIFLE(
            "Gun_M-24Rifle", "M-24 Rifle"), MOLOTOVGRENADE("Weapon_GrenadeMolotov", "Molotov Grenade"), FRAGGRENADE(
            "Weapon_GrenadeFrag", "Frag Grenade");

    private String weaponCode;
    private String friendlyName;

    private Weapon(String weaponCode, String friendlyName) {
        this.weaponCode = weaponCode;
        this.friendlyName = friendlyName;
    }

    public String getWeaponCode() {
        return weaponCode;
    }

    public String getName() {
        return friendlyName;
    }*/

}
