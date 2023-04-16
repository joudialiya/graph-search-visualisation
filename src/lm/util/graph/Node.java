package lm.util.graph;

/* 
 * you need to re-implaiment equals() and hashCode(), 
 * and optionaly toString() 
 */

public abstract class Node implements Comparable<Node>{
    public Node parrent = null;
    public float cost = 0;

    // deep copy
    public abstract Node copy();

    public int compareTo(Node node){
        if(this.cost > node.cost)
            return 1;
        if(this.cost < node.cost)
            return -1;
        
        return 0;
    }
}