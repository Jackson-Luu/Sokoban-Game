import java.util.ArrayList;

/**
 * 
 * @author Jackson Luu, Keller Huang, Dan Yoo, Evan Han and Lilac Liu
 *
 */

public class Map {
    private int[][] locations;
    public Map(int[][] input){
        locations = input;
    }
    //
    public int[][] getLocations(){
        return locations;
    }
    // a method that takes in an arraylist of x ordinates and y ordinates of tiles
    // that need to be changed and changes them to their respective value stored in changeTo
    public void changeTiles(ArrayList<Integer> xOrdinate, ArrayList<Integer> yOrdinate, ArrayList<Integer> changeTo){
        for(int i = 0; i < xOrdinate.size(); i++){
            locations[xOrdinate.get(i)][yOrdinate.get(i)] = changeTo.get(i);
        }
    }
}
