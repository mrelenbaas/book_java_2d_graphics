package horizontallycenteredtext;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;

public class HorizontallyCenteredText {

    public static void main(String[] args) {
        Frame frame = new ApplicationFrame("HorizontallyCenteredText v1.0") {
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setFont(new Font("Serif", Font.PLAIN, 48));
                
                paintHorizontallyCenteredText(g2, "come", 100, 75);
                paintHorizontallyCenteredText(g2, "and", 100, 125);
                paintHorizontallyCenteredText(g2, "play", 100, 175);
            }
            
            protected void paintHorizontallyCenteredText(Graphics2D g2, String s, float centerX, float baselineY) {
                FontRenderContext frc = g2.getFontRenderContext();
                Rectangle2D bounds = g2.getFont().getStringBounds(s, frc);
                float width = (float)bounds.getWidth();
                g2.drawString(s, centerX - width / 2, baselineY);
            }
        };
        frame.setVisible(true);
    }
}
