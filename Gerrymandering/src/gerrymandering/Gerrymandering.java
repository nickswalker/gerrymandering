package gerrymandering;

import grid.*;

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

    public Gerrymandering(int height, int width) {
        super();
        this.setTitle("Gerrymandering");
        //Make a ten by ten grid that will host Regions
        map = new PoliticalMap(height, width);
        grid = new Grid<>(height, width);
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

    private EnumMap<Party, Integer> tallyAllVotes() {
        Set<GridGroup> groups = grid.getGroups();
        EnumMap<Party, Integer> groupTally = new EnumMap<>(Party.class);
        for (Party party : Party.values()) {
            groupTally.put(party, 0);
        }
        for (GridGroup group : groups) {
            Party regionVote = majorityParty(group);
            groupTally.put(regionVote, groupTally.get(regionVote) + 1);
        }

        return groupTally;
    }

    public void groupCreated(GridGroup group) {

        Color groupColor = colorForGroup(group);
        for (Location location : group) {
            GridObject object = grid.get(location);
            object.setBackground(groupColor);
        }

        if (gameIsOver()) {
            Map.Entry<Party, Integer> winner = determineWinner();
            String endGameMessage = "The " + winner.getKey().name() + " party wins the election!";
            JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this), endGameMessage);
        }
    }

    /**
     Entry point for the application. Sets up a frame and all
     necessary objects.
     */
    public static void main(String[] args) {
        Gerrymandering gerrymandering = new Gerrymandering(10, 10);

    }

    private Party majorityParty(GridGroup group) {
        EnumMap<Party, Integer> groupTally = districtTally(group);
        Map.Entry<Party, Integer> maxEntry = null;
        for (Map.Entry<Party, Integer> entry : groupTally.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

    private EnumMap<Party, Integer> districtTally(GridGroup group) {
        EnumMap<Party, Integer> groupTally = new EnumMap<>(Party.class);
        for (Party party : Party.values()) {
            groupTally.put(party, 0);
        }
        for (Location l : group.contents) {
            Region r = grid.get(l);
            groupTally.put(r.party, 1 + groupTally.get(r.party));
        }
        return groupTally;
    }

    private boolean gameIsOver() {
        return grid.getGroups().size() >= (grid.width * grid.height) / grid.getGroupSize();
    }

    private Map.Entry<Party, Integer> determineWinner() {
        EnumMap<Party, Integer> results = tallyAllVotes();
        Map.Entry<Party, Integer> maxEntry = null;
        for (Map.Entry<Party, Integer> entry : results.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }

    private Color colorForGroup(GridGroup group) {
        EnumMap<Party, Integer> tally = districtTally(group);
        Party majorityParty = majorityParty(group);
        float strength = (float) tally.get(majorityParty) / grid.getGroupSize();
        Color partyColor = majorityParty.color;
        int newRed = (int) ((float) partyColor.getRed() * strength);
        int newGreen = (int) ((float) partyColor.getGreen() * strength);
        int newBlue = (int) ((float) partyColor.getBlue() * strength);
        return new Color(255 - (newBlue + newGreen) / 2, 255 - (newBlue + newRed) / 2, 255 - (newGreen + newRed) / 2);

    }

}
