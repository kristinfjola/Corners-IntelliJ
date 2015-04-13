/**
 * @author: Kristin Fjola Tomasdottir
 * @date	09.02.2015
 * @goal: 	A class for representing question and answer boxes 
 * 			for the category Math
 */
package boxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MathBox extends Box{

	private int number;
	private BitmapFont bmFont;
	private String text;
	
	/**
	 * @param width	- width of box
	 * @param height - height of box
	 * @param number - answer for box
	 */
	public MathBox(int width, int height, int number, String text, BitmapFont bmFont){
		super(width, height);
		this.number = number;
		this.bmFont = bmFont;
		this.bmFont.setColor(Color.BLACK);
		this.text = text;
	}
	
	@Override
	public void draw(SpriteBatch batch){
		super.draw(batch);
		float plusY = bmFont.getBounds(text).height+ (rec.height-bmFont.getBounds(text).height)/2;
		float plusX = (rec.width-bmFont.getBounds(text).width)/2;
		bmFont.draw(batch, text, rec.x + plusX, rec.y + plusY);
    }

	/**
	 * @return answer for box
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 * set's the answer for the box
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the text of the box
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 * set's the text for the box
	 */
	public void setText(String text) {
		this.text = text;
	}
}
