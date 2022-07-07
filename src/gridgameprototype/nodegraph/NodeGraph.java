package gridgameprototype.nodegraph;

import gridgameprototype.nodegraph.node.Node;
import gridgameprototype.nodegraph.node.NodeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class NodeGraph {
    private final ArrayList<NodeType> nodeTypeList = new ArrayList<>();
    private final HashMap<UUID, Node<? extends NodeType>> nodeDictionary = new HashMap<>();

    public <NT extends NodeType> void addNode(Node<NT> node) {
        nodeDictionary.put(node.uuid, node);
        nodeTypeList.add(node.nodeType); //come back and consider how values that are no longer in the graph (because old nodes with this type got replaced or deleted) should be removed
    }
}
