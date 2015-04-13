/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	05.04.2015
 * @goal 	The class is a DTO for the values of the colors in the boxes
 * 			in the Colors category of the game.
 */
package logic;

import com.badlogic.gdx.graphics.Color;

public class CorColor {

	private Color color;
	private String name;
	private Boolean light;
	
	/**
	 * @param color
	 * @param name
	 * @param light
	 */
	public CorColor(Color color, String name, Boolean light) {
		super();
		this.color = color;
		this.name = name;
		this.light = light;
	}

	/**
	 * @return the color
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
	 * @return the name of the color
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name - of the color
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return light
	 */
	public Boolean getLight() {
		return light;
	}
	
	/**
	 * @param light - is the color dark or light
	 */
	public void setLight(Boolean light) {
		this.light = light;
	}
	
}
