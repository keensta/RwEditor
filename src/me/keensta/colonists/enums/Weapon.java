package me.keensta.colonists.enums;

public enum Weapon {

    NONE("", "No Gun"), PISTOL("Gun_Pistol", "Pistol"), SHOTGUN("Gun_Pump Shotgun", "Pump Shotgun"), T9INCENDIARYL("Gun_T-9 Incendiary Launcher",
            "T-9 Incendairy Launcher"), UZI("Gun_Uzi", "Uzi"), LEEENFIELD("Gun_Lee-Enfield", "Lee Enfield"), M16RIFLE(
            "Gun_M-16Rifle", "M16 Rifle"), R4ChargeRifle("Gun_R-4 charge rifle", "R-4 Charge Rifle"), M24RIFLE(
            "Gun_M-24Rifle", "M-24 Rifle"), GRENADEMOLOTOV("Weapon_GrenadeMolotov", "Molotov Grenade"), GRENADEFRAG(
            "Weapon_GrenadeFrag", "Frag GRenade");

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
    }

}
