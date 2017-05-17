/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amoba;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author murph
 */
public class Button6x6 extends JButton implements ActionListener{
    private static final int BOARD_SIZE=6;
    
    public Button6x6(){
        this.setText("6x6");
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
