package gerrymandering;

import javax.swing.border.LineBorder;
import java.awt.*;

public class RegionBorder extends LineBorder {
    private boolean top, right, bottom, left;
    private Color color;
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final int DEFAULT_THICKNESS = 2;

    public RegionBorder(boolean t, boolean r, boolean b, boolean l, Color color) {
        super(color);
        this.thickness = DEFAULT_THICKNESS;
        this.top = t;
        this.right = r;
        this.bottom = b;
        this.left = l;

    }
    public RegionBorder(boolean t, boolean r, boolean b, boolean l) {
        this(t,r,b,l, DEFAULT_COLOR);

    }
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(lineColor);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //float dash[] = { thickness * 2 };
        //g2d.setStroke( new BasicStroke(thickness, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1f, dash, 0));
        g2d.setStroke( new BasicStroke(this.thickness));
        int thicknessOffset = thickness/2;
        //Top
        if(this.top) g2d.drawLine(x,y,x+width,y);
        //Right
        if(this.right) g2d.drawLine(x+width-thicknessOffset,y,x+width-thicknessOffset,y+height);
        //Bottom
        if(this.bottom) g2d.drawLine(x,y+height-thicknessOffset,x+width,y+height-thicknessOffset);
        //Left
        if(this.left) g2d.drawLine(x,y,x,y+height);

        g2d.dispose();

    }
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = thickness;
        insets.top = thickness;
        insets.right = thickness;
        insets.bottom = thickness;
        return insets;
    }

}