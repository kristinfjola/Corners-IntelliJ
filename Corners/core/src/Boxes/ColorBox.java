/**
 * @author: Kristin Fjola Tomasdottir
 * @date	09.02.2015
 * @goal: 	A class for representing question and answer boxes 
 * 			for the category Colors
 */
package boxes;

import com.badlogic.gdx.graphics.Color;

public class ColorBox extends Box{

	Color color;
	
	/**
	 * @param width	- width of box
	 * @param height - height of box
	 * @param color - color to display on box
	 */
	public ColorBox(int width, int height, Color color){
		super(width, height);
		this.color = color;
	}

	/**
	 * @return color of box
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
