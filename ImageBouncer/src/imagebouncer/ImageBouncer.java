package imagebouncer;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.Random;

public class ImageBouncer extends AnimationComponent {

    public static void main(String[] args) {
        String filename = "C:\\Users\\mrele\\Documents\\book_java_2d_graphics\\ImageBouncer\\src\\imagebouncer\\knudsen.gif";
        if (args.length > 0) filename = args[0];
        
        Image image = Utilities.blockingLoad(filename);
        
        final ImageBouncer bouncer = new ImageBouncer(image);
        Frame f = new AnimationFrame(bouncer);
        f.setFont(new Font("Serif", Font.PLAIN, 12));
        Panel controls = new Panel();
        controls.add(bouncer.createCheckbox("Bilinear", ImageBouncer.BILINEAR));
        controls.add(bouncer.createCheckbox("Transform", ImageBouncer.TRANSFORM));
        final Choice typeChoice = new Choice();
        typeChoice.add("TYPE_INT_RGB");
        typeChoice.add("TYPE_INT_ARGB");
        typeChoice.add("TYPE_INT_ARGB_PRE");
        typeChoice.add("TYPE_INT_3BYTE_BGR");
        typeChoice.add("TYPE_INT_BYTE_GRAY");
        typeChoice.add("TYPE_INT_USHORT_GRAY");
        typeChoice.add("TYPE_INT_USHORT_555_RGB");
        typeChoice.add("TYPE_INT_USHORT_565_RGB");
        f.add(controls, BorderLayout.NORTH);
        
        typeChoice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                String type = typeChoice.getSelectedItem();
                bouncer.setImageType(type);
            }
        });
        
        f.setVisible(true);
    }
    
    private boolean mBilinear = false;
    private boolean mTransform = false;
    public static final int BILINEAR = 1;
    public static final int TRANSFORM = 3;
    
    private float mDeltaX, mDeltaY;
    private float mX, mY, mWidth, mHeight;
    private float mTheta;
    private Image mOriginalImage;
    private BufferedImage mImage;
    
    public ImageBouncer(Image image) {
        mOriginalImage = image;
        setImageType("TYPE_INT_RGB");
        
        Random random = new Random();
        mX = random.nextFloat() * 500;
        mY = random.nextFloat() * 500;
        mWidth = mImage.getWidth();
        mHeight = mImage.getHeight();
        mDeltaX = random.nextFloat() * 3;
        mDeltaY = random.nextFloat() * 3;
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent ce) {
                Dimension d = getSize();
                if (mX < 0) mX = 0;
                else if (mX + mWidth >= d.width) mX = d.width - mWidth - 1;
                if (mY < 0) mY = 0;
                else if (mY + mHeight >= d.height) mY = d.height - mHeight - 1;
            }
        });
    }
    
    public void setSwitch(int item, boolean value) {
        switch(item) {
            case BILINEAR: mBilinear = value; break;
            case TRANSFORM: mTransform = value; break;
            default: break;
        }
    }
    
    public void setImageType(String s) {
        int type = BufferedImage.TYPE_CUSTOM;
        if (s.equals("TYPE_INT_RGB"))
            type = BufferedImage.TYPE_INT_RGB;
        else if (s.equals("TYPE_INT_ARGB"))
            type = BufferedImage.TYPE_INT_ARGB;
        else if (s.equals("TYPE_INT_ARGB_PRE"))
            type = BufferedImage.TYPE_INT_ARGB_PRE;
        else if (s.equals("TYPE_3BYTE_BGR"))
            type = BufferedImage.TYPE_3BYTE_BGR;
        else if (s.equals("TYPE_BYTE_GRAY"))
            type = BufferedImage.TYPE_BYTE_GRAY;
        else if (s.equals("TYPE_USHORT_GRAY"))
            type = BufferedImage.TYPE_USHORT_GRAY;
        else if (s.equals("TYPE_USHORT_555_RGB"))
            type = BufferedImage.TYPE_USHORT_555_RGB;
        else if (s.equals("TYPE_USHORT_565_RGB"))
            type = BufferedImage.TYPE_USHORT_565_RGB;
        else {
            System.out.println("Unrecognized type.");
            return;
        }
        mImage = Utilities.makeBufferedImage(mOriginalImage, type);
    }
    
    protected Checkbox createCheckbox(String label, final int item) {
        Checkbox check = new Checkbox(label);
        check.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                setSwitch(item, (ie.getStateChange() == ie.SELECTED));
            }
        });
        return check;
    }
    
    public void timeStep() {
        Dimension d = getSize();
        if (mX + mDeltaX < 0) mDeltaX = -mDeltaX;
        else if (mX + mWidth + mDeltaX >= d.width) mDeltaX = -mDeltaX;
        if (mY + mDeltaY < 0) mDeltaY = -mDeltaY;
        else if (mY + mHeight + mDeltaY >= d.height) mDeltaY = -mDeltaY;
        mX += mDeltaX;
        mY += mDeltaY;
        
        mTheta += Math.PI / 192;
        if (mTheta > (2 * Math.PI)) mTheta -= (2 * Math.PI);
    }
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        setTransform(g2);
        setBilinear(g2);
        
        g2.drawImage(mImage, AffineTransform.getTranslateInstance(mX, mY), null);
    }
    
    protected void setTransform(Graphics2D g2) {
        if (mTransform == false) return;
        float cx = mX + mWidth / 2;
        float cy = mY + mHeight / 2;
        g2.rotate(mTheta, cx, cy);
    }
    
    protected void setBilinear(Graphics2D g2) {
        if (mBilinear == false) return;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }
}
