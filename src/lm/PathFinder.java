package lm;

import javax.swing.*;

import lm.util.graph.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class PathFinder extends JComponent 
    implements Runnable{

    public static int CELL_SIZE = 40;
    public static int EDGE_SIZE = 7;
    public static int NBR_CELLS = 20;
    
    int total_size;

    Image img;
    Graphics2D g2;

    public Graph grid = new Graph();
    Utilities grid_handle = new Utilities(grid);
    AlgoStep step = null;
    
    //---------------------------
    Cell hovered = null;
    Cell start = null;
    Cell target = null;
    Cell found = null;
    //---------------------------

    //---- Control flags
    public boolean isAnimation;
    //------------------

    PathFinder(){

        //-------------------------------------------------------------------------
        // adding the nodes
        for(int row=0; row<NBR_CELLS; ++row){
            for(int col=0; col<NBR_CELLS; ++col){
                Cell cell = new Cell(col, row);
                grid.add(cell);
            }
        }
        // adding the connections
        for(int row=0; row<NBR_CELLS; ++row){
            for(int col=0; col<NBR_CELLS; ++col){

                Cell cell = (Cell) grid.get(new Cell(col, row));
                // add the neaighbors
                if (row-1>-1)
                    grid.link(cell , new Cell(col, row-1), false);
                    
                if (col-1>-1)
                    grid.link(cell , new Cell(col-1, row), false);

            }
        }
        //-------------------------------------------------------------------------

        this.total_size = NBR_CELLS * (CELL_SIZE + EDGE_SIZE) - EDGE_SIZE;
        this.setPreferredSize(new Dimension(total_size, total_size));
        
        //-- setting up the image 
        this.img = new BufferedImage(total_size, total_size, BufferedImage.TYPE_INT_RGB);
        this.g2 = (Graphics2D) img.getGraphics();
        //-----------------------

        this.addMouseListener(mouse_handle);
        this.addMouseMotionListener(mouse_handle);

        JFrame frame = new JFrame();
        frame.add(this);
        frame.add(new ControlPan(this), BorderLayout.EAST);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        this.start = new Cell(12, 13);
        this.target = new Cell(8,7);
        this.found = (Cell)grid_handle.uniform_cost_search(start, target);

    }
    public void update(){

        if(isAnimation)
            next_step();
        

    }
    private void next_step(){
        AlgoState state = grid_handle.algorithm_state;
        
        if( state == null)
            return;
        
        if(state.steps.size() != 0){
            this.step = state.steps.remove(0);
        }
    }

    public void draw(){

        // clear the screen
        this.clear(null);

        // draw the links
        this.draw_links();

        // draw the cells
        this.draw_grid();

        // draw algo step
        if(isAnimation)
            this.draw_algo_step();

        // draw the path
        this.draw_path();

        // draw the spacial cells
        if(hovered != null){
            this.g2.setColor(Color.GREEN);
            this.draw_cell(hovered, true);
        }
        if(start != null){
            this.g2.setColor(Color.BLUE);
            this.draw_cell(start, true);
        }
        if(target != null){
            this.g2.setColor(Color.RED);
            this.draw_cell(target, true);
        }
        
        // display
        this.getGraphics().drawImage(img, 0, 0, null);
    }

    private void draw_cost(Cell cell){
        this.g2.drawString(
                Float.toString(cell.cost),
                cell.x * (CELL_SIZE + EDGE_SIZE),
                cell.y * (CELL_SIZE + EDGE_SIZE)
            );
    };
    private void draw_algo_step(){

        if(step == null)
            return;

        Iterator<Node> over_visited = step.visited.iterator();
        Iterator<Node> over_set = step.set.iterator();

        while(over_visited.hasNext()){
            Cell cell = (Cell)over_visited.next();
            this.g2.setColor(Color.BLUE);
            this.draw_cell(cell);
            
        }

        while(over_set.hasNext()){
            Cell cell =(Cell)over_set.next();
            this.g2.setColor(Color.WHITE);
            this.draw_cell(cell, true);
            this.draw_cost(cell);
        }

        this.g2.setColor(Color.LIGHT_GRAY);
        this.draw_cell((Cell)step.active, true);
        
    }
    private void draw_links(){
        for(int row=0; row<NBR_CELLS; ++row){
            for(int col=0; col<NBR_CELLS; ++col){

                this.g2.setColor(Color.PINK);
                Cell curr = (Cell) grid.get(new Cell(col, row));

                for(Node node: grid.adjacency_list.get(curr)){
                    this.draw_link(curr, (Cell)node);
                }
            }
        }
    }
    private void clear(Color color){
        this.g2.setColor(color == null? Color.BLACK: color);
        this.g2.fillRect(0, 0, total_size, total_size);;
    }
    private void draw_path(){
        
        if (this.found == null || this.found.parrent == null)
            return;

        Stroke def  = this.g2.getStroke();
        this.g2.setStroke(new BasicStroke(5));

        this.g2.setColor(Color.YELLOW);

        java.util.List<Node> path = Utilities.back_to_the_root(found);

        for(Node cell: path){
            //this.draw_cell((Cell) cell, true);
            if (cell.parrent != null)
                this.draw_link((Cell)cell, (Cell)cell.parrent);
        }

        this.g2.setStroke(def);
        
    }
    private void draw_grid(){
        for(Node node:grid.adjacency_list.keySet()){
            Cell cell = (Cell)node;
            if (((Cell)grid.get(cell)).isWall){
                this.g2.setColor(Color.DARK_GRAY);
            }else{
                this.g2.setColor(Color.PINK);
            }
            this.draw_cell(cell, true);
        }
    }
    private void draw_cell(Cell cell){
        draw_cell(cell, false);
    }
    private void draw_cell(Cell cell, boolean isfull){
        if(isfull){

            this.g2.fillRect(
                cell.x * (CELL_SIZE + EDGE_SIZE), 
                cell.y * (CELL_SIZE + EDGE_SIZE), 
                CELL_SIZE, 
                CELL_SIZE
            );

        }else{

            Stroke def  = this.g2.getStroke();
            this.g2.setStroke(new BasicStroke(2));

            this.g2.drawRect(
                cell.x * (CELL_SIZE + EDGE_SIZE), 
                cell.y * (CELL_SIZE + EDGE_SIZE), 
                CELL_SIZE, 
                CELL_SIZE
            );

            this.g2.setStroke(def);
        }
    }
    private void draw_link(Cell from, Cell to){

        this.g2.drawLine(
            ((Cell)from).x  * (CELL_SIZE + EDGE_SIZE) + CELL_SIZE/2, 
            ((Cell)from).y  * (CELL_SIZE + EDGE_SIZE) + CELL_SIZE/2,
            ((Cell)to).x    * (CELL_SIZE + EDGE_SIZE) + CELL_SIZE/2,
            ((Cell)to).y    * (CELL_SIZE + EDGE_SIZE) + CELL_SIZE/2
        );

    }
    public void run(){

        while(true){
            try {

                update();
                draw();
                Thread.sleep(16);
                //System.out.print(".");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    MouseAdapter mouse_handle = new MouseAdapter() {

        @Override
        public void mouseClicked(MouseEvent e) {
            

            // left button
            if(e.getButton() == 1 ){

                if(e.isShiftDown()){
                    PathFinder.this.start = hovered;
                }else if (e.isControlDown()){
                    PathFinder.this.target = hovered;
                }else{

                    // place remove a wall
                    hovered.isWall = hovered.isWall ? false: true;

                    if (hovered.isWall){

                        grid.remove_link(hovered , new Cell(hovered.x, hovered.y-1), false);
                        grid.remove_link(hovered , new Cell(hovered.x-1, hovered.y), false);
                        grid.remove_link(hovered , new Cell(hovered.x, hovered.y+1), false);
                        grid.remove_link(hovered , new Cell(hovered.x+1, hovered.y), false);

                    }else{

                        if (
                            hovered.y-1 > -1 && 
                            !((Cell)grid.get(new Cell(hovered.x, hovered.y-1))).isWall
                        ){
                            grid.link(hovered , new Cell(hovered.x, hovered.y-1), false);
                        }

                        if (
                            hovered.x-1 > -1 && 
                            !((Cell)grid.get(new Cell(hovered.x-1, hovered.y))).isWall
                        ){
                            grid.link(hovered , new Cell(hovered.x-1, hovered.y), false);
                        }

                        if (
                            hovered.y+1< NBR_CELLS && 
                            !((Cell)grid.get(new Cell(hovered.x, hovered.y+1))).isWall
                        ){
                            grid.link(hovered , new Cell(hovered.x, hovered.y+1), false);
                        }

                        if (
                            hovered.x+1< NBR_CELLS && 
                            !((Cell)grid.get(new Cell(hovered.x+1, hovered.y))).isWall
                        ){
                            grid.link(hovered , new Cell(hovered.x+1, hovered.y), false);
                        }
                    }      
                        
                }

                if(start != null && target!= null){
                    target.parrent = null;
                    start.parrent = null;
                    //found = (Cell) Utilities.depth_first_search(grid, start, target);
                    found = (Cell) grid_handle.uniform_cost_search(start, target);
                }
            }

        }
        @Override
        public void mouseMoved(MouseEvent e) {

            PathFinder.this.hovered = (Cell) grid.get(
                new Cell(e.getX() / (CELL_SIZE + EDGE_SIZE), e.getY() / (CELL_SIZE + EDGE_SIZE))
            );
        }
    };

    public static void main(String[] args) {
        new Thread(new PathFinder()).start();
    }
}