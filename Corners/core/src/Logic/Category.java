/**
 * Name: 	Kristin Fjola Tomasdottir
 * Date: 	09.02.2015
 * Goal: 	A Superclass for all categories of the game
 */
package logic;

import boxes.Box;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Category {
	
	int level;
	Box question;
	Texture questionTexture;
	Array<Box> answers;
	Texture[] answerTextures = new Texture[4];
	int qWidth;
	int qHeight;
	int screenWidth = 480;
    int screenHeight = 800;
	
	public Box checkIfHitAnswer(){
		return null;
	}

	/**
	 * @param
	 * @return	blabla
	 */
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Box getQuestion() {
		return question;
	}

	public void setQuestion(Box question) {
		this.question = question;
	}

	public Texture getQuestionTexture() {
		return questionTexture;
	}

	public void setQuestionTexture(Texture questionTexture) {
		this.questionTexture = questionTexture;
	}

	public Array<Box> getAnswers() {
		return answers;
	}

	public void setAnswers(Array<Box> answers) {
		this.answers = answers;
	}

	public int getqWidth() {
		return qWidth;
	}

	public void setqWidth(int qWidth) {
		this.qWidth = qWidth;
	}

	public int getqHeight() {
		return qHeight;
	}

	public void setqHeight(int qHeight) {
		this.qHeight = qHeight;
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

	public Texture[] getAnswerTextures() {
		return answerTextures;
	}

	public void setAnswerTextures(Texture[] answerTextures) {
		this.answerTextures = answerTextures;
	}
	
	
}
