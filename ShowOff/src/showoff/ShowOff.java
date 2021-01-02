package showoff;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

import com.sun.image.codec.jpeg.*;

public class ShowOff extends Component{

    public static void main(String[] args) {
        try {
            String filename = "Raphael.jpg";
            String message = "Java2D";
            int split = 4;
            if (args.length > 0) filename = args[0];
            if (args.length > 1) message = args[1];
            if (args.length > 2) split = Integer.parseInt(args[2]);

            ApplicationFrame f = new ApplicationFrame("ShowOf v1.0");
            f.setLayout(new BorderLayout());
            ShowOff showOff = new ShowOff(filename, message, split);
            f.add(showOff, BorderLayout.CENTER);
            f.setSize(f.getPreferredSize());
            f.center();
            f.setResizable(false);
            f.setVisible(true);
        }
        catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
    
    private BufferedImage mImage;
    private Font mFont;
    private String mMessage;
    private int mSplit;
    private TextLayout mLayout;
    
    public ShowOff(String filename, String message, int split) throws IOException, ImageFormatException {
        InputStream in = getClass().getResourceAsStream(filename);
        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
        mImage = decoder.decodeAsBufferedImage();
        in.close();
        mFont = new Font("Serif", Font.PLAIN, 116);
        mMessage = message;
        mSplit = split;
        setSize((int)mImage.getWidth(), (int)mImage.getHeight());
    }
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawBackground(g2);
        drawImageMosaic(g2);
        drawText(g2);
    }
    
    protected void drawBackground(Graphics2D g2) {
        int side = 45;
        int width = getSize().width;
        int height = getSize().height;
        Color[] colors = { Color.yellow, Color.cyan, Color.orange, Color.pink, Color.magenta, Color.lightGray };
        for (int y = 0; y < height; y += side) {
            for (int x = 0; x < width; x += side) {
                Ellipse2D ellipse = new Ellipse2D.Float(x, y, side, side);
                int index = (x + y) / side % colors.length;
                g2.setPaint(colors[index]);
                g2.fill(ellipse);
            }
        }
    }
    
    protected void drawImageMosaic(Graphics2D g2) {
        int side = 36;
        int width = mImage.getWidth();
        int height = mImage.getHeight();
        for (int y = 0; y < height; y += side) {
            for (int x = 0; x < width; x += side) {
                float xBias = (float)x / (float)width;
                float yBias = (float)y / (float)height;
                float alpha = 1.0f - Math.abs(xBias - yBias);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                int w = Math.min(side, width - x);
                int h = Math.min(side, height - y);
                BufferedImage tile = mImage.getSubimage(x, y, w, h);
                g2.drawImage(tile, x, y, null);
            }
        }
    }
    
    protected void drawText(Graphics2D g2) {
        FontRenderContext frc = g2.getFontRenderContext();
        mLayout = new TextLayout(mMessage, mFont, frc);
        int width = getSize().width;
        int height = getSize().height;
        Rectangle2D bounds = mLayout.getBounds();
        double x = (width - bounds.getWidth()) / 2;
        double y = height - bounds.getHeight();
        drawString(g2, x, y, 0);
        drawString(g2, width - bounds.getHeight(), y, -Math.PI / 2);
    }
    
    protected void drawString(Graphics2D g2, double x, double y, double theta) {
        g2.translate(x, y);
        g2.rotate(theta);
        String first = mMessage.substring(0, mSplit);
        float width = drawBoxedString(g2, first, Color.white, Color.red, 0);
        String second = mMessage.substring(mSplit);
        drawBoxedString(g2, second, Color.blue, Color.white, width);
        g2.rotate(-theta);
        g2.translate(-x, -y);
    }
    
    protected float drawBoxedString(Graphics2D g2, String s, Color c1, Color c2, double x) {
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout subLayout = new TextLayout(s, mFont, frc);
        float advance = subLayout.getAdvance();
        GradientPaint gradient = new GradientPaint((float)x, 0, c1, (float)(x + advance), 0, c2);
        g2.setPaint(gradient);
        Rectangle2D bounds = mLayout.getBounds();
        Rectangle2D back = new Rectangle2D.Double(x, 0, advance, bounds.getHeight());
        g2.fill(back);
        g2.setPaint(Color.white);
        g2.setFont(mFont);
        g2.drawString(s, (float)x, (float)-bounds.getY());
        return advance;
    }
}
