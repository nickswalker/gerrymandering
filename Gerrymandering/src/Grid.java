import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;

public class Grid <E extends GridObject> extends JPanel implements ActionListener {

    private static final int CELL_WIDTH = 100, CELL_HEIGHT = 100;
    private static final Dimension CELL_DIMENSION = new Dimension(CELL_WIDTH, CELL_HEIGHT);
    private static final int CELL_PADDING = 5;

	private ArrayList<ArrayList<E>> contents;
    private GridObjectGroupManager<E> groupManager;
	public final int width, height;


    public Grid(int width, int height ){
		this.height = height;
		this.width = width;
    	this.contents = new ArrayList<ArrayList <E>>();
		for(int i = 0; i < height; i++){
			this.contents.add(new ArrayList<E>());
		}

		this.setBackground(Color.white);
		this.setOpaque(true);

    }

	public void put(E object, int x, int y){
		object.location = new Location(y,x);
		this.contents.get(y).add(x, object);

		object.setPreferredSize(CELL_DIMENSION);
		object.setMaximumSize(CELL_DIMENSION);
		object.setMinimumSize(CELL_DIMENSION);
		object.setSize(CELL_DIMENSION);

		object.addActionListener(this);

		this.add(object);
	}
    @Nullable
    public E get(Location l){
        int y = l.getRow();
        int x = l.getCol();
        return this.contents.get(y).get(x);
    }

    public boolean checkIfLocationIsValidForGrid(Location l){
        if(l.getCol() >= this.width || l.getRow() >=this.height) return false;
        if(l.getCol()< 0 || l.getRow() < 0) return false;
        return true;
    }
    //Doesn't always contain 4!
    public EnumMap<Direction,Location> getCardinalLocations(Location center){

        int r = center.getRow();
        int c = center.getCol();
        EnumMap<Direction, Location> temp = new EnumMap<Direction, Location>(Direction.class);
        Location north = new Location(r-1,c);
        Location east = new Location(r,c+1);
        Location south = new Location(r+1,c);
        Location west = new Location(r,c-1);
        if(this.checkIfLocationIsValidForGrid(north))temp.put(Direction.NORTH,north);
        if(this.checkIfLocationIsValidForGrid(east))temp.put(Direction.EAST, east);
        if(this.checkIfLocationIsValidForGrid(south))temp.put(Direction.SOUTH, south);
        if(this.checkIfLocationIsValidForGrid(west))temp.put(Direction.WEST, west);

        return temp;
    }
    @NotNull
    public EnumMap<Direction,E> getCardinalNeighbors(Location l){
        EnumMap<Direction, Location> cardinalLocations = this.getCardinalLocations(l);
        EnumMap<Direction, E> cardinalNeighbors = new EnumMap<Direction, E>(Direction.class);
        for (Map.Entry<Direction, Location> entry : cardinalLocations.entrySet())
        {
            cardinalNeighbors.put(entry.getKey(),this.get(entry.getValue()));
        }

        return cardinalNeighbors;
    }
    //Objects must be on the same grid
    public boolean objectsAreCardinalNeighbors(E obj1, E obj2){
        EnumMap<Direction, E> obj1Neighbors = this.getCardinalNeighbors(obj1.getGridLocation());
        for (Map.Entry<Direction, E> entry : obj1Neighbors.entrySet())
        {
            if(entry.getValue() == obj2) return true;
        }
        return false;

    }
    @Override
    //Group behavior:
    //Group forms only when GROUP_SIZE contiguous locations are active.
    //Groups are disbanded when any object in a group is clicked
    public void actionPerformed(ActionEvent e) {
        E clicked = (E)e.getSource();
        Location location = clicked.getGridLocation();
        clicked.setActive(!clicked.active());
        if(clicked.active()){
            HashSet<E> set = getContiguousGroup(clicked.location);

            if(set.size()>4){
                for( E obj: set){
                    obj.setActive(false);
                }
            }
        }
    }

    public HashSet<E> getContiguousGroup( Location start){
        HashSet<E> set =  new HashSet<E>();
        this.populateSetWithNeighbors(start, set);
        return set;

    }
    //Recursive
    @NotNull
    private int populateSetWithNeighbors( Location start, HashSet<E> set){
        //Get start neighbors
        EnumMap<Direction, E> immediateNeighbors = this.getCardinalNeighbors(start);

        //Check if they're active
        for (Map.Entry<Direction, E> entry : immediateNeighbors.entrySet()){
            E obj = entry.getValue();
            Location location = entry.getValue().location;
            if(obj.active() ){
                //If it's unique thus far (it can successfully added to the set, search it's neighbors
                if(set.add(obj)) populateSetWithNeighbors(location, set);
            }
        }
        return set.size();
    }

}