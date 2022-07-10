package gridgameprototype.nodegraph.node;

import gridgameprototype.nodegraph.NodeGraph;

import java.util.ArrayList;
import java.util.UUID;

public class Node<NT extends NodeType> {
    public final UUID uuid;
    public final NT nodeType;
    public final NodeGraph parentGraph;
    protected final ArrayList<UUID> parentNodes = new ArrayList<>();
    protected final ArrayList<UUID> childNodes = new ArrayList<>();

    protected Node(NodeGraph parentGraph, NT nodeType) {
        uuid = UUID.randomUUID();
        this.parentGraph = parentGraph;
        this.nodeType = nodeType;
    }

    public void addParent(UUID parentID) {
        parentNodes.add(parentID);
        parentGraph.getNode(parentID).addChild(uuid);
    }

    public void addChild(UUID childID) {
        childNodes.add(childID);
    }
}
