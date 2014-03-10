package me.keensta.xmleditting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.keensta.AppWindow;
import me.keensta.util.Notification;

import org.apache.commons.codec.binary.Base64;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class DeleteRubbish {

    private File xmlFile;
    private SAXBuilder builder;
    private AppWindow app;
    
    public DeleteRubbish(File xmlFile, SAXBuilder builder, AppWindow app) {
        this.xmlFile = xmlFile;
        this.builder = builder;
        this.app = app;
    }

    public void activateCode() {
        try {
            xmlFile = app.getFile();
            
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            String string = rootNode.getChildText("compressedThingMap");

            // Get bytes from string
            byte[] byteArray = Base64.decodeBase64(string.getBytes());

            for(int i = 0; i < byteArray.length; i++) {
                if(byteArray[i] == 56 || byteArray[i] == 57) {
/*                    int x = i%200;
                    int y = i/200;
                    System.out.println("X: " + x + " Y: " + y);*/
                    byteArray[i] = 0;
                }
            }

            String newCMT = new String(Base64.encodeBase64(byteArray));

            rootNode.getChild("compressedThingMap").setText(newCMT);

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("def"));
            List<Element> markedToBeRemoved = new ArrayList<Element>();

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getValue().equalsIgnoreCase("SandbagRubble") || e.getValue().equalsIgnoreCase("FilthSand")
                        || e.getValue().equalsIgnoreCase("FilthDirt") || e.getValue().equalsIgnoreCase("DebrisSlag")
                        || e.getValue().equalsIgnoreCase("RockRubble")) {
                    if(e.getParentElement().getName().equalsIgnoreCase("thing")) {
                        markedToBeRemoved.add(e.getParentElement());
                    }
                }
            }

            for(int i = 0; i < markedToBeRemoved.size(); i++) {
                Element e = markedToBeRemoved.get(i);
                e.getParentElement().removeContent(e);
            }

            Notification.createInfoNotification("All rubbish has been removed", 3000);

            XMLOutputter xmlOutput = new XMLOutputter();
            FileWriter fw = new FileWriter(xmlFile);

            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, fw);

            fw.close();
            
            app.setFile(xmlFile);
            
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
    }

    public String[] splitStringAtIndex(int i, String text) {
        String[] newString = { "" };
        int index = 0;
        int j = 0;

        while(index < text.length()) {
            newString[j] = text.substring(index, Math.min(index + i, text.length()));
            j += 1;
        }
        return newString;
    }

    public String rejoinString(String[] text) {
        String newString = null;
        for(String s : text) {
            newString = newString + s;
        }
        return newString.trim();
    }

}
