/**
 * @author: Kristin Fjola Tomasdottir
 * @date	09.02.2015
 * @goal: 	A class for representing question and answer boxes 
 * 			for the category Flags
 */
package boxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlagBox extends Box{

	private String country;
	private String capital;
	private BitmapFont bmFont;
	private String text;
	
	/**
	 * @param width	- width of box
	 * @param height - height of box
	 * @param country - country to display on box
	 */
	public FlagBox(int width, int height, String country, String capital, BitmapFont bmFont){
		super(width, height);
		this.setCountry(country);
		this.setCapital(capital);
		this.bmFont = bmFont;
		this.bmFont.setColor(Color.BLACK);
	}
	
	@Override
	public void draw(SpriteBatch batch){
		super.draw(batch);
		if(this.text != null){
			float plusY = bmFont.getBounds(text).height+ (rec.height-bmFont.getBounds(text).height)/2;
			float plusX = (rec.width-bmFont.getBounds(text).width)/2;
			bmFont.draw(batch, text, rec.x + plusX, rec.y + plusY);
		}
    }

	/**
	 * @return BitmapFont of box
	 */
	public BitmapFont getBmFont() {
		return bmFont;
	}

	/**
	 * @param bmFont - BitmapFont of box
	 */
	public void setBmFont(BitmapFont bmFont) {
		this.bmFont = bmFont;
	}

	/**
	 * @return text on box
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text - new text on box
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return country for box
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country - country of the box
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 
	 * @return capital of country
	 */
	public String getCapital() {
		return capital;
	}

	/**
	 * 
	 * @param capital - capital of the box
	 */
	public void setCapital(String capital) {
		this.capital = capital;
	}
	
	
}
