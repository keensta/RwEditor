package me.keensta.file;

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

import me.keensta.AppWindow;

public class ModLoader {

    AppWindow app;
    private SAXBuilder builder;

    List<String> modsActive = new ArrayList<String>();
    private File ModDirectory;

    public ModLoader(AppWindow app, SAXBuilder builder) {
        this.app = app;
        this.builder = builder;

        loadMods();
    }

    private void loadMods() {
        for(String mod : app.getDataHandler().getDataList("activeMods", app.getModFile().getName())) {
            modsActive.add(mod);
        }
        
        ModDirectory = app.getPref().getModDirectory();
        loadModData();
    }

    public void displayLoadedMods() {
        for(String mod : modsActive) {
            System.out.println("Mod: " + mod);
        }

        /*
         * for(File f : modsDirectory.listFiles()) {
         * System.out.println(f.getName()); }
         */
    }

    public void loadModData() {
        File[] files = ModDirectory.listFiles();
        
        for(File f : files) {

            if(modsActive.contains(f.getName())) {

                File modF = new File(f.getAbsoluteFile() + "\\Defs\\ThingDefs");
                File[] modFiles = modF.listFiles();

                for(File modFile : modFiles) {
                    
                    if(modFile.getName().contains(".xml") && modFile.isFile()) {
                        System.out.println("Loading mod: " + modFile.getName());
                        
                        loadFileData(modFile);
                    }
                }

            }

        }
    }

    private void loadFileData(File modFile) {
        try {
            Document doc = builder.build(modFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("ThingDef"));

            while(c.hasNext()) {
                Element e = c.next();

                if(e.hasAttributes()) {

                    if(e.getAttribute("ParentName") == null) {
                        continue;
                    }

                    if(e.getAttributeValue("ParentName").equals("BaseGun")
                            || e.getAttributeValue("ParentName").equals("BaseEquipment")) {
                        app.getWeaponHandler().createNewWeapon(e.getChildText("label"), e.getChildText("defName"));
                    }

                }

            }
            
            System.out.println("Completed loading mod: " + modFile.getName());

        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
    }

}
