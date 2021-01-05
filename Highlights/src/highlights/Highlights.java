package highlights;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.AffineTransform;

public class Highlights extends ApplicationFrame {

    public static void main(String[] args) {
        Frame f = new Highlights();
        f.setVisible(true);
    }
    
    private TextLayout mTextLayout;
    private TextHitInfo mFirstHit, mSecondHit;
    private int mX = 40, mY = 80;
    
    public Highlights() {
        super("Highlights v1.0");
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                mFirstHit = mTextLayout.hitTestChar(me.getX() - mX, me.getY() - mY);
                mSecondHit = null;
            }
            public void mouseRelease(MouseEvent me) {
                mSecondHit = mTextLayout.hitTestChar(me.getX() - mX, me.getY() - mY);
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
                mSecondHit = mTextLayout.hitTestChar(me.getX() - mX, me.getY() - mY);
                repaint();
            }
        });
    }
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        
        String s = "Camelopardalis";
        Font font = new Font("Serif", Font.PLAIN, 32);
        if (mTextLayout == null) {
            FontRenderContext frc = g2.getFontRenderContext();
            mTextLayout = new TextLayout(s, font, frc);
        }
        
        if (mFirstHit != null && mSecondHit != null) {
            Shape base = mTextLayout.getLogicalHighlightShape(mFirstHit.getInsertionIndex(), mSecondHit.getInsertionIndex());
            AffineTransform at = AffineTransform.getTranslateInstance(mX, mY);
            Shape highlight = at.createTransformedShape(base);
            g2.setPaint(Color.green);
            g2.fill(highlight);
        }
        
        g2.setPaint(Color.black);
        mTextLayout.draw(g2, mX, mY);
    }
}
