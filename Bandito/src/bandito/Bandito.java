package bandito;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Bandito {

    public static void main(String[] args) {
        ApplicationFrame f = new ApplicationFrame("Bandito v1.0");
        
        String filename = "C:\\Users\\mrele\\Documents\\book_java_2d_graphics\\Bandito\\src\\bandito\\Ethol with Roses.jpg";
        SplitImageComponent sic = new SplitImageComponent(filename);
        
        float[][] matrix = {
            { -1, 0, 0, 255 },
            { 0, 1, 0, 0 },
            { 0, 0, 1, 0 }
        };
        BandCombineOp op = new BandCombineOp(matrix, null);
        
        BufferedImage sourceImage = sic.getImage();
        Raster source = sourceImage.getRaster();
        WritableRaster destination = op.filter(source, null);
        
        BufferedImage destinationImage = new BufferedImage(sourceImage.getColorModel(), destination, false, null);
        sic.setSecondImage(destinationImage);
        
        f.setLayout(new BorderLayout());
        f.add(sic, BorderLayout.CENTER);
        f.setSize(f.getPreferredSize());
        f.center();
        f.setVisible(true);
    }
}
