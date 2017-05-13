import java.util.ArrayList;

/**
 *
 * @author Jackson Luu, Keller Huang, Dan Yoo, Evan Han and Lilac Liu
 *
 */

public class GameEngine {
    GameState currGame;
    int gameSizex;
    int gameSizey;
    //move currently takes in a number between 1-4 corresponding to up, right, down, and left respectively
    public GameEngine(int gameSizex, int gameSizey){
        currGame = new GameState(new Map(generateMap(gameSizex,gameSizey),gameSizex,gameSizey) , 0 , 0);
        this.gameSizex = gameSizex;
        this.gameSizey = gameSizey;
        Boolean quit = false;
    }

    private int[][] generateMap(int x, int y){
        int[][] map = new int[x][y];
        int[][] samplemap = {


                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int[][] samplemap2 = {


                {0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 8, 0, 0},

        };

        //generate a map here
        return samplemap2;
    }

    private Boolean checkValid(int move){
        //if tile is empty
        //checks if tile is empty or occupied
        //if tile is occupied by a box, checks the tile after
        Map functionMap = currGame.getCurrMap();
        ArrayList<Integer> playerLoc = functionMap.checkPlayerLocation();
        int yLoc = playerLoc.get(0);
        int xLoc = playerLoc.get(1);
        System.out.println("Player at: " + yLoc + " " + xLoc);
        //move up
        if(move == Map.MOVE_UP){
            //moving off map
            if(yLoc == 0)
                return false;
            //checking tile above
            if(functionMap.checkTile(xLoc,yLoc - 1) == Map.EMPTY ||
                    functionMap.checkTile(xLoc,yLoc - 1) == Map.GOAL) {
                return true;
                // check collision with wall
            } else if (functionMap.checkTile(xLoc, yLoc - 1) == Map.WALL) {
                return false;
            }else if(functionMap.checkTile(xLoc,yLoc - 1) == Map.BOX ||
                    functionMap.checkTile(xLoc,yLoc - 1) == Map.GOAL_BOX) {
                //making sure its not being pushed off map
                return yLoc - 1 != 0 &&
                        (functionMap.checkTile(xLoc, yLoc - 2) == Map.EMPTY
                                || functionMap.checkTile(xLoc, yLoc - 2) == Map.GOAL);
            }
            //move right
        }else if(move == Map.MOVE_RIGHT){
            if(xLoc + 1 == functionMap.getMapSize().get(0))
                return false;
            if(functionMap.checkTile(xLoc + 1,yLoc) == Map.EMPTY ||
                    functionMap.checkTile(xLoc + 1 ,yLoc) == Map.GOAL) {
                return true;
                // check collision with wall
            } else if (functionMap.checkTile(xLoc + 1, yLoc) == Map.WALL) {
                return false;
            }else if(functionMap.checkTile(xLoc + 1,yLoc) == Map.BOX ||
                    functionMap.checkTile(xLoc + 1,yLoc) == Map.GOAL_BOX) {
                //making sure its not being pushed off map
                return xLoc + 2 != functionMap.getMapSize().get(0) &&
                        (functionMap.checkTile(xLoc + 2, yLoc) == Map.EMPTY ||
                                functionMap.checkTile(xLoc + 2, yLoc) == Map.GOAL);
            }
        } else if (move == Map.MOVE_DOWN) {
            if (yLoc + 1 == functionMap.getMapSize().get(1))
                return false;
            if(functionMap.checkTile(xLoc,yLoc + 1) == Map.EMPTY ||
                    functionMap.checkTile(xLoc,yLoc + 1) == Map.GOAL){
                return true;
                // check collision with wall
            } else if (functionMap.checkTile(xLoc, yLoc + 1) == Map.WALL) {
                return false;
            }else if(functionMap.checkTile(xLoc,yLoc + 1) == Map.BOX ||
                    functionMap.checkTile(xLoc,yLoc + 1) == Map.GOAL_BOX) {
                //making sure its not being pushed off map
                return yLoc + 2 != functionMap.getMapSize().get(1) &&
                        (functionMap.checkTile(xLoc, yLoc + 2) == Map.EMPTY ||
                                functionMap.checkTile(xLoc, yLoc + 2) == Map.GOAL);
            }
        } else if (move == Map.MOVE_LEFT) {
            if(xLoc == 0)
                return false;
            if(functionMap.checkTile(xLoc - 1,yLoc) == Map.EMPTY ||
                    functionMap.checkTile(xLoc - 1 ,yLoc) == Map.GOAL){
                return true;
                // check collision
            } else if (functionMap.checkTile(xLoc - 1 , yLoc) == Map.WALL) {
                return false;
            }else if(functionMap.checkTile(xLoc - 1,yLoc) == Map.BOX ||
                    functionMap.checkTile(xLoc - 1,yLoc) == Map.GOAL_BOX) {
                //making sure its not being pushed off map
                return xLoc - 1 != 0 &&
                        (functionMap.checkTile(xLoc - 2, yLoc) == Map.EMPTY ||
                                functionMap.checkTile(xLoc - 2, yLoc) == Map.GOAL);
            }
        }
        return false;
    }

    public void makeMove(int move){
        Map functionMap = currGame.getCurrMap();
        functionMap = new Map(currGame.getCurrMap().getLocations(),gameSizex,gameSizey);
        ArrayList<Integer> playerLoc = functionMap.checkPlayerLocation();
        int yLoc = playerLoc.get(0);
        int xLoc = playerLoc.get(1);
        if(checkValid(move)){
            System.out.println("VALID");
            //removing player from current tile
            if(functionMap.checkTile(xLoc,yLoc) == Map.PLAYER_ON_GOAL){
                functionMap.changeTile(xLoc,yLoc, Map.GOAL);
            }else if(functionMap.checkTile(xLoc,yLoc) >= 1 && functionMap.checkTile(xLoc,yLoc) <= 4){
                System.out.println("EMPTY");
                functionMap.changeTile(xLoc,yLoc, Map.EMPTY);
            }
            //check tile above
            ArrayList<Integer> loc = augment(xLoc,yLoc,move);
            xLoc = loc.get(0);
            yLoc = loc.get(1);
            System.out.println(xLoc + " " +yLoc);
            if(functionMap.checkTile(xLoc,yLoc) == Map.EMPTY || functionMap.checkTile(xLoc,yLoc) == Map.GOAL){
                if(functionMap.checkTile(xLoc,yLoc) == Map.EMPTY)
                    functionMap.changeTile(xLoc,yLoc,Map.MOVE_UP);
                if(functionMap.checkTile(xLoc,yLoc) == Map.GOAL)
                    functionMap.changeTile(xLoc,yLoc,Map.PLAYER_ON_GOAL);
            }else if(functionMap.checkTile(xLoc,yLoc) == Map.BOX || functionMap.checkTile(xLoc,yLoc) == Map.GOAL_BOX){
                //if there's a box
                if(functionMap.checkTile(xLoc,yLoc) == Map.BOX){
                    functionMap.changeTile(xLoc,yLoc,Map.MOVE_UP);
                }else if(functionMap.checkTile(xLoc,yLoc) == Map.GOAL_BOX){
                    functionMap.changeTile(xLoc,yLoc,Map.PLAYER_ON_GOAL);
                }
                loc = augment(xLoc,yLoc,move);
                xLoc = loc.get(0);
                yLoc = loc.get(1);
                if(functionMap.checkTile(xLoc,yLoc) == Map.EMPTY){
                    functionMap.changeTile(xLoc,yLoc,Map.BOX);
                }else if(functionMap.checkTile(xLoc,yLoc) == Map.GOAL){
                    functionMap.changeTile(xLoc,yLoc,Map.GOAL_BOX);
                }
            }
        }
        GameState updated = new GameState(functionMap,0,0);
        currGame = updated;
    }

    private ArrayList<Integer> augment(int xLoc, int yLoc, int move){
        if(move == 1){
            yLoc--;
        }else if(move == 2){
            xLoc++;
        }else if(move == 3){
            yLoc++;
        }else if(move == 4){
            xLoc--;
        }
        System.out.println(xLoc + " " +yLoc);
        ArrayList<Integer> ret = new ArrayList<Integer>();
        ret.add(xLoc);
        ret.add(yLoc);
        return ret;
    }
}
