package transparenttext;

import java.awt.*;
import java.awt.geom.*;

public class TransparentText {

    public static void main(String[] args) {
        ApplicationFrame f = new ApplicationFrame("TransparentText v1.0") {
            private int mNumberOfLines = 25;
            private Color[] mColors = { Color.red, Color.green, Color.blue };
            
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Rectangle2D r = new Rectangle2D.Double(50, 50, 150, 100);
                g2.setPaint(Color.red);
                g2.fill(r);
                
                Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4f);
                
                g2.setComposite(c);
                
                g2.setPaint(Color.blue);
                g2.setFont(new Font("Times New Roman", Font.PLAIN, 72));
                g2.drawString("Composite", 25, 130);
            }
        };
        f.setSize(400, 200);
        f.center();
        f.setVisible(true);
    }
    
}
