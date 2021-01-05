package victorvector;

import java.awt.*;
import java.awt.font.*;

public class VictorVector {

    public static void main(String[] args) {
        Frame f = new ApplicationFrame("VictorVector v1.0") {
            public void paint (Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                String s = "What's our vector, victor?";
                Font font = new Font("Serif", Font.PLAIN, 24);
                FontRenderContext frc = g2.getFontRenderContext();
                
                GlyphVector gv = font.createGlyphVector(frc, s);
                g2.drawGlyphVector(gv, 40, 60);
            }
        };
        f.setVisible(true);
    }
}
