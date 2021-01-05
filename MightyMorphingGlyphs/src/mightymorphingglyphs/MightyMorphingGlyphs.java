package mightymorphingglyphs;

import java.awt.*;
import java.awt.geom.Line2D;

public class MightyMorphingGlyphs {

    public static void main(String[] args) {
        Frame f = new ApplicationFrame() {
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Font font = new Font("Lucida Sans Regular", Font.PLAIN, 32);
                g2.setFont(font);
                
                g2.drawString("\u062e \u0644 \u0639", 40, 80);
                g2.drawString("\u062e\u0644\u0639", 40, 120);
            }
        };
        f.setVisible(true);
    }
}
