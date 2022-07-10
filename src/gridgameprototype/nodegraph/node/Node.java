package gridgameprototype.nodegraph.node;

import gridgameprototype.nodegraph.NodeGraph;
import gridgameprototype.nodegraph.node.nodetypes.NodeType;

import java.util.ArrayList;
import java.util.UUID;

public class Node<NT extends NodeType> {
    public final UUID uuid;
    public final NT nodeType;
    public final NodeGraph parentGraph;
    protected final ArrayList<UUID> parentNodes = new ArrayList<>();
    public final ArrayList<UUID> childNodes = new ArrayList<>(); // come back and make this protected maybe

    protected Node(NodeGraph parentGraph, NT nodeType, UUID[] parentNodes) {
        uuid = UUID.randomUUID();
        this.parentGraph = parentGraph;
        this.nodeType = nodeType;
        this.parentGraph.addNode(this, parentNodes);
    }

    public void addParent(UUID parentID) {
        parentNodes.add(parentID);
        parentGraph.getNode(parentID).addChild(uuid);
    }

    public void addChild(UUID childID) {
        childNodes.add(childID);
    }
}
