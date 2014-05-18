import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.*;


public class Region extends GridObject {
    protected Party party;
    private int population;
    private JLabel label;

    public static final Color ACTIVE_COLOR = new Color(220,220,220);

	public Region(){
        this.party = Party.values()[ (int)(Math.random()*Party.values().length)];
		this.population = (int)(Math.random()*10);

		final Region self = this;

		this.setBorderPainted(true);
		this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        this.setFocusable(false);
		this.setContentAreaFilled(false);
		this.setUI(new DistrictButtonUI());

        this.label = new JLabel(this.party.abbr);
        this.label.setFont(new Font("Helvetica", Font.BOLD, 21));
        this.label.setForeground(Color.BLUE);
        this.add(this.label);
		this.setBackground(Color.BLUE);
		if(this.party == Party.REPUBLICAN){
			this.setBackground(Color.RED);
            this.label.setForeground(Color.RED);
		}

        this.setBorder(new DistrictBorder(true, true, true, true));
	}

	public Region(Party party, int population){
        this.party = party;
        this.population = population;
    }
    public void setBorder(boolean north, boolean east, boolean south, boolean west){
        if(active()) {
            this.setBorder(new DistrictBorder(north, east, south, west, this.party.color.darker()));
        }
        else {

        }
    }
	public void paint(Graphics g){
		if (getModel().isPressed()) {
			g.setColor(ACTIVE_COLOR);
			g.fillRect(0, 0, getWidth(), getHeight());
		} else if (this.active()) {
			g.setColor(ACTIVE_COLOR);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		super.paint(g);
	}



}

class DistrictButtonUI extends BasicButtonUI {

	private static final DistrictButtonUI buttonUI = new DistrictButtonUI();

	DistrictButtonUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new DistrictButtonUI();
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		//System.out.println("UI Paint");

	}

	@Override
	public void paintButtonPressed(Graphics g, AbstractButton b) {

		//paintText(g, b, b.getBounds(), b.getText());
		//g.setColor(Color.red.brighter());
		//g.fillRect(0, 0, b.getSize().width, b.getSize().height);
	}


	@Override
	protected void paintFocus(Graphics g, AbstractButton b,
							  Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
	}
}