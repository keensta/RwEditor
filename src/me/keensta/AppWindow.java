package me.keensta;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import me.keensta.UI.GameInfo;
import me.keensta.UI.Menu;
import me.keensta.UI.PawnEditting;
import me.keensta.UI.Resources;
import me.keensta.UI.World;
import me.keensta.colonists.WeaponHandler;
import me.keensta.file.Backup;
import me.keensta.file.ErrorLog;
import me.keensta.file.ModLoader;
import me.keensta.file.Pref;
import me.keensta.util.AppPosition;
import me.keensta.util.Image;
import me.keensta.xmleditting.ClearBlood;
import me.keensta.xmleditting.ClearCorpses;
import me.keensta.xmleditting.ClearWeapons;
import me.keensta.xmleditting.ConvertRaiders;
import me.keensta.xmleditting.DataHandler;
import me.keensta.xmleditting.DeleteRaiders;
import me.keensta.xmleditting.DeleteRubbish;
import me.keensta.xmleditting.EditResources;
import me.keensta.xmleditting.SpawnGeyser;

import org.jdom2.input.SAXBuilder;

import com.alee.extended.painter.DashedBorderPainter;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.rootpane.WebFrame;

public class AppWindow extends JPanel {

    private static final long serialVersionUID = 1L;

    private JFrame frame;

    // Gui stuff
    private WebMenuBar menuVar;
    private WebLabel borderLabel = new WebLabel();
    
    // Classes
    //TODO: Make sure all these classes use the AppPostion class.
    private Menu menu;
    private Image img = new Image();
    private GameInfo gameInfo;
    private Resources res;
    private World world;
    private PawnEditting pawnEdit;
    
    //File Classes
    private Backup bk;
    @SuppressWarnings("unused")
    private ErrorLog el;
    @SuppressWarnings("unused")
    private ModLoader ml;
    private Pref pref;
    
    //Editing classes
    private ClearBlood cb;
    private ClearCorpses cc;
    private ClearWeapons cw;
    private ConvertRaiders cr;
    private DeleteRaiders draid;
    private DeleteRubbish dr;
    private EditResources er;
    private SpawnGeyser sg;
    private WeaponHandler wh;
    
    // Xml Stuff
    private SAXBuilder builder = new SAXBuilder();
    private File xmlFile;
    private File modsFile;
    private DataHandler dataHandler;

    public static void main(String[] args) {
        try {
            // Setting up WebLookAndFeel style : http://weblookandfeel.com/
            UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
        } catch(Throwable e) {
            e.printStackTrace();
        }
        WebLookAndFeel.setDecorateFrames(true);

        WebFrame frame = new WebFrame("Rimworld Editor");
        frame.setShowMaximizeButton(false);
        frame.setResizable(false);
        frame.setRound(10);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new AppWindow(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public AppWindow(JFrame frame) {
        this.frame = frame;

        // Load Needed Classes
        menu = new Menu(this);
        pref = new Pref(builder);
        el = new ErrorLog();
        wh = new WeaponHandler();

        // BuildComponents
        menuVar = menu.createMenu(menuVar);
        pref.loadData();
        pref.checkDirectory();
        
        // Adjust size and set layout and make border
        setPreferredSize(new Dimension(580, 310));
        setLayout(null);
        setBorder();
        
        // Add components
        add(menuVar);

        // Set component bounds (only needed by Absolute Positioning)
        menuVar.setBounds(AppPosition.MENUBAR_X, AppPosition.MENUBAR_Y, 580, 25);
    }

    //TODO: Move to menu class might be more fitting for this?
    @SuppressWarnings("rawtypes")
    private void setBorder() {
        DashedBorderPainter dbp = new DashedBorderPainter();
        dbp.setColor(Color.GRAY);
        dbp.setWidth(2);
        dbp.setDashPhase(3);
        borderLabel.setPainter(dbp);

        add(borderLabel);

        borderLabel.setBounds(0, 26, 580, 284);
    }

    public void makeVisible(int i) {
        menu.save.setEnabled(true);
        menu.backup.setEnabled(true);
        //menu.restore.setEnabled(true);
        
        if(i == 0) {
            gameInfo = new GameInfo(this);
            res = new Resources(this);
            world = new World(this);
            pawnEdit = new PawnEditting(this);
            
            gameInfo.BuildComponents();
            res.BuildComponents();
            world.BuildComponents();
            pawnEdit.BuildComponents();
        } else {
            gameInfo.updateComponents();
            res.updateComponents();
        }
        
    }
    
    // Following code doesn't directly effect Gui.
    public void setFiles(File file, File modsFile) {
        xmlFile = file;
        this.modsFile = modsFile;
        
        menu.saveFileDir.setText(file.getAbsolutePath());
        
        dataHandler = new DataHandler(xmlFile, this.modsFile, builder);
        bk = new Backup(this);
        ml = new ModLoader(this, builder);
        
        bk.setBackupFile(xmlFile);
        
        intilizeClasses();
    }

    private void intilizeClasses() {
        cc = new ClearCorpses(xmlFile, builder, this);
        cw = new ClearWeapons(xmlFile, builder, this);
        cr = new ConvertRaiders(xmlFile, builder, this);
        draid = new DeleteRaiders(xmlFile, builder, this);
        dr = new DeleteRubbish(xmlFile, builder, this);
        er = new EditResources(xmlFile, builder, this);
        sg = new SpawnGeyser(xmlFile, builder, this);
    }

    public JFrame getFrame() {
        return frame;
    }

    public SAXBuilder getBuilder() {
        return builder;
    }
    
    public void setFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public File getFile() {
        return xmlFile;
    }
    
    public File getModFile() {
        return modsFile;
    }
    
    public Pref getPref() {
        return pref;
    }
    
    public WeaponHandler getWeaponHandler() {
        return wh;
    }
    
    public Backup getBackupHandler() {
        return bk;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }
    
    public Image getImage() {
        return img;
    }
    
    public Resources getRes() {
        return res;
    }
    
    public ClearBlood getClearBlood() {
        return cb;
    }
    
    public ClearCorpses getClearCorpses() {
        return cc;
    }
    
    public ClearWeapons getClearWeapons() {
        return cw;
    }
    
    public ConvertRaiders getConvertRaiders() {
        return cr;
    }
    
    public DeleteRaiders getDeleteRaiders() {
        return draid;
    }
    
    public DeleteRubbish getDeleteRubbish() {
        return dr;
    }
    
    public EditResources getEditResources() {
        return er;
    }

    public SpawnGeyser getSpawnGeyser() {
        return sg;
    }

    public void setSpawnGeyser(SpawnGeyser sg) {
        this.sg = sg;
    }
    
    public GameInfo getGameInfo() {
        return gameInfo;
    }
}
