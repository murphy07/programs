package amoba;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Button10x10 extends JButton implements ActionListener{
    private static final int BOARD_SIZE=10;
    
    public Button10x10(){
        this.setText("10x10");
        addActionListener(this);
    }

    public static int getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameView view=new GameView(BOARD_SIZE);
    }
    
    
}
