package lm.util.graph;

import java.util.*;

/* 
 * this class represent a step of a search algo, \
 * it keeps tack of its [frantier], and the visited nodes.
 */
public class AlgoStep {

    public List<Node> visited = new ArrayList<>();
    public List<Node> set = new ArrayList<>();
    public Node active = null;

    public AlgoStep (Collection<? extends Node> visited, Collection<? extends Node> set, Node active){

        this.active = active;
        this.visited.addAll(visited);
        this.set.addAll(set);
    }
}
