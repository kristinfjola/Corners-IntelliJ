/**
 * @author: Kristin Fjola Tomasdottir
 * @date	09.02.2015
 * @goal: 	A Superclass for all question and answer boxes
 */
package boxes;

import com.badlogic.gdx.math.Rectangle;

public class Box {

	Rectangle rec;
	int screenWidth = 480;
    int screenHeight = 800;
    
    /**
	 * @param width	- width of box
	 * @param height - height of box
	 */
    public Box(int width, int height){
    	this.rec = new Rectangle();
		this.rec.width = width;
		this.rec.height = height;
    }
    
	/**
	 * @return rectangle for box
	 */
	public Rectangle getRec() {
		return rec;
	}
	
	/**
	 * @param rec
	 */
	public void setRec(Rectangle rec) {
		this.rec = rec;
	}
	
	/**
	 * @return width of screen
	 */
	public int getScreenWidth() {
		return screenWidth;
	}
	
	/**
	 * @param screenWidth
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	
	/**
	 * @return height of screen
	 */
	public int getScreenHeight() {
		return screenHeight;
	}
	
	/**
	 * @param screenHeight
	 */
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
    
    
}
