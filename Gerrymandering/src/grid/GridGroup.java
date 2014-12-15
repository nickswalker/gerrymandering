package grid;

import java.util.HashSet;
import java.util.Iterator;

/**
 A class that represents a collection of GridObjects
 */
public class GridGroup implements Iterable<Location> {
    public HashSet<Location> contents = new HashSet<>();
    private Grid grid;
    private boolean complete;

    public GridGroup(Location origin, Grid grid) {
        this.grid = grid;
        add(origin);
    }

    public boolean add(Location nextLocation) {
        if (complete) {
            throw new RuntimeException("Group is too large.");
        }
        boolean added = this.contents.add(nextLocation);
        if (contents.size() == grid.getGroupSize()) {
            complete = true;
            completed();
        }
        if (added) {
            GridObject object = grid.get(nextLocation);
            object.setActive(true);
        }
        return added;
    }

    public void disband() {
        for (Location member : this.contents) {
            GridObject object = grid.get(member);
            object.setGrouped(false);
            object.setActive(false);
        }
    }

    public boolean contains(Location candidate) {
        return contents.contains(candidate);
    }

    private void completed() {
        for (Location location : contents) {
            GridObject object = grid.get(location);
            object.setActive(false);
            object.setGrouped(true);
        }
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean adjacentTo(Location location) {
        for (Location element : contents) {
            if (grid.locationsAreCardinalNeighbors(element, location)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Iterator<Location> iterator() {
        return contents.iterator();
    }
}
