
package mozi.start;

import mozi.frontend.MainFrame;
import mozi.frontend.panel.*;
/**
 * az osztály segítségével tudjuk elindítani az alkalmazásunkat
 */
public class Boot {
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(
                () -> new MainFrame().setVisible(true)
        );
    }
}
