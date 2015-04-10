/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	05.02.2015
 * @goal 	Levels is a screen that shows the user the levels that he/she can play in a category.
 * 			It also shows the stars that the user has won.
 */
package screens;

import logic.Category;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.corners.game.MainActivity;


public class Levels implements Screen{

	private MainActivity main;
	private Skin skin;
	private Stage stage;
	private Table container;
	private Category cat;
	private InputProcessor inputProcessor;
	private InfoBar infoBar;
	public int[] stars;
	private SpriteBatch batch;
	
	/**
	 * Constructor that sets the private variable and starts the screen.
	 * 
	 * @param main
	 * @param category
	 */
	public Levels(MainActivity main, Category category){
		this.main = main;
		this.cat = category;
		this.infoBar = new InfoBar(main);
		setUpCat();
 	    Gdx.input.setCatchBackKey(true);
 	    addBackToProcessor(); 
 	    processData(category);
		create();
	}

	/**
	 * Sets up the buttons on the level screen
	 */
	public void create(){
		stage = new Stage();
		this.batch = new SpriteBatch();
		skin = main.skin;

		container = new Table();
		stage.addActor(container);
		container.setFillParent(true);

		setAllProcessors();
		
		setUpInfoBar();

		int cnt = 1;
		Table tableLevels = new Table();
		tableLevels.defaults().size(main.scrWidth/4.2f,main.scrHeight/6f);
		for (int rows = 0; rows < 3; rows++) {
			tableLevels.row();
			for (int columns = 0; columns < 3; columns++) {
				Table level = new Table();
				level.top();
				
				level.add(getLevelButton(cnt)).size(main.scrWidth/4.2f,main.scrHeight/6f).expand();
				level.row();
				level.add(getStarTable(cnt));
				
				tableLevels.add(level).expand();
				cnt++;
			}
		}
		container.add(tableLevels).expand().fill();
	}

	/**
	 * Renders the stuff on the screen 
	 *
	 * @param delta
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
        batch.draw(main.background, 0, 0, main.scrWidth, main.scrHeight);
        batch.end();
        
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();		
	}

	/**
	 * Sets the screen to its proper size
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
	public void show() {
	}

	/**
	 * Disposes the screen
	 */
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	/**
	 * Creates a button to represent a level
	 * level is a int number that represents the level number of the button
	 * 
	 * @param level
	 * @return The button to use for the level
	 */
	public TextButton getLevelButton(final int level) {
		TextButton button;
		
		if(stars[level] > -2) {
			button = new TextButton(""+level, skin, main.screenSizeGroup+"-L"+"-level-yellow");
			button.addListener(new ClickListener() {	
				@Override
				public void clicked (InputEvent event, float x, float y) {
					cat.refreshQuestions();
					main.play = new Play(main, cat, level);
		            main.setScreen(main.play);
		            
		            cat.setDirections(main, level);
				}
			});	
		}
		else {
			button = new TextButton(""+level, skin, main.screenSizeGroup+"-L"+"-level-grey");
		}
		
		button.setName("Level" + Integer.toString(level));
			
		return button;
	}
	

	/**
	 * @param level
	 * @return table of stars that fit the number of stars in the database
	 */
	public Table getStarTable(int level) {
		Table starTable = new Table();
		int numberOfStars = stars[level];
		int cntStars = 0;
		for (int star = 0; star < 3; star++) {
			if(cntStars < numberOfStars) {
				starTable.add(new Image(main.fullStar)).size(main.scrWidth/4.2f/3);
				cntStars++;
			}
			else {
				starTable.add(new Image(main.emptyStar)).size(main.scrWidth/4.2f/3);
			}
		}
		return starTable;
	}
	
	/**
	 * Sets the stars variable with appropriate values for the category
	 * 
	 * @param category
	 */
	private void processData(Category category) {
			stars = main.data.getStarsArray(category);
	}

	/**
	 * Adds the game stage and the back button processors to a multiplexer
	 */
	private void setAllProcessors() {
		Gdx.input.setCatchBackKey(true);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(inputProcessor);
		Gdx.input.setInputProcessor(multiplexer); 	
	}
	
	/**
	 * Creates a input processor that catches the back key 
	 */
	private void addBackToProcessor() {
		inputProcessor = new InputProcessor() {
			
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}
				
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}
				
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}
				
			@Override
			public boolean scrolled(int amount) {
				return false;
			}
				
			@Override
			public boolean mouseMoved(int screenX, int screenY) {
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
			
			/**
			 * Handles the back event
			 */
			@Override
			public boolean keyDown(int keycode) {
				if(keycode == Keys.BACK){
					main.setScreen(new Categories(main));
				}
				return false;
			}
		};
	}
	
	/**
	 * Sets up the info bar
	 */
	public void setUpInfoBar() {
		double averageStars = main.data.getAverageStars(cat);
		int finishedLevels = main.data.getLevelsFinished(cat);
		InfoBar infoBar = new InfoBar(main);
		infoBar.setMiddleText(cat.getType());
		infoBar.setRightText(finishedLevels+"/9");
		infoBar.setLeftImage("stars/"+main.getStarAmount(averageStars)+".png");
	 	container.add(infoBar.getInfoBar()).size(main.scrWidth, main.scrHeight/10).fill().row();
	}
	
	/**
	 * Sends the skin, screen size group and the size of the play screen into the category
	 */
	public void setUpCat() {
		cat.setSkin(main.skin);
		cat.setScreenSizeGroup(main.screenSizeGroup);
		
		float playScreenWidth = main.scrWidth;
		float pBarHeight = (new Image(new Texture("progressBar/background.png"))).getPrefHeight();
		float playScreenHeight = main.scrHeight-infoBar.barHeight-pBarHeight;
		cat.setPlayScreenWidth((int) playScreenWidth);
		cat.setPlayScreenHeight((int) playScreenHeight);

		cat.setUpBoxes();
	}
}
