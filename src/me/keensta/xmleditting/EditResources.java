package me.keensta.xmleditting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.keensta.AppWindow;
import me.keensta.util.Notification;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
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

    public void activateCode(String pos, String stackCount) {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element things = rootNode.getChild("Things");

            Iterator<Element> c = things.getDescendants(new ElementFilter("Pos"));

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getText().equalsIgnoreCase(pos)) {
                    Element eP = e.getParentElement();
                    if(eP.hasAttributes()) {
                        if(eP.getAttributeValue("Class").equalsIgnoreCase("ThingResource")) {
                            eP.getChild("StackCount").setText(stackCount);
                            break;
                        }
                    }
                }
            }

            Notification.createInfoNotification("Updated resource stack size.", 3000);
            
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

    public void setAllCode(List<String> pos, String stackCount) {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element things = rootNode.getChild("Things");

            Iterator<Element> c = things.getDescendants(new ElementFilter("Pos"));

            while(c.hasNext()) {
                Element e = c.next();

                if(pos.contains(e.getText())) {
                    Element eP = e.getParentElement();
                    if(eP.hasAttributes()) {
                        if(eP.getAttributeValue("Class").equalsIgnoreCase("ThingResource")) {
                            eP.getChild("StackCount").setText(stackCount);
                            continue;
                        }
                    }
                }
            }
            
            Notification.createInfoNotification("Updated all resources stack size to " + stackCount, 3000);
            
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

    public List<String> getStockpileList() {
        List<String> stockPiles = new ArrayList<String>();
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("Zone"));

            // Adding blank line
            stockPiles.add("");

            while(c.hasNext()) {
                Element e = c.next();

                if(e.hasAttributes()) {
                    if(e.getAttributeValue("Class").equalsIgnoreCase("Zone_Storage")) {
                        stockPiles.add(e.getChildText("zoneName"));
                    }
                }
            }

            return stockPiles;
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
        return stockPiles;
    }

    public String[] getResources(String zoneName) {
        String[] s = { "N/A:0:(0,0,0)" };
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element zm = rootNode.getChild("ZoneManager");

            Iterator<Element> c = zm.getDescendants(new ElementFilter("zoneName"));
            List<String> locations = new ArrayList<String>();

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getText().equalsIgnoreCase(zoneName)) {
                    Element zone = e.getParentElement();
                    Element squares = zone.getChild("squares");

                    for(int i = 0; i < squares.getChildren().size(); i++) {
                        locations.add(squares.getChildren().get(i).getText());
                    }

                }
            }

            return getItemsAtPos(locations);
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
        return s;
    }

    private String[] getItemsAtPos(List<String> list) {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element things = rootNode.getChild("Things");

            Iterator<Element> c = things.getDescendants(new ElementFilter("Pos"));
            String data = "";
            int i = 0;

            while(c.hasNext()) {
                Element e = c.next();

                if(list.contains(e.getText())) {
                    Element eP = e.getParentElement();
                    if(eP.hasAttributes()) {
                        if(eP.getAttributeValue("Class").equalsIgnoreCase("ThingResource")) {
                            if(eP.getChildText("Def") == null || eP.getChildText("StackCount") == null) {
                                continue;
                            }
                            data = data + e.getParentElement().getChildText("Def") + "(" + i + ")" + ":"
                                    + e.getParentElement().getChildText("StackCount") + ":" + e.getText() + "#";
                            i++;
                            continue;
                        }
                    }
                }
            }

            String[] s = data.trim().split("#");
            return s;
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
        return null;
    }

}
