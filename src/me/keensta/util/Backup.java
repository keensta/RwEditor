package me.keensta.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Backup {
    
    private File xmlFileBK;
    
    public Backup() {
        
    }
    
    public void createBackup() {
        try {
            /*File targetDir = new File(getCurrentDirectory(System.getProperty("os.name")));
            
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
            
            if(bk.renameTo(new File(targetDir+ bk.getName()))) {
                System.out.println(new File(targetDir+ bk.getName()));
                System.out.println("Made");
            } else {
                System.out.println("Failed");
            }
            */
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setBackupFile(File xmlFile) {
        this.xmlFileBK = xmlFile;
    }
    
    public File getBackupFile() {
        return xmlFileBK;
    }
    
    private String getCurrentDirectory(String sn) {
        if(sn.contains("Windows"))
            return "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\LocalLow\\Ludeon Studios\\RimWorld\\Saves\\RWEditor\\ ";
        else if(sn.contains("OS X"))
            return "//Users//" + System.getProperty("user.name") + "//Library//Caches//unity.Ludeon Studios.RimWorld//Saves//RWEditor// ";
        return "";
    }

}
