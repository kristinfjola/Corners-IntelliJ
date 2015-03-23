/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	05.02.2015
 * @goal 	Settings will be the class for the screen where that the user can change the settings of the game 
 * 			Has no functionality so far.
 */
package screens;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
	Slider slider;
	SliderStyle sliderStyle;
	int sliderValue;
	Label nameLabel;
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
		table.top().left();
		table.setFillParent(true);
		stage.addActor(table);
		
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
	 * Set's up an option for logging into Facebook
	 */
	public void setUpFacebook() {
		Label labelFb = new Label("Facebook", settingsStyle);	
		
		btnLogin = new TextButton("Login", skin, main.screenSizeGroup+"-L"+"-facebook");
		if(main.facebookService.isLoggedIn()){
			btnLogin.setText("Logout");
		} else {
			btnLogin.setText("Login");
		}
		setLoginListener();
		
		/* trying to move text on button
		btnLogin.getLabel().setPosition(500, 500);
		btnLogin.getLabel().setOrigin(200, 200);
		btnLogin.getLabel().moveBy(300, 300);
		*/
		
		table.add(labelFb).expandX().left().pad(pad).padBottom(pad + main.scrWidth/6f);
		//table.add(btnLogin).expandX().right().pad(pad).padBottom(pad + main.scrWidth/6f).row();
		table.add(btnLogin).width(this.main.scrWidth/2.3f).height(this.main.scrHeight/10).right().pad(pad).padBottom(pad + main.scrWidth/6f).row();
		addLine();
	}
	
	/**
	 * Set's up an option for changing the game's sound (on/off)
	 */
	public void setUpSound() {	
		sliderStyle = new SliderStyle();
		sliderStyle.knob = main.knob;
		sliderStyle.background = main.backgroundOn;
		slider = new Slider(0,1,0.01f,false,sliderStyle);		
		if(main.settingsVolume) {
			sliderStyle.background = main.backgroundOn;
			slider.setValue(1);
			sliderValue = 1;
		}
		else {
			sliderStyle.background = main.backgroundOff;
			slider.setValue(0);
			sliderValue = 0;
		}

		setSoundListener();

		table.add(new Label("Sound", settingsStyle)).expandX().left().pad(pad);		
		table.add(slider).width(main.scrWidth/5).expandX().right().pad(pad).row();
		addLine();
	}
	
	/**
	 * Set's up an option for changing the characters name
	 */
	public void setUpName() {
		nameLabel = new Label(cat.getName(), settingsStyleRight);
		
		setNameListener();
		
		table.add(new Label("Name", settingsStyle)).expandX().left().pad(pad);
		table.add(nameLabel).expandX().right().pad(pad).row();
		addLine();
	}
	
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
					btnLogin.setText("Login");
					main.facebookService.logOut();
				} else {
					btnLogin.setText("Logout");
					main.facebookService.logIn();
				}
				
			}
		});
	}
	
	/**
	 * Set's a listener for the sound button
	 */
	public void setSoundListener() {		
		slider.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if((int)Math.round(slider.getValue())==0) {
					sliderStyle.background = main.backgroundOff;
					main.updateSettingsVolume(false);
					slider.setValue((int)Math.round(slider.getValue()));
					sliderValue = (int)slider.getValue();
				}
				else {
					sliderStyle.background = main.backgroundOn;
					main.updateSettingsVolume(true);
					main.clickedSound.play(main.volume);
					slider.setValue((int)Math.round(slider.getValue()));
					sliderValue = (int)slider.getValue();
				}
			}
			
			@Override
			public void touchDragged (InputEvent event, float x, float y, int pointer) {
				if(sliderValue != (int)Math.round(slider.getValue())) {
					if((int)Math.round(slider.getValue())==1) {
						sliderStyle.background = main.backgroundOn;
						sliderValue = 1;
					}
					else {
						sliderStyle.background = main.backgroundOff;
						sliderValue = 0;
					}
				}
			}
		});
	}
	
	/**
	 * Set's a listener for displaying a dialog to change the character's name
	 */
	public void setNameListener() {
		/*Drawable cursor = main.skin.getDrawable("empty");
		Drawable selection = main.skin.getDrawable("empty");
		Drawable background = main.skin.getDrawable("empty");
		TextFieldStyle nameStyle = new TextFieldStyle(main.skin.getFont(main.screenSizeGroup+"-M"),
				Color.BLACK, cursor,selection,background);
		TextField nameInput = new TextField("", nameStyle);
		*/
		
		final Dialog textInputDialog = new Dialog("", this.main.skin);
		
		Label center = new Label("Save", main.skin);
		center.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				textInputDialog.remove();
				super.touchUp(event, x, y, pointer, button);
			}
		});
		textInputDialog.button("Nafn:");
		textInputDialog.getButtonTable().row();
		textInputDialog.button("Carl jr").row();
		textInputDialog.getContentTable().row();
		textInputDialog.add(center);
		
		nameLabel.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				textInputDialog.show(stage);
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
}
