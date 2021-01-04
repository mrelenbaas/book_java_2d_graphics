package combiningshapes;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

public class CombiningShapes extends JComponent {
    
    public static void main(String[] args) {
        ApplicationFrame f = new ApplicationFrame("CombiningShapes v1.0");
        f.add(new CombiningShapes());
        f.setSize(220, 220);
        f.center();
        f.setVisible(true);
    }
    
    private Shape mShapeOne, mShapeTwo;
    private JComboBox mOptions;
    
    public CombiningShapes() {
        mShapeOne = new Ellipse2D.Double(40, 20, 80, 80);
        mShapeTwo = new Rectangle2D.Double(60, 40, 80, 80);
        setBackground(Color.white);
        setLayout(new BorderLayout());
        
        JPanel controls = new JPanel();
        
        mOptions = new JComboBox(
                new String[] { "outline", "add", "intersection", "subtract", "exclusive or"
                }
        );
        
        mOptions.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                repaint();
            }
        });
        controls.add(mOptions);
        add(controls, BorderLayout.SOUTH);
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        String option = (String)mOptions.getSelectedItem();
        if (option.equals("outline")) {
            g2.draw(mShapeOne);
            g2.draw(mShapeTwo);
            return;
        }
        
        Area areaOne = new Area(mShapeOne);
        Area areaTwo = new Area(mShapeTwo);
        if (option.equals("add")) areaOne.add(areaTwo);
        else if (option.equals("intersection")) areaOne.intersect(areaTwo);
        else if (option.equals("subtract")) areaOne.subtract(areaTwo);
        else if (option.equals("exclusive or")) areaOne.exclusiveOr(areaTwo);
        
        g2.setPaint(Color.orange);
        g2.fill(areaOne);
        
        g2.setPaint(Color.black);
        g2.draw(areaOne);
    }
    
}
