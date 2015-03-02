/**
 * @author: Kristin Fjola Tomasdottir
 * @date	05.02.2015
 * @goal: 	The display of each level which includes a question in the middle
 * 			of the screen and 4 answers located at each corner of the screen
 */
package screens;

import logic.Category;
import boxes.Box;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.corners.game.MainActivity;

public class Play implements Screen, InputProcessor{

	MainActivity main;
	Category cat;
	SpriteBatch batch;
	OrthographicCamera camera;
	Vector3 touchPos;
    boolean hit = false;
    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();
    int level;
    boolean lockPos = false;
    int questionsAnswered = 0;
    boolean touchUp = false;
    float sum = 0;
    String xDirection = "none";
    String yDirection = "none";
    float origX;
    float origY;
    boolean swipeQuestion = false;
    boolean hitWrong = false;

    long startTime = 0;	
    long secondsPassed;
    ProgressBar progressBar;
    BitmapFont time;
    private Stage stage;
    private State state;
	
	/**
	 * @param main - main activity of the game
	 * @param cat - what category is being played
	 */
	public Play(MainActivity main, Category cat, int level){
		this.main = main;
		this.cat = cat;
		this.level = level;
		this.state = State.RUN;
		stage = new Stage();
		Gdx.input.setInputProcessor(this);
		origX = screenWidth/2 - cat.getQuestion().getRec().getWidth()/2;
	    origY = screenHeight/2 - cat.getQuestion().getRec().getHeight()/2;
 	    Gdx.input.setCatchBackKey(true);
	    time = cat.getBmFont();
	    time.setColor(Color.BLACK);
		camera = new OrthographicCamera();
 	    camera.setToOrtho(false, screenWidth, screenHeight);
 	    batch = new SpriteBatch();
 	    createProgressBar();
 	    getNewQuestion();
	}
	
	public enum State {
		PAUSE,
		RUN
	}

	/**
	 * Renders a question in the middle of the screen and 4 answers located
	 * at each corner of the screen. Handles when user touches the screen
	 * and drags/swipes the question.
	 */
	@Override
	public void render(float delta) {
		switch (state) {
		case RUN:
			update();
			break;
		case PAUSE:
			break;
		}
		
		draw();
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
	
	public void drawProgressBar(){
		long endTime = System.nanoTime();
	    secondsPassed = (endTime - startTime)/1000000000;  
		progressBar.setValue(secondsPassed);
		time.draw(batch, secondsPassed+"", progressBar.getWidth(), screenHeight/5 - screenHeight/100);
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
	    progressBar.setPosition(screenWidth/4, screenHeight/5);
	    progressBar.setSize(screenWidth/2, progressBar.getPrefHeight());
	    progressBar.setAnimateDuration(1);
	    stage.addActor(progressBar);
	}
	
	public void animateFinishLevel() {
		pause();
		
		Dialog dialog = new Dialog("Level completed!", this.main.skin);
		dialog.getContentTable().row();
		dialog.getContentTable().row();
		dialog.text("Congratulations!"); 
		dialog.show(this.stage);
		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		        // Do your work
		    	main.setScreen(new Levels(main, cat));
		    }
		}, 2);
		
	}
	
	public void moveQuestionOverAnswer() {
		if(xDirection == "left" ) {
			cat.getQuestion().getRec().x = 0;
		}
		
	}
	
	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		this.state = State.PAUSE;
	}

	@Override
	public void resume() {
		this.state = State.RUN;
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
		if(keycode == Keys.BACK){
			main.setScreen(new Levels(main, cat));
        }
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
	
	public void update() {
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
				
				cat.getQuestion().getRec().x = touchPos.x - cat.getQuestion().getRec().getWidth() / 2;
				cat.getQuestion().getRec().y = touchPos.y - cat.getQuestion().getRec().getHeight() / 2;
				lockPos = true;
				swipeQuestion = true;
				hitWrong = false;
			}
		}
		
		// swipe question smoothly after touchUp
		if(touchUp && swipeQuestion && !hitWrong) {
			if(xDirection != "none") {
				if(xDirection == "right") {
					cat.getQuestion().getRec().x += 6;
					if(cat.getQuestion().getRec().x > screenWidth-cat.getQuestion().getRec().getWidth()) {
						cat.getQuestion().getRec().x = screenWidth-cat.getQuestion().getRec().getWidth();
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
					if(cat.getQuestion().getRec().y > screenHeight-cat.getQuestion().getRec().getHeight()) {
						cat.getQuestion().getRec().y = screenHeight-cat.getQuestion().getRec().getHeight();
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
				cat.getQuestion().getRec().x = screenWidth / 2 - cat.getQuestion().getRec().getWidth() / 2;
				cat.getQuestion().getRec().y = screenHeight / 2 - cat.getQuestion().getRec().getHeight() / 2;
			}
		}
		
		// hit answer
		Box hit = cat.checkIfHitAnswer();
		if(hit != null){
			moveQuestionOverAnswer();
			questionsAnswered++;
			if(questionsAnswered >= 7){
				questionsAnswered = 0;
				level++;
				animateFinishLevel();
			}
			getNewQuestion();
		}
		
		//if hit wrong answer then move question to the middle
		//we need to make our own overlaps function so this looks good:
		Box hitBox = cat.checkIfHitBox();
		if(hitBox != null && hit == null) {
			hitWrong = true;
			cat.getQuestion().getRec().x = screenWidth / 2 - cat.getQuestion().getRec().getWidth() / 2;
			cat.getQuestion().getRec().y = screenHeight / 2 - cat.getQuestion().getRec().getHeight() / 2;
		}
		
		// check time
		if(secondsPassed > progressBar.getMaxValue()){
			cat.getQuestion().getRec().x = screenWidth / 2 - cat.getQuestion().getRec().getWidth() / 2;
			cat.getQuestion().getRec().y = screenHeight / 2 - cat.getQuestion().getRec().getHeight() / 2;
			loose();
		}
	}
	
	public void draw() {
		Gdx.gl.glClearColor(21/255f, 149/255f, 136/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		// draw question and answers
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Box answer : cat.getAnswers()){
			answer.draw(batch);
		}
		cat.getQuestion().draw(batch);	
		drawProgressBar();
		batch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}
}
