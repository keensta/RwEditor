package me.keensta.UI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import me.keensta.AppWindow;
import me.keensta.util.AppPosition;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.window.WebProgressDialog;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.alee.utils.ThreadUtils;

@SuppressWarnings("serial")
public class AboutView extends JPanel {

    private AppWindow app;

    public AboutView(AppWindow app) {
        this.app = app;

        createPage();
    }

    public static void loadAboutPage(AppWindow app) {
        final WebProgressDialog progress = new WebProgressDialog("Some progress");
        progress.setText("Loading something...");

        // Starting updater thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i <= 100; i++) {
                    ThreadUtils.sleepSafely(125);
                    progress.setProgress(i);
                    if(i == 25) {
                        progress.setText("Ahh I'd just stop waiting");
                    } else if(i == 50) {
                        progress.setText("You expecting something special?");
                    } else if(i == 75) {
                        progress.setText("You're still here, Better start loading.");
                    } else if(i == 95) {
                        progress.setText("Stealing paypal details *Done*");
                    }
                }
                ThreadUtils.sleepSafely(1000);
                progress.setVisible(false);
            }
        }).start();

        // Displaying dialog
        progress.setModal(true);
        progress.setVisible(true);
        loadAbout(app);
    }

    private static void loadAbout(AppWindow app) {
        WebFrame frame = new WebFrame("About RwEditor");
        frame.setShowMaximizeButton(false);
        frame.setResizable(false);
        frame.setRound(10);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new AboutView(app));
        frame.pack();
        frame.setLocationRelativeTo(app);
        frame.setVisible(true);
    }

    public void createPage() {
        setLayout(null);
        setPreferredSize(new Dimension(450, 233));
        ImageIcon img = app.getImage().loadIcon("/icons/tempAbout.png");

        WebLabel version = new WebLabel("Version - Beta");
        version.setDrawShade(true);
        version.setShadeColor(new Color(244, 147, 11));

        WebLabel copyright = new WebLabel("Copyright © 2014");
        copyright.setDrawShade(true);
        copyright.setShadeColor(new Color(244, 147, 11));

        WebLabel author = new WebLabel("Author - Keensta");
        author.setDrawShade(true);
        author.setShadeColor(new Color(244, 147, 11));
        
        WebLabel testers = new WebLabel("Tester(s) - Swederell");
        testers.setDrawShade(true);
        testers.setShadeColor(new Color(244, 147, 11));

        WebDecoratedImage img1 = new WebDecoratedImage(img);
        TooltipManager.setTooltip(img1, "Current temp product icon", TooltipWay.up);

        WebLabel wl = new WebLabel(
                "<html>Hi, thanks for using RwEditor. This is built to help make savefile changing simpler.<br>"
                        + "All current features should work but do report any bugs. It already has loads of features and I plan to keep adding more.<br>"
                        + "<br> This is open source and is under the GPLv3 License. Please read up on the license before doing any modification on this program.</html>");

        add(version);
        add(copyright);
        add(author);
        add(testers);
        add(img1);
        add(wl);

        version.setBounds(AppPosition.GAMEVERSION_X, AppPosition.GAMEVERSION_Y, 100, 23);
        copyright.setBounds(AppPosition.COPYRIGHT_X, AppPosition.COPYRIGHT_Y, 100, 23);
        author.setBounds(AppPosition.AUTHOR_X, AppPosition.AUTHOR_Y, 100, 23);
        testers.setBounds(AppPosition.TESTERS_X, AppPosition.TESTERS_Y, 200, 23);
        img1.setBounds(AppPosition.IMG1_X, AppPosition.IMG1_Y, 128, 128);
        wl.setBounds(AppPosition.W1_X, AppPosition.W1_y, 319, 133);
    }
}
