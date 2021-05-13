package sample.AI;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private boolean isMax;

    private List<Node> nodes;

    public Node()
    {
        this.nodes = new ArrayList<>();
    }

    Node(boolean isMax)
    {
        //this();
        this.isMax = isMax;
        //this.childNodes = new ArrayList<>();
    }

    public boolean isMax()
    {
        return isMax;
    }

    public void addChild(Node child)
    {
        nodes.add(child);
    }

    public List<Node> getNodes()
    {
        return nodes;
    }
}