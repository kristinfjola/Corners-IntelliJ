/**
 * @author: Kristin Fjola Tomasdottir
 * @date	09.02.2015
 * @goal: 	A class for representing question and answer boxes 
 * 			for the category Math
 */
package boxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MathBox extends Box{

	int number;
	BitmapFont font;
	String text;
	
	/**
	 * @param width	- width of box
	 * @param height - height of box
	 * @param number - answer for box
	 */
	public MathBox(int width, int height, int number, String text){
		super(width, height);
		this.number = number;
		this.font = new BitmapFont();
		this.font.setColor(Color.BLACK);
		this.text = text;
	}

	/**
	 * @return answer for box
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
