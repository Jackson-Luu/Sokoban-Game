import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

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

    private static final int WALL = 0;
    private static final int FLOOR = 1;
    private static final int BOX = 2;
    private static final int GOAL = 3;
    private static final int PLAYER = 4;

    private static int[][] generateMap(int x, int y){
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
                map[i][j] = (nextTile <= 3) ? FLOOR : WALL; // higher number = lower wall density
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

