import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jackson Luu, Keller Huang, Dan Yoo, Evan Han and Lilac Liu
 *
 */
//random comment for git remove later
public class GameEngine {
    GameState currGame;
    int gameSizex;
    int gameSizey;
    ArrayList<GameState> currGameStates;
    //move currently takes in a number between 1-4 corresponding to up, right, down, and left respectively
    public GameEngine(int gameSizex, int gameSizey){
        currGameStates = new ArrayList<>();
        currGameStates.add(new GameState(new Map(generateMap(gameSizex,gameSizey),gameSizex,gameSizey) , 0 , 0));
        currGame = currGameStates.get(0);
        this.gameSizex = gameSizex;
        this.gameSizey = gameSizey;
        Boolean quit = false;
    }

    private static final int WALL = Map.WALL;
    private static final int FLOOR = Map.EMPTY;
    private static final int BOX = Map.BOX;
    private static final int GOAL = Map.GOAL;
    private static final int PLAYER = Map.MOVE_DOWN;

    private static final int WALL_DENSITY = 2;  // changes the amount of walls generated
    // lower number = less walls. Range 0..10

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

    public static int[][] generateMap(int x, int y) {
        int[][] map = new int[x][y];
        int boxes = genRandom(x/8, x/3); // upper bound is exclusive
        final int b = boxes;
        int goals = boxes;
        System.out.printf("Boxes: %d, Goals: %d, \n", boxes, goals);
        int nextTile;

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                map[i][j] = FLOOR;
            }
        }

        // generate boxes
        int[][] boxLocation = new int[b][2];
        int currBox = 0;
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

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (map[i][j] == BOX) {
                    boxLocation[currBox][1] = i;
                    boxLocation[currBox][0] = j;
                    currBox++;
                }
            }
        }

        //'push'  boxes towards bottom of map to generate goals
        int[][] individualPaths = new int[b][x*8];
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < x*8; j++) {
                individualPaths[i][j] = 0;
            }
        }

        //keep track of box paths, create goals corresponding to boxes
        ArrayList<Integer> boxPath = new ArrayList<Integer>();
        for (int curr = 0; curr < b; curr++) {
            int[] box = {boxLocation[curr][0], boxLocation[curr][1]};
            //...
            int amt = genRandom((int) (x*1.5), x*2);
            ArrayList<Integer> path = push(map, box, amt);
            //int currPath = 0;
            System.out.printf("size=%s \n", path.size());
            for (int i = 0; i < path.size(); i++) {

                individualPaths[curr][i] = path.get(i);
                boxPath.add(path.get(i));
            }
            System.out.printf("currX: %d, currY: %d\n", box[0], box[1]);
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (i == box[0] && j == box[1] && map[j][i] == FLOOR) {
                        map[j][i] = GOAL;
                        goals--;
                    } else if (i == box[0] && j == box[1] && (map[j][i] == GOAL || map[j][i] == BOX)) {
                        push(map, box, 7);
                        map[box[0]][box[1]] = GOAL;
                        goals--;
                    }
                }
            }
        }
        // generate walls
        for (int i = 0; i < x; i++ ) {
            for (int j = 0; j < y; j++) {
                nextTile = genRandom(0,11);

                if (nextTile <= WALL_DENSITY && map[i][j] == FLOOR) {
                    for(int q = 0; q < boxPath.size(); q += 2) {
                        if (i != boxPath.get(q) && j != boxPath.get(q+1)) {
                            map[i][j] = WALL;
                        }
                    }

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

        //reduce the amount of walls in rows with potentially too many
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                int walls = 0;
                if (map[i][j] == WALL) {
                    walls++;
                }
                if (walls > x/2) { // over half the row is walls
                    for (i = 0; i < x; i++) {
                        if (map[i][j] == WALL) {
                            nextTile = genRandom(0,10);
                            if (nextTile > 0) {
                                map[i][j] = FLOOR; //80% chance to change a wall to floor
                            }
                        }
                    }
                }
            }
        }

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
        int nWalls = 0;
        int nFloors = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (map[i][j] == BOX) finalbox++;
                if (map[i][j] == GOAL) finalgoal++;
                if (map[i][j] == WALL) nWalls++;
                if (map[i][j] == FLOOR) nFloors++;
                System.out.printf("%d ", map[i][j]);
            }
            System.out.println();

        }
        System.out.printf("EBoxes: %d, EGoals: %d\nWalls: %d Floors: %d\n", finalbox, finalgoal, nWalls, nFloors);

        for (int i = 0; i < boxLocation.length; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.println(boxLocation[i][j]);
            }
        }
        //solution
        for (int i = 0; i < b; i++) {
            System.out.printf("Box: %d \n", i);
            for (int j = 0; j < x*8; j += 2) {
                if (individualPaths[i][j] != 0 && individualPaths[i][j+1] != 0)
                    System.out.printf("Move: %d, x: %d, y: %d\n", j/2, individualPaths[i][j], individualPaths[i][j+1]);
            }
            System.out.println("\n");
        }
        //

        return map;
    }

    private static int genRandom(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private static ArrayList<Integer> push(int[][] map, int[] box, int amt) {
        System.out.printf("amt: %d\n", amt);
        ArrayList<Integer> path = new ArrayList<Integer>();
        for (int p = 0; p < amt; p++) {
            int dir = genRandom(0, 8);
            if (dir == 0) {
                if (box[0] > 0 && box[0] < map.length - 1) {
                    if (map[box[0] - 1][box[1]] == FLOOR)
                        //push up
                        box[0]--;
                    else p--;
                }
            } else if (dir >= 1 && dir <= 2) {
                if (box[0] > 0 && box[0] < map.length - 1) {
                    if (map[box[0] + 1][box[1]] == FLOOR)
                        //push down
                        box[0]++;
                    else p--;
                }
            }
            if (dir == 3) {
                if (box[1] > 0 && box[1] < map.length - 1) {
                    if (map[box[0]][box[1] - 1] == FLOOR)
                        //push left
                        box[1]--;
                    else p--;
                }
            } else if (dir >= 4 && dir <= 7) {
                if (box[1] > 0 && box[1] < map.length - 1) {
                    if (map[box[0]][box[1] + 1] == FLOOR)
                        //push right
                        box[1]++;
                    else p--;
                }
            }

            for (int i = 0; i < box.length; i++) {
                //System.out.printf("%d ", box[i]);
                path.add(box[i]);
            }
            //System.out.printf("\n");
        }
        return path;
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
        Map functionMap = new Map(currGame.getCurrMap().getLocations(),gameSizex,gameSizey);
        ArrayList<Integer> playerLoc = functionMap.checkPlayerLocation();
        int yLoc = playerLoc.get(0);
        int xLoc = playerLoc.get(1);
        if(checkValid(move)){
            System.out.println("VALID");
            //removing player from current tile
            if(functionMap.checkTile(xLoc,yLoc) >= Map.PLAYER_ON_GOAL_UP && functionMap.checkTile(xLoc,yLoc) <= Map.PLAYER_ON_GOAL_LEFT){
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
                    functionMap.changeTile(xLoc,yLoc,move);
                if(functionMap.checkTile(xLoc,yLoc) == Map.GOAL)
                    functionMap.changeTile(xLoc,yLoc,move+8);
            }else if(functionMap.checkTile(xLoc,yLoc) == Map.BOX || functionMap.checkTile(xLoc,yLoc) == Map.GOAL_BOX){
                //if there's a box
                if(functionMap.checkTile(xLoc,yLoc) == Map.BOX){
                    functionMap.changeTile(xLoc,yLoc,move);
                }else if(functionMap.checkTile(xLoc,yLoc) == Map.GOAL_BOX){
                    functionMap.changeTile(xLoc,yLoc,move+8);
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
        }else{
            functionMap.changeTile(xLoc,yLoc,move);
        }
        GameState updated = new GameState(functionMap,0,0);
        currGame = updated;
        currGameStates.add(updated);
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

    public void resetState() {
        currGame = new GameState(currGameStates.get(0).getCurrMap(), 0, 0);
        currGameStates.clear();
        currGameStates.add(currGame);
    }

    public void prevState() {
        if (currGameStates.size() == 0 || currGameStates.size() == 1) {
            return;
        }
        currGame = currGameStates.get(currGameStates.size() - 2);
        currGameStates.remove(currGameStates.size() - 1);
    }

    public void newGame(){
        currGameStates = new ArrayList<>();
        currGameStates.add(new GameState(new Map(generateMap(gameSizex,gameSizey),gameSizex,gameSizey) , 0 , 0));
        currGame = currGameStates.get(0);
    }

    public Boolean isFinished(){
        int[][] checker;
        checker = currGame.getCurrMap().getLocations();
        for(int i = 0; i < gameSizex; i++){
            for(int m = 0; m < gameSizey; m++){
                if(checker[m][i] == Map.BOX){
                    return false;
                }
            }
        }
        return true;
    }
}
