package amoba;

import java.awt.Dimension;
import javax.swing.JTextArea;

public class TextField extends JTextArea{
    public TextField(){
        setText("Valassz meretet a palyahoz");
        setPreferredSize(new Dimension(250,100));
        setEditable(false);
    }

    
}
