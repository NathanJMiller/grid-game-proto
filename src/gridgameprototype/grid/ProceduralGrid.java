package gridgameprototype.grid;

import gridgameprototype.grid.procedural.GenerationConfiguration;

public abstract class ProceduralGrid extends TileGrid {
    protected ProceduralGrid(int width, int height, GenerationConfiguration config) {
        super(width, height);
        generate(config);
    }
    protected abstract void generate(GenerationConfiguration config);
}
