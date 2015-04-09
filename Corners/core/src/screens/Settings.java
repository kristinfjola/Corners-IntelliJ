/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	05.02.2015
 * @goal 	Settings will be the class for the screen where that the user can change the settings of the game 
 * 			Has no functionality so far.
 */
package screens;

import java.util.List;

import logic.Category;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.corners.game.MainActivity;


public class Settings implements Screen{

	MainActivity main;
	Skin skin;
	Stage stage;
	SpriteBatch batch;
	Category cat;
	
	private InputProcessor inputProcessor;
	Table table;
	
	//Setting up the view
	LabelStyle settingsStyle;
	LabelStyle settingsStyleRight;
	Label labelLogin;
	float pad;
	Slider soundSlider;
	SliderStyle soundSliderStyle;
	int soundSliderValue;
	Label nameLabel;
	TextButton btnLogin;
	Slider notificationsSlider;
	SliderStyle notificationsSliderStyle;
	int notificationsSliderValue;
	
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
		this.cat = new Category();
		addBackToProcessor();
		setAllProcessors();
		main.activityRequestHandler.showFacebook(true);
		
		settingsStyle = new LabelStyle(main.skin.getFont(main.screenSizeGroup+"-M"), Color.BLACK);
		settingsStyleRight = new LabelStyle(main.skin.getFont(main.screenSizeGroup+"-M"), new Color(45/255f,45/255f,45/255f,1));
		pad = main.scrWidth/12f;
	}
	
	@Override
	public void show() {
		table = new Table();
		table.top();
		table.setFillParent(true);
		
		stage.addActor(table);
		
		setUpInfoBar();
		setUpFacebook(true);
		setUpSound();
		setUpNotifications();
		setUpName();
	}
	
	/**
	 * refreshes all components in table for screen
	 */
	public void refreshTable(){
		table.reset();
		table.top();
		table.setFillParent(true);
		
		setUpInfoBar();
		setUpFacebook(false);
		setUpSound();
		setUpNotifications();
		setUpName();
	}

	/**
	 * Renders the screen
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(21/255f, 149/255f, 136/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
        batch.draw(main.background, 0, 0, main.scrWidth, main.scrHeight);
		batch.end();
		
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
	
	/**
	 * Set's up the info bar
	 */
	public void setUpInfoBar() {
		InfoBar infoBar = new InfoBar(main);
		infoBar.setMiddleText("Settings");
	 	table.add(infoBar.getInfoBar()).expandX().left().size(main.scrWidth, main.scrHeight/10).row();
	}
	
	/**
	 * Set's up an option for logging into Facebook
	 */
	public void setUpFacebook(boolean firstTime) {
		Label labelFb = new Label("Facebook", settingsStyle);	
		
		if(firstTime){
			if(main.facebookService.isLoggedIn()){
				btnLogin = new TextButton("", skin, main.screenSizeGroup+"-L"+"-fb_logout");
			} else {
				btnLogin = new TextButton("", skin, main.screenSizeGroup+"-L"+"-fb_login");
			}
		}
		setLoginListener();
		
		table.add(labelFb).expandX().left().pad(pad).padBottom(main.scrWidth/6f);
		table.add(btnLogin).width(this.main.scrWidth/2.3f).height(this.main.scrHeight/10).expandX().right().pad(pad).padBottom(main.scrWidth/6f).row();
		addLine();
	}
	
	/**
	 * @param loggedIn
	 * Changes facebook button to 'log in' or 'logged out'
	 */
	public void changeLoginButton(boolean loggedIn){
		if(loggedIn){
			btnLogin = new TextButton("", skin, main.screenSizeGroup+"-L"+"-fb_login");
		} else {
			btnLogin = new TextButton("", skin, main.screenSizeGroup+"-L"+"-fb_logout");
		}
		refreshTable();
	}
	
	/**
	 * Set's up an option for changing the game's sound (on/off)
	 */
	public void setUpSound() {	
		soundSliderStyle = new SliderStyle();
		soundSliderStyle.knob = main.knob;
		soundSliderStyle.background = main.backgroundOn;
		soundSlider = new Slider(0,1,0.01f,false,soundSliderStyle);		
		if(main.settingsVolume) {
			soundSliderStyle.background = main.backgroundOn;
			soundSlider.setValue(1);
			soundSliderValue = 1;
		}
		else {
			soundSliderStyle.background = main.backgroundOff;
			soundSlider.setValue(0);
			soundSliderValue = 0;
		}

		setSoundListener();

		table.add(new Label("Sound", settingsStyle)).expandX().left().pad(pad);		
		table.add(soundSlider).width(main.scrWidth/5).expandX().right().pad(pad).row();
		addLine();
	}
	
	/**
	 * Set's up an option for setting the game's notifications (on/off)
	 */
	public void setUpNotifications(){
		notificationsSliderStyle = new SliderStyle();
		notificationsSliderStyle.knob = main.knob;
		notificationsSliderStyle.background = main.backgroundOn;
		notificationsSlider = new Slider(0,1,0.01f,false,notificationsSliderStyle);		
		if(main.data.isNotificationOn()) {
			notificationsSliderStyle.background = main.backgroundOn;
			notificationsSlider.setValue(1);
			notificationsSliderValue = 1;
		}
		else {
			notificationsSliderStyle.background = main.backgroundOff;
			notificationsSlider.setValue(0);
			notificationsSliderValue = 0;
		}
		
		setNotificationsListener();
		
		table.add(new Label("Notifications", settingsStyle)).expandX().left().pad(pad);		
		table.add(notificationsSlider).width(main.scrWidth/5).expandX().right().pad(pad).row();
		addLine();
	}
	
	/**
	 * Set's up an option for changing the characters name
	 */
	public void setUpName() {
		nameLabel = new Label(main.data.getName(), settingsStyleRight);
		
		Image carl = new Image(new Texture("faces/happycarl.png"));
		
		Image btnEdit = new Image(new Texture("buttons/edit.png"));
		setNameListener(btnEdit);
		
		Table charTable = new Table();
		charTable.add(nameLabel).pad(pad).padRight(pad/4);
		charTable.add(btnEdit).width(main.scrWidth/18).height(main.scrWidth/18);
		table.add(carl).width(main.scrWidth/7).height(main.scrWidth/7).expandX().left().padLeft(pad);
		table.add(charTable).expandX().right().padRight(pad).row();
		addLine();
	}
	
	/**
	 * Adds a line to the layout
	 */
	public void addLine() {
		Pixmap pm = new Pixmap(1,1,Format.RGBA8888);
		pm.setColor(new Color(39/255f,39/255f,39/255f,0.5f));
		pm.fill();
		Image img = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(pm))));
		float lineWidth = main.scrWidth*0.9f;
		float padLeft = (main.scrWidth-lineWidth)/2;
		table.add(img).size(lineWidth,1).left().pad(0).padLeft(padLeft).row();
	}
	
	
	/**
	 * Set's a listener for the Facebook login
	 */
	public void setLoginListener(){
		btnLogin.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(main.facebookService.isLoggedIn()){
					//btnLogin.setText("Log in");
					main.facebookService.logOut();
					changeLoginButton(true);
				} else {
					//btnLogin.setText("Log out");
					main.facebookService.logIn();
					changeLoginButton(false);
				}
				
			}
		});
	}
	
	/**
	 * Set's a listener for the sound button
	 */
	public void setSoundListener() {		
		soundSlider.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if((int)Math.round(soundSlider.getValue())==0) {
					soundSliderStyle.background = main.backgroundOff;
					main.updateSettingsVolume(false);
					soundSlider.setValue((int)Math.round(soundSlider.getValue()));
					soundSliderValue = (int)soundSlider.getValue();
				}
				else {
					soundSliderStyle.background = main.backgroundOn;
					main.updateSettingsVolume(true);
					main.clickedSound.play(main.volume);
					soundSlider.setValue((int)Math.round(soundSlider.getValue()));
					soundSliderValue = (int)soundSlider.getValue();
				}
			}
			
			@Override
			public void touchDragged (InputEvent event, float x, float y, int pointer) {
				if(soundSliderValue != (int)Math.round(soundSlider.getValue())) {
					if((int)Math.round(soundSlider.getValue())==1) {
						soundSliderStyle.background = main.backgroundOn;
						soundSliderValue = 1;
					}
					else {
						soundSliderStyle.background = main.backgroundOff;
						soundSliderValue = 0;
					}
				}
			}
		});
	}
	
	/**
	 * Set's a listener for the notifications slider
	 */
	public void setNotificationsListener() {		
		notificationsSlider.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if((int)Math.round(notificationsSlider.getValue())==0) {
					notificationsSliderStyle.background = main.backgroundOff;
					main.notificationsService.cancelNotifications();
					main.data.setNotification(false);
					notificationsSlider.setValue((int)Math.round(notificationsSlider.getValue()));
					notificationsSliderValue = (int)notificationsSlider.getValue();
				}
				else {
					notificationsSliderStyle.background = main.backgroundOn;
					main.notificationsService.setNotifications();
					main.data.setNotification(true);
					notificationsSlider.setValue((int)Math.round(notificationsSlider.getValue()));
					notificationsSliderValue = (int)notificationsSlider.getValue();
				}
				System.out.println("notifications db: " + main.data.isNotificationOn());
			}
			
			@Override
			public void touchDragged (InputEvent event, float x, float y, int pointer) {
				if(notificationsSliderValue != (int)Math.round(notificationsSlider.getValue())) {
					if((int)Math.round(notificationsSlider.getValue())==1) {
						notificationsSliderStyle.background = main.backgroundOn;
						notificationsSliderValue = 1;
					}
					else {
						notificationsSliderStyle.background = main.backgroundOff;
						notificationsSliderValue = 0;
					}
				}
			}
		});
	}
	
	/**
	 * Set's a listener for displaying a dialog to change the character's name
	 */
	public void setNameListener(Image btnEdit) {		
		ClickListener listener = new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				main.dialogs.showCharNameDialog("Enter your character's name", main, nameLabel);
				super.touchUp(event, x, y, pointer, button);
			}
		};
		nameLabel.addListener(listener);
		btnEdit.addListener(listener);
	}
}
