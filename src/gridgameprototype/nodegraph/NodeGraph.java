package gridgameprototype.nodegraph;

import gridgameprototype.nodegraph.node.Node;
import gridgameprototype.nodegraph.node.nodetypes.NodeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class NodeGraph {
    private final ArrayList<NodeType> nodeTypeList = new ArrayList<>();
    private final HashMap<UUID, Node<? extends NodeType>> nodeDictionary = new HashMap<>();
    private final HashMap<UUID, Node<? extends NodeType>> rootNodes = new HashMap<>();

    public <NT extends NodeType> void addNode(Node<NT> node, UUID[] parentNodes) {
        nodeDictionary.put(node.uuid, node);
        nodeTypeList.add(node.nodeType); //come back and consider how values that are no longer in the graph (because old nodes with this type got replaced or deleted) should be removed
        if(parentNodes != null && parentNodes.length > 0) {
            for(UUID parentID : parentNodes) node.addParent(parentID);
        } else {
            rootNodes.put(node.uuid, node);
        }
    }

    public Node<? extends NodeType> getNode(UUID id) {
        return nodeDictionary.get(id);
    }

    public HashMap<UUID, Node<? extends NodeType>> getNodes() {
        return nodeDictionary;
    }

    public HashMap<UUID, Node<? extends NodeType>> getRootNodes() {
        return rootNodes;
    }
}
