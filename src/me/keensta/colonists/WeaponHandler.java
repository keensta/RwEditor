package me.keensta.colonists;

import java.util.ArrayList;
import java.util.List;

public class WeaponHandler {
    
    List<Weapon> weapons = new ArrayList<Weapon>();
    List<String> weaponNames = new ArrayList<String>(); //For ComboBox
    
    public WeaponHandler() {
        Weapon w = new Weapon("No Gun", "isNull");
        
        weapons.add(w);
        weaponNames.add(w.getGunName());
    }
    
    public void createNewWeapon(String name, String tag) {
        if(weaponExists(name, tag))
            return;
        
        Weapon w = new Weapon(name, tag);
        
        weapons.add(w);
        weaponNames.add(w.getGunName());
    }

    private boolean weaponExists(String name, String tag) {
        
        for(Weapon w : weapons) {
            if(w.getGunName().equals(name) && w.getTagName().equals(tag))
                return true;
        }
        
        return false;
    }
    
    public Weapon getWeaponByName(String name) {
        for(Weapon w : weapons) {
            if(w.getGunName().equals(name))
                return w;
        }
        
        return weapons.get(0);
    }
    
    public Weapon getWeaponByTag(String tag) {
        for(Weapon w : weapons) {
            if(w.getTagName().equals(tag))
                return w;
        }
        
        return weapons.get(0);
    }
    
    public Object[] getWeaponNames() {
        return weaponNames.toArray();
    }

}
