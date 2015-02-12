package boxes;

import com.badlogic.gdx.math.Rectangle;

public class Box {

	Rectangle rec;
	int screenWidth = 480;
    int screenHeight = 800;
	public Rectangle getRec() {
		return rec;
	}
	public void setRec(Rectangle rec) {
		this.rec = rec;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
    
    
}
