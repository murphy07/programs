package amoba;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Button14x14 extends JButton implements ActionListener{
    private static final int BOARD_SIZE=14;
    
    public Button14x14(){
        this.setText("14x14");
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
