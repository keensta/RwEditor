package me.keensta.xmleditting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import me.keensta.util.Notification;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class SpawnGeyser {

    private File xmlFile;
    private SAXBuilder builder;

    public SpawnGeyser(File xmlFile, SAXBuilder builder) {
        this.xmlFile = xmlFile;
        this.builder = builder;
    } 

    public void activateCode() {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element things = rootNode.getChild("Things");
            String location = getStockpileLocation(doc);
            
            if(location.equalsIgnoreCase("")) {
                Notification.createInfoNotification("Can't find a stockpile 1x1 named SG", 3000);
                return;
            }
            
            Element thing = new Element("Thing");
            Element def = new Element("Def");
            Element id = new Element("ID");
            Element pos = new Element("Pos");
            Element rot = new Element("Rot");
            
            thing.setAttribute(new Attribute("Class", "Building_SteamGeyser"));
            def.setText("SteamGeyser");
            id.setText("SteamGeyser0");
            pos.setText(location);
            rot.setText("1");
            
            thing.addContent(def);
            thing.addContent(id);
            thing.addContent(pos);
            thing.addContent(rot);
            
            things.addContent(thing);
            
            Notification.createInfoNotification("Spawned SteamGeyser at " + location, 3000);
            
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

    public String getStockpileLocation(Document d) {
        Document doc = d;
        Element rootNode = doc.getRootElement();

        Iterator<Element> c = rootNode.getDescendants(new ElementFilter("zoneName"));
        String location = "";
        Element remove = null; //Element here so we can remove stockpile

        while(c.hasNext()) {
            Element e = c.next();

            if(e.getText().equalsIgnoreCase("SG")) {
                Element ep = e.getParentElement();
                location = ep.getChild("squares").getChildText("IntVec3");
           
                remove = ep;
                break;
            }
        }
        
        if(remove != null) {
           Element ep = remove.getParentElement();
           ep.removeContent(remove);
        }
        
        return location;
    }
}
