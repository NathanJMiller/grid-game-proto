package gridgameprototype.grid;

import gridgameprototype.grid.tile.GridPosition;
import gridgameprototype.grid.tile.Tile;

import java.util.BitSet;
import java.util.HashMap;

public class TileGrid {

    public final int WIDTH;
    public final int HEIGHT;
    private final HashMap<Integer, HashMap<Integer, Tile>> grid = new HashMap<>();
    private final HashMap<Integer, HashMap<Integer, BitSet>> connectingTileData = new HashMap<>();
    private boolean validConnectionData = false;

    public TileGrid(int width, int height) {
        WIDTH = width;
        HEIGHT = height;

        for(int x = 0; x < WIDTH; x++) {

            HashMap<Integer, Tile> column = new HashMap<>();
            HashMap<Integer, BitSet> conDataColumn = new HashMap<>();

            for(int y = 0; y < HEIGHT; y++) {

                column.put(y, new Tile(new GridPosition(x, y)));
                conDataColumn.put(y, new BitSet(9));

            }

            grid.put(x, column);
            connectingTileData.put(x, conDataColumn);
        }

        updateConnectingTileData();

    }

    private void updateConnectingTileData() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0, bit_index = 0; y < HEIGHT; y++) {
                for(int x_off = -1; x_off <= 1; x_off++) {
                    int h = x + x_off;
                    if(h == x) continue;
                    boolean position_x_outside = h < 0 || h >= WIDTH;
                    for(int y_off = -1; y_off <= 1; y_off++, bit_index++) {
                        int k = y + y_off;
                        if(k == y) continue;
                        boolean position_y_outside = k < 0 || k >= HEIGHT;
                        boolean position_outside = position_x_outside || position_y_outside;
                        if(position_outside) {
                            connectingTileData.get(x).get(y).set(bit_index);
                            continue;
                        }
                        boolean position_is_solid = grid.get(h).get(k).Solid;
                        if(position_is_solid) {
                            connectingTileData.get(x).get(y).set(bit_index);
                        } else connectingTileData.get(x).get(y).clear(bit_index);
                    }
                }
            }
        }
        validConnectionData = true;
    }

    protected void putTile(int x, int y, Tile tile) {
        grid.get(x).put(y, tile);
        validConnectionData = false;
    }

    protected Tile getTile(int x, int y) {
        return grid.get(x).get(y);
    }

    public BitSet getConnectionData(int x, int y) {
        if(!validConnectionData) updateConnectingTileData();
        return connectingTileData.get(x).get(y);
    }

    public String printToString() {
        StringBuilder output = new StringBuilder();
        char block = '\u2588';
        char empty = '\u2591';
        for(int y = 0; y < HEIGHT; y++) {
            StringBuilder line = new StringBuilder();
            for(int x = 0; x < WIDTH; x++) {
                if(getTile(x, y).Solid) {
                    line.append(block);
                } else line.append(empty);
            }
            line.append(' ');
            line.append('\n');
            output.append(line);
        }
        return output.toString();
    }

}
