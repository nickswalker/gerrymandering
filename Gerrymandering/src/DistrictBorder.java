import javax.swing.border.LineBorder;
import java.awt.*;

public class DistrictBorder extends LineBorder {
    private boolean top, right, bottom, left;
    private Color color;
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final int DEFAULT_THICKNESS = 2;

    public DistrictBorder(boolean t, boolean r, boolean b, boolean l, Color color) {
        super(color);
        this.thickness = DEFAULT_THICKNESS;
        this.top = t;
        this.right = r;
        this.bottom = b;
        this.left = l;

    }
    public DistrictBorder(boolean t, boolean r, boolean b, boolean l) {
        this(t,r,b,l, DEFAULT_COLOR);

    }
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height){

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(lineColor);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //float dash[] = { thickness * 2 };
        //g2d.setStroke( new BasicStroke(thickness, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1f, dash, 0));
        g2d.setStroke( new BasicStroke(this.thickness));
        //Top
        if(this.top) g2d.drawLine(x,y,x+width,y);
        //Right
        if(this.right) g2d.drawLine(x+width,y,x+width,y+height);
        //Bottom
        if(this.bottom) g2d.drawLine(x,y+height,x+width,y+height);
        //Left
        if(this.left) g2d.drawLine(x,y,x,y+height);

        g2d.dispose();

    }
    public Insets getBorderInsets(Component c) {
        return new Insets(  top ? thickness : 0,
                            right ? thickness : 0,
                            bottom ? thickness : 0,
                            left ? thickness : 0
        );
    }

    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = this.left ? thickness : 0;
        insets.top = this.top ? thickness : 0;
        insets.right = this.right ? thickness : 0;
        insets.bottom = this.bottom ? thickness : 0;
        return insets;
    }

}
