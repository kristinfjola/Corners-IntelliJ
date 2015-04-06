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
	
	/**
	 * character 0: levels 0-3
	 * character 1: levels 4-7
	 * character 2: levels 8-12
	 * character 3: levels 13-19
	 * character 4: levels 20-27
	 */
	
	private MainActivity main;
	private Texture[] imgs;
	private int totalLevels;
	private int[] levelsSplit;
	
	/**
	 * Constructor. Creates the the interface and sets the private variables
	 */
	public Character(MainActivity main) {
		this.main = main;
		
		totalLevels = 27;
		setUpLevelsSplit(new Float[]{0.15f, 0.3f, 0.5f, 0.75f, 1f});
		
		imgs = new Texture[levelsSplit.length];
		for(int i=0; i<levelsSplit.length; i++) {
			imgs[i] = new Texture("carl/piggy"+i+".png");
		}
	}
	
	/**
	 * sets the private variable levelSplit based on the total amount of levels
	 * the game offers
	 * levelsSplit = {4,8,13,20,27}
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
		return 4;
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
		
		if(characterNumber == levelsSplit.length-1) return 0;
		int levelsToNext = levelsSplit[characterNumber];
		return levelsToNext-getLevelsFinished();
	}
	
	public boolean characterGrew() {
		int levels = getLevelsFinished();
		for(int i=0; i<levelsSplit.length-1; i++) {
			if(levels==levelsSplit[i]) return true;
		}
		return false;
	}
}
