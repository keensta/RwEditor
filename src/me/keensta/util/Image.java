package me.keensta.util;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Image {

    private static final Map<String, ImageIcon> iconsCache = new HashMap<String, ImageIcon>();

    public ImageIcon loadIcon(String path) {
        return loadIcon(getClass(), path);
    }

    @SuppressWarnings("rawtypes")
    public ImageIcon loadIcon(Class nearClass, String path) {
        String key = nearClass.getCanonicalName() + ":" + path;
        if(!iconsCache.containsKey(key)) {
            iconsCache.put(key, new ImageIcon(nearClass.getResource(path)));
        }
        return iconsCache.get(key);
    }

}
