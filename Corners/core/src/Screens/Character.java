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
	
	MainActivity main;
	Texture[] imgs;
	int totalLevels;
	int[] levelsSplit;
	
	public Character(MainActivity main) {
		this.main = main;
		
		totalLevels = 27;
		setUpLevelsSplit(new Float[]{0.15f, 0.3f, 0.5f, 0.75f, 1f});
		
		imgs = new Texture[levelsSplit.length];
		for(int i=0; i<levelsSplit.length; i++) {
			imgs[i] = new Texture("carl/carl"+i+".jpg");
		}
	}
	
	public void setUpLevelsSplit(Float[] percentage) {
		levelsSplit = new int[percentage.length];
		for(int i=0; i<percentage.length; i++) {
			levelsSplit[i] = (int)Math.floor(percentage[i]*totalLevels);
		}
	}
	
	public Texture getCharacterImg() {
		return imgs[getCharacterNumber()];
	}
	
	public int getCharacterNumber() {
		int levelsFinished = getLevelsFinished();
		
		for(int i=0; i<levelsSplit.length; i++) {
			if(levelsFinished < levelsSplit[i]) return i;
		}
		return 4;
	}
	
	public int getLevelsFinished() {
		int levelsFinished = main.data.getAllFinished();
		return levelsFinished;
	}

	public int getNrOfLevelsToNext() {
		int characterNumber = getCharacterNumber();
		
		if(characterNumber == levelsSplit.length-1) return 0;
		int levelsToNext = levelsSplit[characterNumber];
		return levelsToNext-getLevelsFinished();
	}
}
