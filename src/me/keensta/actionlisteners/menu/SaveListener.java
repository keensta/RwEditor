package me.keensta.actionlisteners.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import me.keensta.AppWindow;
import me.keensta.util.Notification;

public class SaveListener implements ActionListener {

    private AppWindow app;

    public SaveListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Document doc = app.getBuilder().build(app.getFile());
            app.getDataHandler().setData("mapInfo/name", app.getGameInfo().getColonyName().getText(), app.getFile().getName());

            XMLOutputter xmlOutput = new XMLOutputter();
            FileOutputStream fos = new FileOutputStream(app.getFile());

            xmlOutput.setFormat(Format.getRawFormat());
            xmlOutput.output(doc, fos);

            fos.close();

        } catch(IOException | JDOMException e1) {
            e1.printStackTrace();
        }

        Notification.createInfoNotification("File Saved...", 5000);
    }
}
