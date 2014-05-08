package me.keensta.colonists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Traits {

    private File traitFile = new File("traits.txt");
    private List<String> traits = new ArrayList<String>();

    public Traits() {
        traits.add("");
        
        loadTraits();
    }

    private void loadTraits() {
        System.out.println("TRAITS LOADING DATA START");
        
        try {
            @SuppressWarnings("resource")
            BufferedReader br = new BufferedReader(new FileReader(traitFile));
            String line = null;

            while((line = br.readLine()) != null) {
                traits.add(line);
                System.out.println("Triat: " + line + " has been added");
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("TRAITS LOADING DATA END" + System.getProperty("line.separator"));
    }

    public Object[] getTraits() {
        return traits.toArray();
    }

}
