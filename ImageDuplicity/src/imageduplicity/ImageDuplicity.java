package imageduplicity;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import com.sun.image.codec.jpeg.*;

public class ImageDuplicity extends Component {

    public static void main(String[] args) {
        ApplicationFrame f = new ApplicationFrame("ImageDuplicity v1.0");
        f.setLayout(new BorderLayout());
        Component c = new ImageDuplicity();
        f.add(c, BorderLayout.CENTER);
        f.setSize(200, 250);
        f.center();
        f.setVisible(true);
    }
    
    private BufferedImage mImage;
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        if (mImage == null) createOffscreenImage();
        g2.drawImage(mImage, 0, 0, this);
    }
    
    private void createOffscreenImage() {
        Dimension d = getSize();
        int w = d.width, h = d.height;
        mImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2 = mImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        try {
            String filename = "Raphael.jpg";
            InputStream in = getClass().getResourceAsStream(filename);
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);
            BufferedImage image = decoder.decodeAsBufferedImage();
            in.close();
            g2.drawImage(image, 0, 0, w, h, null);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        
        g2.setStroke(new BasicStroke(2));
        Color[] colors = { Color.red, Color.blue, Color.green };
        for (int i = -32; i < 40; i += 8) {
            g2.setPaint(colors[Math.abs(i) % 3]);
            g2.drawOval(i, i, w - i * 2, h - i * 2);
        }
    }
}
