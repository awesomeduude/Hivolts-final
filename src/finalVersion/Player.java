package finalVersion;
import java.awt.Color; //imports color
//Defines a player, which is controlled by the user
public class Player extends MobileObject{
	/**
	 * Constructor for a player
	 *
	 */
	protected Player(){
		this.bodyColor = Color.GREEN;
		this.eyeColor = Color.BLUE;
	}
	/**
	 * checks if the player can move 
	 *
	 * @return false if the player cannot move and true if the player can move
	 */
	protected boolean canMove(){
		//If the player is outside the grid
		if (Board.coordsToGrid(this.x) <=0 || Board.coordsToGrid(this.x) >=11||Board.coordsToGrid(this.y) <=0 || Board.coordsToGrid(this.y)>=11){

			return false;
		}
		for (Fence fence:HFrame.b.interiorFences){
			if (isTouching(fence)){
				return false;
			}
		}
		for (Mho mho: HFrame.b.mhos){
			if (isTouching(mho)){
				return false;
			}
		}

		return true;
	}
	
	/**
	 * It uses polymorphism so it works for mhos and fences
	 * Checks if the player is touching another object
	 * @param object is any object on the game board
	 * @return is true if the player is touching another object
	 */
	private boolean isTouching(Fence object){
		if (this.x == object.getX() && this.y == object.getY()){
			return true;
		}
		return false;
	}
}