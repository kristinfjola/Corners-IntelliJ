/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	05.02.2015
 * @goal 	Settings will be the class for the screen where that the user can change the settings of the game 
 * 			Has no functionality so far.
 */
package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
	
	private InputProcessor inputProcessor;
	Sound clickedSound = Gdx.audio.newSound(Gdx.files.internal("sounds/clicked.mp3"));
	Table table;
	LabelStyle settingsStyle;
	Label labelLogin;
	Label labelSoundStatus;
	
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
		Gdx.input.setInputProcessor(stage);
		main.facebookService.setScreen(this);		
		settingsStyle = new LabelStyle(main.skin.getFont(main.screenSizeGroup+"-M"), Color.BLACK);
		addBackToProcessor();
		setAllProcessors();
	}
	
	@Override
	public void show() {
		table = new Table();
		table.top().left();
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
		
		setUpFacebook();
		setUpSound();
		setUpName();
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
		skin.dispose();
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
	
	public void addLine() {
		Pixmap pm = new Pixmap(1,1,Format.RGBA8888);
		pm.setColor(new Color(39/255f,39/255f,39/255f,0.5f));
		pm.fill();
		Image img = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(pm))));
		table.center().top();
		table.add(img).size(screenWidth*0.9f,1).row();
	}
	
	public void setUpFacebook() {
		labelLogin = new Label("Login", settingsStyle);
		setLoginListener();
		table.add(labelLogin).left().pad(screenWidth/12f).padLeft(screenWidth/24f).row();
		addLine();
	}
	
	public void setUpSound() {
		Label labelSound = new Label("Sound", settingsStyle);
		
		String soundStatus;
		if(main.settingsVolume) soundStatus = "ON";
		else soundStatus = "OFF";
		labelSoundStatus = new Label(soundStatus, settingsStyle);
		setSoundListener();

		//table.add(labelSound).left().pad(screenWidth/12f);
		table.add(labelSoundStatus).left().pad(screenWidth/12f).padLeft(screenWidth/24f).row();
		addLine();
	}
	
	public void setUpName() {
		table.add(new Label("Name", settingsStyle)).left().pad(screenWidth/12f).padLeft(screenWidth/24f).row();
		addLine();
	}
	
	public void setLoginListener(){
		if(main.facebookService.isLoggedIn()){
			labelLogin.setText("Logout");
			labelLogin.addListener(new ClickListener() {
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					System.out.println("logout clicked: is logging out");
					main.facebookService.logOut();
					super.touchUp(event, x, y, pointer, button);
				}
			});
		} else {
			labelLogin.setText("Login");
			labelLogin.addListener(new ClickListener() {
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					System.out.println("login clicked: is logging in");
					main.facebookService.logIn();
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
	}
	
	public void setSoundListener() {
		labelSoundStatus.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(main.settingsVolume) {
					main.updateSettingsVolume(false);
					labelSoundStatus.setText("OFF");
				}
				else {
					main.updateSettingsVolume(true);
					labelSoundStatus.setText("ON");
					clickedSound.play(main.volume);
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

}
