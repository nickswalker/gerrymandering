import javax.swing.*;

import java.awt.*;

public class Gerrymandering extends JApplet {
    private Grid<Region> grid;
	public Gerrymandering(){
		this.grid = new Grid<Region>(10,10);
		this.createMap();
		GridLayout layout = new GridLayout(10,10,0,0);
		layout.preferredLayoutSize(new Container());
		this.grid.setLayout(layout);
		this.grid.setSize(new Dimension(1000, 1000));
		this.add(this.grid);
	}
	public void createMap() {
		for (int rows = 0; rows < this.grid.height; rows++) {
			for (int i = 0; i < this.grid.width; i++) this.grid.put(new Region(), i, rows);

		}
	}
	public static void main(String s[]) {
		JFrame f = new JFrame("Gerrymandering");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JApplet applet = new Gerrymandering();
		f.getContentPane().add("Center", applet);

		applet.init();
		f.pack();
		f.setSize(new Dimension(1000,1200));
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setVisible(true);
		f.setResizable(false);
	}
}
