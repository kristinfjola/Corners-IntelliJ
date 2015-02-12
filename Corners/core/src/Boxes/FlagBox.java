package boxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class FlagBox extends Box{

	String country;
	
	public FlagBox(int width, int height, String country){
		this.rec = new Rectangle();
		this.rec.width = width;
		this.rec.height = height;
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
