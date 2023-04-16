package lm.util.graph;

import java.util.*;


/* 
 * 
 */
public class Utilities {

    public Graph graph = null;
    public AlgoState algorithm_state = null; 

    public Utilities(Graph g){
        this.graph = g;
    }

    public void init(){
        algorithm_state = new AlgoState();
    }

    public Node bredth_first_search(Node start, Node target){

        this.init();

        List<Node> visited = new ArrayList<>();
        List<Node> queue = new ArrayList<>();

        queue.add(start.copy());
        
        while(!queue.isEmpty()){
            
            
            Node curr = queue.remove(0);
            visited.add(curr);
            algorithm_state.appand(new AlgoStep(visited, queue, curr));

            if (curr.equals(target)){
                algorithm_state.solution = curr;
                return curr;
            }

            for(Node child: graph.adjacency_list.get(curr)){

                
                if(!visited.contains(child) && !queue.contains(child)){
                    Node child_copy = child.copy();
                    child_copy.parrent = curr;
                    queue.add(child_copy);
                }
            }

        }
        return null;
    }
    public Node depth_first_search(Node start, Node target){

        this.init();
        List<Node> visited = new ArrayList<>();
        Stack<Node> stack = new Stack<>();

        stack.push(start.copy());
        
        while(!stack.isEmpty()){
            
            
            Node curr = stack.pop();
            visited.add(curr);
            algorithm_state.appand(new AlgoStep(visited, stack, curr));

            if (curr.equals(target)){
                algorithm_state.solution = curr;
                return curr;
            }

            for(Node child: graph.adjacency_list.get(curr)){

                Node child_copy = child.copy();
                
                if(!visited.contains(child)){
                    child_copy.parrent = curr;
                    stack.push(child_copy);
                }
            }

        }
        return null;
    }
    
    public Node uniform_cost_search( Node start, Node target){

        this.init();
        List<Node> visited = new ArrayList<>();
        Queue<Node> queue = new PriorityQueue<>();

        queue.add(start.copy());

        while(!queue.isEmpty()){

            
            Node curr = queue.poll();
            visited.add(curr);
            algorithm_state.appand(new AlgoStep(visited, queue, curr));

            if(curr.equals(target)){
                algorithm_state.solution = curr;
                return curr;
            }
            
            for(Node child: graph.adjacency_list.get(curr)){
                
                Node child_copy = child.copy();
                child_copy.cost = curr.cost + 1;
                child_copy.parrent = curr;
                
                if(!visited.contains(child)){

                    if(!queue.contains(child)){
                        queue.add(child_copy);
                    }else{
                        Node in = null;
                        for(Node node: queue){
                            if(node.equals(child)){
                                in = node;
                                break;
                            }
                        }
                        //in.cost > child_copy.cost
                        if(in.compareTo(child_copy) == 1){
                            queue.remove(in);
                            queue.add(child_copy);
                        }    
                    }
                }
            }
        }
        return null;
    }

    public static List<Node> back_to_the_root(Node node){
        List<Node> list = new ArrayList<>();
        Node curr = node;
        while(curr != null){
            //System.out.println(curr);
            list.add(curr);
            curr = curr.parrent;
        }
        return list;
    }
}
