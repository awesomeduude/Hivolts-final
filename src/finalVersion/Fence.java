package finalVersion;
//Represents a basic object on the board, as well as a fence

public class Fence {
	//x coordinate and y coordinate for a fence.
	protected int x,y;
	
	//Size of the objects in the game.
	protected static final int SIZE = 40;
	
	
	protected Fence(){
	}
	
	protected Fence(int x, int y){
		this.x = x;
		this.y = y;
	}
	//Accessor and Modifier methods for the properties of a basic object
	protected int getX() {
		return x;
	}
	protected void setX(int x) {
		this.x = x;
	}
	protected int getY() {
		return y;
	}
	protected void setY(int y) {
		this.y = y;
	}
	protected static int getSize() {
		return SIZE;
	}
}