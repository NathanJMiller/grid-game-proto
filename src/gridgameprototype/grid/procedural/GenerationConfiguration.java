package gridgameprototype.grid.procedural;

import java.util.Random;

public final class GenerationConfiguration {
    private final Random random;
    private final float eccentricity; //consider a decay value with a similar purpose?
    //malice property, that builds up when you exit a room without clearing the hazards? thus causing more hazardous rooms to generate?

    public GenerationConfiguration() {
        random = new Random();
        eccentricity = 0;
    }
    public GenerationConfiguration(long seed) {
        random = new Random(seed);
        eccentricity = 0;
    }
    public GenerationConfiguration(float eccentricity) {
        random = new Random();
        this.eccentricity = eccentricity;
    }

    public Random getRandom() {
        return random;
    }

    public float getEccentricity() {
        return eccentricity;
    }
}
