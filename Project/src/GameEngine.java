import java.util.ArrayList;
/**
 * 
 * @author Jackson Luu, Keller Huang, Dan Yoo, Evan Han and Lilac Liu
 *
 */

public class GameEngine {
    GameState currGame;
    //move currently takes in a number between 1-4 corresponding to up, right, down, and left respectively
    public GameEngine(int gameSizex, int gameSizey){
        currGame = new GameState(new Map(generateMap(gameSizex,gameSizey),gameSizex,gameSizey) , 0 , 0);

    }

    private int[][] generateMap(int x, int y){
        int[][] map = new int[x][y];
        //generate a map here
        return map;
    }

    private Boolean checkValid(int move){
        //if tile is empty
        //checks if tile is empty or occupied
        //if tile is occupied by a box, checks the tile after
        ArrayList<Integer> playerLoc = currGame.getCurrMap().checkPlayerLocation();
        //move up
        if(move == 1){
            //moving off map
            if(playerLoc.get(1) == 0) return false;
            //checking tile above
            if(currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) - 1) == 0 || currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) - 1) == 7){
                return true;
            }else if(currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) - 1) == 5 || currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) - 1) == 8){
                //making sure its not being pushed off map
                if(playerLoc.get(1) - 1 == 0)return false;
                if(currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) - 2) == 0 || currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) - 2) == 7){
                    return true;
                }else{
                    return false;
                }
            }
         //move right
        }else if(move == 2){
            if(playerLoc.get(0) + 1 == currGame.getCurrMap().getMapSize().get(0)) return false;
            if(currGame.getCurrMap().checkTile(playerLoc.get(0) + 1,playerLoc.get(1)) == 0 || currGame.getCurrMap().checkTile(playerLoc.get(0) + 1 ,playerLoc.get(1)) == 7){
                return true;
            }else if(currGame.getCurrMap().checkTile(playerLoc.get(0) + 1,playerLoc.get(1)) == 5 || currGame.getCurrMap().checkTile(playerLoc.get(0) + 1,playerLoc.get(1)) == 8){
                //making sure its not being pushed off map
                if(playerLoc.get(0) + 2 == currGame.getCurrMap().getMapSize().get(0))return false;
                if(currGame.getCurrMap().checkTile(playerLoc.get(0) + 2,playerLoc.get(1)) == 0 || currGame.getCurrMap().checkTile(playerLoc.get(0) + 2,playerLoc.get(1) - 2) == 7){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public void makeMove(int move){

    }
}

