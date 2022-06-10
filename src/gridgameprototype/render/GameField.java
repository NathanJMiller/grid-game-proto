package gridgameprototype.render;

import gridgameprototype.grid.procedural.GenerationConfiguration;
import gridgameprototype.grid.procedural.VaultGrid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class GameField extends JPanel {

    public final boolean DEBUG_MODE;
    public final int GRID_SQUARE_SIZE = 64;

    private long timeOfLastFrame;
    private Point mouseClickLocation;

    private Image wall;
    private final String levelString;

    public GameField(boolean debug) {
        super();
        updateFrameTime();
        DEBUG_MODE = debug;

        addMouseListener(new GameMouseEvent());

        VaultGrid testLevel = new VaultGrid(16, 9, new GenerationConfiguration());
        levelString = testLevel.printToString();
        System.out.println(levelString);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 450);
    }

    protected void refreshDisplay() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //g.setColor(new Color(0.5f, 0.65f, 0.8f, 1f));
        //g.drawRect(30, 100, this.getWidth() - 60, this.getHeight() - 200);
        if (DEBUG_MODE) paintDebugInfo(g);
        updateFrameTime();
    }

    private void paintDebugInfo(Graphics g) {
        boolean didAssetsLoad = loadAssets();
        if(didAssetsLoad) paintWallTile(g);
        paintGridLines(g);
        paintMousePosition(g);

        g.setColor(Color.ORANGE);
        g.drawString(String.format("resolution[%d, %d]", getWidth(), getHeight()), 10, 10);
        g.drawString(String.format("time_of_last_update[%s]", new Date(timeOfLastFrame)), 10, 25);
        g.drawString(String.format("time_since_last_update[%d]", getTimeSinceLastFrame()), 10, 40);
        g.drawString(String.format("assets_loaded[%b]", didAssetsLoad), 10, 55);
        g.setColor(Color.MAGENTA);
        int y = 70;
        for(String line : levelString.split("\s")) {
            g.drawString(line, 50, y);
            y += 12;
        }
    }
    private void paintGridLines(Graphics g) {
        g.setColor(Color.BLUE);
        int x_offset = (getWidth()/2)%GRID_SQUARE_SIZE + GRID_SQUARE_SIZE/2;
        int y_offset = (getHeight()/2)%GRID_SQUARE_SIZE + GRID_SQUARE_SIZE/2;
        for(int h = 0; h <= getWidth()/GRID_SQUARE_SIZE; h++) {
            int column_x_pos = h * GRID_SQUARE_SIZE + x_offset;
            g.drawLine(column_x_pos, 0, column_x_pos, getHeight());
        }
        for(int k = 0; k <= getHeight()/GRID_SQUARE_SIZE; k++) {
            int row_y_pos = k * GRID_SQUARE_SIZE + y_offset;
            g.drawLine(0, row_y_pos, getWidth(), row_y_pos);
        }

        int center_x = getWidth()/2;
        int center_y = getHeight()/2;
        int lower_center_x_bound = center_x - GRID_SQUARE_SIZE/2;
        int lower_center_y_bound = center_y - GRID_SQUARE_SIZE/2;

        g.setColor(Color.CYAN);
        g.drawRect(lower_center_x_bound, lower_center_y_bound, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE);
    }
    private void paintMousePosition(Graphics g) {
        if(mouseClickLocation == null) return;
        int x = mouseClickLocation.x;
        int y = mouseClickLocation.y;

        g.setColor(Color.GREEN);
        g.drawLine(0, y, getWidth(), y);
        g.drawLine(x, 0, x, getHeight());
    }

    private long getTimeSinceLastFrame() {
        return System.currentTimeMillis() - timeOfLastFrame;
    }

    private boolean loadAssets() {
        try {
            File file = new File("wall.png");
            wall = ImageIO.read(file);
            //floor = ImageIO.read(new File("floor.png"));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private void paintWallTile(Graphics g) {
        int center_x = getWidth()/2;
        int center_y = getHeight()/2;
        int lower_center_x_bound = center_x - GRID_SQUARE_SIZE/2;
        int lower_center_y_bound = center_y - GRID_SQUARE_SIZE/2;
        g.drawImage(wall, lower_center_x_bound, lower_center_y_bound, GRID_SQUARE_SIZE, GRID_SQUARE_SIZE, Color.BLUE, null);
    }

    private void updateFrameTime() {
        timeOfLastFrame = System.currentTimeMillis();
    }

    private class GameMouseEvent extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            mouseClickLocation = e.getPoint();
            refreshDisplay();
        }
    }

}
