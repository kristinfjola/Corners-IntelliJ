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
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.corners.game.MainActivity;

public class Play implements Screen, InputProcessor{

	MainActivity main;
	Category cat;
	SpriteBatch batch;
	OrthographicCamera camera;
	private Stage stage;
	private State state;
    boolean hit = false;
    int screenWidth = Gdx.graphics.getWidth();
    int screenHeight = Gdx.graphics.getHeight();
    int level;
    int questionsAnswered = 0;
    int nrOfQuestions;
    float origX;
    float origY;
    InfoBar infoBar;
    Table table;
    InputProcessor inputProcessor;

    // swipe
    Vector3 touchPos;
    boolean swipeQuestion = false;
    boolean hitWrong = false;
    boolean touchUp = false;
    float sum = 0;
    String xDirection = "none";
    String yDirection = "none";
    boolean lockPos = false;

    // time
    long startTime = 0;	
    long secondsPassed;
    long totalSecondsWasted = 0;
    ProgressBar progressBar;
    ProgressBarStyle progressBarStyle;
    BitmapFont time;
    long maxTime;
    boolean delayTime = false;
    int stars = 3;
    
    // levels
    int maxNumLevels = 9;
	
	/**
	 * @param main - main activity of the game
	 * @param cat - what category is being played
	 */
	public Play(MainActivity main, Category cat, int level){
		this.main = main;
		infoBar = new InfoBar(main);
		this.cat = cat;
		this.level = level;
		this.state = State.RUN;
		stage = new Stage();
		addPauseToProcessor();
		setAllProcessors(); 
		
		table = new Table();
		table.top();
		table.setFillParent(true);
		
		//Setting up the info bar
		double tempStars = 1;
		stage.addActor(table);
		//infoBar.setLeftText(tempStars+"/3");
		infoBar.setMiddleText("Level "+level);
		infoBar.setRightText("");
		infoBar.setLeftImage(infoBar.getStarAmount(tempStars)+"stars");
		infoBar.setRightImage("pause");
	 	table.add(infoBar.getInfoBar()).size(screenWidth, screenHeight/10).fill().row();

		origX = screenWidth/2 - cat.getQuestion().getRec().getWidth()/2;
	    origY = screenHeight/2 - cat.getQuestion().getRec().getHeight()/2;
 	    Gdx.input.setCatchBackKey(true);
	    time = cat.getBmFont();
	    time.setColor(Color.BLACK);
	    maxTime = 10;
	    nrOfQuestions = 9;
		camera = new OrthographicCamera();
 	    camera.setToOrtho(false, screenWidth, screenHeight);
 	    batch = new SpriteBatch();
 	    createProgressBar();
 	    startQuestion();
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
	
	public void win(){
		questionsAnswered = 0;
		totalSecondsWasted = 0;
		setCorrectProgressBar();
		
		if(level < maxNumLevels) { 
			animateFinishLevel(true, false);
			level++;
		} else {
			animateFinishLevel(true, true);
		}
		
		saveStars();
	}
	
	public void moveQuestionToStartPos(){
		cat.getQuestion().getRec().x = origX;
		cat.getQuestion().getRec().y = origY;
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
		setWrongProgressBar();
		refreshProgressBar(true);
		delayTime = true;
		animateFinishLevel(false, false);
	}
	
	public void resetTime(){
		progressBar.setValue(0);
		startTime = System.nanoTime();
	}
	
	public void startQuestion(){
		moveQuestionToStartPos();
		cat.generateNewQuestion(level);
		resetTime();
	}
	
	public void getNewQuestion(){
		startQuestion();
		setNormalProgressBar();
		refreshProgressBar(false);	
	}
	
	public void drawProgressBar(){
		long endTime = System.nanoTime();
		if(!delayTime){
			secondsPassed = (endTime - startTime)/1000000000;  
		}
		progressBar.setValue(secondsPassed);
		long timeLeft = (maxTime - secondsPassed) >= 0 ? (maxTime - secondsPassed) : 0;
		time.draw(batch, Long.toString(timeLeft), progressBar.getWidth(), screenHeight/5 - screenHeight/100);
	}
	
	public void displayRightAnswerAndGetNewQuestion(){
		setCorrectProgressBar();
		refreshProgressBar(true);	
		delayTime = true;
		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		    	getNewQuestion();
		    	delayTime = false;
		    }
		}, 1);
		updateStars();
	}
	
	public void createProgressBar(){
		main.skin.add("bg", new Texture(Gdx.files.internal("progressBar/background.png")));
		main.skin.add("bg_wrong", new Texture(Gdx.files.internal("progressBar/background_wrong.png")));
		main.skin.add("bg_correct", new Texture(Gdx.files.internal("progressBar/background_correct.png")));
 	    main.skin.add("knob", new Texture(Gdx.files.internal("progressBar/knob.png")));
 	    main.skin.add("knob_wrong", new Texture(Gdx.files.internal("progressBar/knob_wrong.png")));
 	    main.skin.add("knob_correct", new Texture(Gdx.files.internal("progressBar/knob_correct.png")));
 	    progressBarStyle = new ProgressBarStyle(main.skin.getDrawable("bg"), main.skin.getDrawable("knob"));
 	    progressBarStyle.knobBefore = progressBarStyle.knob;
 	    progressBar = new ProgressBar(0, maxTime, 0.5f, false, progressBarStyle);
	    progressBar.setPosition(screenWidth/4, screenHeight/5);
	    progressBar.setSize(screenWidth/2, progressBar.getPrefHeight());
	    progressBar.setAnimateDuration(1);
	    stage.addActor(progressBar);
	}
	
	public void animateFinishLevel(boolean win, boolean finishCat) {
		pause();
		
		Dialog dialog = new Dialog("", this.main.skin);
		if(win) dialog.getContentTable().row();
		else dialog.text("Oh no!");
		dialog.getContentTable().row();
		if(win && !finishCat) dialog.text("Level complete!");
		else if(finishCat) dialog.text(cat.getType()+" complete!");
		else dialog.text("You lost!");
		dialog.getContentTable().row();
		
		if(win) {
			Table starsTable = getStars(stars);
			dialog.getContentTable().add(starsTable);
			if(finishCat) {
				dialog.getContentTable().row();
				dialog.text("Try to get more stars in each level!");
			}
		} else {
			Table sadFaceTable = getSadFace();
			dialog.getContentTable().add(sadFaceTable);
		}
		
		dialog.show(this.stage);
		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		        // Do your work
		    	main.setScreen(new Levels(main, cat));
		    }
		}, 3);
		
	}
	
	public Table getStars(int numStars) {
		int maxStars = 3;
		int loseStars = maxStars-numStars;
		
		Table starsTable = new Table();
		
		Texture yellow_star = new Texture("stars/star_yellow.png");
		Texture gray_star = new Texture("stars/star_gray.png");
		
		for(int i = 1; i <=numStars; i++) {
			Image win_star = new Image();
			win_star.setDrawable(new TextureRegionDrawable(new TextureRegion(yellow_star)));
			starsTable.add(win_star).padTop(30).padBottom(30);
		}
		
		for(int i = 1; i <= loseStars; i++) {
			Image lose_star = new Image();
			lose_star.setDrawable(new TextureRegionDrawable(new TextureRegion(gray_star)));
			starsTable.add(lose_star).padTop(30).padBottom(30);
		}
		
		return starsTable;
	}
	
	public Table getSadFace() {
		Table sadFaceTable = new Table();
		
		Texture sad_face = new Texture("faces/sad_face.png");
		
		Image sad = new Image();
		sad.setDrawable(new TextureRegionDrawable(new TextureRegion(sad_face)));
		sadFaceTable.add(sad).padTop(50);
		
		return sadFaceTable;
	}
	
	public void moveQuestionOverAnswer() {
		if(xDirection == "left" ) {
			cat.getQuestion().getRec().x = 0;
		}
	}
		
	public void refreshProgressBar(boolean delay){
		progressBar.remove();
		progressBar = new ProgressBar(0, maxTime, 0.5f, false, progressBarStyle);
	    progressBar.setPosition(screenWidth/4, screenHeight/5);
	    progressBar.setSize(screenWidth/2, progressBar.getPrefHeight());
	    progressBar.setAnimateDuration(delay ? 0 : 1);
	    progressBar.setValue(secondsPassed);
	    stage.addActor(progressBar);
	}
	
	public void setNormalProgressBar(){
		progressBarStyle.background = main.skin.getDrawable("bg");
		progressBarStyle.knob = main.skin.getDrawable("knob");
		progressBarStyle.knobBefore = progressBarStyle.knob;
	}
	
	public void setCorrectProgressBar(){
		progressBarStyle.background = main.skin.getDrawable("bg_correct");
		progressBarStyle.knob = main.skin.getDrawable("knob_correct");
		progressBarStyle.knobBefore = progressBarStyle.knob;
	}
	
	public void setWrongProgressBar(){
		progressBarStyle.background = main.skin.getDrawable("bg_wrong");
		progressBarStyle.knob = main.skin.getDrawable("knob_wrong");
		progressBarStyle.knobBefore = progressBarStyle.knob;
	}
	
	public void saveStars(){
		// TODO: save staramount for levels
	}
	
	public void updateStars(){
		totalSecondsWasted += secondsPassed;
		
		int threeStars = 20;
		int twoStars = 45;
		int oneStar = 80;
		
		if(totalSecondsWasted >= threeStars && stars == 3){
			stars = 2;
		} else if(totalSecondsWasted >= twoStars && stars == 2){
			stars = 1;
		} else if(totalSecondsWasted >= oneStar && stars == 1){
			stars = 0;
		}
		
		System.out.println("total secs wasted: " + totalSecondsWasted);
		System.out.println("stars: " + stars);
	}
	
	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		refreshProgressBar(true);
		delayTime = true;
		this.state = State.PAUSE;
	}

	@Override
	public void resume() {
		delayTime = false;
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
				if (!delayTime){
					moveQuestionToStartPos();
				}
			}
		}
		
		// hit answer
		Box hit = cat.checkIfHitAnswer();
		if(hit != null && !delayTime){
			questionsAnswered++;
			if(questionsAnswered >= nrOfQuestions){
				win();
			} else {
				displayRightAnswerAndGetNewQuestion();
			}
		}
		
		//if hit wrong answer then move question to the middle
		//TODO: we need to make our own overlaps function so this looks good:
		Box hitBox = cat.checkIfHitBox();
		if(hitBox != null && hit == null && !delayTime) {
			hitWrong = true;
			loose();
		}
		
		// check time
		if(secondsPassed > progressBar.getMaxValue()){
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
	
	private void addPauseToProcessor() {
		 inputProcessor = new InputProcessor() {	
			 @Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(screenX>screenWidth-screenHeight/10 && screenY<screenHeight/10) {
					table.reset();
					table.top();
					table.setFillParent(true);
					if(state == State.RUN) {
						pause();
						infoBar.setRightImage("play");
					}
					else {
						resume();
						infoBar.setRightImage("pause");
					}
					table.add(infoBar.getInfoBar()).size(screenWidth, screenHeight/10).fill().row();
				}
				return false;
			}
			
			@Override
			public boolean scrolled(int amount) { return false; }
			
			@Override
			public boolean mouseMoved(int screenX, int screenY) { return false; }
			
			@Override
			public boolean keyUp(int keycode) { return false; }
			
			@Override
			public boolean keyTyped(char character) { return false; }
			
			@Override
			public boolean keyDown(int keycode) { return false; }
		};
	}
	
	private void setAllProcessors() {
		Gdx.input.setCatchBackKey(true);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(inputProcessor);
		Gdx.input.setInputProcessor(multiplexer); 	
	}
}
