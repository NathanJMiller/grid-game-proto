package gridgameprototype.grid.tile;

public final class Tile {

    public final GridPosition Position;
    public final boolean Solid;

    public Tile(GridPosition pos) {
        this(pos, true);
    }
    public Tile(GridPosition pos, boolean solid) {
        Position = pos;
        Solid = solid;
    }
}
