import java.util.ArrayList;

public class GridObjectGroup<E extends GridObject> {
    private ArrayList<E> contents;
    GridObjectGroup(E object){
        contents.add(object);
    }
    public boolean add(E object){

         return false;
    }

}
