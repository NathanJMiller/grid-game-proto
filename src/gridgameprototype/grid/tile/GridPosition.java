package gridgameprototype.grid.tile;

import java.util.Objects;

public class GridPosition {

    private final int global_x;
    private final int global_y;

    public GridPosition(int x, int y) {
        global_x  = x;
        global_y = y;
    }

    public GridPosition offsetFrom(GridPosition anchor, int x_offset, int y_offset) {
        return new GridPosition(anchor.global_x + x_offset, anchor.global_y + y_offset);
    }

    public int getGlobalX() {
        return global_x;
    }

    public int getGlobalY() {
        return global_y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridPosition that = (GridPosition) o;
        return global_x == that.global_x && global_y == that.global_y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(global_x, global_y);
    }
}
