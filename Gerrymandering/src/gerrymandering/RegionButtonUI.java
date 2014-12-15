package gerrymandering;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class RegionButtonUI extends BasicButtonUI {

    private static final RegionButtonUI buttonUI = new RegionButtonUI();

    RegionButtonUI() {
    }

    public static ComponentUI createUI(JComponent c) {
        return new RegionButtonUI();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        //System.out.println("UI Paint");

    }

    @Override
    public void paintButtonPressed(Graphics g, AbstractButton b) {

        //paintText(g, b, b.getBounds(), b.getText());
        //g.setColor(Color.red.brighter());
        //g.fillRect(0, 0, b.getSize().width, b.getSize().height);
    }

    @Override
    protected void paintFocus(Graphics g, AbstractButton b,
                              Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
    }
}