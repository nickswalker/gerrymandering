package gerrymandering;

import java.awt.*;

/**
 Represents the political parties in the game.
 */
public enum Party {

	DEMOCRAT("Democrat","D", Color.BLUE),
	REPUBLICAN("Republican","R", Color.RED);

	public final String abbr;
    public final String name;
    public final Color color;

	Party(String name, String abbr, Color color){
		this.abbr = abbr;
        this.color = color;
        this.name = name;
	}

}