package me.keensta.UI;

import me.keensta.AppWindow;
import me.keensta.actionlisteners.menu.AboutListener;
import me.keensta.actionlisteners.menu.LoadListener;
import me.keensta.actionlisteners.menu.SaveListener;
import me.keensta.util.AppPosition;

import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.MenuBarStyle;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.text.WebTextField;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;

public class Menu {

    private AppWindow app;

    private LoadListener ll;
    private SaveListener sl;
    private AboutListener about;

    public WebMenuItem load;
    public WebMenuItem save;
    

    public WebLabel saveFile = new WebLabel("Save File");
    public WebTextField saveFileDir = new WebTextField();


    public Menu(AppWindow app) {
        this.app = app;

        ll = new LoadListener(app);
        sl = new SaveListener(app);
        about = new AboutListener(app);
    }

    public WebMenuBar createMenu(WebMenuBar menuVar) {
        menuVar = new WebMenuBar();
        menuVar.setMenuBarStyle(MenuBarStyle.standalone);
        setUpSaveArea();
        return setupMenuBar(menuVar);
    }
    
    @SuppressWarnings("serial")
    private WebMenuBar setupMenuBar(WebMenuBar menuVar) {

        menuVar.add(new WebMenu("File", app.getImage().loadIcon("/icons/file.png")) {
            {
                add(load = new WebMenuItem("Load", app.getImage().loadIcon("/icons/load.png")) {
                    {
                        addActionListener(ll);
                    }
                });

                add(save = new WebMenuItem("Save", app.getImage().loadIcon("/icons/save.png")) {
                    {
                        addActionListener(sl);
                        setEnabled(false);
                    }
                });
            }
        });

        menuVar.add(new WebMenuItem("About") {
            {
                addActionListener(about);
            }
        });

        return menuVar;
    }
    
    private void setUpSaveArea() {
        saveFile.setDrawShade(true);
        TooltipManager.setTooltip(saveFile, "Select a save file", TooltipWay.trailing, 0);
        saveFileDir.setEditable(false);
        
        app.add(saveFile);
        app.add(saveFileDir);
        
        saveFile.setBounds(AppPosition.SAVEFILE_X, AppPosition.SAVEFILE_Y, 55, 25);
        saveFileDir.setBounds(AppPosition.SAVEFILEDIR_X, AppPosition.SAVEFILEDIR_Y, 517, 23);
    }


}
