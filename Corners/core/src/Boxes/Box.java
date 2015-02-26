/**
 * @author: Kristin Fjola Tomasdottir
 * @date	09.02.2015
 * @goal: 	A Superclass for all question and answer boxes
 */
package boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Box {

	Rectangle rec;
	//int screenWidth = 480;
    //int screenHeight = 800;
	int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();
    Texture texture;
    
    /**
	 * @param width	- width of box
	 * @param height - height of box
	 */
    public Box(int width, int height){
    	this.rec = new Rectangle();
		this.rec.width = width;
		this.rec.height = height;
    }
    
    public void draw(SpriteBatch batch){
    	batch.draw(texture, rec.x, rec.y, rec.width, rec.height);
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

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
    
    
}
