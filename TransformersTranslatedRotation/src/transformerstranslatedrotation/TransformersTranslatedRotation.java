package transformerstranslatedrotation;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class TransformersTranslatedRotation extends Transformers {

    public static void main(String[] args) {
        Transformers t = new TransformersTranslatedRotation();
        Frame f = t.getFrame();
        f.setVisible(true);
    }
    
    public AffineTransform getTransform() {
        float cm = 72 / 2.54f;
        return AffineTransform.getRotateInstance(-Math.PI / 6, 3 * cm, 2 *cm);
    }
}
