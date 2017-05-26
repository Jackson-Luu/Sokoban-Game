import java.util.*;

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

    /**
     * Generate a new map
     * @param x - x dimension
     * @param y - y dimension
     * @return generated map
     */
    public int[][] generateMap(int x, int y) {
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
        // boxes are generated towards top of map
        int[][] boxLocation = new int[b][2];
        int currBox = 0;
        for (int i = 1; i < x-1; i++) {
            for (int j = 1; j < y-1; j++) {
                nextTile = genRandom(0,3);
                if (nextTile == 3 && map[i][j] == FLOOR && boxes > 0) {
                    map[i][j] = BOX;
                    boxes--;
                }
            }
        }
        //keep track of box locations
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (map[i][j] == BOX) {
                    boxLocation[currBox][1] = j;
                    boxLocation[currBox][0] = i;
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
            int amt = genRandom((int) (x*1.5), x*2);
            ArrayList<Integer> path = push(map, box, amt);
            System.out.printf("size=%s \n", path.size());
            for (int i = 0; i < path.size(); i++) {
                individualPaths[curr][i] = path.get(i);
                boxPath.add(path.get(i));
            }
            System.out.printf("currX: %d, currY: %d\n", box[0], box[1]);
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (i == box[0] && j == box[1] && map[i][j] == FLOOR) {
                        map[i][j] = GOAL;
                        goals--;
                    } else if (i == box[0] && j == box[1] && (map[i][j] == GOAL || map[i][j] == BOX)) {
                        push(map, box, 7);
                        map[box[0]][box[1]] = GOAL;
                        goals--;
                    }
                }
            }
        }
        // generate walls
        System.out.println("BOXPATH" + boxPath.size());
        Boolean generate = false;
        for (int i = 0; i < x; i++ ) {
            generate = false;
            for (int j = 0; j < y; j++) {
                nextTile = genRandom(0,11);

                if (nextTile <= WALL_DENSITY && map[i][j] == FLOOR) {
                    generate = true;
                    for(int q = 0; q < boxPath.size(); q += 2) {
                        if (i == boxPath.get(q) && j == boxPath.get(q+1)) {
                            generate = false;
                        }
                    }
                    if(generate == true)map[i][j] = WALL;

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
        for (int j = 0; j < x; j++) {
            for (int i = 0; i < y; i++) {
                int walls = 0;
                if (map[i][j] == WALL) {
                    walls++;
                }
                if (walls > x/2) { // over half the row is walls
                    for (i = 0; i < x; i++) {
                        if (map[i][j] == WALL) {
                            nextTile = genRandom(0,10);
                            if (nextTile > 0) {
                                map[i][j] = FLOOR;
                            }
                        }
                    }
                }
            }
        }
        int wallCount = 0;
        //counts walls around a tile
        for(int j = 0; j < 3;j++) {
            for (int i = 0; i < x; i++) {
                for (int m = 0; m < y; m++) {
                    wallCount = 0;
                    if (i == 0 || i == x - 1) {
                        wallCount++;
                    }
                    if (m == 0 || m == y - 1) {
                        wallCount++;
                    }
                    if (i != 0 && map[i - 1][m] == WALL) wallCount++;
                    if (i != x - 1 && map[i + 1][m] == WALL) wallCount++;
                    if (m != 0 && map[i][m - 1] == WALL) wallCount++;
                    if (m != y - 1 && map[i][m + 1] == WALL) wallCount++;
                    if (wallCount >= 3 && map[i][m] == FLOOR) map[i][m] = WALL;
                }
            }
        }
        //fill in inaccessible floor tiles
        map = removeBubbles(map);

        // generate player
        Boolean spawned = false;
        for (int j = 0; j < y; j++) {
            for(int m = 0; m < x; m++){
                if (map[j][m] == FLOOR) {
                    map[j][m] = PLAYER;
                    spawned = true;
                    break;
                }
            }
            if(spawned)break;
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
        //
        //solution
        for (int i = 0; i < b; i++) {
            System.out.printf("Box: %d \n", i);
            for (int j = 0; j < x*8; j += 2) {
                if (individualPaths[i][j] != 0 && individualPaths[i][j+1] != 0)
                    System.out.printf("Move: %d, y: %d, x: %d\n", j/2, individualPaths[i][j], individualPaths[i][j+1]);
            }
            System.out.println("\n");
        }

        for(int i = 0; i < boxPath.size(); i+=2){
            System.out.println("AVOID: " + boxPath.get(i) + "," + boxPath.get(i+1));
        }
        //

        return map;
    }

    /*private int[][] removeBubbles(int[][] map){
        ArrayList<Integer> node;
        Queue<ArrayList<Integer>> q = new LinkedList<ArrayList<Integer>>();
        Boolean[][] seen = new Boolean[gameSizey][gameSizex];
        for(int i = 0; i < gameSizex; i++){
            for(int m = 0; m < gameSizey; m++){
                seen[m][i] = false;
            }
        }
        int currx;
        int curry;
        for(int m = 0; m < gameSizex; m++){
            for(int i = 0; i < gameSizey; i++){
                if(seen[i][m] == false){
                    node = new ArrayList<Integer>();
                    node.add(i);
                    node.add(m);
                    q.add(node);
                    ArrayList<Integer> newNode;
                    while(!q.isEmpty()){
                        node = q.remove();
                        currx = node.get(1);
                        curry = node.get(0);
                        if (curry != 0 && map[curry - 1][currx] == FLOOR && seen[curry-1][currx] == false){
                            newNode = new ArrayList<Integer>();
                            newNode.add(curry-1);
                            newNode.add(currx);
                            q.add(newNode);
                            seen[curry-1][currx] = true;
                        }else if(map[curry-1][currx] == BOX || map[curry-1][currx] == GOAL){
                            break;
                        }
                        if (curry != gameSizex - 1 && map[curry + 1][currx] == FLOOR && seen[curry+1][currx] == false){
                            newNode = new ArrayList<Integer>();
                            newNode.add(curry+1);
                            newNode.add(currx);
                            q.add(newNode);
                            seen[curry+1][currx] = true;
                        }else if(map[curry+1][currx] == BOX || map[curry+1][currx] == GOAL){
                            break;
                        }
                        if (currx != 0 && map[curry][currx - 1] == FLOOR && seen[curry][currx-1] == false) {
                            newNode = new ArrayList<Integer>();
                            newNode.add(curry);
                            newNode.add(currx-1);
                            q.add(newNode);
                            seen[curry][currx-1] = true;
                        }else if(map[curry][currx-1] == BOX || map[curry][currx-1] == GOAL){
                            break;
                        }
                        if (currx != gameSizey - 1 && map[curry][currx + 1] == WALL && seen[curry][currx+1] == false){
                            newNode = new ArrayList<Integer>();
                            newNode.add(curry);
                            newNode.add(currx+1);
                            q.add(newNode);
                            seen[curry][currx+1] = true;
                        }else if(map[curry][currx+1] == BOX || map[curry][currx+1] == GOAL){
                            break;
                        }
                    }
                    if(q.isEmpty())
                }
            }
        }
    }*/

    /**
     * Fill in inaccessible floor tiles with walls
     * @param map - current map
     * @return - map with inaccessible floor tiles filled in
     */
    public int[][] removeBubbles(int[][] map){
        /*
            Let 1 = next to wall
            Let 2 = next to box or goal
         */
        final int NEXTWALL = 1;
        final int NEXTBOXGOAL = 2;
        Boolean changed = true;
        while(changed) {
            changed = false;
            int i;
            int m = 0;
            int e;
            for (i = 0; i < gameSizey; i++) {
                for (m = 0; m < gameSizex; m++) {
                    if(map[i][m] == FLOOR || map[i][m] == NEXTWALL) {
                        System.out.println("x");
                        //check up
                        if (i != 0) {
                            e = checker(map[i-1][m]);
                            if(e != FLOOR) {
                                if(e > map[i][m]) {
                                    map[i][m] = e;
                                    System.out.println("CHANGING [" + i + "][" + m + "] to " + e);
                                    changed = true;
                                }
                            }
                        }
                        if(map[i][m] != NEXTBOXGOAL && i != gameSizey-1){
                            e = checker(map[i+1][m]);
                            if(e != FLOOR) {
                                if(e > map[i][m]) {
                                    map[i][m] = e;
                                    System.out.println("CHANGING [" + i + "][" + m + "] to " + e);
                                    changed = true;
                                }
                            }
                        }
                        if(map[i][m] != NEXTBOXGOAL && m != 0){
                            e = checker(map[i][m-1]);
                            if(e != FLOOR) {
                                if(e > map[i][m]) {
                                    map[i][m] = e;
                                    System.out.println("CHANGING [" + i + "][" + m + "] to " + e);
                                    changed = true;
                                }
                            }
                        }
                        if(map[i][m] != NEXTBOXGOAL && m != gameSizex-1){
                            e = checker(map[i][m+1]);
                            if(e != FLOOR) {
                                if(e > map[i][m]) {
                                    map[i][m] = e;
                                    System.out.println("CHANGING [" + i + "][" + m + "] to " + e);
                                    changed = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int i = 0; i < gameSizey; i++){
            for(int m = 0; m < gameSizex; m++){
                if(map[i][m] == NEXTWALL) map[i][m] = WALL;
                if(map[i][m] == NEXTBOXGOAL) map[i][m] = FLOOR;
            }
        }
        return map;
    }

    private static int checker(int check){
        final int NEXTWALL = 1;
        final int NEXTBOXGOAL = 2;
        if(check == WALL || check == NEXTWALL){
            return NEXTWALL;
        }else if(check == GOAL || check == BOX || check == NEXTBOXGOAL){
            return NEXTBOXGOAL;
        }
        return FLOOR;
    }

    /**
     * Generate a random number
     * @param min - lower bound
     * @param max - upper bound
     * @return - number
     */
    private static int genRandom(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    private static Boolean isReachable(int[][] map,int tilex,int tiley){
        int startLocx;
        int startLocy;
        for(int i = 0; i < 15; i++){
            for(int m = 0; m < 15; m++){
                if(map[i][m] == PLAYER){
                    startLocx = m;
                    startLocy = i;
                }
            }
        }
        int[][] tempMap = new int[15][15];
        for(int i = 0; i < 15; i++){
            for(int m = 0; m < 15; m++){
                tempMap[i][m] = map[i][m];
            }
        }

        Boolean reachable = false;
        Boolean noFloor = false;
        final int REACH = 24;
        while(!noFloor){
            noFloor = true;
            for(int i = 0; i < 15; i++){
                for(int m = 0; m < 15; m++){
                    if(tempMap[i][m] == FLOOR) {
                        //check up
                        noFloor = false;
                        if (i != 0) {
                            if(tempMap[i-1][m] == REACH || tempMap[i-1][m] == PLAYER){
                                tempMap[i][m] = REACH;
                            }
                        }
                        if(tempMap[i][m] == FLOOR && i != 14){
                            if(tempMap[i+1][m] == REACH || tempMap[i+1][m] == PLAYER){
                                tempMap[i][m] = REACH;
                            }
                        }
                        if(tempMap[i][m] == FLOOR && m != 0){
                            if(tempMap[i][m-1] == REACH || tempMap[i][m-1] == PLAYER){
                                tempMap[i][m] = REACH;
                            }
                        }
                        if(tempMap[i][m] == FLOOR && m != 14){
                            if(tempMap[i][m+1] == REACH || tempMap[i][m+1] == PLAYER){
                                tempMap[i][m] = REACH;
                            }
                        }
                    }
                }
            }
        }
        if(tempMap[tiley][tilex] == GOAL || tempMap[tiley][tilex] == BOX){
            if (tiley != 0) {
                if(tempMap[tiley-1][tilex] == REACH || tempMap[tiley-1][tilex] == PLAYER){
                    reachable = true;
                }
            }
            if(tiley != 14){
                if(tempMap[tiley+1][tilex] == REACH || tempMap[tiley+1][tilex] == PLAYER){
                    reachable = true;
                }
            }
            if(tilex != 0){
                if(tempMap[tiley][tilex-1] == REACH || tempMap[tiley][tilex-1] == PLAYER){
                    reachable = true;
                }
            }
            if(tilex != 14){
                if(tempMap[tiley][tilex+1] == REACH || tempMap[tiley][tilex+1] == PLAYER){
                    reachable = true;
                }
            }
        }else if(tempMap[tiley][tilex] == REACH){
            reachable = true;
        }

        return reachable;
    }

    /**
     * Move boxes from starting positions to goal positions,
     * keeping track of the path.
     * @param map - current map
     * @param box - location of current box
     * @param amt - number of pushes to be made
     * @return - co-ordinates current box was pushed to
     */
    private static ArrayList<Integer> push(int[][] map, int[] box, int amt) {
        System.out.printf("amt: %d\n", amt);
        ArrayList<Integer> path = new ArrayList<Integer>();
        for (int p = 0; p < amt; p++) {
            int dir = genRandom(0, 8);
            if (dir == 0) {
                if (box[0] > 0 && box[0] < map.length - 1) {
                    if(!inPath(path,box[0]-1,box[1])){
                        if (map[box[0] - 1][box[1]] == FLOOR)
                            //push up
                            box[0]--;
                        else p--;
                    }
                }
            } else if (dir >= 1 && dir <= 3) {
                if (box[0] > 0 && box[0] < map.length - 1) {
                    if(!inPath(path,box[0]+1,box[1])){
                        if (map[box[0] + 1][box[1]] == FLOOR)
                            //push down
                            box[0]++;
                        else p--;
                    }
                }
            } else if (dir == 4) {
                if (box[1] > 0 && box[1] < map.length - 1) {
                    if(!inPath(path,box[0],box[1]-1)){
                        if (map[box[0]][box[1] - 1] == FLOOR)
                            //push left
                            box[1]--;
                        else p--;
                    }
                }
            } else if (dir >= 5 && dir <= 7) {
                if (box[1] > 0 && box[1] < map.length - 1) {
                    if(!inPath(path,box[0],box[1]+1)){
                        if (map[box[0]][box[1] + 1] == FLOOR)
                            //push right
                            box[1]++;
                        else p--;
                    }
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

    private static Boolean inPath(ArrayList<Integer> path, int y, int x){
        for(int i =0; i < path.size(); i+=2){
            if(path.get(i) == y && path.get(i+1) == x){
                return true;
            }
        }
        return false;
    }

    private Boolean checkValid(int move){
        //if tile is empty
        //checks if tile is empty or occupied
        //if tile is occupied by a box, checks the tile after
        Map functionMap = currGame.getCurrMap();
        ArrayList<Integer> playerLoc = functionMap.checkPlayerLocation();
        int yLoc = playerLoc.get(0);
        int xLoc = playerLoc.get(1);
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
            //removing player from current tile
            if(functionMap.checkTile(xLoc,yLoc) >= Map.PLAYER_ON_GOAL_UP && functionMap.checkTile(xLoc,yLoc) <= Map.PLAYER_ON_GOAL_LEFT){
                functionMap.changeTile(xLoc,yLoc, Map.GOAL);
            }else if(functionMap.checkTile(xLoc,yLoc) >= 1 && functionMap.checkTile(xLoc,yLoc) <= 4){
                functionMap.changeTile(xLoc,yLoc, Map.EMPTY);
            }
            //check tile above
            ArrayList<Integer> loc = augment(xLoc,yLoc,move);
            xLoc = loc.get(0);
            yLoc = loc.get(1);
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
        }else {
            if (functionMap.checkTile(xLoc, yLoc) >= Map.MOVE_UP && functionMap.checkTile(xLoc, yLoc) <= Map.MOVE_LEFT) {
                functionMap.changeTile(xLoc, yLoc, move);
            }else if(functionMap.checkTile(xLoc, yLoc) >= Map.MOVE_UP+8 && functionMap.checkTile(xLoc, yLoc) <= Map.MOVE_LEFT+8){
                functionMap.changeTile(xLoc,yLoc,move+8);
            }
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
    
    public void setState(GameState setState){
        currGame = setState;
    }
}
