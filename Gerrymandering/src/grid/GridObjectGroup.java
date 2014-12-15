package grid;

import java.util.HashSet;

/**
 A class that represents a collection of GridObjects

 @param <E>  */
public class GridObjectGroup<E extends GridObject> {
    public HashSet<E> contents = new HashSet<>();
    private Grid<E> grid;
    private static final int MAX_SIZE = 5;

    GridObjectGroup(E object) {
        contents.add(object);
    }

    GridObjectGroup(HashSet<E> set, Grid<E> g) {
        this.grid = g;
        int cap = set.size() >= 5 ? 5 : set.size();
        int i = 0;
        for (E obj : set) {
            this.add(obj);
            i++;
            if (i >= cap) {
                break;
            }
        }
    }

    public boolean add(E obj) {
        boolean added = false;
        if (contents.size() > MAX_SIZE) {
            return false;
        } else {
            added = this.contents.add(obj);
            if (contents.size() == MAX_SIZE) {
                configureGroupAppearance();
            }
        }

        return added;
    }

    private void configureGroupAppearance() {
        for (E member : this.contents) {
            boolean n = true, e = true, s = true, w = true;
            for (E otherInSet : this.contents) {
                if (otherInSet.location == member.location) {
                    continue;
                }
                Direction dir = grid.getDirectionOfAdjacency(member, otherInSet);

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
                    case NONE:
                        break;
                }
            }

            member.setGrouped(n, e, s, w);
            member.setActive(false);
        }
    }

    public void disband() {
        for (E member : this.contents) {
            member.setGrouped(false,false,false,false);
        }
    }

    public boolean contains(GridObject object) {
        return contents.contains(object);
    }

}
