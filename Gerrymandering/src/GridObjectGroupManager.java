import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class GridObjectGroupManager<E> {
    private HashSet<E> groups = new HashSet<E>();
    public GridObjectGroupManager(){

      }
    public boolean add(E set){
        return groups.add(set);
    }
	public int size(){
		return groups.size();
	}
    public EnumMap<Party,Integer> checkScore(){
        EnumMap<Party, Integer> groupTally = new EnumMap<Party, Integer>(Party.class);
		for(Party party : Party.values()){
			groupTally.put(party,0);
		}
        for(E group : this.groups){
            //IMPROPER CAST
            GridObjectGroup<Region> regionGroup = (GridObjectGroup<Region>)group;
            Party regionVote = regionGroup.majorityParty();
            groupTally.put(regionVote, groupTally.get(regionVote)+1);
        }

        return groupTally;
    }
    public GridObjectGroup gridObjectGroupForGridObject(GridObject obj){
        for(E group: this.groups){
            GridObjectGroup<Region> regionGroup = (GridObjectGroup<Region>) group;
            for(Object candidate: regionGroup.contents) {
                if(obj==candidate)return regionGroup;
            }
        }
        return null;
    }
    public void remove(GridObjectGroup group){
        this.groups.remove(group);
    }
}
