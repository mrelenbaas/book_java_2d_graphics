package pngdecoder;

import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

import java.awt.*;
import java.awt.event.*;

public class PNGDecoder {

    public static void main(String[] args) {
        String name = "basn3p08.png";
        if (args.length > 0) name = args[0];
        InputStream in = PNGDecoder.class.getResourceAsStream(name);
        try {
            final BufferedImage image = PNGDecoder.decode(in);
            in.close();
            Frame f = new ApplicationFrame("PNGDecoder v1.0") {
                public void paint(Graphics g) {
                    Insets insets = getInsets();
                    g.drawImage(image, insets.left, insets.top, null);
                }
            };
            f.setVisible(true);
            Insets insets = f.getInsets();
            f.setSize(image.getWidth() + insets.left + insets.right, image.getHeight() + insets.top + insets.bottom);
        } catch (IOException e) {
            System.out.println("ERROR CLOSING INPUTSTEAM IN PNGDECODER CONTRUCTOR");
        }
    }
    
    public static BufferedImage decode(InputStream in) throws IOException {
        DataInputStream dataIn = new DataInputStream(in);
        readSignature(dataIn);
        PNGData chunks = readChunks(dataIn);
        
        long widthLong = chunks.getWidth();
        long heightLong = chunks.getHeight();
        if (widthLong > Integer.MAX_VALUE || heightLong > Integer.MAX_VALUE)
            throw new IOException("That image is too wide or tall.");
        int width = (int)widthLong;
        int height = (int)heightLong;
        
        ColorModel cm = chunks.getColorModel();
        WritableRaster raster = chunks.getRaster();
        BufferedImage image = new BufferedImage(cm, raster, false, null);
        
        return image;
    }
    
    protected static void readSignature(DataInputStream in) throws IOException {
        File someFile = new File("C:\\Users\\mrele\\Documents\\book_java_2d_graphics\\PNGDecoder\\src\\pngdecoder\\basn3p08.png");
        System.out.println(someFile.exists());
        if (in == null) {
            System.out.println("in == null");
        }
        else {
            System.out.println("in != null");
        }
        long signature = in.readLong();
        if (signature != 0x89504e470d0a1a0aL)
            throw new IOException ("PNG signature not found!");
    }
    
    protected static PNGData readChunks(DataInputStream in) throws IOException {
        PNGData chunks = new PNGData();
        
        boolean trucking = true;
        while (trucking) {
            try {
                int length = in.readInt();
                if (length < 0)
                    throw new IOException("Sorry, that file is too long.");
                byte[] typeBytes = new byte[4];
                in.readFully(typeBytes);
                
                byte[] data = new byte[length];
                in.readFully(data);
                                
                long crc = in.readInt() & 0x00000000ffffffffL;
                if (verifyCRC(typeBytes, data, crc) == false)
                    throw new IOException("That file appears to be corrupted.");
                
                PNGChunk chunk = new PNGChunk(typeBytes, data);
                chunks.add(chunk);
            } catch (EOFException eofe) {
                trucking = false;
            }
        }
        return chunks;
    }
    
    protected static boolean verifyCRC(byte[] typeBytes, byte[] data, long crc) {
        CRC32 crc32= new CRC32();
        crc32.update(typeBytes);
        crc32.update(data);
        long calculated = crc32.getValue();
        return (calculated == crc);
    }
}
