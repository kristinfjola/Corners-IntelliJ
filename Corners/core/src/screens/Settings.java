/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	05.02.2015
 * @goal 	Settings will be the class for the screen where that the user can change the settings of the game 
 * 			Has no functionality so far.
 */
package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
	TextButton btnTest;
	
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
		//profile_pic = new TextureRegion(new Texture("carl/carl4.jpg"));
		main.facebookService.setScreen(this);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void show() {
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		
		String screenSizeGroup = main.screenSizeGroup;
		
		
		
		btnLogin = new TextButton("Login", skin, screenSizeGroup+"-L");
		updateLoginBtn();
		table.add(btnLogin).padTop(screenHeight/2.4f).size(screenWidth/1.5f, screenHeight/8).padBottom(screenHeight/20).row();
		
		btnTest = new TextButton("Test", skin, screenSizeGroup+"-L");
		table.add(btnTest).size(screenWidth/1.5f, screenHeight/8).row();
		btnTest.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("whatup");
				String id = main.facebookService.getUserId();
				System.out.println(id);
				//profile_pic = main.facebookService.getProfilePicture(id);
			}
		});
		
		stage.addActor(table);
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
		
		/*if(main.facebookService.isLoggedIn() && profile_pic != null){
			batch.begin();
			batch.draw(profile_pic, screenWidth*0.25f, screenHeight*0.6f, screenWidth*0.5f, screenWidth*0.5f);	
			batch.end();
		}*/
		
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
