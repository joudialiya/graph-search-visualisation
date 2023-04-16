package lm;

import lm.util.graph.*;
import java.util.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPan extends JPanel {
    
    PathFinder base = null;
    Button enable_animation_button;
    Button generate_button = null;

    ControlPan(PathFinder base){
        this.enable_animation_button = new Button("Animation");
        this.enable_animation_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
                base.isAnimation = !base.isAnimation;
            }
        });

        this.generate_button = new Button("Genarate");
        this.generate_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event){
                generate_maze();
            }
        });
        this.generate_button.setEnabled(true);

        this.base = base;
        this.add(enable_animation_button);
        this.add(generate_button);
        this.setVisible(true);
    }

    private void generate_maze(){
        Graph grid = base.grid;
        List<Cell> visited = new ArrayList<>();
        generate_helper(grid, visited, new Cell(0, 0));

    }

    private void generate_helper (Graph grid, List<Cell> visited, Cell cell){

        

    }
    private void clear_grid(Graph grid){
        for (Node node: grid.adjacency_list.keySet()){
            grid.clear_links(node, false);
            ((Cell)node).isWall = true;
        }
    }

}
