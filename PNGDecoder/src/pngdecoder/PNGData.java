package pngdecoder;

import java.awt.Transparency;
import java.awt.color.*;
import java.awt.image.*;
import java.io.*;
import java.util.zip.*;

public class PNGData {
    private int mNumberOfChunks;
    private PNGChunk[] mChunks;
    
    public PNGData() {
        mNumberOfChunks = 0;
        mChunks = new PNGChunk[10];
    }
    
    public void add(PNGChunk chunk) {
        mChunks[mNumberOfChunks++] = chunk;
        if (mNumberOfChunks >= mChunks.length) {
            PNGChunk[] largerArray = new PNGChunk[mChunks.length + 10];
            System.arraycopy(mChunks, 0, largerArray, 0, mChunks.length);
            mChunks = largerArray;
        }
    }
    
    public long getWidth() {return getChunk("IHDR").getUnsignedInt(0);}
    public long getHeight() {return getChunk("IHDR").getUnsignedInt(4);}
    public short getBitsPerPixel() {return getChunk("IHDR").getUnsignedByte(8);}
    public short getColorType() {return getChunk("IHDR").getUnsignedByte(9);}
    public short getCompression() {return getChunk("IHDR").getUnsignedByte(10);}
    public short getFilter() {return getChunk("IHDR").getUnsignedByte(11);}
    public short getInterlace() {return getChunk("IHDR").getUnsignedByte(12);}
    
    public ColorModel getColorModel() {
        short colorType = getColorType();
        int bitsPerPixel = getBitsPerPixel();
        
        if (colorType == 3) {
            byte[] paletteData = getChunk("PLTE").getData();
            int paletteLength = paletteData.length / 3;
            return new IndexColorModel(bitsPerPixel, paletteLength, paletteData, 0, false);
        }
        System.out.println("Unsupported color type: " + colorType);
        return null;
    }
    
    public WritableRaster getRaster() {
        int width = (int)getWidth();
        int height = (int)getHeight();
        int bitsPerPixel  = getBitsPerPixel();
        short colorType = getColorType();
        
        if (colorType == 3) {
            byte[] imageData = getImageData();
            DataBuffer db = new DataBufferByte(imageData, imageData.length);
            WritableRaster raster = Raster.createPackedRaster(db, width, height, bitsPerPixel, null);
            return raster;
        }
        else System.out.println("Unsupported color type!");
        return null;
    }
    
    public byte[] getImageData() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int i = 0; i < mNumberOfChunks; i++) {
                PNGChunk chunk = mChunks[i];
                if (chunk.getTypeString().equals("IDAT")) {
                    out.write(chunk.getData());
                }
            }
            out.flush();
            
            InflaterInputStream in = new InflaterInputStream(new ByteArrayInputStream(out.toByteArray()));
            ByteArrayOutputStream inflatedOut = new ByteArrayOutputStream();
            int readLength;
            byte[] block = new byte[8192];
            while ((readLength = in.read(block)) != -1)
                inflatedOut.write(block, 0, readLength);
            inflatedOut.flush();
            byte[] imageData = inflatedOut.toByteArray();
            
            int width = (int)getWidth();
            int height = (int)getHeight();
            int bitsPerPixel = getBitsPerPixel();
            int length = width * height * bitsPerPixel / 8;
            
            byte[] prunedData = new byte[length];
            
            if (getInterlace() == 0) {
                int index = 0;
                for (int i = 0; i < length; i++) {
                    if ((i * 8 / bitsPerPixel) % width == 0) {
                        index++;
                    }
                    prunedData[i] = imageData[index++];
                }
            }
            else System.out.println("Couldn't undo interlacing.");
            
            return prunedData;
        } 
        catch (IOException ioe) {}
        return null;
    }
    
    public PNGChunk getChunk(String type) {
        for (int i = 0; i < mNumberOfChunks; i++)
            if (mChunks[i].getTypeString().equals(type))
                return mChunks[i];
        return null;
    }
}
