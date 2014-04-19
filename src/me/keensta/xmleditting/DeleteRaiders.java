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

public class DeleteRaiders {

    private File xmlFile;
    private SAXBuilder builder;
    private AppWindow app;

    public DeleteRaiders(File xmlFile, SAXBuilder builder, AppWindow app) {
        this.xmlFile = xmlFile;
        this.builder = builder;
        this.app = app;
    }

    public void activateCode() {
        try {
            xmlFile = app.getFile();
            
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("team"));
            List<Element> markedToBeRemoved = new ArrayList<Element>();

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getValue().equalsIgnoreCase("Raider")) {
                    if(e.getParentElement().getName().equalsIgnoreCase("thing")) {
                        markedToBeRemoved.add(e.getParentElement());
                    }
                }
            }

            for(int i = 0; i < markedToBeRemoved.size(); i++) {
                Element e = markedToBeRemoved.get(i);
                e.getParentElement().removeContent(e);
            }

            Notification.createInfoNotification("Raider(s) Removed: " + markedToBeRemoved.size(), 3000);

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

    /*
     * SAXBuilder builder = new SAXBuilder(); File xmlFile = new File(
     * "C:\\Users\\Daniel\\AppData\\LocalLow\\Ludeon Studios\\RimWorld\\JavaHacked.rim"
     * );
     * 
     * Document doc = (Document) builder.build(xmlFile); Element rootNode =
     * doc.getRootElement();
     * 
     * // update staff id attribute Element nodes =
     * rootNode.getChild("Resources"); staff.getAttribute("id").setValue("2");
     * 
     * // add new age element Element age = new Element("age").setText("28");
     * staff.addContent(age);
     * 
     * // update salary value staff.getChild("salary").setText("7000");
     * 
     * // remove firstname element staff.removeChild("firstname");
     * 
     * XMLOutputter xmlOutput = new XMLOutputter();
     * 
     * // display nice nice xmlOutput.setFormat(Format.getPrettyFormat());
     * xmlOutput.output(doc, new FileWriter("c:\\file.xml"));
     * 
     * // xmlOutput.output(doc, System.out);
     * 
     * System.out.println("File updated!"); } catch (IOException io) {
     * io.printStackTrace(); } catch (JDOMException e) { e.printStackTrace(); }
     */

}
