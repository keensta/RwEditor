package me.keensta.xmleditting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.keensta.util.Notification;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ClearWeapons {

    private File xmlFile;
    private SAXBuilder builder;

    public ClearWeapons(File xmlFile, SAXBuilder builder) {
        this.xmlFile = xmlFile;
        this.builder = builder;
    }

    public void activeCode() {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("Thing"));
            List<String> weaponRackLocations = new ArrayList<String>();

            while(c.hasNext()) {
                Element e = c.next();

                if(e.hasAttributes()) {
                    if(e.getAttributeValue("Class").equalsIgnoreCase("Building_EquipmentRack")) {
                        weaponRackLocations.add(e.getChildText("Pos"));
                    }
                }

            }

            Iterator<Element> b = rootNode.getDescendants(new ElementFilter("Thing"));
            List<Element> markedToBeRemoved = new ArrayList<Element>();
            while(b.hasNext()) {
                Element e = b.next();

                if(e.hasAttributes()) {
                    if(e.getAttributeValue("Class").equalsIgnoreCase("Equipment")) {
                        if(!weaponRackLocations.contains(e.getChildText("Pos"))) {
                            markedToBeRemoved.add(e);
                        }
                    }
                }
            }

            for(int i = 0; i < markedToBeRemoved.size(); i++) {
                markedToBeRemoved.get(i).getParentElement().removeContent(markedToBeRemoved.get(i));
            }

            Notification.createInfoNotification("Weapon(s) Cleaned up", 3000);
            
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

}
