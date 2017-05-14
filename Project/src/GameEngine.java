import java.util.ArrayList;
import java.util.Random;

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

    private static final int WALL = Map.WALL;
    private static final int FLOOR = Map.EMPTY;
    private static final int BOX = Map.BOX;
    private static final int GOAL = Map.GOAL;
    private static final int PLAYER = Map.MOVE_UP;

    /*private int[][] generateMap(int x, int y){
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
    }*/

    public static int[][] generateMap(int x, int y){
        int[][] map = new int[x][y];
        //int xChunks = x / 3;
        //int yChunks = y / 3;
        int boxes = genRandom(x/8, x/3); // upper bound is exclusive
        int goals = boxes;
        //int player = 1;
        //int currChunk = 0;
        //int lastChunk = xBlocks * yBlocks;
        System.out.printf("Boxes: %d, Goals: %d \n", boxes, goals);
        // Wall: 0
        // Floor: 1
        // Box : 2
        // Goal: 3
        // Player: 4
        int nextTile;

        // generate walls
        for (int i = 0; i < x; i++ ) {
            for (int j = 0; j < y; j++) {
                nextTile = genRandom(0,6);
                map[i][j] = (nextTile <= 4) ? FLOOR : WALL; // higher number = lower wall density
            }
        }

        // generate boxes
        for (int i = 1; i < x-1; i++) {
            for (int j = 1; j < y-1; j++) {
                nextTile = genRandom(0,4);
                if (nextTile <= 2 && map[i][j] == FLOOR) {
                    map[i][j] = FLOOR;
                } else if (nextTile == 3 && map[i][j] == FLOOR && boxes > 0) {
                    map[i][j] = BOX;
                    boxes--;
                }
            }
        }

        // generate goal spaces away from boxes...
        for (int i = x-2; i > 1; i--) {
            for (int j = y-2; j > 1; j--) {
                nextTile = genRandom(0,4);
                if (nextTile <= 2 && map[i][j] == FLOOR) {
                    map[i][j] = FLOOR;
                } else if (nextTile == 3 && map[i][j] == FLOOR && goals > 0) {
                    map[i][j] = GOAL;
                    goals--;
                }
            }

        }

        // ensure no walls around starting area, boxes and goals
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (map[i][j] == WALL) map[i][j] = FLOOR;
            }
        }

        for (int i = 1; i < x -1; i++) {
            for (int j = 1; j < x -1; j++) {
                if ((map[i][j] == BOX || map[i][j] == GOAL)) {
                    if (map[i-1][j-1] == WALL) map[i-1][j-1] = FLOOR;
                    if (map[i][j-1] == WALL) map[i][j-1] = FLOOR;
                    if (map[i+1][j-1] == WALL) map[i+1][j-1] = FLOOR;
                    if (map[i-1][j] == WALL) map[i-1][j] = FLOOR;
                    if (map[i+1][j-1] == WALL) map[i+1][j] = FLOOR;
                    if (map[i-1][j+1] == WALL) map[i-1][j+1] = FLOOR;
                    if (map[i][j+1] == WALL) map[i][j+1] = FLOOR;
                    if (map[i+1][j+1] == WALL) map[i+1][j+1] = FLOOR;
                }
            }
        }

        //ensure there is a path from top to bottom

        // generate player
        for (int j = 0; j < y; j++) {
            if (map[0][j] == FLOOR) {
                map[0][j] = PLAYER;
                break;
            }
        }

        // debug
        int finalbox = 0;
        int finalgoal = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (map[i][j] == BOX) finalbox++;
                if (map[i][j] == GOAL) finalgoal++;
                System.out.printf("%d", map[i][j]);
            }
            System.out.println();

        }
        System.out.printf("EBoxes: %d, EGoals: %d\n", finalbox, finalgoal);
        //

        return map;
    }

    private static int genRandom(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
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
    
    public GameState getState() {
    	return this.currGame;
    }
}
