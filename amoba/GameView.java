package amoba;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameView extends JFrame implements GameListener{
    private int boardsize;
    private Buttons[][] board;
    public JLabel currentPlayer = new JLabel("1.jatekos");
    GameLogic logic;
    private Action gameAction = new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
            Buttons b = (Buttons) e.getSource();
            setText(b);
            b.setEnabled(false);
            logic.doStep(b.getIndex());
    }
    };
    void buttonSetText(int turn){
        if(turn==1){
           currentPlayer.setText("2.jatekos");
        }
        else{
            currentPlayer.setText("1.jatekos");
        }
    }
    
    private void setText(Buttons b) {
        if(logic.turn==1){
            b.setText("X");
            logic.turn=2;
            buttonSetText(1);
        }
        else{
            b.setText("O");
            logic.turn=1;
            buttonSetText(2);
        }
        logic.roundNumber+=1;
    }
    
    public GameView(int boardsize){
        this.boardsize=boardsize;
        logic=new GameLogic(boardsize);
        logic.registerGameListener(this);
        board=new Buttons[boardsize][boardsize];
        startGame();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 400);
        setTitle("fghj");
        setVisible(true);
    }
    

    public int getBoardsize() {
        return boardsize;
    }

    public void setBoardsize(int boardsize) {
        this.boardsize = boardsize;
    }
    public void startGame(){
        createGameArea();
        createStatusArea();
        createMenu();
    }

    private void createGameArea() {
        JPanel panel = new JPanel(new GridLayout(boardsize, boardsize));
        panel.setPreferredSize(new Dimension(300, 300));
        createButtons(panel);
        add(panel, BorderLayout.SOUTH);
    }
    private void createButtons(JPanel panel) {
        for(int i = 0; i < boardsize; ++i) {
            for(int j = 0; j < boardsize; ++j) {
                Buttons button = new Buttons(i,j);
                button.setAction(gameAction);
                board[i][j] = button;
                panel.add(button);
            }
        }
    }
    

    private void createStatusArea() {
        JPanel panel=new JPanel();
        panel.add(new JLabel("Jatekos kovetkezik:"));
        panel.add(currentPlayer);
        add(panel, BorderLayout.NORTH);
    }

    private void createMenu() {
        JPanel panel = new JPanel();
        JMenuBar menu;
        JMenu men;
        menu=new JMenuBar();
        men=new JMenu("Beallitas");
        JMenuItem item1 = new JMenuItem("Palyameret allitas:10x10");
        item1.addActionListener((ActionEvent e) -> {
            logic.initComponents();
            this.dispose();
            GameView view=new GameView(10);
        });
         JMenuItem item2 = new JMenuItem("Palyameret allitas:6x6");
        item2.addActionListener((ActionEvent e) -> {
            logic.initComponents();
            this.dispose();
            GameView view=new GameView(6);
        });
         JMenuItem item3 = new JMenuItem("Palyameret allitas:14x14");
        item3.addActionListener((ActionEvent e) -> {
            logic.initComponents();
            this.dispose();
            GameView view=new GameView(14);
        });
        men.add(item1);
        men.add(item2);
        men.add(item3);
        menu.add(men);
        panel.add(menu);
        add(panel);
    }

    @Override
    public void fieldDelete(int row, int col) {
        board[row][col].setText("");
        board[row][col].setEnabled(true);
    }

    @Override
    public void gameFinished() {
        if(logic.turn==1){
            JOptionPane.showMessageDialog(null, "Player "+2+" win");
        }
        if(logic.turn==2){
            JOptionPane.showMessageDialog(null, "Player "+1+" win");
        }
        logic.initComponents();
        setDefault();
    }

    @Override
    public void gameDraw() {
        JOptionPane.showMessageDialog(null, "Match is a draw");
        logic.initComponents();
        setDefault();
    }

    public void setDefault(){
        for(int i=0;i<boardsize;i++){
            for(int j=0;j<boardsize;j++){
                board[i][j].setText("");
                board[i][j].setEnabled(true);
                currentPlayer.setText("1.jatekos");
            }
        }
    }
    

    
    
}
