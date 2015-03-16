/**
 * @author: Edda Bjork Konradsdottir
 * @date: 	05.02.2015
 * @goal: 	A class for the categories screen (mostly interface)
 */

package screens;

import logic.Category;
import logic.Colors;
import logic.Flags;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.corners.game.MainActivity;

import data.Data;
import data.DataProcessor;

public class Categories implements Screen {
	private Texture carl; //the character, let's call it Carl
	private SpriteBatch batch;
	private MainActivity main;
	private float screenWidth;
	private float screenHeight;
	private Skin skin;
	private Stage stage;
	private InputProcessor inputProcessor;
	public Data data;
	private Table table;
	
	/**
	 * Constructor. Creates the the interface and sets the
	 * private variables
	 * 
	 * @param main - applicable activity
	 */
	public Categories(final MainActivity main){
		this.main = main;
		this.batch = new SpriteBatch();
		this.carl = new Texture("carl/carl4.jpg");
		stage = new Stage();
		this.screenWidth = Gdx.graphics.getWidth();
		this.screenHeight = Gdx.graphics.getHeight();
		
		addBackToProcessor();
		setAllProcessors();
		processData();
		
		skin = main.skin;
		
		// Create a table that fills the screen. Everything else will go inside this table.
		this.table = new Table();
		table.top();
		table.setFillParent(true);
		stage.addActor(table);
		
		setUpInfoBar();
		
		final TextButton btnMath = new TextButton("Math", skin, main.screenSizeGroup+"-L");
		btnMath.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				main.levels = new Levels(main, new logic.Math());
				main.setScreen(main.levels);
			}
		});

		final TextButton btnColors = new TextButton("Colors", skin, main.screenSizeGroup+"-L");
		btnColors.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("COLORS!");
				main.levels = new Levels(main, new Colors());
	            main.setScreen(main.levels);
			}
		});
		
		final TextButton btnFlags = new TextButton("Flags", skin, main.screenSizeGroup+"-L");
		btnFlags.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("FLAGS!");
				main.levels = new Levels(main, new Flags());
	            main.setScreen(main.levels);
			}
		});
		
		table.add(btnMath).width(this.screenWidth/1.5f).height(this.screenHeight/8).padTop(screenHeight/3f).padBottom(this.screenHeight/20);
		table.row();
		table.add(btnColors).width(this.screenWidth/1.5f).height(this.screenHeight/8).padBottom(this.screenHeight/20);
		table.row();
		table.add(btnFlags).width(this.screenWidth/1.5f).height(this.screenHeight/8).padBottom(this.screenHeight/20);
		table.row();
	}
	

	@Override
	public void show() {
	}
	
	/**
	 * Renders all the cool stuff on the screen every delta time
	 * 
	 * @param delta
	 */
	public void render(float delta) {
		Gdx.gl.glClearColor(21/255f, 149/255f, 136/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
		batch.draw(carl, screenWidth*0.25f, screenHeight*0.6f, screenWidth*0.5f, screenWidth*0.5f);	
        batch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	/**
	 * Resizes the screen to the applicable size, the parameters
	 * width and height are the new width and height the screen 
	 * has been resized to
	 * 
	 * @param width
	 * @param height
	 */
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
	public void dispose() {
		stage.dispose();
	}

	/**
	 * Gets the data from the database
	 */
	private void processData() {
		 Category category = new Category();
		 data = new Data();
		 data = category.getData();
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
					main.setScreen(new Start(main));
				}
				return false;
			}
		};
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
	 * Sets up the info bar
	 */
	public void setUpInfoBar() {
		double tempStars = data.getAverageStars();
		int tempLevels = data.getAllFinished();
		InfoBar infoBar = new InfoBar(main);
		infoBar.setMiddleText("Categories");
		infoBar.setRightText(tempLevels+"/27");
		infoBar.setLeftImage(infoBar.getStarAmount(tempStars)+"stars");
		table.add(infoBar.getInfoBar()).size(screenWidth, screenHeight/10).fill().row();
	}
}
