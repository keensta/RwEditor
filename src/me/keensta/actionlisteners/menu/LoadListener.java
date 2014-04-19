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
        
        chooser.setDialogTitle("Choose RimWorld Save");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(app.getPref().getRWDirectory().toString() + "//Saves//"));

        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
                app.setFiles(chooser.getSelectedFile(), getModsLoadedFile(chooser.getCurrentDirectory()));
                app.makeVisible((loadedBefore ? 1 : 0));
                loadedBefore = true;
        }
    }

    private File getModsLoadedFile(File currentDirectory) {
        String newDir = currentDirectory.getAbsolutePath().replace("Saves", "");
        File modsFile = new File(newDir + "//Config//" + "ModsConfig.xml");

        return modsFile;
    }



}
