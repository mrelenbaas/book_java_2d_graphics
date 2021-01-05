package iteratorunderstrike;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.*;

public class IteratorUnderStrike {

    public static void main(String[] args) {
        Frame f = new ApplicationFrame("IteratorUnderStrike v1.0") {
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                
                String s = "\"Click here,\" she said purred.";
                
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Font plainFont = new Font("Times New Roman", Font.PLAIN, 24);
                AttributedString as = new AttributedString(s);
                as.addAttribute(TextAttribute.FONT, plainFont);
                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 1, 11);
                as.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON, 18, 22);
                
                g2.drawString(as.getIterator(), 24, 70);
            }
        };
        f.setVisible(true);
    }
    
}
