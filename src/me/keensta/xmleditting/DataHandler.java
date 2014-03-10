package me.keensta.xmleditting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.keensta.AppWindow;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;

public class DataHandler {

    private AppWindow app;
    private File file;
    private SAXBuilder builder;

    private File[] files;
    
    public DataHandler(AppWindow app, File xmlFile, File loadedMods, SAXBuilder builder) {
        this.app = app;
        this.builder = builder;
        
        this.files = new File[] {xmlFile, loadedMods};
    }
    
    /**
     * Gets the instance of the File so we can use it. From using a the files name
     * @param fileName The files name.
     * @return File The file we were looking for.
     * @throws FileNotFoundException If it happens the file doesn't exist do this..
     */
    private File getFile(String fileName) throws FileNotFoundException {
        
        for(File f : files) {
            if(f.getName().equals(fileName))
                return f;
        }
        
        throw new FileNotFoundException("Seems like I used a bad filename " + " FIlENAME: " + fileName);
    }

    /**
     * Uses a String as a location to get the tags Data as a string
     * ColonyName
     * 
     * @param dataPath Loction of tag in xml file ex "ColonyInfo/ColonyName".
     * @param fileName File on which you wish to edit
     * @return String String found at tag of given location
     */
    public String getDataString(String dataPath, String fileName) {
        try {
            file = getFile(fileName);
            
            Document doc = builder.build(file);
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
    
    /**
     * Uses a String as a location to get the tags Data then casted to INT
     * ColonyName
     * 
     * @param dataPath Loction of tag in xml file ex "ColonyInfo/ColonyName".
     * @param fileName File on which you wish to edit
     * @return int Int found at tag of given location
     */
    public int getDataInt(String dataPath, String fileName) {
        try {
            file = getFile(fileName);
            
            Document doc = builder.build(file);
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
    
    /**
     * Uses a String as a location to get the tags Data list.
     * 
     * @param dataPath Loction of tag in xml file ex "ColonyInfo/ColonyName".
     * @param fileName File on which you wish to edit
     * @return List<String> String List found at tag of given location
     */
    public List<String> getDataList(String dataPath, String fileName){
        List<String> dataList = new ArrayList<String>();
        
        try {
            file = getFile(fileName);
            
            Document doc = builder.build(file);
            Element rootNode = doc.getRootElement();

            String[] dataNames = dataPath.trim().split("/");
            Element e = rootNode;
            
            for(String data : dataNames) {
                if(e.getChild(data) == null)
                    return dataList;
                
                e = e.getChild(data);
            }
            

            Iterator<Element> c = e.getDescendants(new ElementFilter("li"));

            while(c.hasNext()) {
                
               dataList.add(c.next().getText());
                
            }
            
            return dataList;
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
        
        return dataList;
    }
    
    /**
     * Sets the tags data at given String location
     * 
     * @param dataPath Loction of tag in xml file ex "ColonyInfo/ColonyName".
     * @param fileName File on which you wish to edit
     * @param value The new value you wish to replace existing one with
     */
    public void setData(String dataPath, String value, String fileName) {
        try {
            file = getFile(fileName);
            
            Document doc = builder.build(file);
            Element rootNode = doc.getRootElement();

            String[] dataNames = dataPath.trim().split("/");
            Element e = rootNode;

            for(String data : dataNames) {
                if(e.getChild(data) == null)
                    return;
                
                if(dataNames.length == dataNames.length)
                    e.getChild(data).setText(value);
            }
            
            if(e.getText() == null)
                return;
            
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
    }

}
