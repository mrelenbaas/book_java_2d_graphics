package texturepaintfill;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;

import com.sun.image.codec.jpeg.*;

public class TexturePaintFill extends ApplicationFrame {

    public static void main(String[] args) throws Exception {
        TexturePaintFill f = new TexturePaintFill("Raphael.jpg");
        f.setTitle("TexturePaintFill v1.0");
        f.setSize(800, 800);
        f.center();
        f.setVisible(true);
    }
    
    private BufferedImage mImage;
    
    public TexturePaintFill(String filename) throws IOException, ImageFormatException {
        InputStream in = getClass().getResourceAsStream(filename);
        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
        mImage = decoder.decodeAsBufferedImage();
    }
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        RoundRectangle2D r = new RoundRectangle2D.Float(25, 35, 755, 755, 25, 25);
        Rectangle2D tr = new Rectangle2D.Double(0, 0, mImage.getWidth(), mImage.getHeight());
        TexturePaint tp = new TexturePaint(mImage, tr);
        g2.setPaint(tp);
        g2.fill(r);
    }
}
