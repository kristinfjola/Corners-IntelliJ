/**
 * @author: Johanna Agnes Magnusdottir
 * @date	02.03.2015
 * @goal: 	The display of the info bar that is on the top of each screen. 
 * 			It tells you where in the game you are (what category, what level, etc.)
 * 			and other information such as average amount of stars/levels
 */

package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.corners.game.MainActivity;

public class InfoBar {
	float screenWidth = Gdx.graphics.getWidth();
	float screenHeight = Gdx.graphics.getHeight();
	float barHeight;
	float barWidth;
	MainActivity main;
	String leftText="";
	String middleText="";
	String rightText="";
	String[] leftImages = new String[]{"infoBar/empty.png","infoBar/empty.png","infoBar/empty.png"};
	String rightImage="infoBar/empty.png";
	
	/**
	 * Constructor. Creates the the interface and sets the private variables
	 */
	public InfoBar(MainActivity main) {
		this.main = main;
		barWidth = screenWidth;
	 	barHeight = screenHeight/10;
	}
	
	/**
	 * Sets up the infoBar table, adds the corresponding 
	 * elements to it in the right place
	 * @return infoBar table
	 */
	public Table getInfoBar(){
		Table infoBarTable = new Table(main.skin);
		Pixmap pm = new Pixmap(1, 1, Format.RGBA8888);
	 	pm.setColor(new Color(118/255f, 185/255f, 52/255f, 1));
	 	pm.fillRectangle(0,0,1,1);
	 	infoBarTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm))));
	 	
	 	Button leftButton = new Button(main.skin);
	 	Label leftLabel = new Label(leftText, main.skin, main.screenSizeGroup+"-M");
	 	leftLabel.setAlignment(Align.center);
	 	leftButton.stack(getStarTable(), leftLabel).expand().fill();
	 	
	 	Label middleLabel = new Label(middleText, main.skin, main.screenSizeGroup+"-L");
	 	middleLabel.setAlignment(Align.center);
	 	
	 	Button rightButton = new Button(main.skin);
	 	Label rightLabel = new Label(rightText, main.skin, main.screenSizeGroup+"-M");
	 	rightLabel.setAlignment(Align.center);
	 	rightButton.stack(new Image(new Texture(rightImage)), rightLabel).expand().fill();
	 	
	 	infoBarTable.add(leftButton).size(barHeight).padLeft(main.scrWidth/40).expand().left();
	 	infoBarTable.add(middleLabel).expand();
	 	infoBarTable.add(rightButton).size(barHeight).padRight(main.scrWidth/40).expand().right();
	 	
	 	return infoBarTable;
	}

	/**
	 * @param leftText, the text that should be in the left corner of the infoBar
	 */
	public void setLeftText(String leftText) {
		this.leftText = leftText;
	}
	
	/**
	 * @param middleText, the text that should be in the middle of the infoBar
	 */
	public void setMiddleText(String middleText) {
		this.middleText = middleText;
	}
	
	/**
	 * @param rightText, the text that should be in the right corner of the infoBar
	 */
	public void setRightText(String rightText) {
		this.rightText = rightText;
	}
	
	/**
	 * @param leftImages, and array of 3 images that should be in the left corner of the infoBar,
	 * stacked together in a triangle
	 */
	public void setLeftImages(String[] leftImages) {
		this.leftImages = leftImages;
	}
	
	/**
	 * @param rightImage, the image that should be in the right corner of the infoBar
	 */
	public void setRightImage(String rightImage) {
		this.rightImage = rightImage;
	}
	
	/**
	 * @return table with the three images in leftImages stacked together in a triangle
	 */
	public Table getStarTable() {
		Table starTable = new Table();
	 	starTable.add(new Image(new Texture(leftImages[1]))).size(barHeight/2).colspan(2).row();
	 	starTable.add(new Image(new Texture(leftImages[0]))).size(barHeight/2).padTop(-barHeight/6).padRight(barHeight/15);
	 	starTable.add(new Image(new Texture(leftImages[2]))).size(barHeight/2).padTop(-barHeight/6);
	 	
	 	return starTable;
	}
}
