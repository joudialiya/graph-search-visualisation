package lm;

import lm.util.graph.*;
import java.util.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

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
        this.generate_button.setEnabled(false);

        this.base = base;
        this.add(enable_animation_button);
        this.add(generate_button);
        this.setVisible(true);
    }

    private void generate_maze(){
        Graph grid = base.grid;
        generate_helper(grid, new Cell(0, 0));

    }

    private void generate_helper (Graph grid, Cell cell){

        List<Node> list = grid.adjacency_list.get(cell);
        int size = grid.adjacency_list.get(cell).size();
        if(size == 0)
            return;

        int index = new Random().nextInt(size);
        Cell next = (Cell) grid.adjacency_list.get(cell).get(index);

        for(int i=0; i<size; ++i){
            if(i != index){
                Node to_unbound = grid.adjacency_list.get(cell).get(i);
                grid.remove_link(
                    cell,
                    to_unbound,
                    false
                );
            }
        }
        generate_helper(grid, next);

    }

}
