package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.corners.game.MainActivity;

public class Extra {
	float screenWidth = Gdx.graphics.getWidth();
	float screenHeight = Gdx.graphics.getHeight();
	BitmapFont bmFont;
	Rectangle bannerRec;
	
	//temporary
	Texture leftBox;
	Texture middleBox;
	Texture rightBox;
	
	public Extra(MainActivity main) {
		bmFont = main.skin.getFont(main.screenSizeGroup+"-M");
	 	bmFont.setColor(Color.BLACK);
	 	
	 	bannerRec = new Rectangle();
	 	bannerRec.width = screenWidth;
	 	bannerRec.height = screenHeight/10;
		
		// TODO make into one box, this is temporary to see how big each "box" is
		Pixmap pm = new Pixmap(100, 100, Format.RGBA8888);
	 	pm.setColor(Color.RED);
	 	pm.fillRectangle(0,0,100,100);
	 	leftBox = new Texture(pm);
	 	pm.setColor(Color.GREEN);
	 	pm.fillRectangle(0,0,100,100);
	 	middleBox = new Texture(pm);	
	 	pm.setColor(Color.YELLOW);
	 	pm.fillRectangle(0,0,100,100);
	 	rightBox = new Texture(pm);
	}

	public void draw(SpriteBatch batch, String leftText, String middleText, String rightText) {
		float leftBeginX = 0;
	 	float leftWidth = bannerRec.height;
	 	float leftPlusX = (leftWidth-bmFont.getBounds(leftText).width)/2;
	 	
	 	float middleBeginX = bannerRec.height;
	 	float middleWidth = bannerRec.width - 2*bannerRec.height;
	 	float middlePlusX = (middleWidth-bmFont.getBounds(middleText).width)/2;
	 	
	 	float rightBeginX = bannerRec.width - bannerRec.height;
	 	float rightWidth = bannerRec.height;
	 	float rightPlusX = (rightWidth-bmFont.getBounds(rightText).width)/2;
	 	
	 	float plusY = bmFont.getBounds(middleText).height+ (bannerRec.height-bmFont.getBounds(middleText).height)/2;
	 	
		batch.begin();		
		batch.draw(leftBox, leftBeginX, screenHeight-bannerRec.height, leftWidth, bannerRec.height);
		batch.draw(middleBox, middleBeginX, screenHeight-bannerRec.height, middleWidth, bannerRec.height);
		batch.draw(rightBox, rightBeginX, screenHeight-bannerRec.height, rightWidth, bannerRec.height);
		
		bmFont.draw(batch, leftText, leftBeginX+leftPlusX, screenHeight-bannerRec.height+plusY);
		bmFont.draw(batch, middleText, middleBeginX+middlePlusX, screenHeight-bannerRec.height+plusY);
		bmFont.draw(batch, rightText, rightBeginX+rightPlusX, screenHeight-bannerRec.height+plusY);
		batch.end();
	}
}
