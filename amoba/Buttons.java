
package amoba;

import javax.swing.JButton;

public class Buttons extends JButton{
    
    
    Integer[] index;
    public Integer[] getIndex() {
        return index;
    }
    public Buttons(int i,int j){
        index=new Integer[]{i,j};
    }
}
