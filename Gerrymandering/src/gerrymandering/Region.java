package gerrymandering;

import grid.GridObject;

import javax.swing.*;
import java.awt.*;

/**
 GridObject subclass that represents a voting region.
 */
public class Region extends GridObject {
    protected Party party;
    private int population;
    private JLabel label;

    public static final Color ACTIVE_COLOR = new Color(220, 220, 220);

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
        if (this.party == Party.REPUBLICAN) {
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


    public void paint(Graphics g) {
        if (isInGroup()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (getModel().isPressed()) {
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

