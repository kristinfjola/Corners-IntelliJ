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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.corners.game.MainActivity;

public class Categories implements Screen {
	private Texture carl; //the character, let's call it Carl
	private SpriteBatch batch;
	private MainActivity main;
	private Skin skin;
	Category cat;
	private Stage stage;
	private InputProcessor inputProcessor;
	private Table table;
	
	/**
	 * Constructor. Creates the the interface and sets the
	 * private variables
	 * 
	 * @param main - applicable activity
	 */
	public Categories(MainActivity main){
		this.main = main;
		this.batch = new SpriteBatch();
		stage = new Stage();
		this.main.scrWidth = Gdx.graphics.getWidth();
		this.main.scrHeight = Gdx.graphics.getHeight();
		main.activityRequestHandler.showFacebook(false);
		
		addBackToProcessor();
		setAllProcessors();
		//addCarl();
		carl = main.character.getCharacterImg();
		
		skin = main.skin;
		this.cat = new Category();
		
		// Create a table that fills the screen. Everything else will go inside this table.
		this.table = new Table();
		table.top();
		table.setFillParent(true);
		stage.addActor(table);
		
		setUpInfoBar();
	}
	

	@Override
	public void show() {
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
		
		table.add(new Label(main.data.getName(), main.skin, main.screenSizeGroup+"-M"))
				.padTop(main.scrHeight*0.4f-main.scrHeight/10).padBottom(main.scrHeight/40).row();
		table.add(btnMath).size(main.scrWidth/1.5f, main.scrHeight/8).padBottom(main.scrHeight/20).row();
		table.add(btnColors).size(main.scrWidth/1.5f, main.scrHeight/8).padBottom(main.scrHeight/20).row();
		table.add(btnFlags).size(main.scrWidth/1.5f, main.scrHeight/8).padBottom(main.scrHeight/20).row();
		
	}
	
	/*private void addCarl() {
		int levelsFinished = 0;
		levelsFinished = main.data.getAllFinished();
		if(levelsFinished < 5){
			this.carl = new Texture("carl/carl1green.jpg");
		}
		else if(levelsFinished < 10){
			this.carl = new Texture("carl/carl2green.jpg");
		}
		else if(levelsFinished < 15){
			this.carl = new Texture("carl/carl3green.jpg");
		}
		else if(levelsFinished < 20){
			this.carl = new Texture("carl/carl4green.jpg");
		}
		else{
			this.carl = new Texture("carl/carl5green.jpg");
		}
	}*/
	
	/**
	 * Renders all the cool stuff on the screen every delta time
	 * 
	 * @param delta
	 */
	public void render(float delta) {
		Gdx.gl.glClearColor(21/255f, 149/255f, 136/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        batch.draw(main.background, 0, 0, main.scrWidth, main.scrHeight);
		batch.draw(carl, main.scrWidth*0.25f, main.scrHeight*0.6f, main.scrWidth*0.5f, main.scrWidth*0.5f);	
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
		double tempStars = main.data.getAllAverageStars();
		int tempLevels = main.data.getAllFinished();
		InfoBar infoBar = new InfoBar(main);
		infoBar.setMiddleText("Categories");
		infoBar.setRightText(tempLevels+"/27");
		infoBar.setLeftImage(infoBar.getStarAmount(tempStars));
		table.add(infoBar.getInfoBar()).size(main.scrWidth, main.scrHeight/10).fill().row();
	}
}
