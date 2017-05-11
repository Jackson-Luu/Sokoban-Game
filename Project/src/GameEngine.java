import java.util.ArrayList;
import java.util.Objects;

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
        if(move == Map.MOVE_UP){
            //moving off map
            if(playerLoc.get(1) == 0)
                return false;
            //checking tile above
            if(currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) - 1) == Map.EMPTY ||
                    currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) - 1) == Map.GOAL) {
                return true;
            // check collision with wall
            } else if (currGame.getCurrMap().checkTile(playerLoc.get(0), playerLoc.get(1) - 1) == Map.WALL) {
                return false;
            }else if(currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) - 1) == Map.MOVABLE_BOX ||
                    currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) - 1) == Map.FINISHED_GOAL) {
                //making sure its not being pushed off map
                return playerLoc.get(1) - 1 != 0 &&
                        (currGame.getCurrMap().checkTile(playerLoc.get(0), playerLoc.get(1) - 2) == Map.EMPTY
                        || currGame.getCurrMap().checkTile(playerLoc.get(0), playerLoc.get(1) - 2) == Map.GOAL);
            }
         //move right
        }else if(move == Map.MOVE_RIGHT){
            if(playerLoc.get(0) + 1 == currGame.getCurrMap().getMapSize().get(0))
                return false;
            if(currGame.getCurrMap().checkTile(playerLoc.get(0) + 1,playerLoc.get(1)) == Map.EMPTY ||
                    currGame.getCurrMap().checkTile(playerLoc.get(0) + 1 ,playerLoc.get(1)) == Map.GOAL) {
                return true;
            // check collision with wall
            } else if (currGame.getCurrMap().checkTile(playerLoc.get(0) + 1, playerLoc.get(1)) == Map.WALL) {
                return false;
            }else if(currGame.getCurrMap().checkTile(playerLoc.get(0) + 1,playerLoc.get(1)) == Map.MOVABLE_BOX ||
                    currGame.getCurrMap().checkTile(playerLoc.get(0) + 1,playerLoc.get(1)) == Map.FINISHED_GOAL) {
                //making sure its not being pushed off map
                return playerLoc.get(0) + 2 != currGame.getCurrMap().getMapSize().get(0) &&
                        (currGame.getCurrMap().checkTile(playerLoc.get(0) + 2, playerLoc.get(1)) == Map.EMPTY ||
                         currGame.getCurrMap().checkTile(playerLoc.get(0) + 2, playerLoc.get(1) - 2) == Map.GOAL);
            }
        } else if (move == Map.MOVE_DOWN) {
            if (Objects.equals(playerLoc.get(1), currGame.getCurrMap().getMapSize().get(1)))
                return false;
            if(currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) + 1) == Map.EMPTY ||
                    currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) + 1) == Map.GOAL){
                return true;
                // check collision with wall
            } else if (currGame.getCurrMap().checkTile(playerLoc.get(0), playerLoc.get(1) + 1) == Map.WALL) {
                return false;
            }else if(currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) + 1) == Map.MOVABLE_BOX ||
                    currGame.getCurrMap().checkTile(playerLoc.get(0),playerLoc.get(1) + 1) == Map.FINISHED_GOAL) {
                //making sure its not being pushed off map
                return playerLoc.get(1) + 1 != 0 &&
                        (currGame.getCurrMap().checkTile(playerLoc.get(0), playerLoc.get(1) + 2) == Map.EMPTY ||
                         currGame.getCurrMap().checkTile(playerLoc.get(0), playerLoc.get(1) + 2) == Map.GOAL);
            }
        } else if (move == Map.MOVE_LEFT) {
            if(playerLoc.get(0) + 1 == currGame.getCurrMap().getMapSize().get(0))
                return false;
            if(currGame.getCurrMap().checkTile(playerLoc.get(0) - 1,playerLoc.get(1)) == Map.EMPTY ||
                    currGame.getCurrMap().checkTile(playerLoc.get(0) - 1 ,playerLoc.get(1)) == Map.GOAL){
                return true;
                // check collision with wall
            } else if (currGame.getCurrMap().checkTile(playerLoc.get(0) - 1 , playerLoc.get(1)) == Map.WALL) {
                return false;
            }else if(currGame.getCurrMap().checkTile(playerLoc.get(0) - 1,playerLoc.get(1)) == Map.MOVABLE_BOX ||
                    currGame.getCurrMap().checkTile(playerLoc.get(0) - 1,playerLoc.get(1)) == Map.FINISHED_GOAL) {
                //making sure its not being pushed off map
                return playerLoc.get(0) + 2 != currGame.getCurrMap().getMapSize().get(0) &&
                        (currGame.getCurrMap().checkTile(playerLoc.get(0) - 2, playerLoc.get(1)) == Map.EMPTY ||
                         currGame.getCurrMap().checkTile(playerLoc.get(0) - 2, playerLoc.get(1) - 2) == Map.GOAL);
            }
        }
        return false;
    }

    public void makeMove(int move){

    }
}

