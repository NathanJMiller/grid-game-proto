package gridgameprototype.grid.procedural;

import gridgameprototype.grid.ProceduralGrid;

import java.util.Random;

public class RoomGrid extends ProceduralGrid {
    protected RoomGrid(int width, int height, GenerationConfiguration config) {
        super(width, height, config);
    }

    @Override
    protected void generate(GenerationConfiguration config) {
        Random random = config.getRandom();
        float eccentricity = config.getEccentricity();
        int complexity = decideComplexity(random, eccentricity);
    }

    private int decideComplexity(Random random, float eccentricity) {
        float maxComplexity = Math.min(random.nextFloat(), eccentricity + 0.2f) * 5;
        int intMaxComp = Math.round(maxComplexity);
        return random.nextInt(intMaxComp) + 1;
    }
}
