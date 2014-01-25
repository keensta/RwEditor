package me.keensta.xmleditting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class DeleteRubbish {

    File xmlFile;
    SAXBuilder builder;

    public DeleteRubbish(File xmlFile, SAXBuilder builder) {
        this.xmlFile = xmlFile;
        this.builder = builder;
    }

    public void activateCode() {
        try {
            // TODO: Create it so It can decode CompresedThingMap correctly.
            // (Working on it)
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            // Gets Rid of Rock Debris
            String string = rootNode.getChildText("CompressedThingMap");

            // Get bytes from string
            byte[] byteArray = Base64.decodeBase64(string.getBytes());

            for(int i = 0; i < byteArray.length; i++) {
                if(byteArray[i] == 55 || byteArray[i] == 56) {
                    byteArray[i] = 0;
                }
            }

            String newCMT = new String(Base64.encodeBase64(byteArray));

            rootNode.getChild("CompressedThingMap").setText(newCMT);

            // Gets Rid of Slag Debris
            Iterator<Element> c = rootNode.getDescendants(new ElementFilter("Def"));
            List<Element> markedToBeRemoved = new ArrayList<Element>();

            while(c.hasNext()) {
                Element e = c.next();

                if(e.getValue().equalsIgnoreCase("SandbagRubble") || e.getValue().equalsIgnoreCase("FilthSand")
                        || e.getValue().equalsIgnoreCase("FilthDirt")) {
                    if(e.getParentElement().getName().equalsIgnoreCase("Thing")) {
                        markedToBeRemoved.add(e.getParentElement());
                    }
                }
            }

            for(int i = 0; i < markedToBeRemoved.size(); i++) {
                markedToBeRemoved.get(i).getParentElement().removeContent(markedToBeRemoved.get(i));
            }

            JOptionPane.showMessageDialog(null, "All Rubbish Removed", "InfoBox", JOptionPane.INFORMATION_MESSAGE);

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
