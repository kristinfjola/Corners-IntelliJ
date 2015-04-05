package logic;

import com.badlogic.gdx.graphics.Color;


public class CorColor {

	private Color color;
	private String name;
	
	public CorColor(Color color, String name) {
		super();
		this.color = color;
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
