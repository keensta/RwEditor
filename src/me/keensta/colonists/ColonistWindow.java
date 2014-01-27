package me.keensta.colonists;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.alee.laf.rootpane.WebFrame;

import me.keensta.AppWindow;
import me.keensta.UI.AboutView;
import me.keensta.UI.ColonistView;

@SuppressWarnings("unused")
public class ColonistWindow extends JPanel {

    private static final long serialVersionUID = 2L;
    private AppWindow app;

    private ColonistView cv;

    public ColonistWindow(AppWindow app) {
        setPreferredSize(new Dimension(560, 400));
        setLayout(null);

        this.app = app;
        cv = new ColonistView(app, this);
        
        cv.createWindow();
    }

    public static void main(AppWindow app) {
        WebFrame frame = new WebFrame("Colonist Editor");
        frame.setShowMaximizeButton(false);
        frame.setResizable(false);
        frame.setRound(10);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new ColonistWindow(app));
        frame.pack();
        frame.setLocationRelativeTo(app);
        frame.setVisible(true);
    }
    
    public ColonistView getColonistView() {
        return this.cv;
    }

}
