/**
 * @author 	Johanna Agnes Magnusdottir
 * @date 	30.03.2015
 * @goal 	Holds on to information regarding the user's character (image and 
 * 			amount of level needed to "grow up"
 */

package screens;

import com.badlogic.gdx.graphics.Texture;
import com.corners.game.MainActivity;

public class Character {
	
	/*
	 * levelsSplit = {6, 13, 20, 27}
	 * character 0: levels 0-5
	 * character 1: levels 6-12
	 * character 2: levels 13-19
	 * character 3: levels 20-26
	 * character 4: levels 27
	 */
	
	private MainActivity main;
	private Texture[] imgs;
	private int totalLevels;
	private int[] levelsSplit;
	
	/**
	 * Constructor. Sets the private variables
	 */
	public Character(MainActivity main) {
		this.main = main;
		
		totalLevels = 27;
		Float[] percentage = new Float[]{0.25f, 0.5f, 0.75f, 1f};
		setUpLevelsSplit(percentage);
		
		imgs = new Texture[levelsSplit.length+1];
		for(int i=0; i<imgs.length; i++) {
			imgs[i] = new Texture("carl/piggy"+i+".png");
		}
	}
	
	/**
	 * sets the private variable levelSplit based on the total amount of levels
	 * the game offers
	 * @param percentage hold the amount of percentage of how many levels need 
	 * to be finished in order to "grow up"
	 */
	public void setUpLevelsSplit(Float[] percentage) {
		levelsSplit = new int[percentage.length];
		for(int i=0; i<percentage.length; i++) {
			levelsSplit[i] = (int)Math.floor(percentage[i]*totalLevels);
		}
	}
	
	/**
	 * @return texture image of the user's current character
	 */
	public Texture getCharacterImg() {
		return imgs[getCharacterNumber()];
	}
	
	/**
	 * @return the number of the user's current character (0-4)
	 */
	public int getCharacterNumber() {
		int levelsFinished = getLevelsFinished();
		
		for(int i=0; i<levelsSplit.length; i++) {
			if(levelsFinished < levelsSplit[i]) return i;
		}
		return levelsSplit.length;
	}
	
	/**
	 * @return the total amount of levels finished
	 */
	public int getLevelsFinished() {
		int levelsFinished = main.data.getAllFinished();
		return levelsFinished;
	}

	/**
	 * @return the amount of levels needed to finish in order to "grow up" and 
	 * get the next character available
	 */
	public int getNrOfLevelsToNext() {
		int characterNumber = getCharacterNumber();
		if(characterNumber == levelsSplit.length) return 0;
		int levelsToNext = levelsSplit[characterNumber];
		return levelsToNext-getLevelsFinished();
	}
	
	/**
	 * @return if the character just grew (the amount of levels finished
	 * is in the levelsSplit. Else returns false
	 */
	public boolean characterGrew() {
		int levels = getLevelsFinished();
		for(int i=0; i<levelsSplit.length-1; i++) {
			if(levels==levelsSplit[i]) return true;
		}
		return false;
	}
}
