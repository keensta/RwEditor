package me.keensta.xmleditting;

import java.io.File;
import java.io.FileOutputStream;
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
    private AppWindow app;
    
    public EditResources(File xmlFile, SAXBuilder builder, AppWindow app) {
        this.xmlFile = xmlFile;
        this.builder = builder;
        this.app = app;
    }

    public EditResources(AppWindow app) {
        xmlFile = app.getFile();
        builder = app.getBuilder();
    }

    public void activateCode(String pos, String stackCount) {
        try {
            xmlFile = app.getFile();
            
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element things = rootNode.getChild("things");

            Iterator<Element> c = things.getDescendants(new ElementFilter("pos"));

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getText().equalsIgnoreCase(pos)) {
                    Element eP = e.getParentElement();
                    if(eP.hasAttributes()) {
                        if(eP.getAttributeValue("Class").equalsIgnoreCase("ThingResource")) {
                            eP.getChild("stackCount").setText(stackCount);
                            break;
                        }
                    }
                }
            }

            Notification.createInfoNotification("Updated resource stack size.", 3000);
            
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

    public void setAllCode(List<String> pos, String stackCount) {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();
            Element things = rootNode.getChild("things");

            Iterator<Element> c = things.getDescendants(new ElementFilter("pos"));

            while(c.hasNext()) {
                Element e = c.next();

                if(pos.contains(e.getText())) {
                    Element eP = e.getParentElement();
                    if(eP.hasAttributes()) {
                        if(eP.getAttributeValue("Class").equalsIgnoreCase("ThingResource")) {
                            eP.getChild("stackCount").setText(stackCount);
                            continue;
                        }
                    }
                }
            }
            
            Notification.createInfoNotification("Updated all resources stack size to " + stackCount, 3000);
            
            XMLOutputter xmlOutput = new XMLOutputter();
            FileOutputStream fos = new FileOutputStream(xmlFile);

            xmlOutput.setFormat(Format.getRawFormat());
            xmlOutput.output(doc, fos);

            fos.close();

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

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("li"));

            // Adding blank line
            stockPiles.add("");

            while(c.hasNext()) {
                Element e = c.next();

                if(e.hasAttributes()) {
                    if(e.getAttributeValue("Class").equalsIgnoreCase("Zone_Stockpile")) {
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
            Element zm = rootNode.getChild("zoneManager");
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
                    break;
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
            Element things = rootNode.getChild("things");

            Iterator<Element> c = things.getDescendants(new ElementFilter("pos"));
            String data = "";
            int i = 0;

            while(c.hasNext()) {
                Element e = c.next();

                if(list.contains(e.getText())) {
                    Element eP = e.getParentElement();
                    if(eP.hasAttributes()) {
                        if(eP.getAttributeValue("Class").equalsIgnoreCase("ThingResource")) {
                            if(eP.getChildText("def") == null || eP.getChildText("stackCount") == null) {
                                continue;
                            }
                            data = data + e.getParentElement().getChildText("def") + "(" + i + ")" + ":"
                                    + e.getParentElement().getChildText("stackCount") + ":" + e.getText() + "#";
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
