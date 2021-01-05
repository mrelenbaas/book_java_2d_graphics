package inlinegraphics;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.Ellipse2D;
import java.text.*;

public class InlineGraphics{

    public static void main(String[] args) {
        Frame f = new ApplicationFrame("InlineGraphics v1.0") {
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Font serifFont = new Font("Serif", Font.PLAIN, 32);
                
                AttributedString as = new AttributedString("Star \ufffc pin");
                as.addAttribute(TextAttribute.FONT, serifFont);
                
                String filename = "roa2.jpg";
                Image image = new javax.swing.ImageIcon(filename).getImage();
                ImageGraphicAttribute imageAttribute = new ImageGraphicAttribute(image, GraphicAttribute.TOP_ALIGNMENT);
                as.addAttribute(TextAttribute.CHAR_REPLACEMENT, imageAttribute, 5, 6);
                g2.drawString(as.getIterator(), 20, 120);
                
                as = new AttributedString("Red \ufffc circle");
                as.addAttribute(TextAttribute.FONT, serifFont);
                
                Shape shape = new Ellipse2D.Float(0, -25, 25, 25);
                ShapeGraphicAttribute shapeAttribute = new ShapeGraphicAttribute(shape, GraphicAttribute.ROMAN_BASELINE, ShapeGraphicAttribute.STROKE);
                as.addAttribute(TextAttribute.CHAR_REPLACEMENT, shapeAttribute, 4, 5);
                as.addAttribute(TextAttribute.FOREGROUND, Color.red, 4, 5);
                g2.drawString(as.getIterator(), 20, 200);
            }
        };
        f.setVisible(true);
    }
}
