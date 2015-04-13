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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.corners.game.MainActivity;

public class Play implements Screen, InputProcessor{

	private MainActivity main;
	private Category cat;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Stage stage;
	private State state;
    private int level;
    private boolean timeOver;
    public Dialog pauseDialog;
    private InputProcessor inputProcessor;
    private int questionsAnswered;
    private int nrOfQuestions;
    private float origX;
    private float origY;
    
    // end dialog
    private int questionStatusDisplay;
    private int thisLevelOldStars;
    
    // infoBar
    private InfoBar infoBar;
    private Table table;
    
    //progressBar
    private float progressBarHeight;

    // swipe
    private Vector3 touchPos;
    private boolean swipeQuestion;
    private boolean hitBox;
    private boolean touchUp;
    private float sum;
    private String xDirection;
    private String yDirection;
    private boolean lockPos;

    // time
    private long startTime;	
    private long secondsPassed;
    private long totalSecondsWasted;
    private long oldSecondsPassed;
    private ProgressBar progressBar;
    private ProgressBarStyle progressBarStyle;
    private BitmapFont time;
    private long maxTime;
    private boolean delayTime;
    private int stars;
    
    // levels
    private int maxNumLevels;
	
	/**
	 * @param main - main activity of the game
	 * @param cat - what category is being played
	 */
	public Play(MainActivity main, Category cat, int level){
		this.main = main;
		infoBar = new InfoBar(main);
		this.cat = cat;
		this.level = level;
		camera = new OrthographicCamera();
 	    camera.setToOrtho(false, main.scrWidth, main.scrHeight);
 	    batch = new SpriteBatch();
		state = State.RUN;
		stage = new Stage();
		table = new Table();
		table.top().setFillParent(true);
		
		timeOver = false;
		questionsAnswered = 0;
		nrOfQuestions = 9;
		origX = main.scrWidth/2 - cat.getQuestion().getRec().getWidth()/2;
	    origY = main.scrHeight/2 - cat.getQuestion().getRec().getHeight()/2;
		
		questionStatusDisplay = 0;
	    thisLevelOldStars = 0;
	    
	    progressBarHeight = (new Image(new Texture("progressBar/background.png"))).getPrefHeight();
	    
	    swipeQuestion = false;
	    hitBox = false;
	    touchUp = false;
	    sum = 0;
	    xDirection = "none";
	    yDirection = "none";
	    lockPos = false;
	    
	    startTime = 0;	
	    totalSecondsWasted = 0;
	    oldSecondsPassed = 0;
	    time = main.skin.getFont(main.screenSizeGroup+"-M");
	    time.setColor(Color.BLACK);
	    maxTime = cat.getTimeForLevel(level);
	    delayTime = false;
	    stars = 3;
	    
	    maxNumLevels = 9;
		
		addPauseToProcessor();
		setAllProcessors(); 
		
		setUpInfoBar();

 	    Gdx.input.setCatchBackKey(true);

 	    createProgressBar();
 	    startQuestion();
	}
	
	/**
	 * @author eddabjorkkonradsdottir
	 * 
	 * handles the state of the game - pause/run
	 */
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
	
	/**
	 * Resets the level and notifies user that he has won
	 */
	public void win(){
		thisLevelOldStars = main.data.getStarsByString(cat.getType()).getStarsOfALevel(level);
		saveStars(stars);
		questionsAnswered = 0;
		totalSecondsWasted = 0;
		setCorrectProgressBar();
		
		if(level < maxNumLevels) { 
			showFinishLevelDialog(true, false);
			level++;
			main.levelFinishedSound.play(main.volume);
		} else {
			showFinishLevelDialog(true, true);
			main.categoryFinishedSound.play(main.volume);
		}
	}
	
	/**
	 * Moves question to center
	 */
	public void moveQuestionToStartPos(){
		cat.getQuestion().getRec().x = origX;
		cat.getQuestion().getRec().y = origY;
	}
	
	/**
	 * @param touchPos
	 * @return true if you touch the question
	 */
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
	
