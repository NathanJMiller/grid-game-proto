package gridgameprototype.grid.procedural;

import gridgameprototype.grid.ProceduralGrid;
import gridgameprototype.grid.tile.GridPosition;
import gridgameprototype.grid.tile.Tile;

import java.util.Random;

public class VaultGrid extends ProceduralGrid {
    public VaultGrid(int width, int height, GenerationConfiguration config) {
        super(width, height, config);
    }

    @Override
    protected void generate(GenerationConfiguration config) {
        Random random = config.getRandom();
        double eccentricity = config.getEccentricity();
        int xInterval = getPillarXInterval();
        int yInterval = getPillarYInterval();
        for(int x = 1; x < WIDTH - 1; x++) {
            for(int y = 1; y < HEIGHT - 1; y++) {
                //if(x % xInterval == 0 && y % yInterval == 0) continue;
                if((x == 1 || x == WIDTH - 2) && y % yInterval == 0 && random.nextDouble() > eccentricity) continue;
                if((y == 1 || y == HEIGHT - 2) && x % xInterval == 0 && random.nextDouble() > eccentricity) continue;
                putTile(x, y, new Tile(new GridPosition(x, y), false));
            }
        }
    }

    private int getPillarXInterval() {
        for(int interval = 3;; interval++) {
            if((WIDTH - 1) % interval == 0) return interval;
        }
    }
    private int getPillarYInterval() {
        for(int interval = 3;; interval++) {
            if((HEIGHT - 1) % interval == 0) return interval;
        }
    }
}
