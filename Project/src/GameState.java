/**
 * 
 * @author Jackson Luu, Keller Huang, Dan Yoo, Evan Han and Lilac Liu
 *
 */

public class GameState {
    private int moves;
    //Time can be set to a different type later
    private int time;
    //Hints if we have a solve working
    //private int Hints;
    Map currMap;

    public GameState(Map currMap, int currMoves, int time){
        this.currMap = currMap;
        moves = currMoves;
        this.time = time;
    }

    public int getMoves(){
        return moves;
    }

    public int getTime(){
        return time;
    }

    public Map getCurrMap(){
        return currMap;
    }
}
