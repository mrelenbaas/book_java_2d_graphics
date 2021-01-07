package componenttest;

import java.awt.*;
import java.awt.color.*;
import java.awt.image.*;

public class ComponentTest {

    public static void main(String[] args) {
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ColorModel cm = new ComponentColorModel(cs, new int[] { 5, 6, 5 }, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        
        Color fifty = new Color(cs, new float[] { 1.0f, 1.0f, 1.0f }, 0);
        float[] components = fifty.getComponents(null);
        System.out.print("Original normalized components: ");
        for (int i = 0; i < 3; i++) System.out.print(components[i] + " ");
        System.out.println();
        int[] unnormalized = cm.getUnnormalizedComponents(components, 0, null, 0);
        
        System.out.print("Original unnormalized components: ");
        for (int i = 0; i < 3; i++) System.out.print(unnormalized[i] + " ");
        System.out.println();
        Object pixel = cm.getDataElements(unnormalized, 0, (Object)null);
        System.out.print("Pixel samples: ");
        byte[] pixelBytes = (byte[])pixel;
        for (int i = 0; i < 3; i++) System.out.print(pixelBytes[i] + " ");
        System.out.println();
        
        unnormalized = cm.getComponents(pixel, null, 0);
        System.out.print("Derived unnormalized components: ");
        for (int i = 0; i < 3; i++) System.out.print(unnormalized[i] + " ");
        System.out.println();
        components = cm.getNormalizedComponents(unnormalized, 0, null, 0);
        System.out.print("Derived normalized components: ");
        for (int i = 0; i < 3; i++) System.out.print(components[i] + " ");
        System.out.println();
    }
}
