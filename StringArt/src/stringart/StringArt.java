package stringart;

import java.awt.*;
import java.awt.geom.*;

public class StringArt {

    public static void main(String[] args) {
        Frame f = new ApplicationFrame("StringArt v1.0") {
            private int mNumberOfLines= 25;
            private Color[] mColors = { Color.red, Color.green, Color.blue };
            
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                
                Dimension d = getSize();
                for (int i = 0; i < mNumberOfLines; i++) {
                    double ratio = (double)i / (double)mNumberOfLines;
                    Line2D line = new Line2D.Double(0, ratio * d.height, ratio * d.width, d.height);
                    g2.setPaint(mColors[i % mColors.length]);
                    g2.draw(line);
                }
            }
        };
        f.setSize(200, 200);
        f.setVisible(true);
    }
    
}
