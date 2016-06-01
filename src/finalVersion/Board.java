package finalVersion;
import java.util.ArrayList;

//The board is an object with contains all of the fences, mhos and player
public class Board {

	//Specifies the number of mhos and fences on the board
	private final static int NUM_MHOS = 12;
	private final static int NUM_INTERIOR_FENCES = 20;
	//Specifies the size of the grid
	private final static int GRID_SIZE = 12;

	//The offset is how far all the components of the board is shifted, both on the x and y axis.
	private final static int OFFSET = 40;

	//Array Lists that store all of the fences and mhos 
	protected ArrayList<Fence> perimeterFences = new ArrayList<Fence>();
	protected ArrayList<Fence> interiorFences = new ArrayList<Fence>();
	protected ArrayList<Mho> mhos = new ArrayList<Mho>();

	//Creates the player from the player class
	protected Player p = new Player();

	//Open Spaces are spaces on the board which are not occupied by a fence or player. A player can jump to any open space.
	private ArrayList<Integer> openSpaces = new ArrayList<Integer>();

	//Constructs a new board
	protected Board(){
		init();
	}
	/**
	 * Clears everything off the board and generates new, randomly generated fences, mho, and a player
	 */
	protected void init(){
		perimeterFences.clear();
		interiorFences.clear();
		openSpaces.clear();
		mhos.clear();
		makeOpenSpaces();
		makePlayer();
		makePerimeterFences();
		makeRandomInteriorFences();
		makeMhos();
	}

	/**
	 * generates a random number between a minimum and maximum value
	 * @param min is the lowest possible value, it is inclusive
	 * @param max is the highest possible value, it is also inclusive
	 * @return the random integer
	 */
	private static int randInt(int min, int max){
		int range = (max-min) + 1;
		return (int) (Math.random() * range) + min;
	}

	/**
	 * Converts grid coordinates into a coordinate
	 * this works for both x and y coordinates
	 * Grid coordinates are explained more in the readme
	 * @param coord is the grid coordinate on the game board.
	 * @return returns the converted x or y coordinate
	 */
	private static int gridToCoords(int coord){
		return (coord*MobileObject.STEP)+OFFSET;
	}

	/**
	 * Converts an x or y coordinate to a grid coordinate
	 * performs the opposite of the gridToCoords method.
	 * @param coord is the x or y to be converted
	 * @return returns the converted coordinate
	 */
	protected static int coordsToGrid(int coord){
		return (coord-OFFSET)/MobileObject.STEP;
	}

	/**
	 * Converts a space number(0-99) to a x grid coordinate(1-10) inclusive
	 * main purpose is to serve as an intermediate method for spaceToXCoord
	 * @param a is the space number
	 * @return returns the corresponding x grid coordinate
	 */
	private static int spaceToXGrid(int a){
		return a%10 +1;
	}

	/**
	 * Converts a space number(0-99) to a x coordinate
	 * @param a is the space number
	 * @return returns the x coordinate
	 */
	private static int spaceToXCoord(int a){
		return gridToCoords(spaceToXGrid(a));
	}

	/**
	 * Converts a space number(0-99) to a y grid coordinate(1-10) inclusive
	 * main purpose is to serve as an intermediate method for spaceToYCoord
	 * @param a is the space number
	 * @return returns the corresponding y grid coordinate
	 */
	private static int spaceToYGrid(int a){
		return a/10 +1;
	}
	/**
	 * Converts a space number(0-99) to a y coordinate
	 * @param a is the space number
	 * @return returns the y coordinate
	 */
	private static int spaceToYCoord(int a){
		return gridToCoords(spaceToYGrid(a));
	}
	/**
	 * Initializes the openSpaces array to have 100 spaces
	 * these 100 spaces are the 100 that are on the interior of the perimeter fences
	 */
	private void makeOpenSpaces(){
		for (int space=0;space<100;space++){
			openSpaces.add(space);
		}
	}
	/**
	 * generates the fences that go on the perimeter of the board
	 */
	private void makePerimeterFences(){
		for(int i=0;i<GRID_SIZE;i++){
			//top and bottom rows
			perimeterFences.add(new Fence(gridToCoords(i),gridToCoords(0)));
			perimeterFences.add(new Fence(gridToCoords(i),gridToCoords(GRID_SIZE-1)));

			//left and right columns
			if (i!=0 && i!=GRID_SIZE-1){
				perimeterFences.add(new Fence(gridToCoords(0), gridToCoords(i)));
				perimeterFences.add(new Fence(gridToCoords(GRID_SIZE-1), gridToCoords(i)));
			}
		}
	}
	/**
	 * generates the 20 randomly placed interior fences
	 */
	private void makeRandomInteriorFences(){

		for (int i=0;i<NUM_INTERIOR_FENCES;i++){
			//finds a random open space
			int space = findOpenSpace();

			///adds a fence to that space
			interiorFences.add(new Fence(spaceToXCoord(space), spaceToYCoord(space)));
		}
	}

