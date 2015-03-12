/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	05.02.2015
 * @goal 	Settings will be the class for the screen where that the user can change the settings of the game 
 * 			Has no functionality so far.
 */
package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.corners.game.MainActivity;

public class Settings implements Screen{

	MainActivity main;
	Skin skin;
	Stage stage;
	SpriteBatch batch;
	float screenWidth = Gdx.graphics.getWidth();
	float screenHeight = Gdx.graphics.getHeight();
	TextButton btnLogin;
	
	/**
	 * Constructor that sets the private variables and starts the screen
	 * 
	 * @param main
	 */
	public Settings(MainActivity main){
		this.main = main;
		batch = new SpriteBatch();
		stage = new Stage();
		skin = this.main.skin;
		main.facebookService.setScreen(this);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void show() {
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		stage.addActor(table);
		
		String screenSizeGroup = main.screenSizeGroup;
		
		
		
		btnLogin = new TextButton("Login", skin, screenSizeGroup+"-L");
		updateLoginBtn();
		table.add(btnLogin).padTop(screenHeight/2.4f).size(screenWidth/1.5f, screenHeight/8).padBottom(screenHeight/20).row();
	}
	
	public void updateLoginBtn(){
		if(main.facebookService.isLoggedIn()){
			btnLogin.setText("logout");
			btnLogin.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					System.out.println("logout clicked: is logging out");
					main.facebookService.logOut();
				}
			});
		} else {
			btnLogin.setText("login");
			btnLogin.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					System.out.println("login clicked: is logging in");
					main.facebookService.logIn();
				}
			});
		}
	}

	/**
	 * Renders the screen
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(21/255f, 149/255f, 136/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
	}

	/**
	 * Sets the screen to its proper size
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
		skin.dispose();
	}

}
