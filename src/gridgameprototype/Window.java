package gridgameprototype;

import gridgameprototype.render.GameField;
import gridgameprototype.time.GameClock;

import javax.swing.*;

public class Window {

    private static void createStartWindow(boolean debug) {
        JFrame frame = new JFrame("Skeleton Merchant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gamePanel = new GameField(debug);

        frame.getContentPane().add(gamePanel);

        frame.pack();
        frame.setVisible(true);

        GameClock clock = new GameClock(gamePanel);
    }

    public static void main(String[] args) {

        //GameClock clock = new GameClock();

        boolean debug = false;

        for(String argument: args) {
            if(argument.equalsIgnoreCase("debug")) {
                System.out.println("Running in debug mode...");
                debug = true;
            }
        }

        boolean finalDebug = debug;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createStartWindow(finalDebug);
            }
        });
    }
}
