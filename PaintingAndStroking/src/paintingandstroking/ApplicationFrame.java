/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paintingandstroking;

import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author mrele
 */
public class ApplicationFrame extends Frame{
    
    public ApplicationFrame() { this("Application Frame v1.0"); }
    
    public ApplicationFrame(String title) {
        super(title);
        createUI();
        System.out.println("Constructor complete.");
    }
    
    protected void createUI() {
        setSize(500, 400);
        center();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }
    
    public void center() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        setLocation(x, y);
    }
}
