/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	A Superclass for all categories of the game
 */
package logic;

import boxes.Box;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Category {
	
	Box question;
	Texture questionTexture;
	Array<Box> answers;
	Texture[] answerTextures = new Texture[4];
	int qWidth;
	int qHeight;
	int screenWidth = 480;
    int screenHeight = 800;
	
    /**
	 * @param
	 * @return	true if question box collapses with answer box
	 */
    public Box checkIfHitAnswer(){
		return null;
	}

	/**
	 * @return current question
	 */
	public Box getQuestion() {
		return question;
	}

	/**
	 * @param question
	 */
	public void setQuestion(Box question) {
		this.question = question;
	}

	/**
	 * @return texture on current question
	 */
	public Texture getQuestionTexture() {
		return questionTexture;
	}

	/**
	 * @param questionTexture
	 */
	public void setQuestionTexture(Texture questionTexture) {
		this.questionTexture = questionTexture;
	}

	/**
	 * @return current answers
	 */
	public Array<Box> getAnswers() {
		return answers;
	}

	/**
	 * @param answers
	 */
	public void setAnswers(Array<Box> answers) {
		this.answers = answers;
	}

	/**
	 * @return width of current question
	 */
	public int getqWidth() {
		return qWidth;
	}

	/**
	 * @param qWidth
	 */
	public void setqWidth(int qWidth) {
		this.qWidth = qWidth;
	}

	/**
	 * @return hight of current question
	 */
	public int getqHeight() {
		return qHeight;
	}

	/**
	 * @param qHeight
	 */
	public void setqHeight(int qHeight) {
		this.qHeight = qHeight;
	}

	/**
	 * @return width of screen
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @param screenWidth
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	/**
	 * @return height of screen
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * @param screenHeight
	 */
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	/**
	 * @return texture for current answers
	 */
	public Texture[] getAnswerTextures() {
		return answerTextures;
	}

	/**
	 * @param answerTextures
	 */
	public void setAnswerTextures(Texture[] answerTextures) {
		this.answerTextures = answerTextures;
	}
	
	
}
