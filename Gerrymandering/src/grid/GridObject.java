package grid;

import javax.swing.*;
import java.awt.*;

public abstract class GridObject extends JButton {
    protected Location location;
    protected Grid grid;
    private boolean active;

    public GridObject() {
        setPreferredSize(new Dimension(50, 50));
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Location getGridLocation() {
        return this.location;
    }

    /**
     Adds self to the grid. Configures internal state and references to the
     parent grid. Avoid adding to the grid directly because you might not
     update everything properly.

     @param gr
     @param loc
     */
    public void putSelfInGrid(Grid gr, Location loc) {
        gr.put(this, loc.getCol(), loc.getRow());
        grid = gr;
        location = loc;
    }

    public void setActive(boolean value) {
        this.active = value;
        this.repaint();
    }

    public boolean active() {
        return this.active;
    }

    public abstract void setGrouped(boolean value);

    public abstract void setGroupedBorder(boolean n, boolean e, boolean s, boolean w);

    public abstract boolean isGroupable(GridObject object);
}
