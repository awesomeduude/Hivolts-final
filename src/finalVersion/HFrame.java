package finalVersion;

import javax.swing.JFrame; //import JFrame

import java.awt.*; //import user interfaces and painting graphics and images
import java.awt.event.KeyListener; //import keylistener
import java.awt.event.KeyEvent; //import keyevent
import java.util.ArrayList; //import arraylists

//The graphics representation of a Board
public class HFrame extends JFrame implements KeyListener{
	//This line of code is here so that eclipse wont give me a compile warning
	static final long serialVersionUID = 0;
	//The size of the JFrame
	protected static final int Frame_Size_X = 1100;
	protected static final int Frame_Size_Y = 750;
	//The two fonts used in the game
	private Font bigFont,smallFont;
	
	//array of strings that contains all the keys that will be used in the game.
	private String[] keys = {"q","w","e","a","s","d","z","x","c","j","y","n"};
	
	//array of mhos that contains all the mhos to be discarded/cleared.
	private ArrayList<Mho> discardMhos = new ArrayList<Mho>();
	
	/**
	 * Constructor for an HFrame
	 * Assigns specified font values.
	 * Sets size, background color, adds KeyListener, and calls the paint method.
	 */
	protected HFrame(){
		init();
	}
	private void init(){
		bigFont = new Font("TimesRoman", Font.PLAIN, 50);
		smallFont = new Font("TimesRoman", Font.ITALIC, 25);
		setSize(Frame_Size_X,Frame_Size_Y);
		setBackground(Color.WHITE);
		this.addKeyListener(this);
		repaint();
	}
	//creates a board
	protected static Board b = new Board();
	
	//Is true when a game has begun
	private boolean gameStarted = false;
	
	//Is true when the game is over
	private boolean gameOver = false;
	
	//is true if the player won
	
	private boolean playerWon;
	
	
	
	/**
	 *Draws the components of the board onto the JFrame
	 */
	public void paint(Graphics g){
		g.setColor(getBackground());
		g.fillRect(0,0, Frame_Size_X, Frame_Size_Y);
		
		if (!gameStarted){
			startNewGame(g);
			
		} 
		if (gameStarted && !gameOver){
			updateObjects(g);
		}
		if (gameOver){
			drawGameOverBanner(g);
		}
	}
	/**
	 * clears the old positions of the objects and draws the new positions
	 * @param g is the graphics object that draws and clears the objects
	 */
	private void updateObjects(Graphics g){
		clearMhos(g);
		clearPlayer(g);
		drawMhos(g);
		drawPlayer(g);
		drawFences(g);
		Draw.drawInstructions(g,smallFont);
	}
	
	/**
	 * Draws both the interior and perimeter fences
	 * @param g is the Graphics object
	 */
	private void drawFences(Graphics g){
		g.setColor(Color.BLACK);
		drawInteriorFences(g);
		drawPerimeterFences(g);
	}
	
