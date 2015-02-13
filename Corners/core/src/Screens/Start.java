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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.corners.game.MainActivity;

public class Start implements Screen{
	MainActivity main;
	Stage stage;
	SpriteBatch batch;
	Skin skin;
	float screenWidth = Gdx.graphics.getWidth();
	float screenHeight = Gdx.graphics.getHeight();
	Texture carl = new Texture("carl1.jpg");
	
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
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		stage.addActor(table);
		
		String screenSizeGroup = main.screenSizeGroup;
		
		final TextButton btnCategories = new TextButton("Categories", skin, screenSizeGroup);
		btnCategories.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + btnCategories.isChecked());
				System.out.println("start");
				dispose();
				main.categories = new Categories(main);
				main.setScreen(main.categories);
			}
		});
		
		TextButton btnSettings = new TextButton("Settings", skin, screenSizeGroup);
		TextButton btnFriends = new TextButton("Friends", skin, screenSizeGroup);
		// TODO Add listeners
		
		table.add(btnCategories).padTop(screenHeight/2.8f).size(screenWidth/1.5f, screenHeight/8).padBottom(screenHeight/20).row();
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
		Gdx.gl.glClearColor(54/255f, 83/255f, 139/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(carl, screenWidth/4, screenHeight*2/3, screenWidth/2, screenHeight/3);
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
}
