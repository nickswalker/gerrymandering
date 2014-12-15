package gerrymandering;

import grid.Grid;
import grid.GridDelegate;
import grid.GridObjectGroup;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 The driving class of the game.
 Sets up a grid and initializes the game.
 */
public class Gerrymandering extends JFrame implements GridDelegate {
    private PoliticalMap map;
    private Grid<Region> grid;

    public Gerrymandering() {
        super();
        this.setTitle("Gerrymandering");
        //Make a ten by ten grid that will host Regions
        map = new PoliticalMap(10, 10);
        grid = new Grid<>(10, 10);
        grid.setGroupSize(5);
        map.setUpGrid(grid);
        this.setSize(new Dimension(500, 500));
        grid.setDelegate(this);
        grid.setSize(this.getSize());
        this.getContentPane().add(this.grid);

        setSize(new Dimension(600, 600));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public EnumMap<Party, Integer> checkScore() {
        Set<GridObjectGroup<Region>> groups = grid.getGroups();
        EnumMap<Party, Integer> groupTally = new EnumMap<>(Party.class);
        for (Party party : Party.values()) {
            groupTally.put(party, 0);
        }
        for (GridObjectGroup<Region> group : groups) {
            Party regionVote = majorityParty(group);
            groupTally.put(regionVote, groupTally.get(regionVote) + 1);
        }

        return groupTally;
    }

    public void groupCreated(GridObjectGroup group) {
        System.out.println(checkScore());
        if (grid.getGroups().size() >= (grid.width * grid.height) / grid.getGroupSize()) {
            EnumMap<Party, Integer> results = checkScore();
            Map.Entry<Party, Integer> maxEntry = null;
            for (Map.Entry<Party, Integer> entry : results.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            String endGameMessage = "The " + maxEntry.getKey() + " party wins the election!";
            JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), endGameMessage);
        }
    }

    /**
     Entry point for the application. Sets up a frame and all
     necessary objects.
     */
    public static void main(String[] args) {
        Gerrymandering gerrymandering = new Gerrymandering();

    }

    public Party majorityParty(GridObjectGroup<Region> group) {
        EnumMap<Party, Integer> groupTally = new EnumMap<>(Party.class);
        for (Party party : Party.values()) {
            groupTally.put(party, 0);
        }
        for (Region r : group.contents) {

            groupTally.put(r.party, 1 + groupTally.get(r.party));
        }
        Map.Entry<Party, Integer> maxEntry = null;
        for (Map.Entry<Party, Integer> entry : groupTally.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

}