	/**
	 * Draws the interior fences
	 * @param g is the Graphics object
	 */
	private void drawInteriorFences(Graphics g){
		g.setColor(Color.BLACK);
		for (Fence fence:b.interiorFences){
			Draw.drawFence(g, fence);
		}
	}
	/**
	 * Draws the perimeter fences
	 * @param g is the Graphics object
	 */
	private void drawPerimeterFences(Graphics g){
		
		for (Fence fence:b.perimeterFences){
			Draw.drawFence(g, fence);
		}	
	}
	/**
	 * Draws the mhos
	 * @param g is the Graphics object
	 */
	private void drawMhos(Graphics g){
		
		for(Mho mho:b.mhos){
			Draw.drawMho(g,mho);
		}
	}
	/**
	 *Draws the player
	 * @param g is the Graphics object
	 */
	private void drawPlayer(Graphics g){
		Draw.drawPlayer(g, b.p);
	}
	/**
	 *Clears all of the old positions of the mhos and removes the mhos that have touched a fence
	 * @param g is the Graphics object
	 */
	private void clearMhos(Graphics g){
		for(Mho mho:b.mhos){
			g.clearRect(mho.getxOld(),mho.getyOld(),Mho.SIZE,Mho.SIZE);
		}
		for(Mho mho:discardMhos) {
			g.clearRect(mho.getX(), mho.getY(), Mho.SIZE, Mho.SIZE);
		}
		discardMhos.clear();
	}
	/**
	 * Clears the old Position of the player
	 * @param g is the graphics object
	 */
	private void clearPlayer(Graphics g){
		g.clearRect(b.p.getxOld(),b.p.getyOld(),Player.SIZE,Player.SIZE);
	}
	/**
	 * This method is called everytime a key is pressed on the keyboard
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		//Converts e.getKeyChar() to a string
		String keyPressed =  e.getKeyChar() + "";
		//only runs if the player has pressed a valid key
		
		if (contains(keys,keyPressed)){ 
			
			switch (keyPressed){
			//if the key is w, then it moves the player up.
			case "w": 
				b.p.moveUp();
				break;
				//if the key is a, then it moves the player left.
			case "a": 
				b.p.moveLeft();
				break;
				//if the key is x, then it moves the player down.	
			case "x": 
				b.p.moveDown();
				break;
				//if the key is d, then it moves the player right.
			case "d": 
				b.p.moveRight();
				break;
				//if the key is q, then it moves the player up and to the left.
			case "q":
				b.p.moveUpLeft();
				break;
				//if the key is e, then it moves the player up and to the right. 
			case "e":
				b.p.moveUpRight();	
				break;
				//if the key is z, then it moves the player down and to the left.
			case "z":
				b.p.moveDownLeft();
				break;
				//if the key is c, then it moves the player down and to the right.
			case "c":
				b.p.moveDownRight();
				break;
				//if the key is j, then it moves the player to a random location.
			case "j":
				b.jump(b.p);
				break;
				//if the key is s, then it doesn't move
			case "s":
				break;
				//if the key is y when game is over, then the game resets.
			case "y":
				if (gameOver){
					gameStarted = false;
				}
				break;
				//if the key is n when game is over, then JFrame window closes and program terminates.
			case "n":
				if(gameOver){
					System.exit(1);
				}
				break;
			}
			//Updates mho position and discards the mhos that need to be discarded
			if (!keyPressed.equals("j") && !keyPressed.equals("y") && !keyPressed.equals("n")){
				for (int i =0; i <b.mhos.size(); i++) {
					b.mhos.get(i).updatePosition(b);
					if (b.mhos.get(i).isDiscard()) {
						discardMhos.add(b.mhos.get(i));
						b.mhos.remove(i);
						i--;
					}
				}
			}
			
			//Checks if the player won or lost
			if (!gameOver){
				if (b.mhos.size() == 0) {
					playerWon = true;
					gameOver = true;
				}
				if(!b.p.canMove()){
					playerWon = false;
					gameOver = true;
				}
			}
		}
		repaint();
	}
	/**
	 * Checks to see if a string is in an array
	 * @param a is the array of strings
	 * @param b is the string that will be checked
	 * @return returns true of b is in a
	 */
	private boolean contains(String[] a, String b){
		for (String element:a){
			if (element.equals(b)){
				return true;
			}
		}
		return false;
	}
	/**
	 *Draws the banner that appears when the game is over
	 * @param g Graphics is the graphics object
	 */
	private void drawGameOverBanner(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(0,0,Frame_Size_X, Frame_Size_Y);
		g.setColor(Color.BLACK);
		g.setFont(bigFont);
		if(playerWon){
			g.drawString("You Win!",Frame_Size_X/2-100, Frame_Size_Y/2);
		} else{
			g.drawString("You Died!",Frame_Size_X/2-100, Frame_Size_Y/2);
		}
		g.setFont(smallFont);
		g.drawString("Press Y to Play again, Press N to quit",Frame_Size_X/2-200, (int)(Frame_Size_Y/1.5));
	}
	/**
	 * Clears the board
	 * @param g is the Graphics object
	 */
	private void clearBoard(Graphics g){
		g.setColor(Color.WHITE);
		g.clearRect(0,0,Frame_Size_X,Frame_Size_Y);
	}
	/**
	 * resets the board and starts a new game
	 * @param g is the graphics object
	 */
	private void startNewGame(Graphics g){
		clearBoard(g);
		b.init();
		gameStarted = true;
		gameOver = false;
	}
	//Other two methods from the KeyListener interface, they are not used in this program.
	@Override
	public void keyReleased(KeyEvent e) { 
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
}