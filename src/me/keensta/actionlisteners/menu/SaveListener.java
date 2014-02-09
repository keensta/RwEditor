package me.keensta.actionlisteners.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import me.keensta.AppWindow;
import me.keensta.util.Notification;

public class SaveListener implements ActionListener {

    @SuppressWarnings("unused")
    private AppWindow app;

    public SaveListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*try {
            Document doc = app.getBuilder().build(app.getFile());

            FileWriter fw = new FileWriter(app.getFile());
            XMLOutputter xmlOutput = new XMLOutputter();

            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, fw);

            fw.close();

        } catch(IOException | JDOMException e1) {
            e1.printStackTrace();
        }
*/
        Notification.createInfoNotification("File Saved...", 5000);
    }
}
