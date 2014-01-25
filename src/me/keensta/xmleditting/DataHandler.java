package me.keensta.xmleditting;

import java.io.File;
import java.io.IOException;

import me.keensta.AppWindow;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class DataHandler {

    @SuppressWarnings("unused")
    private AppWindow app;
    private File xmlFile;
    private SAXBuilder builder;

    public DataHandler(AppWindow app, File xmlFile, SAXBuilder builder) {
        this.app = app;
        this.xmlFile = xmlFile;
        this.builder = builder;
    }

    /**
     * Uses a string like "ColonyInfo/ColonyName" and will obtain the string at
     * ColonyName
     * 
     * @param dataPath
     *            Loction of string in xml file ex "ColonyInfo/ColonyName".
     * @return String from xml that was requested.
     */
    public String getDataString(String dataPath) {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            String[] dataNames = dataPath.trim().split("/");
            Element e = rootNode;

            for(String data : dataNames) {
                e = e.getChild(data);
            }

            return e.getText();

        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
        return dataPath;
    }
    
    public int getDataInt(String dataPath) {
        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            String[] dataNames = dataPath.trim().split("/");
            Element e = rootNode;

            for(String data : dataNames) {
                if(e.getChild(data) == null)
                    return 0;
                e = e.getChild(data);
            }
            
            if(e.getText() == null)
                return 0;

            return Integer.parseInt(e.getText());

        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
