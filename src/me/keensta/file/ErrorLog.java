package me.keensta.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class ErrorLog {
    
    PrintStream ps;
    
    public ErrorLog() {
       /* try {
            ps = new PrintStream(new File("RwEditor_Output.txt"));
            System.setErr(ps);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

}
