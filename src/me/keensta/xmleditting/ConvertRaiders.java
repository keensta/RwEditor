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

public class ConvertRaiders {

    private File xmlFile;
    private SAXBuilder builder;

    public ConvertRaiders(File xmlFile, SAXBuilder builder) {
        this.xmlFile = xmlFile;
        this.builder = builder;
    }

    public void activateCode(double percentage) {
        // Will implement some thing allowing you to convert 20%, 50% so on..
        percentage = 100;
        // TODO: add door key item to newly joined colonist.
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("Team"));
            List<Element> markedToBeChanged = new ArrayList<Element>();

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getValue().equalsIgnoreCase("Raider")) {
                    if(e.getParentElement().getName().equalsIgnoreCase("Thing")) {
                        markedToBeChanged.add(e);
                    }
                }
            }

            for(int i = 0; i < markedToBeChanged.size(); i++) {
                Element e = markedToBeChanged.get(i);

                e.setText("Colonist");
                e.getParentElement().getChild("Kind").setText("Colonist");
                e.getParentElement().removeChild("Jobs");
            }

            Notification.createInfoNotification("Raider(s) Converted: " + markedToBeChanged.size(), 3000);

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
