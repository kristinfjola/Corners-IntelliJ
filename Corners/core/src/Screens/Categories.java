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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.corners.game.MainActivity;

public class Categories implements Screen {
	Texture carl; //the character, let's call it Carl
	SpriteBatch batch;
	MainActivity main;
	float screenWidth;
	float screenHeight;
	Skin skin;
	Stage stage;
	
	
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
		Gdx.input.setInputProcessor(stage);
		
		skin = main.skin;
		
		String screenSizeGroup = main.screenSizeGroup;

		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		stage.addActor(table);
		
		final TextButton btnMath = new TextButton("Math", skin, screenSizeGroup);
		btnMath.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				main.levels = new Levels(main, new logic.Math(main.skin.getFont("tempMiniFont")));
				main.setScreen(main.levels);
			}
		});

		final TextButton btnColors = new TextButton("Colors", skin, screenSizeGroup);
		btnColors.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("COLORS!");
				main.levels = new Levels(main, new Colors());
	            main.setScreen(main.levels);
			}
		});
		
		final TextButton btnFlags = new TextButton("Flags", skin, screenSizeGroup);
		btnFlags.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("FLAGS!");
				main.levels = new Levels(main, new Flags());
	            main.setScreen(main.levels);
			}
		});
		
		table.add(btnMath).width(this.screenWidth/1.5f).height(this.screenHeight/8).padTop(this.screenHeight/2.8f).padBottom(this.screenHeight/20);
		table.row();
		table.add(btnColors).width(this.screenWidth/1.5f).height(this.screenHeight/8).padBottom(this.screenHeight/20);
		table.row();
		table.add(btnFlags).width(this.screenWidth/1.5f).height(this.screenHeight/8).padBottom(this.screenHeight/20);
		table.row();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
        batch.draw(carl, screenWidth/4, screenHeight*2/3, screenWidth/2, screenWidth/2);
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
	public void dispose() {
		stage.dispose();
	}
}
