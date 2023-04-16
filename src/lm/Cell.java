package lm;

import lm.util.graph.Node;

public class Cell extends Node{

    boolean isWall = false;
    int x;
    int y;

    Cell(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Cell set_type(boolean tag){
        this.isWall = tag;
        return this;
    }

    @Override
    public boolean equals(Object obj){
        Cell cell = (Cell) obj;
        if (cell.x == this.x && cell.y == this.y)
            return true;
        return false;
    }
    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
    @Override
    public String toString(){
        return "[" + x +  ", " + y + "]";
    }
    @Override
    public Node copy(){
        return new Cell(this.x, this.y);
    }
}
