package lm.test;

import javax.swing.*;
import java.awt.*;

public class Testing extends JComponent {
    
    Testing(){
        this.setPreferredSize(new Dimension(400, 400));
        JFrame frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        System.out.println("print");
        g.setColor(Color.BLACK);
        g.drawString("String!!!", 100, 100);
    }
    public static void main(String[] argv){

        new Testing();

        
    }
}

