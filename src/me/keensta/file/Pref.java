package me.keensta.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Pref {

    // Holds any data needed when program reopens...
    private File prefFile = new File("pref.xml");
    private SAXBuilder builder;
    
    private File MODDIRECTORY = new File("");

    public Pref(SAXBuilder builder) {
        this.builder = builder;
    }

    public void savePref() {
        try {
            //File bk = new File("pref.xml");
            
            setData();
            
            /*InputStream in = new FileInputStream(prefFile);
            OutputStream out = new FileOutputStream(bk);

            byte[] buffer = new byte[1024];

            int length;
            while((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();*/
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        try {
            Document doc = builder.build(prefFile);
            
            Element rootNode = doc.getRootElement();
            Element modDir = new Element("ModDir");
            
            rootNode.getChild("firstLoad").setText("no");
            modDir.setText(MODDIRECTORY.getAbsolutePath());
            
            rootNode.addContent(modDir);
            
            XMLOutputter xmlOutput = new XMLOutputter();
            FileWriter fw = new FileWriter(prefFile);

            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, fw);

            fw.close();
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
    }
    
    public void loadData() {
        try {
            Document doc = builder.build(prefFile);
            Element rootNode = doc.getRootElement();
            
            if(rootNode.getChildText("firstLoad").equals("yes")) {
                return;
            }
            
            MODDIRECTORY = new File(rootNode.getChildText("ModDir"));
            
        } catch(IOException io) {
            io.printStackTrace();
        } catch(JDOMException e) {
            e.printStackTrace();
        }
    }
    
    public File getModDirectory() {
        return MODDIRECTORY;
    }

    public void setModDirectory(File MODDIRECTORY) {
        this.MODDIRECTORY = MODDIRECTORY;
    }

}
