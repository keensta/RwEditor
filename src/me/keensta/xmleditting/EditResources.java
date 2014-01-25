package me.keensta.xmleditting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import me.keensta.AppWindow;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class EditResources {

    private File xmlFile;
    private SAXBuilder builder;

    public EditResources(File xmlFile, SAXBuilder builder) {
        this.xmlFile = xmlFile;
        this.builder = builder;
    }

    public EditResources(AppWindow app) {
        xmlFile = app.getFile();
        builder = app.getBuilder();
    }

    public void activateCode(String money, String food, String metal, String medicine, String uranium, String shells,
            String missiles) {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element res = rootNode.getChild("Resources");

            String[] possibleData = { "Money", "Food", "Metal", "Medicine", "Uranium", "Shells", "Missiles" };
            for(int i = 0; i < possibleData.length; i++) {
                Element e = res.getChild(possibleData[i]);
                e.getParentElement().removeContent(e);
                res.removeContent(e);
            }

            Element eMoney = new Element("Money").setText(money);
            Element eFood = new Element("Food").setText(food);
            Element eMetal = new Element("Metal").setText(metal);
            Element eMedicine = new Element("Medicine").setText(medicine);
            Element eUranium = new Element("Uranium").setText(uranium);
            Element eShells = new Element("Shells").setText(shells);
            Element eMissiles = new Element("Missiles").setText(missiles);

            res.addContent(eMoney);
            res.addContent(eFood);
            res.addContent(eMetal);
            res.addContent(eMedicine);
            res.addContent(eUranium);
            res.addContent(eShells);
            res.addContent(eMissiles);

            JOptionPane.showMessageDialog(null, "Resources Updated", "InfoBox", JOptionPane.INFORMATION_MESSAGE);

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
    
    
    @Deprecated
    public HashMap<String, String> getCurrentResources() {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element res = rootNode.getChild("Resources");

            String[] possibleData = { "Money", "Food", "Metal", "Medicine", "Uranium", "Shells", "Missiles" };
            HashMap<String, String> resources = new HashMap<String, String>();
            resources.clear();

            for(int i = 0; i < possibleData.length; i++) {
                Element e = res.getChild(possibleData[i]);

                if(e != null) {
                    possibleData[i] = null;
                    resources.put(e.getName(), e.getValue());
                }

            }

            String[] noData = possibleData;
            for(String s : noData) {
                resources.put(s, "0");
            }

            return resources;
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
        return null;
    }

}
