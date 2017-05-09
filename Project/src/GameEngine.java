/**
 * 
 * @author Jackson Luu, Keller Huang, Dan Yoo, Evan Han and Lilac Liu
 *
 */

public class GameEngine {
    GameState currGame;
    //move currently takes in a number between 1-4 corresponding to up, right, down, and left respectively
    public GameEngine(int gameSizex, int gameSizey){
        currGame = new GameState(new Map(generateMap(gameSizex,gameSizey)) , 0 , 0);

    }

    private int[][] generateMap(int x, int y){
        int[][] map = new int[x][y];
        //generate a map here
        return map;
    }

    private Boolean checkValid(int move){
        //if tile is empty
        ArrayList<Integer> playerLoc;
        if(move == 1){
            if(currGame.getCurrMap().checkTile())
        }
        return true;
    }

    public void makeMove(int move){

    }
}
