package transformersrotation;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class TransformersTranslation extends Transformers {
    
    public AffineTransform getTransform() {
        return AffineTransform.getTranslateInstance(150, 0);
    }
}
