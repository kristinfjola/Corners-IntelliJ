/**
 * @author: Johanna Agnes Magnusdottir
 * @date: 	05.02.2015
 * @goal: 	A class for the Start screen (mostly interface)
 */

package screens;

import com.badlogic.gdx.Gdx;
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

public class Start implements Screen{
	private MainActivity main;
	private Stage stage;
	private SpriteBatch batch;
	private Skin skin;
	private float screenWidth = Gdx.graphics.getWidth();
	private float screenHeight = Gdx.graphics.getHeight();
	private Texture carl = new Texture("carl/carl4.jpg");
	Table table;
	
	/**
	 * Constructor. Creates the the interface and sets the
	 * private variables
	 * 
	 * @param main - applicable activity
	 */
	public Start(final MainActivity main){
		this.main = main;		
		stage = new Stage();
		batch = new SpriteBatch();
		skin = main.skin;
		Gdx.input.setInputProcessor(stage);
	}
	
	/**
	 * Creates and sets up the buttons on the start screen
	 */
	@Override
	public void show() {
		this.table = new Table();
		table.top();
		table.setFillParent(true);
		stage.addActor(table);
		
		String screenSizeGroup = main.screenSizeGroup;
		
		setUpInfoBar();

		final TextButton btnCategories = new TextButton("Play", skin, screenSizeGroup+"-L");
		btnCategories.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + btnCategories.isChecked());
				System.out.println("start");
				dispose();
				main.categories = new Categories(main);
				main.setScreen(main.categories);
			}
		});
		
		TextButton btnSettings = new TextButton("Settings", skin, screenSizeGroup+"-L");
		TextButton btnFriends = new TextButton("Friends", skin, screenSizeGroup+"-L");
		// TODO Add listeners
		
		table.add(btnCategories).padTop(screenHeight/3f).size(screenWidth/1.5f, screenHeight/8).padBottom(screenHeight/20).row();
		table.add(btnSettings).size(screenWidth/1.5f, screenHeight/8).padBottom(screenHeight/20).row();
		table.add(btnFriends).size(screenWidth/1.5f, screenHeight/8).padBottom(screenHeight/20).row();
		
	}

	/**
	 * Renders stuff on the screen every delta time
	 * 
	 * @param delta
	 */
	@Override
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
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Disposes the screen
	 */
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	/**
	 * Sets up the info bar
	 */
	public void setUpInfoBar() {
		InfoBar infoBar = new InfoBar(main);
		infoBar.setMiddleText("Corners");
	 	table.add(infoBar.getInfoBar()).size(screenWidth, screenHeight/10).fill().row();
	}
}
