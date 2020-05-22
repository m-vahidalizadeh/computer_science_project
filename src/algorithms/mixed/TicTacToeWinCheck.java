package algorithms.mixed;

/**
 * Created by Mohammad on 5/15/2016.
 */
public class TicTacToeWinCheck {
    static char[][] board = new char[][]{
            {'x','o','x'},
            {'o','x','x'},
            {'o','o','x'}
    };
    private static char winner= 'n';

    public static void main(String[] args) {
        if(winCheck()) System.out.println("Winner: " + winner);
        else System.out.println("No winner");
    }

    public static boolean winCheck(){
        for(int i= 0; i< 3; i++){
            if(rowCheck(i)){
                return true;
            }
        }
        for(int j= 0; j< 3; j++){
            if(columnCheck(j)){
                return true;
            }
        }
        if(diagonal1Check()) return true;
        if(diagonal2Check()) return true;
        return false;
    }

    public static boolean rowCheck(int i){
        for(int j= 0; j< 2; j++){
            if(board[i][j] != board[i][j+1]){
                return false;
            }
        }
        winner= board[i][0];
        return true;
    }

    public static boolean columnCheck(int j){
        for(int i= 0; i< 2; i++){
            if(board[i][j] != board[i+1][j]){
                return false;
            }
        }
        winner= board[0][j];
        return true;
    }

    public static boolean diagonal1Check(){

        for(int i= 0; i< 2; i++){
            if(board[i][i] != board[i+1][i+1]){
                return false;
            }
        }
        winner= board[0][0];
        return true;

    }

    public static boolean diagonal2Check(){

        for(int i= 0; i< 2; i++){
            if(board[2-i][i] != board[1-i][i+1]){
                return false;
            }
        }
        winner= board[0][2];
        return true;

    }



}


