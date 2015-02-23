/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	The display of each level which includes a question in the middle
 * 			of the screen and 4 answers located at each corner of the screen
 */
package screens;

import logic.Category;
import boxes.Box;
import boxes.MathBox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.corners.game.MainActivity;

public class Play implements Screen, InputProcessor{

	MainActivity main;
	Category cat;
	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touchPos;
    boolean hit = false;
    int screenWidth = 480;
    int screenHeight = 800;
    int qSize = 100;
    int level;
    boolean lockPos = false;
    int questionsAnswered = 0;
    boolean touchUp = false;
    float sum = 0;
    String xDirection = "none";
    String yDirection = "none";
    float origX = screenWidth / 2 - qSize / 2;
    float origY = screenHeight / 2 - qSize / 2;
    boolean swipeQuestion = false;

    long startTime = 0;	
    ProgressBar progressBar;
    private Stage stage;
	
	/**
	 * @param main - main activity of the game
	 * @param cat - what category is being played
	 */
	public Play(MainActivity main, Category cat, int level){
		this.main = main;
		this.cat = cat;
		this.level = level;
		stage = new Stage();
		Gdx.input.setInputProcessor(this);
		
		camera = new OrthographicCamera();
 	    camera.setToOrtho(false, screenWidth, screenHeight);
 	    batch = new SpriteBatch();
 	    createProgressBar();
 	    getNewQuestion();
	}

	/**
	 * Renders a question in the middle of the screen and 4 answers located
	 * at each corner of the screen. Handles when user touches the screen
	 * and drags/swipes the question.
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(21/255f, 149/255f, 136/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		// progress bar
		long endTime = System.nanoTime();
	    long secondsPassed = (endTime - startTime)/1000000000;  
		progressBar.setValue(secondsPassed);

		// draw question and answers
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Box answer : cat.getAnswers()){
			answer.draw(batch);
		}
		cat.getQuestion().draw(batch);	
		batch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
		// swipe question
		if(Gdx.input.isTouched()) {
			touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			if(lockPos || touchOverlapsQuestion(touchPos)){
				if(lockPos) {
					camera.unproject(touchPos);
				}
				
				if(cat.getQuestion().getRec().x - origX < 30 && 
						cat.getQuestion().getRec().x - origX > -30) {
					xDirection = "none";
				} else {
					if(cat.getQuestion().getRec().x > origX) {
						xDirection = "right";
					} else {
						xDirection = "left";
					}
				}
				if(cat.getQuestion().getRec().y - origY < 30 &&
						cat.getQuestion().getRec().y - origY > -30) {
					yDirection = "none";
				} else {
					if(cat.getQuestion().getRec().y > origY) {
						yDirection = "up";
					} else {
						yDirection = "down";
					}
				}
				
				cat.getQuestion().getRec().x = touchPos.x - qSize / 2;
				cat.getQuestion().getRec().y = touchPos.y - qSize / 2;
				lockPos = true;
				swipeQuestion = true;
			}
		}
		
		// swipe question smoothly after touchUp
		if(touchUp && swipeQuestion) {
			if(xDirection != "none") {
				if(xDirection == "right") {
					cat.getQuestion().getRec().x += 6;
					if(cat.getQuestion().getRec().x > screenWidth-qSize) {
						cat.getQuestion().getRec().x = screenWidth-qSize;
					}
				} else {
					cat.getQuestion().getRec().x -= 6;
					if(cat.getQuestion().getRec().x < 0) {
						cat.getQuestion().getRec().x = 0;
					}
				}
			}
			if(yDirection != "none") {
				if(yDirection == "up") {
					cat.getQuestion().getRec().y += 12;
					if(cat.getQuestion().getRec().y > screenHeight-qSize) {
						cat.getQuestion().getRec().y = screenHeight-qSize;
					}
				} else {
					cat.getQuestion().getRec().y -= 12;
					if(cat.getQuestion().getRec().y < 0) {
						cat.getQuestion().getRec().y = 0;
					}
				}
			}
			
			sum += 3;
			if(sum > 50) {
				touchUp = false;
				swipeQuestion = false;
				sum = 0;
				Box hitBox = cat.checkIfHitBox();
				if(hitBox == null) {
					cat.getQuestion().getRec().x = screenWidth / 2 - qSize / 2;
					cat.getQuestion().getRec().y = screenHeight / 2 - qSize / 2;
				}
			}
		}
		
		// hit answer
		Box hit = cat.checkIfHitAnswer();
		if(hit != null){
			questionsAnswered++;
			if(questionsAnswered >= 7){
				questionsAnswered = 0;
				level++;
			}
			getNewQuestion();
		}
		
		// check time
		if(secondsPassed > progressBar.getMaxValue()){
			loose();
		}
	}
	
	public boolean touchOverlapsQuestion(Vector3 touchPos){
		camera.unproject(touchPos);
		if(touchPos.x >= cat.getQuestion().getRec().x 
				&& touchPos.x <= cat.getQuestion().getRec().x + cat.getQuestion().getRec().getWidth()
				&& touchPos.y >= cat.getQuestion().getRec().y
				&& touchPos.y <= cat.getQuestion().getRec().y + cat.getQuestion().getRec().getHeight()){
			return true;
		}
		return false;
	}
	
	public void loose(){
		main.setScreen(new Levels(main, cat));
	}
	
	public void resetTime(){
		progressBar.setValue(0);
		startTime = System.nanoTime();
	}
	
	public void getNewQuestion(){
		cat.generateNewQuestion(level);
		resetTime();
	}
	
	public void resetProgressBar(){
		progressBar.remove();
		createProgressBar();
	}
	
	public void createProgressBar(){
		main.skin.add("bg", new Texture(Gdx.files.internal("progressBar/background.png")));
 	    main.skin.add("knob", new Texture(Gdx.files.internal("progressBar/knob.png")));
 	    ProgressBarStyle barStyle = new ProgressBarStyle(main.skin.getDrawable("bg"), main.skin.getDrawable("knob"));
 	    barStyle.knobBefore = barStyle.knob;
 	    progressBar = new ProgressBar(0, 10, 0.5f, false, barStyle);
	    progressBar.setPosition(300, 300);
	    progressBar.setSize(500, progressBar.getPrefHeight());
	    progressBar.setAnimateDuration(1);
	    stage.addActor(progressBar);
	}
	
	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/**
	 * If user let's go of the question box it returns to the middle
	 * of the screen.
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		lockPos = false;
		touchUp = true;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
