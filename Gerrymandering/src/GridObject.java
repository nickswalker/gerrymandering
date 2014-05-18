import javax.swing.*;

public abstract class GridObject extends JButton {
	protected Location location;
    protected Grid grid;
	private boolean active;


    public Grid getGrid(){
        return this.grid;
    }
	public Location getGridLocation(){
		return this.location;
	}
    public void putSelfInGrid(Grid gr, Location loc)
    {
        gr.put(this, loc.getCol(), loc.getRow());
        grid = gr;
        location = loc;
    }
    public void setActive(boolean value){
        this.active = value;
        this.repaint();
    }
    public boolean active(){
        return this.active;
    }
}
