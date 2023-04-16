package lm.util.graph;

import java.util.*;

/* this class represent the state of a search algo */
public class AlgoState {
    
    public List<AlgoStep> steps = new ArrayList<>();
    public Node solution = null;

    public void appand(AlgoStep step){
        this.steps.add(step);
    }
}
