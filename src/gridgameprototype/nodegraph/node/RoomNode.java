package gridgameprototype.nodegraph.node;

import gridgameprototype.nodegraph.NodeGraph;
import gridgameprototype.nodegraph.node.nodetypes.RoomNodeType;

import java.util.UUID;

public class RoomNode extends Node<RoomNodeType> {

    public RoomNode(NodeGraph parentGraph, RoomNodeType roomType, UUID[] parentRooms) {
        super(parentGraph, roomType, parentRooms);
    }
}
