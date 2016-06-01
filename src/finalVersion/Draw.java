package finalVersion;

import java.awt.*; 
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

//The Class for drawing specific objects
public class Draw {
	
	//The location of the top of the instructions box
	private static final int TOP_X =  HFrame.Frame_Size_X-310;
	private static final int TOP_Y = 40;
	//The distance between the word "instructions" and the first instruction
	private static final int INSTRUCTIONS_OFFSET = 30;
	
	//List of instructions for moving the player
	private static String[] instructions = {"Instructions","q = Move up and left","w = Move up","e = Move up and right","a = Move left","s = Sit","d = Move right","z = Move down and left","x = Move down","c = Move down and right","j = Jump"};
	
	//Declares an image which could be any of the images(a fence, mho, or player)
	public static BufferedImage img;
	/**
	 * Draws a single fence
	 * @param g is the graphics object which draws the image onto the JFrame
	 * @param fence is the fence object that will be drawn
	 */
	protected static void drawFence(Graphics g, Fence fence){
		//Finds the correct image for a fence and draws it
		try {
			img = ImageIO.read(ResourceLoader.load("Fence.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, fence.getX(), fence.getY(), Fence.SIZE, Fence.SIZE, null);
	}
	
/**
 * Draws a mho
 * 
 * @param g is the graphics object which draws the image onto the JFrame
 * @param mho is the mho that will be drawn
 */
protected static void drawMho(Graphics g, Mho mho){
	try {
		img = ImageIO.read(ResourceLoader.load("Mho.png"));
	} catch (IOException e) {
		e.printStackTrace();
	}
	g.drawImage(img,mho.getX(), mho.getY(), Mho.SIZE, Mho.SIZE,null);
}
/**
 * Draws a player
 * @param g g is the graphics object which draws the image onto the JFrame
 * @param p is the player to be drawn
 */
protected static void drawPlayer(Graphics g, Player p){
	try {
		img = ImageIO.read(ResourceLoader.load("Player.jpg"));
	} catch (IOException e) {
		e.printStackTrace();
	}
	g.drawImage(img,p.getX(), p.getY(), Player.SIZE, Player.SIZE,null);
}

/**
 * method to draw the instructions
 * @param g g is the graphics object which draws the image onto the JFrame
 * @param f the font for the instructions
 */
protected static void drawInstructions(Graphics g, Font f){
	g.setColor(Color.BLACK);
	g.setFont(f);
	for (int i=0;i<instructions.length;i++){
		g.drawString(instructions[i],TOP_X+10,TOP_Y+INSTRUCTIONS_OFFSET+40*i);
	}	
	g.drawRect(TOP_X, TOP_Y, 300, TOP_Y + INSTRUCTIONS_OFFSET+40*(instructions.length-1));
}	
}