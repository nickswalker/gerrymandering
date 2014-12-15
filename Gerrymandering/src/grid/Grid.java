package grid;

import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

/**
 Class that represents a 2D grid of objects.

 @param <E> The type of GridObject that the grid should store */
public class Grid<E extends GridObject> extends JPanel implements ActionListener {

    private static final int CELL_WIDTH = 100, CELL_HEIGHT = 100;
    private static final Dimension CELL_DIMENSION = new Dimension(CELL_WIDTH, CELL_HEIGHT);
    private static final int CELL_PADDING = 5;

    //Has to be an arraylist to store the generic elements
    private ArrayList<ArrayList<E>> contents;
    private GridGroupManager groupManager = new GridGroupManager();
    public final int width, height;
    private GridDelegate delegate;
    private int groupSize = 0;
    private GridGroup currentGroup;


    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
        this.contents = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            this.contents.add(new ArrayList<>());
        }

        this.setBackground(Color.WHITE);
        this.setOpaque(true);

        GridLayout layout = new GridLayout(height, width, 0, 0);

        setLayout(layout);
        setPreferredSize(new Dimension(600, 600));
        setVisible(true);
    }

    public void setGroupSize(int size) {
        this.groupSize = size;
    }

    public int getGroupSize() {
        return this.groupSize;
    }

    public void put(E object, int x, int y) {
        object.setLocation(new Location(y, x));
        this.contents.get(y).add(x, object);

        object.setPreferredSize(CELL_DIMENSION);
        object.setMaximumSize(CELL_DIMENSION);
        object.setMinimumSize(CELL_DIMENSION);
        object.setSize(CELL_DIMENSION);

        object.addActionListener(this);

        this.add(object);
    }

    @Nullable
    public E get(Location l) {
        int y = l.getRow();
        int x = l.getCol();
        return this.contents.get(y).get(x);
    }

    public boolean checkIfLocationIsValidForGrid(Location l) {
        if (l.getCol() >= this.width || l.getRow() >= this.height) {
            return false;
        }
        if (l.getCol() < 0 || l.getRow() < 0) {
            return false;
        }
        return true;
    }


    //Objects must be on the same grid
    public boolean locationsAreCardinalNeighbors(Location l1, Location l2) {
        int deltaX = Math.abs(l1.getCol() - l2.getCol());
        int deltaY = Math.abs(l1.getRow() - l2.getRow());
        //If either is more than one square away, they can't be adjacent
        return !(deltaX > 1 || deltaY > 1);

    }

    //Objects must be on the same grid
    @Nullable
    public static Direction getDirectionOfAdjacency(Location l1, Location l2) {
        int deltaX = l1.getCol() - l2.getCol();
        int deltaY = l1.getRow() - l2.getRow();
        if (deltaX == 0) {
            switch (deltaY) {
                case -1:
                    return Direction.NORTH;
                case 1:
                    return Direction.SOUTH;
            }
        }
        if (deltaY == 0) {
            switch (deltaX) {
                case -1:
                    return Direction.WEST;
                case 1:
                    return Direction.EAST;
            }
        }
        return null;
    }

    public Set<GridGroup> getGroups() {
        return groupManager.getGroups();
    }

    /**
     Called when the grid is clicked.

     @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //We know that the only thing on the grid are our gridobjects,
        //so we'll just cast the source straight to the generic type.
        E clicked = (E) e.getSource();
        Location clickedLocation = clicked.getGridLocation();
        if (currentGroup != null && currentGroup.contains(clickedLocation)) {
            currentGroup.disband();
            currentGroup = null;
        } else if (clicked.isInGroup()) {
            //Check if is in group, disband it if it is
            GridGroup parentGroup = groupManager.groupForObject(clicked);
            parentGroup.disband();
            this.groupManager.remove(parentGroup);

        } else if (!clicked.active()) {
            if (currentGroup == null) {
                currentGroup = new GridGroup(clickedLocation, this);

            } else {
                boolean adjacent = currentGroup.adjacentTo(clickedLocation);
                if (adjacent) {
                    currentGroup.add(clickedLocation);
                } else {
                    currentGroup.disband();
                    currentGroup = new GridGroup(clickedLocation, this);
                }
            }
            if (currentGroup.isComplete()) {
                groupManager.add(currentGroup);
                if (delegate != null) {
                    delegate.groupCreated(currentGroup);
                }
                currentGroup = null;
            }
        }

    }

    public void setDelegate(GridDelegate delegate) {
        this.delegate = delegate;
    }
}