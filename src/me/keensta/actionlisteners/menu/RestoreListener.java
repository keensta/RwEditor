package me.keensta.actionlisteners.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import me.keensta.AppWindow;
import me.keensta.util.Notification;

public class RestoreListener implements ActionListener {

    private AppWindow app;

    public RestoreListener(AppWindow app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Rimworld", "rim");
        
        chooser.setDialogTitle("Choose RimWorld backup");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File(""));
        
        File backupFile = chooser.getSelectedFile();
        
        backupFile.renameTo(new File(backupFile.getName().replace(".bk", "")));
        app.getBackupHandler().restoreBackup(backupFile);
        
        Notification.createInfoNotification("Restore complete, press the save button.", 6000);
    }

}
