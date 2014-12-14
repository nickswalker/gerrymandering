package gerrymandering;

import grid.Grid;

public class PoliticalMap {

    //Row major order
    private Region[][] map;
    private int height;
    private int width;

    PoliticalMap(int height, int width) {
        this.width = width;
        this.height = height;
        map = new Region[height][width];
        populateMap();
    }

    /**
     Fill the grid with a random arrangement of political regions.
     */
    private void populateMap() {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                map[r][c] = new Region();
            }
        }
    }

    public void setUpGrid(Grid<Region> grid){
        for (int r = 0; r < grid.width; r++) {
            for (int c = 0; c < grid.height; c++) {
                grid.put(map[r][c],c,r);
            }
        }
    }

}
