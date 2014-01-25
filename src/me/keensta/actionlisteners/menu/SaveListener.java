package me.keensta.actionlisteners.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import me.keensta.AppWindow;
import me.keensta.util.Notification;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class SaveListener implements ActionListener {

    private AppWindow app;

    public SaveListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Document doc = app.getBuilder().build(app.getFile());

            FileWriter fw = new FileWriter(app.getFile());
            XMLOutputter xmlOutput = new XMLOutputter();

            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, fw);

            fw.close();

        } catch(IOException | JDOMException e1) {
            e1.printStackTrace();
        }

        Notification.createInfoNotification("File Saved...", 5000);
    }
}
