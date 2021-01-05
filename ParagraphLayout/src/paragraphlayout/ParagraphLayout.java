package paragraphlayout;

import java.awt.*;
import java.awt.font.*;
import java.text.*;

public class ParagraphLayout {

    public static void main(String[] args) {
        Frame f = new ApplicationFrame("ParagraphLayout v1.0") {
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                String s = "Jos\u00e9 Arcadio Buend\u00eda spent the long months " +
                        "of the rainy season shut up in a small room that he " + 
                        "had built in the rear of the house so that no one...";
                Font font = new Font("Serif", Font.PLAIN, 24);
                AttributedString as = new AttributedString(s);
                as.addAttribute(TextAttribute.FONT, font);
                AttributedCharacterIterator aci = as.getIterator();

                FontRenderContext frc = g2.getFontRenderContext();
                LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
                Insets insets = getInsets();
                float wrappingWidth = getSize().width - insets.left - insets.right;
                float x = insets.left;
                float y = insets.top;
                
                while (lbm.getPosition() < aci.getEndIndex()) {
                    TextLayout textLayout = lbm.nextLayout(wrappingWidth);
                    y += textLayout.getAscent();
                    textLayout.draw(g2, x, y);
                    y += textLayout.getDescent() + textLayout.getLeading();
                    x = insets.left;
                }
            }
        };
        f.setVisible(true);
    }
}
