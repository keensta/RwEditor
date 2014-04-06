package me.keensta.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;

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
    private File modsDirectory;


    public ModLoader(AppWindow app, SAXBuilder builder) {
        this.app = app;
        this.builder = builder;
        
        loadMods();
    }

    @SuppressWarnings("serial")
    private void loadMods() {
        for(String mod : app.getDataHandler().getDataList("activeMods", app.getModFile().getName())) {
            modsActive.add(mod);
        }
        
        if(app.getPref().getModDirectory().getAbsolutePath().equals(new File("").getAbsolutePath())) {
            //Allows selecting of Folders.
            JFileChooser chooser = new JFileChooser(new File(".")) {
                public void approveSelection() {
                    if(getSelectedFile().isFile()) {
                        return;
                    } else
                        super.approveSelection();
                }
            };

            chooser.setDialogTitle("Choose RimWorld Mod Folder");

            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                modsDirectory = chooser.getSelectedFile();
                
                app.getPref().setModDirectory(modsDirectory);
                app.getPref().savePref();
                
                loadModData();
            }
        } else {
            modsDirectory = app.getPref().getModDirectory();
            
            loadModData();
        }
    }

    public void displayLoadedMods() {
        for(String mod : modsActive) {
            System.out.println("Mod: " + mod);
        }

        /*for(File f : modsDirectory.listFiles()) {
            System.out.println(f.getName());
        }*/
    }
    
    public void loadModData() {
        File[] files = modsDirectory.listFiles();
        
        for(File f : files) {
            
            if(modsActive.contains(f.getName())) {
                
                File modF = new File(f.getAbsoluteFile() + "\\Defs\\ThingDefs");
                File[] modFiles = modF.listFiles();
                
                for(File modFile : modFiles) {
                    if(modFile.getName().contains(".xml") && modFile.isFile()) {
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
                   
                    if(e.getAttributeValue("ParentName").equals("BaseGun") || e.getAttributeValue("ParentName").equals("BaseEquipment")) {
                        app.getWeaponHandler().createNewWeapon(e.getChildText("label"), e.getChildText("defName"));
                    }
                    
                }
                
            }
            
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
    }

}
