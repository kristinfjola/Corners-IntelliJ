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
	final float screenWidth = Gdx.graphics.getWidth();
	final float screenHeight = Gdx.graphics.getHeight();
	InputProcessor inputProcessor; 
	
	/**
	 * Constructor that sets the private variable and starts the screen.
	 * 
	 * @param main
	 * @param category
	 */
	public Levels(MainActivity main, Category category){
		this.main = main;
		cat = category;
 	    Gdx.input.setCatchBackKey(true);
 	    addBackToProcessor(); 
		create();
	}

	private void setAllProcessors() {
		Gdx.input.setCatchBackKey(true);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(inputProcessor);
		Gdx.input.setInputProcessor(multiplexer); 	
	}

	/**
	 * Sets up the buttons on the level screen
	 */
	public void create(){
		stage = new Stage();
		skin = main.skin;

		container = new Table();
		stage.addActor(container);
		container.setFillParent(true);

		setAllProcessors();
		
		int cnt = 1;
		Table tableLevels = new Table();
		tableLevels.defaults().size(screenWidth/4.2f,screenHeight/6f);
		for (int rows = 0; rows < 3; rows++) {
			tableLevels.row();
			for (int columns = 0; columns < 3; columns++) {
				Table level = new Table();
				level.top();
				
				level.add(getLevelButton(cnt)).size(screenWidth/4.2f,screenHeight/6f).expand();
				level.row();
				level.add(getStarTable(cnt));
				
				tableLevels.add(level).expand();
				cnt++;
			}
		}
		container.add(tableLevels).expand().fill();
	}
	
	@Override
	public void show() {
	}

	/**
	 * Renders the stuff on the screen 
	 *
	 * @param delta
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		
		if(level == 1) {
			button = new TextButton(""+level, skin, main.screenSizeGroup+"-L"+"-level-yellow");
		}
		else {
			button = new TextButton(""+level, skin, main.screenSizeGroup+"-L"+"-level-grey");
		}
		
		button.setName("Level" + Integer.toString(level));
		button.addListener(new ClickListener() {	
			/**
			 * Sends the user to a new play screen
			 * 
			 * @param event
			 * @param x
			 * @param y
			 */
			@Override
			public void clicked (InputEvent event, float x, float y) {
				main.play = new Play(main, cat, level);
	            main.setScreen(main.play);
			}
		});		
		return button;
	}
	

	public Table getStarTable(int level) {
		Table starTable = new Table();
		
		// TODO Get stars from db
		int numberOfStars = 2;
		int cntStars = 0;
		
		for (int star = 0; star < 3; star++) {
			if(level == 1 && cntStars != numberOfStars) { //taka ut level==1
				starTable.add(new Image(main.fullStar)).size(screenWidth/4.2f/3);
			}
			else {
				starTable.add(new Image(main.emptyStar)).size(screenWidth/4.2f/3);
			}
			cntStars++;
		}
		
		return starTable;
	}
	
	private void addBackToProcessor() {
		 inputProcessor = new InputProcessor() {
				
				@Override
				public boolean touchUp(int screenX, int screenY, int pointer, int button) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean touchDragged(int screenX, int screenY, int pointer) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean touchDown(int screenX, int screenY, int pointer, int button) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean scrolled(int amount) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean mouseMoved(int screenX, int screenY) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean keyUp(int keycode) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean keyTyped(char character) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean keyDown(int keycode) {
					if(keycode == Keys.BACK){
						main.setScreen(new Categories(main));
			        }
			        return false;
				}
			};
	}

}
