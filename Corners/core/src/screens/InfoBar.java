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
	String leftImage="empty";
	String rightImage="empty";
	
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
	 	//pm.setColor(new Color(20/255f, 120/255f, 113/255f, 1));
	 	pm.setColor(new Color(118/255f, 185/255f, 52/255f, 1));
	 	pm.fillRectangle(0,0,1,1);
	 	infoBarTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm))));
	 	
	 	Button leftButton = new Button(main.skin);
	 	Label leftLabel = new Label(leftText, main.skin, main.screenSizeGroup+"-M");
	 	leftLabel.setAlignment(Align.center);
	 	leftButton.stack(new Image(new Texture("infoBar/"+leftImage+".png")), leftLabel).expand().fill();
	 	
	 	Label middleLabel = new Label(middleText, main.skin, main.screenSizeGroup+"-L");
	 	middleLabel.setAlignment(Align.center);
	 	
	 	Button rightButton = new Button(main.skin);
	 	Label rightLabel = new Label(rightText, main.skin, main.screenSizeGroup+"-M");
	 	rightLabel.setAlignment(Align.center);
	 	rightButton.stack(new Image(new Texture("infoBar/"+rightImage+".png")), rightLabel).expand().fill();
	 	
	 	infoBarTable.add(leftButton).size(barHeight).expand().left();
	 	infoBarTable.add(middleLabel).expand();
	 	infoBarTable.add(rightButton).size(barHeight).expand().right();
	 	
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
	 * @param leftImage, the image that should be in the left corner of the infoBar
	 */
	public void setLeftImage(String leftImage) {
		this.leftImage = leftImage;
	}
	
	/**
	 * @param rightImage, the image that should be in the right corner of the infoBar
	 */
	public void setRightImage(String rightImage) {
		this.rightImage = rightImage;
	}
	
	/**
	 * Rounds stars to the nearest quarter of an integer and puts together a string
	 * @param stars
	 * @return string that refers to the correct image based on the number stars
	 */
	public String getStarAmount(double stars) {
		String starAmount="";
		
		double starsX4 = stars*4;
		double roundedStarsX4 = Math.round(starsX4); 
		double roundedStars = roundedStarsX4/4;
		int wholeStars = (int) Math.floor(roundedStars);
		
		starAmount+=""+wholeStars;
		
		if(roundedStars-wholeStars==0.25) starAmount+="_25";
		else if(roundedStars-wholeStars==0.5) starAmount+="_5";
		else if(roundedStars-wholeStars==0.75) starAmount+="_75";
		
		starAmount+="-stars";
		
		return starAmount;
	}
}
