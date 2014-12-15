package gerrymandering;

import grid.GridObject;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

/**
 GridObject subclass that represents a voting region.
 */
public class Region extends GridObject {
    protected Party party;
    private int population;
    private JLabel label;

    public static final Color ACTIVE_COLOR = new Color(220, 220, 220);
    public static final Color DEFAULT_BORDER_COLOR = new Color(160, 160, 160);
    public static final Color GROUPED_BORDER_COLOR = new Color(90, 90, 90);

    public Region() {
        this.party = Party.values()[(int) (Math.random() * Party.values().length)];
        this.population = (int) (Math.random() * 10);

        this.setBorderPainted(true);
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        this.setFocusable(false);
        this.setContentAreaFilled(false);
        this.setUI(new RegionButtonUI());

        this.label = new JLabel(this.party.abbr);
        this.label.setFont(new Font("Helvetica", Font.BOLD, 21));
        this.label.setForeground(Color.BLUE);
        this.add(this.label);
        this.setBackground(Color.BLUE);
        if (this.party == Party.REPUBLICAN) {
            this.setBackground(Color.RED);
            this.label.setForeground(Color.RED);
        }
    }

    /**
     Non-random constructor.

     @param party
     @param population
     */
    public Region(Party party, int population) {
        this.party = party;
        this.population = population;
    }

    public void setGrouped(boolean north, boolean east, boolean south, boolean west) {
        this.setBorder(new RegionBorder(north, east, south, west, GROUPED_BORDER_COLOR));
    }

    public void setNormalBorder() {
        this.setBorder(new RegionBorder(true, true, true, true, DEFAULT_BORDER_COLOR));
    }

    public void paint(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(ACTIVE_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (this.active()) {
            g.setColor(ACTIVE_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paint(g);
    }


    @Override
    public boolean isGroupable(GridObject object) {
        if (object instanceof Region) {
            Region region = (Region) object;
            return true;

        }
        return false;
    }
}

class RegionButtonUI extends BasicButtonUI {

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