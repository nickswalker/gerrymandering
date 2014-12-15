package grid;

import javax.swing.*;
import java.awt.*;

public abstract class GridObject extends JButton {
    private Location location;
    private Grid grid;
    private boolean active;
    private boolean grouped;

    public GridObject() {
        setPreferredSize(new Dimension(50, 50));
    }

    public Grid getGrid() {
        return this.grid;
    }

    public Location getGridLocation() {
        return this.location;
    }

    protected void setLocation(Location l) {
        this.location = l;
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

    public boolean isInGroup() {
        return grouped;
    }

    public void setGrouped(boolean value) {
        this.grouped = value;
    }

    public abstract boolean isGroupable(GridObject object);
}
