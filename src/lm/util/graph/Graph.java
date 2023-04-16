package lm.util.graph;

import java.util.*;

public class Graph {
    
    public Map<Node, List<Node>> adjacency_list;
    public Map<Node, Float> herestic;

    public Graph(){
        
        this.adjacency_list = new HashMap<>();
        this.herestic = new HashMap<>();
    }

    public void add(Node node){

        adjacency_list.putIfAbsent(node, new ArrayList<>());
        herestic.putIfAbsent(node, 1.f);
    }

    public void remove_node(Node node){

        adjacency_list.remove(node);
        herestic.remove(node);
    }

    public void link(Node from, Node to, boolean orionted){
        
        Node actual_from = this.get(from);
        Node actual_to = this.get(to);

        if (actual_from != null && actual_to != null){

            adjacency_list.get(actual_from).add(actual_to);
            if (!orionted)
                adjacency_list.get(actual_to).add(actual_from);
        }

    }

    public void remove_link(Node from, Node to, boolean orionted){

        Node actual_from = this.get(from);
        Node actual_to = this.get(to);

        System.out.println("[remove link] "+ from + " " + to);

        if (actual_from != null && actual_to != null){

            adjacency_list.get(actual_from).remove(actual_to);
            if (!orionted)
                adjacency_list.get(actual_to).remove(actual_from);
        }
    }
    public Node get(Node node){

        Set<Node> set = adjacency_list.keySet();
        for(Node element: set){
            if (element.equals(node))
                return element;
        }
        return null;
    }
}
