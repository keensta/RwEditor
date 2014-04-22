package me.keensta.xmleditting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import me.keensta.AppWindow;
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
    private AppWindow app;

    public SpawnGeyser(File xmlFile, SAXBuilder builder, AppWindow appWindow) {
        this.xmlFile = xmlFile;
        this.builder = builder;
        this.app = appWindow;
    } 

    public void activateCode() {
        try {
            xmlFile = app.getFile();
            
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element things = rootNode.getChild("things");
            String location = getStockpileLocation(doc);
            
            if(location.equalsIgnoreCase("")) {
                Notification.createInfoNotification("Can't find a stockpile 1x1 named SG", 3000);
                return;
            }
            
            int thingId = app.getDataHandler().getDataInt("mapInfo/maxThingIDIndex", app.getFile().getName()) + 1;
            Element thing = new Element("thing");
            Element def = new Element("def");
            Element id = new Element("id");
            Element pos = new Element("pos");
            
            thing.setAttribute(new Attribute("Class", "SteamGeyser"));
            def.setText("SteamGeyser");
            id.setText("SteamGeyser" + thingId);
            pos.setText(location);
            
            thing.addContent(def);
            thing.addContent(id);
            thing.addContent(pos);
            
            things.addContent(thing);
            app.getDataHandler().setData("mapInfo/maxThingIDIndex", Integer.toString(thingId), app.getFile().getName());
            
            Notification.createInfoNotification("Spawned SteamGeyser at " + location, 3000);
            
            XMLOutputter xmlOutput = new XMLOutputter();
            FileOutputStream fos = new FileOutputStream(xmlFile);

            xmlOutput.setFormat(Format.getRawFormat());
            xmlOutput.output(doc, fos);

            fos.close();
            
            app.setFile(xmlFile);
            
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
                location = ep.getChild("squares").getChildText("li");
           
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
