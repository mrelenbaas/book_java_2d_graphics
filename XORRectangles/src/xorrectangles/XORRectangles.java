package xorrectangles;

import java.awt.*;
import java.awt.geom.*;

public class XORRectangles {

    public static void main(String[] args) {
        ApplicationFrame f = new ApplicationFrame("XORRectangles v1.0"){
            private int mNumberOfLines = 25;
            private Color[] mColors = { Color.red, Color.green, Color.blue };
            
            public void paint(Graphics g) {
                Graphics2D g2= (Graphics2D)g;
                
                g2.setXORMode(Color.white);
                
                Rectangle2D r = new Rectangle2D.Double(50, 50, 150, 100);
                g2.setPaint(Color.red);
                g2.fill(r);
                
                g2.transform(AffineTransform.getTranslateInstance(25, 25));
                g2.setPaint(Color.blue);
                g2.fill(r);
            }
        };
        f.setSize(300, 200);
        f.center();
        f.setVisible(true);
    }
    
}
