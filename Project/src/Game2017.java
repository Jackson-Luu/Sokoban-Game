public class Game2017 {
	public static void main(String[] args){
		int[][] samplemap = {
				
			
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,1,4,1,0,0,0,0,0,0},
				{0,0,0,0,1,1,1,1,3,1,0,0,0,0,0,0},
				{0,0,0,0,1,4,2,3,5,1,1,1,0,0,0,0},
				{0,0,0,0,1,1,1,3,2,3,4,1,0,0,0,0},
				{0,0,0,0,0,0,1,2,1,1,1,1,0,0,0,0},
				{0,0,0,0,0,0,1,4,1,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
			
				
				
		};
		
		new Sokoban(new GameEngine(15, 15));	
	}
}	
