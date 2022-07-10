package gridgameprototype.render;

import gridgameprototype.grid.procedural.GenerationConfiguration;
import gridgameprototype.grid.procedural.VaultGrid;
import gridgameprototype.nodegraph.NodeGraph;
import gridgameprototype.nodegraph.node.Node;
import gridgameprototype.nodegraph.node.RoomNode;
import gridgameprototype.nodegraph.node.nodetypes.RoomNodeType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class GameField extends JPanel {

    public final boolean DEBUG_MODE;
    public final int GRID_SQUARE_SIZE = 64;

    private long timeOfLastFrame;
    private Point mouseClickLocation;

    private Image wall;
    private final String levelString;
    private final NodeGraph ngraph;

    public GameField(boolean debug) {
        super();
        updateFrameTime();
        DEBUG_MODE = debug;

        addMouseListener(new GameMouseEvent());

        VaultGrid testLevel = new VaultGrid(16, 9, new GenerationConfiguration());
        levelString = testLevel.printToString();
        System.out.println(levelString);
        ngraph = new NodeGraph();
        RoomNode rootRoom = new RoomNode(ngraph, RoomNodeType.EMPTY, null);
        RoomNode childRoom1 = new RoomNode(ngraph, RoomNodeType.EMPTY, new java.util.UUID[]{rootRoom.uuid});
        RoomNode childRoom2 = new RoomNode(ngraph, RoomNodeType.EMPTY, new java.util.UUID[]{rootRoom.uuid});
        RoomNode childChildRoom = new RoomNode(ngraph, RoomNodeType.EMPTY, new java.util.UUID[]{childRoom1.uuid, childRoom2.uuid});
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
        paintNodeGraph(g, ngraph);
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
    private void paintNodeGraph(Graphics g, NodeGraph nodeGraph) {
        g.setColor(Color.RED);
        int pointer_x = 300;
        int pointer_y = 100;
        HashMap<UUID, Dimension> drawnNodes = new HashMap<>();
        nodeGraph.getRootNodes().forEach((id, node)->{
            paintNodeGraphNode(g, id, nodeGraph, drawnNodes, pointer_x, pointer_y);
        });
    }
    private void paintNodeGraphNode(Graphics g, UUID id, NodeGraph graph, HashMap<UUID, Dimension> drawnNodes, int pointer_x, int pointer_y) {
        Node<?> node = graph.getNode(id);
        double layerDistance = 80;
        ArrayList<UUID> undrawnChildren = new ArrayList<>();
        node.childNodes.forEach((childId)->{
            if(!drawnNodes.containsKey(childId)) undrawnChildren.add(childId);
        });
        int childCount = undrawnChildren.size();
        double childSeparatingAngle = childCount > 1 ? 90d/(childCount - 1) : 0;

        node.childNodes.forEach((childId)->{
            ArrayList<UUID> drawnSiblings = new ArrayList<>();
            drawnNodes.forEach((drawnId, drawnPos)->{
                if(node.childNodes.contains(drawnId)) {
                    drawnSiblings.add(drawnId);
                }
            });
            double x_offset_angle = childSeparatingAngle == 0 ? 45 : Math.toRadians(childSeparatingAngle*drawnSiblings.size());
            double y_offset_angle = childSeparatingAngle == 0 ? 45 : Math.toRadians(90-childSeparatingAngle*drawnSiblings.size());
            int y_offset = (int)(Math.sin(y_offset_angle) * layerDistance);
            int x_offset = (int)(Math.sin(x_offset_angle) * layerDistance);

            if(!drawnNodes.containsKey(childId)) {
                paintNodeGraphNode(g, childId, graph, drawnNodes, pointer_x + x_offset, pointer_y + y_offset);
                g.drawLine(pointer_x, pointer_y, pointer_x + x_offset, pointer_y + y_offset);
            } else {
                int end_x = drawnNodes.get(childId).width;
                int end_y = drawnNodes.get(childId).height;
                g.drawLine(pointer_x, pointer_y, end_x, end_y);
            }
        });
        drawnNodes.computeIfAbsent(id, (param)->{
            g.drawString(id.toString().substring(0,8), pointer_x, pointer_y);
            return new Dimension(pointer_x, pointer_y);
        });
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
