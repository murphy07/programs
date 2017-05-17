package amoba;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main {
    public static void main(String[] args){
    BoardSetting board=new BoardSetting();    
        
    JFrame frame=new JFrame();
    frame.setSize(350,250);
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.add(board);
    frame.setVisible(true);
    frame.pack();
    }
}