	/**
	 * notifies user when he loses a level
	 */
	public void lose(){
		timeOver = (maxTime - secondsPassed) < 0 ? true : false;
		setWrongProgressBar();
		refreshProgressBar(true);
		delayTime = true;
		showFinishLevelDialog(false, false);
		main.wrongAnswerSound.play(main.volume);
	}
	
	/**
	 * resets time on progress bar
	 */
	public void resetTime(){
		progressBar.setValue(0);
		startTime = System.nanoTime();
	}
	
	/**
	 * sets first question
	 */
	public void startQuestion(){
		moveQuestionToStartPos();
		cat.generateNewQuestion(level);
		resetTime();
		questionStatusDisplay++;
	}
	
	/**
	 * sets new question
	 */
	public void getNewQuestion(){
		startQuestion();
		setNormalProgressBar();
		refreshProgressBar(false);	
		updateStarsInfoBar();
	}
	
	/**
	 * updates the stars image in infobar
	 */
	public void updateStarsInfoBar() {
		table.reset();
		table.top();
		table.setFillParent(true);
		infoBar.setLeftImages(main.getStarImgs(stars));
		table.add(infoBar.getInfoBar()).size(main.scrWidth, main.scrHeight/10).fill().row();
	}
	
	/**
	 * draws the progressbar
	 */
	public void drawProgressBar(){
		long endTime = System.nanoTime();
		if(!delayTime){
			secondsPassed = oldSecondsPassed + (endTime - startTime)/1000000000;  
		}
		progressBar.setValue(secondsPassed);
		long timeLeft = (maxTime - secondsPassed) >= 0 ? (maxTime - secondsPassed) : 0;
		
		float xCoord = main.scrWidth/2-(time.getBounds(Long.toString(timeLeft)).width)/2;
		float yCoord = main.scrHeight-infoBar.getBarHeight()-progressBar.getPrefHeight()*1.5f;
		long timeLeftDisplay = timeLeft == maxTime ? maxTime : timeLeft + 1;
		timeLeftDisplay = timeOver ? 0 : timeLeftDisplay;
		time.draw(batch, Long.toString(timeLeftDisplay), xCoord, yCoord);
	}
	
	/**
	 * draws the number of question you are on
	 */
	public void drawNrOfQuestion(){
		float xCoord = main.scrWidth/2-(time.getBounds(Long.toString(questionStatusDisplay)+"/"+nrOfQuestions).width)/2;
		float yCoord = time.getBounds(Long.toString(questionStatusDisplay)+"/"+nrOfQuestions).height+progressBar.getPrefHeight()*1.5f;
		time.draw(batch, Long.toString(questionStatusDisplay)+"/"+nrOfQuestions, xCoord, yCoord);
	}
	
