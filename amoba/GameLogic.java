package amoba;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic{
     int roundNumber=0;
    int boardSize;
     int turn=1;
    Integer[][] buttons;
    private List<GameListener> listeners = new ArrayList<>();
    public GameLogic(int n){
        this.boardSize=n;
        createArray(this.boardSize);
    }

        public void registerGameListener(GameListener listener) {
        listeners.add(listener);
    }
        public void doStep(Integer[] param){
        Integer[] index=param;
        buttons[index[0]][index[1]]=turn;
        boolean g=checkVictory(index);
        if(g){
            fireGameFinished();
            initComponents();
        }
        if(roundNumber==(boardSize*boardSize)){
            fireGameDraw();
            initComponents();
        }
    }
    public boolean checkVictory(Integer[] param) {
        int x=param[0];
        int y=param[1];
        int horizontalSum=0;
        int verticalSum=0;
        int antiDiagSum=0;
        int diagSum=0;
        //HORIZONTAL
        horizontalSum=chechHorizontal(x,y);
        if(horizontalSum>=4) return true;
        //VERTICAL
        verticalSum=checkVertical(x,y);
        if(verticalSum>=4) return true;
        //DIAG
        diagSum=checkDiag(x,y);
        if(diagSum>=4) return true;
        //ANTIDIAG
        antiDiagSum=checkAntiDiag(x,y);
        if(antiDiagSum>=4) return true;
        if(horizontalSum==2||verticalSum==2||diagSum==2||antiDiagSum==2){
            takeOneButton(turn);
        }
        if(horizontalSum==3||verticalSum==3||diagSum==3||antiDiagSum==3){
            takeOneButton(turn);
            takeOneButton(turn);
        }
        return false;
        
    }
    
    
    private int checkDiag(int x, int y){
        int result=0;
        int c1=0;
        int c2=0;
        int i;
        int j;
        boolean b=true;
        boolean bb=true;
        for(i=x+1;i<boardSize&&b;i++){
            for(j=y+1;j<boardSize;j++){
                if(buttons[i][j]!=buttons[x][y]){
                    b=false;
                    break;
                }if(i!=boardSize-1){
                i++;
                }
                c1+=1;
            }
        }
        for(i=x-1;i>=0&&bb;i--){
            for(j=y-1;j>=0;j--){
                if(buttons[i][j]!=buttons[x][y]){
                    bb=false;
                    break;
                }if(i!=0){
                i--;
                }
                c2+=1;
            }
        }
        result=c1+c2;
        return result;
    }

    private int checkAntiDiag(int x, int y){
        int result=0;
        int i;
        int j;
        int c1=0;
        int c2=0;
        boolean b=true;
        boolean bb=true;
        for(i=x+1;i<boardSize&&b;i++){
            for(j=y-1;j>=0;j--){
                if(buttons[i][j]!=buttons[x][y]){
                    b=false;
                    break;
                }
                if(i!=boardSize-1){
                i++;
                }
                c1+=1;
            }
        }
        for(i=x-1;i>=0&&bb;i--){
            for(j=y+1;j<boardSize;j++){
                if(buttons[i][j]!=buttons[x][y]){
                    bb=false;
                    break;
                }if(i!=0){
                i--;
                }
                c2+=1;
            }
        }
        result=c1+c2;
        return result;
    }

    private void createArray(int boardSize) {
        buttons=new Integer[boardSize][boardSize];
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                buttons[i][j]=0;
            }
        }
    }
    private void takeOneButton(int t){
    Random rnd=new Random();
    int n=rnd.nextInt(boardSize-1);
    int m=rnd.nextInt(boardSize-1);
    if(buttons[n][m]==t){
        deleteButtons(n,m);
    }
    else{
        takeOneButton(t);
    }
}

    private void deleteButtons(int n, int m) {
        buttons[n][m]=0;
        fireFieldDelete(n,m);
    }

    public void initComponents() {
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                buttons[i][j]=0;
                roundNumber=0;
                turn=1;
            }
        }
    }

    private void fireFieldDelete(int n, int m) {
        listeners.forEach(gl -> gl.fieldDelete(n, m));
    }
    private void fireGameFinished() {
        listeners.forEach(gl -> gl.gameFinished());
    }

    private int checkVertical(int x, int y) {
        int result=0;
        int c1=0;
        int c2=0;
        int i;
        int j;
        for(i=x+1;i<boardSize;i++){
            if(buttons[i][y]!=buttons[x][y]){
                break;
            }
            c1+=1;
        }
        for(j=x-1;j>=0;j--){
            if(buttons[j][y]!=buttons[x][y]){
                break;
            }
            c2+=1;
        }
        result=c1+c2;
        return result;
    }

    private int chechHorizontal(int x, int y) {
        int result=0;
        int i;
        int j;
        int c1=0;
        int c2=0;
        for(i=y+1;i<boardSize;i++){
            if(buttons[x][i]!=buttons[x][y]){
                break;
            }
            c1+=1;
        }
        for(j=y-1;j>=0;j--){
                if(buttons[x][j]!=buttons[x][y]){
                break;
            }
            c2+=1;
            }
        result=c1+c2;
        return result;
    }

    private void fireGameDraw() {
        listeners.forEach(gl -> gl.gameDraw());
    }
    
}

