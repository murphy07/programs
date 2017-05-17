
package amoba;

import javax.swing.JPanel;


public class BoardSetting extends JPanel {
   
   public BoardSetting(){
       add(new TextField());
       add(new Button6x6());
       add(new Button10x10());
       add(new Button14x14());
   }
}