	/**
	 * generates the random mhos that go on the board
	 */
	private void makeMhos(){
		for (int i=0; i<NUM_MHOS; i++){
			//finds an open space on the board and adds a mho to it
			int space = findOpenSpace();
			mhos.add(new Mho(spaceToXCoord(space), spaceToYCoord(space),false));
			mhos.get(i).setSpace(space);
		}
		//Changes the openSpaces array to not include mhos, so that the Player has a change to jump onto mhos
		for (int i = 0; i<NUM_MHOS; i++) {
			int space = mhos.get(i).getSpace();
			openSpaces.add(space);
		}
	}
	/**
	 * Randomly places the player on the board
	 */
	private void makePlayer(){
		//finds an open space on the board and sets the players coordinates to that space
		int space = findOpenSpace();
		p.setX(spaceToXCoord(space));
		p.setY(spaceToYCoord(space));
	}
	/**
	 * finds a random open space on the board, and makes it an occupied space
	 * @return returns the random open space on the board
	 */
	private int findOpenSpace(){
		int randomOpenSpace = 0;
		//finds a random space in the openSpacess array
		int index = randInt(0,openSpaces.size()-1);
		randomOpenSpace = openSpaces.get(index);
		
		openSpaces.remove(index);

		return randomOpenSpace;
	}
	/**
	 *. "jumps" a player, which means that the player is teleported to a random space without a fence.
	 * @param p is the player to be jumped
	 */
	protected void jump(Player p){
		//Holds the players current space to be re added to openSpaces
		int oldSpace = p.getSpace();
		
		int space = findOpenSpace();
		
		p.setxOld(p.x);
		p.setyOld(p.y);
		
		p.setX(spaceToXCoord(space));
		p.setY(spaceToYCoord(space));
		p.setSpace(space);
		
		if (oldSpace >= 0) {
			//Adds the players previous position as a space that can be jumped to
			openSpaces.add(oldSpace);
		}
	}

	/**
	 * Checks if a space is free
	 * 
	 * @param gridX is the x coordinate of an object in grid coordinate form
	 * @param gridY is the y coordinate of an object in grid coordinate form
	 * @param the direction of the mho relative to the player
	 * @param onlyCheckMhos specifies whether to check if the space contains both a fence or a mho, or to only check if the space contains a mho
	 * @return returns if the space is free or not
	 */
	protected boolean isFree(int gridX, int gridY, int direction, boolean onlyCheckMhos){
		int x = gridX;
		int y = gridY;
		//Changes the space to be checked depending on the direction of the mho relative to the player
		//For example, for case 1(if the mho is up and right compared to the player) the algorithm adds 1 to both the x and y coordinates
		//because it wants to check if the diagonal space closest to the player is free.
		switch(direction){
		case 1:
			x++;
			y++;
			break;
		case 2:
			x--;
			y++;
			break;
		case 3:
			x++;
			y--;
			break;
		case 4:
			x--;
			y--;
			break;
		case 5:
			y++;
			break;
		case 6:
			x++;
			break;
		case 7:
			y--;
			break;
		case 8:
			x--;
			break;
		}
		x = gridToCoords(x);
		y = gridToCoords(y);
		//Checks if there is a mho at the specified space
		for (Mho mho:mhos){
			if(mho.getX() == x && mho.getY() == y&&!mho.isDiscard()){
				return false;
			}
		}
		if (!onlyCheckMhos){
			//checks if there is a fence at the specified space
			for (Fence fence:interiorFences){
				if (fence.getX() == x && fence.getY() == y){
					return false;
				}
			}
		}
		return true;
	}
}