import java.util.HashSet;

public class GridObjectGroup<E extends GridObject> {
    public HashSet<E> contents = new HashSet<E>();
	private Grid<E> grid;
    private static final int MAX_SIZE= 5;

    GridObjectGroup(E object){
        contents.add(object);
    }
    GridObjectGroup(HashSet<E> set, Grid<E> g){
		this.grid = g;
        int cap = set.size() >= 5 ? 5 : set.size();
        int i = 0;
        for(E obj: set) {
            this.add(obj);
            i++;
			if (i>=cap) break;
        }
    }
    public boolean add(E obj){
        boolean added = false;
        if(contents.size()>MAX_SIZE)
            return false;
        else {
            added = this.contents.add(obj);
            if(contents.size()==MAX_SIZE) configureGroupAppearance();
        }

        return added;
    }
    private void configureGroupAppearance(){
        for (E obj : this.contents) {
            boolean n = true,e = true, s =true,w=true;
            for (E otherInSet : this.contents) {
                if(otherInSet.location == obj.location ) continue;
				System.out.println(otherInSet.location);
				Direction dir = grid.getDirectionOfAdjacency(obj,otherInSet);

				switch (dir) {
					case NORTH:
						n = false;
						break;
					case EAST:
						e = false;
						break;
					case SOUTH:
						s = false;
						break;
					case WEST:
						w = false;
						break;
					case NONE: break;
				}
			}
            Region r = (Region)obj;

            r.setGroupedBorder(n,e,s,w);
			r.setActive(false);
        }
    }
    public void disband(){
        for (E obj : this.contents) {
            Region r = (Region)obj;
            r.setNormalBorder();
        }
    }

}