	/**
	 * notifies user that his answer was correct
	 */
	public void displayRightAnswerAndGetNewQuestion(){
		oldSecondsPassed = 0;
		setCorrectProgressBar();
		refreshProgressBar(true);
		delayTime = true;
		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		    	touchUp = false;
		    	swipeQuestion = false;
		    	moveQuestionToStartPos();
		    	lockPos = false;
		    	
		    	getNewQuestion();
		    	delayTime = false;
		    }
		}, 1);
		updateStars();
		main.correctAnswerSound.play(main.volume);
	}
	
	/**
	 * sets up progress bar
	 */
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
 	    progressBar.setPosition(0,main.scrHeight-main.scrHeight/10-progressBar.getPrefHeight());
	    progressBar.setSize(main.scrWidth, progressBar.getPrefHeight());
	    progressBar.setAnimateDuration(1);
	    stage.addActor(progressBar);
	}
	
	/**
	 * Shows a pop-up screen with information whether the player
	 * finished a level or a category, and how many stars earned
	 * in the corresponding level.
	 * 
	 * @param win - true if hit right answer
	 * @param finishCat - true if the level is the last level in the
	 * category (there for the player has finished the category)
	 */
	public void showFinishLevelDialog(boolean win, boolean finishCat) {
		pause();
		if(win) {
			String[] messages = new String[]{main.data.getName()+" says:","",""};
			if(main.character.characterGrew() && thisLevelOldStars<=0) {
				messages[1] = "I just grew up! Good job!";
			} else if(main.character.getNrOfLevelsToNext()!=0) {
				messages[1] = "Good job! Only "+main.character.getNrOfLevelsToNext()+" more";
				if(main.character.getNrOfLevelsToNext()==1) messages[2] = "level 'till I grow up!";
				else messages[2] = "levels 'till I grow up!";
			} else {
				messages[1] = "Good job!";
			}
			
			String[] starsImgDir = main.getStarImgs(stars);
			if(finishCat && thisLevelOldStars<=0) {
				String title = cat.getType()+" complete!";
				main.dialogService.showEndLevelDialog(title, starsImgDir, "faces/happycarl.png", messages);
			} else {
				String title = "Level complete!";
				main.dialogService.showEndLevelDialog(title, starsImgDir, "faces/happycarl.png", messages);
			}
		} else {
			String title = "Oh no! You lost!";
			String[] messages = new String[]{main.data.getName()+" says:", "", ""};
			messages[1] = "Better luck next time!";
			main.dialogService.showEndLevelDialog(title, new String[]{"","",""}, "faces/sadcarl.png", messages);
		}
		Timer.schedule(getLevelsWindow(), 1);
	}
	
	/**
	 * 
	 * @return a task that shows the levels window
	 */
	public Task getLevelsWindow(){
		return new Task(){
		    @Override
		    public void run() {
		    	main.setScreen(new Levels(main, cat));
		    }
		};
	}
	
	/**
	 * Creates and shows a dialog that fills the screen 
	 * so that the player can't cheat
	 */
	public void showPauseDialog(){
		Pixmap pm = new Pixmap(1,1,Format.RGBA8888);
		pm.setColor(new Color(140/255f, 202/255f, 82/255f, 1));
		pm.fill();
		
		WindowStyle dialogStyle = new WindowStyle();
		dialogStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(pm)));
		dialogStyle.titleFont = main.skin.getFont(main.screenSizeGroup+"-M");
		
		pauseDialog = new Dialog("", dialogStyle);
		pauseDialog.setHeight(cat.getPlayScreenHeight());
		pauseDialog.setWidth(cat.getPlayScreenWidth());
		stage.addActor(pauseDialog);
	}
	
	/**
	 * Removes the dialog that shows up when you pause
	 */
	public void clearPauseDialog(){
		pauseDialog.remove();
	}
	
	/**
	 * places question on top of answer
	 */
	public void moveQuestionOverAnswer() {
		if(xDirection == "left" ) {
			cat.getQuestion().getRec().x = 0;
		}
	}
		
	/**
	 * sets new progress bar
	 * 
	 * @param delay
	 */
	public void refreshProgressBar(boolean delay){
		progressBar.remove();
		progressBar = new ProgressBar(0, maxTime, 0.5f, false, progressBarStyle);
		progressBar.setPosition(0,main.scrHeight-main.scrHeight/10-progressBar.getPrefHeight());
	    progressBar.setSize(main.scrWidth, progressBar.getPrefHeight());
	    progressBar.setAnimateDuration(delay ? 0 : 1);
	    progressBar.setValue(secondsPassed);
	    stage.addActor(progressBar);
	}
	
	/**
	 * sets normal progress bar
	 */
	public void setNormalProgressBar(){
		progressBarStyle.background = main.skin.getDrawable("bg");
		progressBarStyle.knob = main.skin.getDrawable("knob");
		progressBarStyle.knobBefore = progressBarStyle.knob;
	}
	
	/**
	 * sets the progress bar that notifies the user
	 * when he has won
	 */
	public void setCorrectProgressBar(){
		progressBarStyle.background = main.skin.getDrawable("bg_correct");
		progressBarStyle.knob = main.skin.getDrawable("knob_correct");
		progressBarStyle.knobBefore = progressBarStyle.knob;
	}
	
	/**
	 * sets the progress bar that notifies the user
	 * when he has lost
	 */
	public void setWrongProgressBar(){
		progressBarStyle.background = main.skin.getDrawable("bg_wrong");
		progressBarStyle.knob = main.skin.getDrawable("knob_wrong");
		progressBarStyle.knobBefore = progressBarStyle.knob;
	}
	
	/**
	 * calculates stars for finishing the level within the time limit 
	 */
	public void updateStars(){
		totalSecondsWasted += secondsPassed;
		
		int threeStars = cat.get3StarsTimeLimit();
		int twoStars = cat.get2StarsTimeLimit();
		int oneStar = cat.get1StarTimeLimit();
		
		if(totalSecondsWasted >= threeStars && stars == 3){
			stars = 2;
		} else if(totalSecondsWasted >= twoStars && stars == 2){
			stars = 1;
		} else if(totalSecondsWasted >= oneStar && stars == 1){
			stars = 0;
		}
	}
	
	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
	}

	/**
	 * pauses the game
	 */
	@Override
	public void pause() {
		oldSecondsPassed = secondsPassed;
		delayTime = true;
		this.state = State.PAUSE;
	}

	/**
	 * resumes the game
	 */
	@Override
	public void resume() {
		startTime = System.nanoTime();
		delayTime = false;
		this.state = State.RUN;
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK){
			Timer.instance().clear();
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
	
	/**
	 * Checks if the question box has gone out of upper
	 * boundaries of the screen
	 * 
	 * @param axis - y or x axis
	 */
	public void checkUpperBoundaries(String axis) {
		Rectangle rec = cat.getQuestion().getRec();
		
		if(axis == "x") {
			if(rec.x > main.scrWidth-rec.getWidth()) {
				rec.x = main.scrWidth-rec.getWidth();
			}
		} else if(axis == "y") {
			if(rec.y > main.scrHeight-rec.getHeight()-infoBar.getBarHeight()-progressBarHeight) {
				rec.y = main.scrHeight-rec.getHeight()-infoBar.getBarHeight()-progressBarHeight;
			}
		}	
	}
	
	/**
	 * Checks if the question box has gone out of lower
	 * boundaries of the screen
	 * 
	 * @param axis - y or x axis
	 */
	public void checkLowerBoundaries(String axis) {
		Rectangle rec = cat.getQuestion().getRec();
		
		if(axis == "x") {
			if(rec.x < 0) {
				rec.x = 0;
			}
		} else if(axis == "y") {
			if(rec.y < 0) {
				rec.y = 0;
			}
		}
	}
	
	/**
	 * Swipes the question smoothly after the user has let go
	 * of the question box (after touchUp)
	 */
	public void swipeQuestionAfterTouchUp() {
		Rectangle rec = cat.getQuestion().getRec();
		
		if(touchUp && swipeQuestion && !hitBox) {
			if(xDirection != "none") {
				if(xDirection == "right") {
					rec.x += 6;
					checkUpperBoundaries("x");
				} else {
					rec.x -= 6;
					checkLowerBoundaries("x");
				}
			}
			if(yDirection != "none") {
				if(yDirection == "up") {
					rec.y += 12;
					checkUpperBoundaries("y");
				} else {
					rec.y -= 12;
					checkLowerBoundaries("y");
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
	}
	
	/**
	 * Sets the direction of the question box
	 * (in which direction it is swiped)
	 */
	public void setDirections() {
		Rectangle rec = cat.getQuestion().getRec();
		if(rec.x - origX < 30 && rec.x - origX > -30) {
			xDirection = "none";
		} else {
			if(rec.x > origX) {
				xDirection = "right";
			} else {
				xDirection = "left";
			}
		}
		if(rec.y - origY < 30 && rec.y - origY > -30) {
			yDirection = "none";
		} else {
			if(rec.y > origY) {
				yDirection = "up";
			} else {
				yDirection = "down";
			}
		}
	}
	
	/**
	 * Handles when the question has hit a answer box,
	 * whether the answer is right or wrong
	 */
	public void handleHitBox() {
		Box hit = cat.checkIfHitAnswer();
		if(hit != null && !delayTime){
			hitBox = true;
			questionsAnswered++;
			if(questionsAnswered >= nrOfQuestions){
				win();
			} else {
				displayRightAnswerAndGetNewQuestion();
			}
		}
		
		//if hit wrong answer then move question to the middle
		Box _hitBox = cat.checkIfHitBox();
		if(_hitBox != null && hit == null && !delayTime) {
			hitBox = true;
			lose();
		}
	}
	
	/**
	 * The update loop of the game - runs when the game is in
	 * RUN state
	 */
	public void update() { 
		Rectangle rec = cat.getQuestion().getRec();
		
		// swipe question
		if(Gdx.input.isTouched()) {
			touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			if(lockPos || touchOverlapsQuestion(touchPos)){
				if(lockPos) {
					camera.unproject(touchPos);
				}
				
				setDirections();
				
				rec.x = touchPos.x - rec.getWidth() / 2;
				rec.y = touchPos.y - rec.getHeight() / 2;
				lockPos = true;
				swipeQuestion = true;
				hitBox = false;
			}
		}
		
		// swipe question smoothly after touchUp
		swipeQuestionAfterTouchUp();
		
		// hit answer
		handleHitBox();
		
		// check time
		if(secondsPassed > progressBar.getMaxValue()){
			lose();
		}
	}
	
	/**
	 * Draws everything on the screen
	 */
	public void draw() {
		Gdx.gl.glClearColor(21/255f, 149/255f, 136/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
				
		// draw question and answers
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(main.background, 0, 0, main.scrWidth, main.scrHeight);
		batch.end();
		batch.begin();
		for(Box answer : cat.getAnswers()){
			answer.draw(batch);
		}
		cat.getQuestion().draw(batch);
		batch.end();
		batch.begin();
		drawProgressBar();
		drawNrOfQuestion();
		batch.end();
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
	}
	
	/**
	 * Creates new processor that handles the pause button
	 */
	private void addPauseToProcessor() {
		 inputProcessor = new InputProcessor() {	
			 @Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}
			
			/**
			 * Sets a listener to the pause/play button
			 */
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(screenX>main.scrWidth-main.scrHeight/10 && screenY<main.scrHeight/10) {
					table.reset();
					table.top();
					table.setFillParent(true);
					if(state == State.RUN) {
						pause();
						showPauseDialog();
						infoBar.setRightImage("infoBar/play.png");
					}
					else {
						resume();
						clearPauseDialog();
						infoBar.setRightImage("infoBar/pause.png");
					}
					table.add(infoBar.getInfoBar()).size(main.scrWidth, main.scrHeight/10).fill().row();
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
	
	/**
	 * Adds all the processors of the class to a multiplexer
	 */
	private void setAllProcessors() {
		Gdx.input.setCatchBackKey(true);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(this);
		multiplexer.addProcessor(inputProcessor);
		Gdx.input.setInputProcessor(multiplexer); 	
	}
	
	/**
	 * Sets up the info bar
	 */
	public void setUpInfoBar() {
		stage.addActor(table);
		infoBar.setMiddleText("Level "+level);
		infoBar.setRightText("");
		infoBar.setLeftImages(main.getStarImgs(3));
		infoBar.setRightImage("infoBar/pause.png");
	 	table.add(infoBar.getInfoBar()).size(main.scrWidth, main.scrHeight/10).fill().row();

	}
	
	/**
	 * Saves the stars that the player got into the database
	 * @param newStars - amount of stars that the player got in this level
	 */
	public void saveStars(int newStars){
		int levelWon = level;
		openNextLevel(levelWon);
		int oldStars = main.data.getStarsByLevel(levelWon, cat);
		if(newStars < oldStars)
			return;
		main.data.getStarsByString(cat.getType()).updateStars(levelWon, newStars);
		main.data.saveData();
		//save score on facebook if user is logged in
		if(main.facebookService.isLoggedIn()) {
			main.updateScoreOnFacebook();
		}
	}

	/**
	 * Opens the next level if the player won and 
	 * next level
	 * @param levelWon
	 */
	private void openNextLevel(int levelWon) {
		if(levelWon == 9)
			return;
		int nextLevelStars = main.data.getStarsOfALevel(cat.getType(), levelWon + 1);
		//If next level is open already, then return
		if(nextLevelStars > -2)
			return;
		main.data.updateStars(levelWon + 1, -1, cat.getType());
	}
}