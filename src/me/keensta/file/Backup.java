package me.keensta.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import me.keensta.AppWindow;

public class Backup {
    
    private File xmlFileBK;
    private AppWindow app;
    
    public Backup(AppWindow app) {
        this.app = app;
    }
    
    public void createBackup() {
        try {
            File targetDir = new File("//Backup");
            
            if(!targetDir.exists())
                targetDir.mkdir();
            
            File bk = new File(getBackupFile().getName()+".bk");
            InputStream in = new FileInputStream(getBackupFile());
            OutputStream out = new FileOutputStream(bk);
            
            byte[] buffer = new byte[1024];
            
            int length;
            while((length = in.read(buffer)) > 0) {
                out.write(buffer, 0 , length);
            }
            
            in.close();
            out.close();
            
            if(bk.renameTo(new File(targetDir + bk.getName()))) {
                System.out.println(new File(targetDir+ bk.getName()));
            } 
            
            setBackupFile(bk);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void restoreBackup(File xmlFile) {
        app.setFiles(xmlFile, null);
    }
    
    public void setBackupFile(File xmlFile) {
        this.xmlFileBK = xmlFile;
    }
    
    public File getBackupFile() {
        return xmlFileBK;
    }
    
}
