package gridgameprototype.nodegraph.node;

import gridgameprototype.nodegraph.NodeGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Node<NT extends NodeType> {
    public final UUID uuid;
    public final NT nodeType;
    public final NodeGraph parentGraph;
    protected final ArrayList<UUID> parentNodes = new ArrayList<>();
    protected final ArrayList<UUID> childNodes = new ArrayList<>();

    protected Node(NodeGraph pGraph, NT nt, UUID[] pNodes) {
        uuid = UUID.randomUUID();
        parentGraph = pGraph;
        nodeType = nt;
        if(pNodes != null && pNodes.length > 0) {
            parentNodes.addAll(Arrays.asList(pNodes));
        }
    }

    private boolean addChild(Node<? extends NodeType> childNode) {
        return addChild(childNode.uuid);
    }
    private boolean addChild(UUID childID) {
        return childNodes.add(childID);
    }
}
