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
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
	Stack stack;
	
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
		
		stack = new Stack();
		stack.setFillParent(true);
		stack.add(table);
		
		stage.addActor(stack);
		
		setUpInfoBar();
		setUpFacebook();
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
		table.add(labelFb).expandX().left().pad(pad).padTop(main.scrWidth/4f);
		//table.add(btnLogin).expandX().right().pad(pad).padBottom(pad + main.scrWidth/6f).row();
		table.add(btnLogin).width(this.main.scrWidth/2.3f).height(this.main.scrHeight/10)
			.expandX().right().pad(pad).padTop(main.scrWidth/4f).row();
		addLine();
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
		
		setNameListener();
		
		table.add(new Label("Name", settingsStyle)).expandX().left().pad(pad);
		table.add(nameLabel).expandX().right().pad(pad).row();
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
	public void setNameListener() {
		int dPad = (int) (main.scrWidth/30f);
		final int dWidth = (int) (main.scrWidth/1.5f);
		final int dHeight = (int) (main.scrHeight/4f);
		
		final Dialog nameDialog = getNameDialog();
		
		Label enterNameLabel = new Label("Characters name:", main.skin, main.screenSizeGroup+"-S");
		final TextField nameInputField = getNameInputField();
		Table cancelSaveTable = getCancelSaveTable(nameDialog, nameInputField, dPad);

		nameDialog.getContentTable().add(enterNameLabel).expand().top().left().pad(dPad).row();
		nameDialog.getContentTable().add(nameInputField).width(dWidth-2*dPad).expand().top().left().pad(dPad).row();
		nameDialog.getContentTable().add(cancelSaveTable).width(dWidth).bottom().left().row();
		
		nameLabel.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Table t = new Table();
				t.add(nameDialog).size(dWidth, dHeight);
				stack.add(t);
				stage.setKeyboardFocus(nameInputField);
				nameInputField.getOnscreenKeyboard().show(true);
				nameInputField.setText("");
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
	
	public Dialog getNameDialog() {
		Pixmap pm = new Pixmap(1,1,Format.RGBA8888);
		pm.setColor(new Color(211/255f,211/255f,211/255f,1));
		pm.fill();
		WindowStyle dialogStyle = new WindowStyle();
		dialogStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(pm)));
		dialogStyle.titleFont = main.skin.getFont(main.screenSizeGroup+"-S");//ekki notað
		
		Dialog nameDialog = new Dialog("", dialogStyle);
		return nameDialog;
	}
	
	public TextField getNameInputField() {
		Pixmap black = new Pixmap(1,1,Format.RGBA8888);
		black.setColor(Color.BLACK);
		black.fill();
		Pixmap white = new Pixmap(1,1,Format.RGBA8888);
		white.setColor(Color.WHITE);
		white.fill();
		TextFieldStyle nameInputStyle = new TextFieldStyle();
		nameInputStyle.font = main.skin.getFont(main.screenSizeGroup+"-L");
		nameInputStyle.fontColor = Color.BLACK;
		nameInputStyle.cursor = new TextureRegionDrawable(new TextureRegion(new Texture(black)));
		nameInputStyle.selection = new TextureRegionDrawable(new TextureRegion(new Texture(black)));
		nameInputStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(white)));
		
		TextField nameInputField = new TextField("", nameInputStyle);
		nameInputField.setWidth(300);
		nameInputField.setMaxLength(15);
		return nameInputField;
	}
	
	public Table getCancelSaveTable(Dialog nameDialog, TextField nameInputField, int dPad) {
		Label cancelNameButton = getCancelNameButton(nameDialog, nameInputField);
		Label saveNameButton = getSaveNameButton(nameDialog, nameInputField);
		
		Table cancelSaveTable = new Table();
		cancelSaveTable.add(cancelNameButton).expand().left().pad(dPad);
		cancelSaveTable.add(saveNameButton).expand().right().pad(dPad).row();
		
		return cancelSaveTable;
	}
	
	public Label getSaveNameButton(final Dialog nameDialog, final TextField nameInputField) {
		Label saveNameButton = new Label("Save", main.skin, main.screenSizeGroup+"-M");
		saveNameButton.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				String name = nameInputField.getText();
				if(!name.equals("")) {
					main.data.setName(name);
					nameLabel.setText(name);
				}
				nameInputField.getOnscreenKeyboard().show(false);
				nameDialog.remove();
				super.touchUp(event, x, y, pointer, button);
			}
		});
		return saveNameButton;
	}
	
	public Label getCancelNameButton(final Dialog nameDialog, final TextField nameInputField) {
		Label cancelNameButton = new Label("Cancel", main.skin, main.screenSizeGroup+"-M");
		cancelNameButton.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				nameInputField.getOnscreenKeyboard().show(false);
				nameDialog.remove();
				super.touchUp(event, x, y, pointer, button);
			}
		});	
		return cancelNameButton;
	}
}
