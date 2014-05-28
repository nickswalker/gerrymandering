import java.util.HashSet;
import java.util.Objects;

public class GridObjectGroupManager {
    private HashSet<GridObjectGroup> groups = new HashSet<GridObjectGroup>();
    public GridObjectGroupManager(){

      }
    public boolean add(GridObjectGroup set){
        return groups.add(set);
    }
    public void checkScore(){}
    public GridObjectGroup gridObjectGroupForGridObject(GridObject obj){
        for(GridObjectGroup group: this.groups){
            for(Object candidate: group.contents) {
                if(obj==candidate)return group;
            }
        }
        return null;
    }
    public void remove(GridObjectGroup group){
        this.groups.remove(group);
    }
}
