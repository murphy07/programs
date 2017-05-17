
package amoba;


interface GameListener {
    void fieldDelete(int row, int col);
    void gameFinished();
    void gameDraw();
}
