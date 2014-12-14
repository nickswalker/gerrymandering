package grid;

import java.util.HashSet;
import java.util.Set;

public class GridObjectGroupManager<E extends GridObjectGroup> {
    private HashSet<E> groups = new HashSet<>();

    public GridObjectGroupManager() {
    }

    protected boolean add(E set) {
        return groups.add(set);
    }

    public int size() {
        return groups.size();
    }

    public void remove(GridObjectGroup group) {
        this.groups.remove(group);

    }

    public Set<E> getGroups() {
        return groups;
    }

    public GridObjectGroup gridObjectGroupForGridObject(GridObject obj) {
        for (E group : this.groups) {
            if (group.contains(obj)) {
                return group;
            }
        }
        return null;
    }
}
