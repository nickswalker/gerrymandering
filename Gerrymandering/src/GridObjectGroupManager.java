import java.util.HashSet;

public class GridObjectGroupManager {
    private HashSet<GridObjectGroup> groups = new HashSet<GridObjectGroup>();
    public GridObjectGroupManager(){

      }
    public boolean add(GridObjectGroup set){
        return groups.add(set);
    }
    public void checkScore(){}
}
