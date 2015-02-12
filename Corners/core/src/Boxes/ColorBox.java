package boxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class ColorBox extends Box{

	Color color;
	
	public ColorBox(int width, int height, Color color){
		this.rec = new Rectangle();
		this.rec.width = width;
		this.rec.height = height;
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
