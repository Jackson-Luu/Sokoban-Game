import java.util.ArrayList;

/**
 * 
 * @author Jackson Luu, Keller Huang, Dan Yoo, Evan Han and Lilac Liu
 *
 */

/*
Key for map
0: Empty tile
1: player facing up
2: player facing right
3: player facing down
4: player facing left
5: box that can be pushed
6: wall
7: goal tile
8: goal tile with box on top
*/
public class Map {
    private int[][] locations;
    private int mapSizex;
    private int mapSizey;

    public Map(int[][] input){
        locations = input;
    }
    //
    public int[][] getLocations(){
        return locations;
    }
    // a method that takes in an arraylist of x ordinates and y ordinates of tiles
    // that need to be changed and changes them to their respective value stored in changeTo
    public void changeTile(int xOrdinate, int yOrdinate, int changeTo){
        locations[xOrdinate][yOrdinate] = changeTo;
    }

    public int checkTile(int xOrdinate, int yOrdinate){
        return locations[xOrdinate][yOrdinate];
    }
    //returns an arraylist with first value being x value and second being y value
    // if player not found returns empty
    public ArrayList<Integer> checkPlayerLocation(){
        ArrayList<Integer> locationArray = new ArrayList<Integer>();
        for(int i = 0; i < mapSizex ; i++){
            for(int m = 0; m < mapSizey; m++){
                if(locations[i][m] >= 1 && locations[i][m] <= 4){
                    locationArray.add(new Integer(i));
                    locationArray.add(new Integer(m));
                    return locationArray;
                }
            }
        }
        return locationArray;
    }
    //returns a number between 1-4 for the player's orientation
    //returns 0 if player not found
    public int getPlayerOrientation(){
        for(int i = 0; i < mapSizex ; i++){
            for(int m = 0; m < mapSizey; m++){
                if(locations[i][m] >= 1 && locations[i][m] <= 4){
                    return locations[i][m];
                }
            }
        }
        return 0;
    }
}
