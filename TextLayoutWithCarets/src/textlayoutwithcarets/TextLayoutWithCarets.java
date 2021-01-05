package textlayoutwithcarets;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.AffineTransform;
import java.text.*;

public class TextLayoutWithCarets extends ApplicationFrame {

    public static void main(String[] args) {
        TextLayoutWithCarets f = new TextLayoutWithCarets();
        f.setVisible(true);
    }
    
    private TextHitInfo mHit;
    private TextLayout mLayout;
    private boolean mInitialized = false;
    
    public TextLayoutWithCarets() {
        super("TextLayoutWithCarets v1.0");
    }
    
    private void initialize(Graphics2D g2) {
        String s = "Please \u062e\u0644\u0639 slowly.";
        
        int fontSize = 32;
        Font font = new Font("Lucida Sans Regular", Font.PLAIN, fontSize);
        Font italicFont = new Font("Lucida Sans oBLIQUE", Font.ITALIC, fontSize);
        
        AttributedString as = new AttributedString(s);
        as.addAttribute(TextAttribute.FONT, font);
        as.addAttribute(TextAttribute.FONT, italicFont, 2, 5);
        
        AttributedCharacterIterator iterator = as.getIterator();
        
        FontRenderContext frc = g2.getFontRenderContext();
        mLayout = new TextLayout(iterator, frc);
        
        mHit = mLayout.getNextLeftHit(1);
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_D) {
                    mHit = mLayout.getNextRightHit(mHit.getInsertionIndex());
                    if (mHit == null)
                        mHit = mLayout.getNextLeftHit(1);
                    repaint();
                    System.out.println(ke.getKeyChar());
                }
                else if (ke.getKeyCode() == KeyEvent.VK_A) {
                    mHit = mLayout.getNextLeftHit(mHit.getInsertionIndex());
                    if (mHit == null)
                        mHit = mLayout.getNextRightHit(mLayout.getCharacterCount() - 1);
                    repaint();
                    System.out.println(mHit.getInsertionIndex());
                }
                //System.out.println(ke.getKeyChar());
            }
        });
        
        mInitialized = true;
    }
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        if (mInitialized == false) initialize(g2);
        
        float x = 20, y = 80;
        mLayout.draw(g2, x, y);
        
        Stroke[] caretStrokes = new Stroke[2];
        caretStrokes[0] = new BasicStroke();
        caretStrokes[1] = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[] { 4, 4}, 0);
        
        Shape[] carets = mLayout.getCaretShapes(mHit.getInsertionIndex());
        for (int i = 0; i < carets.length; i++) {
            if (carets[i] != null) {
                AffineTransform at = AffineTransform.getTranslateInstance(x, y);
                Shape shape = at.createTransformedShape(carets[i]);
                g2.setStroke(caretStrokes[i]);
                g2.draw(shape);
            }
        }
    }
}
