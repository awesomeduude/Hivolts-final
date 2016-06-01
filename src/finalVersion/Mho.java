package finalVersion;

import java.awt.Color; 
//This class defines a mho
public class Mho extends MobileObject{
	
	private boolean discard; //is true if the mho is touching an electric fence
	/**
	 * constructor for mho
	 * @param x position
	 * @param y position
	 * @param discard boolean for if the mho needs to be discarded or not
	 */
	protected Mho(int x, int y, boolean discard){
		this.x = x;
		this.y = y;
		discard = false;
		this.bodyColor = Color.RED;
		this.eyeColor = Color.YELLOW;
	}
	/**
	 * Moves a mho according to the AI 
	 * @param b components of the board class
	 */
	protected void updatePosition(Board b) {
		
			int directionToPlayer = findDirection(b.p); //direction mho has to go in
			int gridX = Board.coordsToGrid(this.x);
			int gridY = Board.coordsToGrid(this.y);
			
			if (directionToPlayer>4){ //if directions are horizontally or vertically towards the player
				moveStraight(directionToPlayer,b.p); //move straight towards that direction
			}else { 
				int tempXOld = xOld;
				int tempYOld = yOld; 
				
				boolean diagIsFree = b.isFree(gridX,gridY,directionToPlayer,false); //checks if diagonal position is free.
				
				tryToMoveDiag(diagIsFree,gridX, gridY, directionToPlayer, b, false); //moves diagonal if free
				
				//checks if the mho has moved onto a free space, if it hasn't then it tries to move onto a fence
				if (xOld == tempXOld && yOld == tempYOld){ 
					boolean noMho = b.isFree(gridX,gridY,directionToPlayer,true);
					tryToMoveDiag(noMho,gridX, gridY, directionToPlayer, b, true);
				}
			} 
		//checks if the mho has touched a fence
		for (Fence fence: b.interiorFences) {
			if (fence.getX() == x && fence.getY() == y) {
				discard = true;
			}
		}
	}
	/**
	 * Method that finds the direction to the player
	 * 
	 * 1 = mho is up and left compared to player
	 * 2 = mho is up and right compared to player
	 * 3 = mho is down and left compared to player
	 * 4 = mho is down and right compared to player
	 * 5 = mho is above player
	 * 6 = mho is left of player
	 * 7 = mho is below player
	 * 8 = mho is right of player
	 * @param p the player the mho will move to
	 */
	private int findDirection(Player p){
		
		if (this.x<p.getX()&&this.y<p.getY()){
			return 1;
			
		} else if(this.x>p.getX() && this.y<p.getY()){
			return 2;
			
		} else if(this.x<p.getX() && this.y>p.getY()){
			return 3;
			
		} else if(this.x>p.getX() && this.y>p.getY()){
			return 4;
			
		} else if(this.x == p.getX() && this.y<p.getY()){
			return 5;
			
		} else if (this.x<p.getX() && this.y == p.getY()){
			return 6;
			
		} else if (this.x == p.getX() && this.y>p.getY()){
			return 7;
		}
		return 8;
	}
	/**
	 * finds the horizontal direction to the player
	 * @param p the player the mho will move to
	 * @return returns the horizontal direction
	 */
	private int findXDirection(Player p){
		if (this.x<p.x){
			return 6;
		} else{
			return 8;
		}
	}
	/**
	 * finds the vertical direction of the player
	 * @param p the player the mho will move to
	 * @return returns the vertical direction
	 */
	private int findYDirection(Player p){
		if (this.y<p.y){
			return 5;
		} else{
			return 7;
		}
	}
	
	/**
	 * Moves the mho either vertically of horizontally according to the direction to the player
	 * @param directionToPlayer Number for which direction the mho should go.
	 * @param p the player the mho will move to
	 */
	private void moveStraight(int directionToPlayer, Player p){
		switch(directionToPlayer){
		case 5:
			moveDown();
			break;
		case 6:
			moveRight();
			break;
		case 7:
			moveUp();
			break;
		case 8:
			moveLeft();
			break;
		}
	}
	/**
	 * Tries to move the mho diagonally if the space is free, and vertically or horizontally if the diagonal space is not free
	 * @param isFree if the diagonal is free
	 * @param gridX x grid coordinate
	 * @param gridY y grid coordinate
	 * @param directionToPlayer Number for which direction the mho should go
	 * @param b is a board
	 * @param onlyCheckMhos is true if the method should ignore the fences 
	 */
	private void tryToMoveDiag(boolean isFree, int gridX, int gridY, int directionToPlayer, Board b, boolean onlyCheckMhos){
		//if the diagonal is free then move diagonally
		if (isFree){
			switch(directionToPlayer){
			case 1:
				moveDownRight();	
				break;
			case 2:
				moveDownLeft();
				break;
			case 3:
				moveUpRight();
				break;
			case 4:
				moveUpLeft();
				break;
			}
		} else{ //Diagonal towards player is not free
			
			//if the horizontal distance to the player is greater than or equal to the vertical distance
			if (Math.abs(this.x-b.p.x) >= Math.abs(this.y-b.p.y)){
				if (b.isFree(gridX, gridY, findXDirection(b.p),onlyCheckMhos)) {
					if (directionToPlayer == 1 || directionToPlayer == 3){
						moveRight();			
					} else{
						moveLeft();
					}			
				}
			}
			//the vertical distance to the player is greater than the horizontal distance
			else if(b.isFree(gridX, gridY, findYDirection(b.p), onlyCheckMhos)){
				if(directionToPlayer == 1 || directionToPlayer == 2){
					moveDown();
				}else{
					moveUp();
				}
			} 
		}
	}
	/**
	 * Returns the discard value of a mho.
	 * @return the discard value of a mho
	 */
	protected boolean isDiscard() {
		return discard;
	}
}