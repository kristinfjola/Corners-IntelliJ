/**
 * @author: Kristin Fjola Tomasdottir
 * @date	09.02.2015
 * @goal: 	A class for representing question and answer boxes 
 * 			for the category Colors
 */
package boxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ColorBox extends Box{

	private Color color;
	private String name;
	private BitmapFont bmFont;
	private String text;
	private Pixmap pm;

	
	/**
	 * @param width
	 * @param height
	 * @param color
	 * @param name
	 * @param bmFont
	 */
	public ColorBox(int width, int height, Color color, String name, BitmapFont bmFont) {
		super(width, height);
		this.color = color;
		this.name = name;
		this.bmFont = bmFont;
		this.bmFont.setColor(Color.BLACK);
		this.pm  = new Pixmap(width, height, Format.RGBA8888);
	}

	
	@Override
	public void draw(SpriteBatch batch){
		super.draw(batch);
		//this.pm.setColor(this.color);
		//pm.fill();
		if(this.text != null){
			float plusY = bmFont.getBounds(text).height+ (rec.height-bmFont.getBounds(text).height)/2;
			float plusX = (rec.width-bmFont.getBounds(text).width)/2;
			bmFont.draw(batch, text, rec.x + plusX, rec.y + plusY);
		}
    }
	
	/**
	 * @param color
	 */
	public void setBackground(Color color){
		this.pm.setColor(color);
		this.pm.fill();
		setTexture(new Texture(pm));
	}

	/**
	 * @return name of the color
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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
	
	/**
	 * @return BitmapFont for box
	 */
	public BitmapFont getBmFont() {
		return bmFont;
	}


	/**
	 * @param bmFont for box
	 */
	public void setBmFont(BitmapFont bmFont) {
		this.bmFont = bmFont;
	}


	/**
	 * @return text of box
	 */
	public String getText() {
		return text;
	}


	/**
	 * @param text - for box
	 */
	public void setText(String text) {
		this.text = text;
	}
}
