package finalVersion;

import java.awt.*;
//Represents an object than can move(either a mho or the player)

public class MobileObject extends Fence {
	//the x and y coordinates of the previous position of the mobile object
	protected int xOld,yOld;
	//the space position of the mho
	private int space;
	
	//variables for the colors of the mobile objects
	protected Color bodyColor, eyeColor;
	
	//the distance a mobile object moves each time it moves
	protected static final int STEP = (int)((int) Player.SIZE * 1.5);
	/**
	 * moves a mobile object
	 * @param STEPX is how much the x coordinate will change
	 * @param STEPY is how much the y coordinate will change
	 */
	private void move(int STEPX, int STEPY){
		xOld=x;
		yOld=y;
		x+=STEPX;
		y+=STEPY;
	}
	/**
	 * The following methods are there to make the code easier to write, because the names are very descriptive.
	 */
	protected void moveRight(){
		move(STEP,0);
	}
	protected void moveLeft(){
		move(-STEP,0);
	}
	protected void moveUp(){
		move(0,-STEP);
	}
	protected void moveDown(){
		move(0,STEP);
	}
	protected void moveUpRight(){
		move(STEP,-STEP);
	}
	protected void moveUpLeft(){
		move(-STEP,-STEP);
	}
	protected void moveDownLeft(){
		move(-STEP,STEP);
	}
	protected void moveDownRight(){
		move(STEP,STEP);
	}
	//accessor and modifier methods for old x and y positions, space position, and color of mobile objects.
	protected int getxOld() {
		return xOld;
	}
	protected void setxOld(int xOld) {
		this.xOld = xOld;
	}
	protected int getyOld() {
		return yOld;
	}
	protected void setyOld(int yOld) {
		this.yOld = yOld;
	}
	protected int getSpace() {
		return space;
	}
	protected void setSpace(int s) {
		space = s;
	}
	protected Color getBodyColor() {
		return bodyColor;
	}
	protected void setBodyColor(Color bodyColor) {
		this.bodyColor = bodyColor;
	}
	protected Color getEyeColor() {
		return eyeColor;
	}
	protected void setEyeColor(Color eyeColor) {
		this.eyeColor = eyeColor;
	}
}