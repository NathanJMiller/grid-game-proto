package gridgameprototype.time;

import javax.swing.*;
import java.awt.event.ActionListener;

public final class GameClock {

    private final Timer timer;
    private final JComponent gameWindow;
    private final int DELAY = 17;
    private final ActionListener GAME_TICK = e -> tick();

    public GameClock(JComponent window) {
        gameWindow = window;
        timer = new Timer(DELAY, GAME_TICK);
        timer.start();
    }

    /**Take one step forward in the game time, progressing logic by the smallest increment*/
    private void tick() {
        gameWindow.repaint();
    }

}
