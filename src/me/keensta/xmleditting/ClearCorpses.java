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

public class ClearCorpses {

    private File xmlFile;
    private SAXBuilder builder;
    private AppWindow app;

    public ClearCorpses(File xmlFile, SAXBuilder builder, AppWindow app) {
        this.xmlFile = xmlFile;
        this.builder = builder;
        this.app = app;
    }

    public void activateCode() {
        try {
            xmlFile = app.getFile();
            
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("def"));
            List<Element> markedToBeRemoved = new ArrayList<Element>();

            while(c.hasNext()) {
                Element e = c.next();
                
                if(e.getValue().contains("Corpse")) {
                    if(e.getParentElement().getName().equalsIgnoreCase("thing")) {
                        markedToBeRemoved.add(e.getParentElement());
                    }
                }
            }

            for(int i = 0; i < markedToBeRemoved.size(); i++) {
                Element e = markedToBeRemoved.get(i);
                e.getParentElement().removeContent(e);
            }

            ClearBlood cb = new ClearBlood(xmlFile, builder);
            cb.activateCode();

            Notification.createInfoNotification("Blood & Corpse(s) removed", 3000);

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

}
