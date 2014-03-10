package me.keensta.file;

import java.util.ArrayList;
import java.util.List;

import me.keensta.AppWindow;

public class ModLoader {
    
    AppWindow app;
    List<String> modsActive = new ArrayList<String>();
    
    public ModLoader(AppWindow app) {
        this.app = app;
        
        loadMods();
    }
    
    private void loadMods() {
        for(String mod : app.getDataHandler().getDataList("activeMods", app.getModFile().getName())) {
            modsActive.add(mod);
        }
    }
    
    public void displayLoadedMods() {
        for(String mod : modsActive) {
            System.out.println("Mod: " + mod);
        }
    }

}
