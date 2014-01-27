package me.keensta.actionlisteners.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import me.keensta.AppWindow;

public class LoadListener implements ActionListener {

    private AppWindow app;
    boolean loadedBefore = false;
    
    public LoadListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Rimworld", "rim");
        String systemName = System.getProperty("os.name");
        
        chooser.setDialogTitle("Choose RimWorld Save");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(getCurrentDirectory(systemName)));

        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
                app.setXmlFile(chooser.getSelectedFile());
                app.makeVisible((loadedBefore ? 1 : 0));
                loadedBefore = true;
        }
    }

    private String getCurrentDirectory(String sn) {
        if(sn.contains("Windows"))
            return "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\LocalLow\\Ludeon Studios\\RimWorld\\Saves\\";
        else if(sn.contains("OS X"))
            return "//Users//" + System.getProperty("user.name") + "//Library//Caches//unity.Ludeon Studios.RimWorld//Saves//";
        return "";
    }

}
