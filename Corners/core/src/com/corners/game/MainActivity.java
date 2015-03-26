/**
 * @author: Edda Bjork Konradsdottir
 * @date: 	05.02.2015
 * @goal: 	The activity for the game, keeps track of which screen
 * 			to show
 */

package com.corners.game;

import logic.Category;
import screens.Categories;
import screens.Friends;
import screens.Levels;
import screens.Play;
import screens.Settings;
import screens.Start;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class MainActivity extends Game {
	public float scrWidth;
	public float scrHeight;
	
	public Categories categories;
	public Levels levels;
	public Play play;
	public Settings settings;
	public Start start;
	
	public Skin skin;
	public String screenSizeGroup;
	public Category cat;
	
	public Texture fullStar;
	public Texture emptyStar;
	public Friends friends;
	
	public FacebookService facebookService;
	public FacebookUser user;
	public ActionResolver actionResolver;
	public ActivityRequestHandler activityRequestHandler;
	public Notifications notificationsService;
	
	// sound
	public float volume;
	public boolean settingsVolume;
	public boolean phoneVolume;
	public Sound clickedSound;
	public Sound correctAnswerSound;
    public Sound wrongAnswerSound;
    public Sound levelFinishedSound;
    public Sound categoryFinishedSound;
	
    // sound slider drawables
    public Drawable backgroundOn;
	public Drawable backgroundOff;
	public Drawable knob;
	
	@Override
	/** Method called once when the application is created. **/
	public void create () {
		scrWidth = Gdx.graphics.getWidth();
		scrHeight= Gdx.graphics.getHeight();
		skin = getSkin();
		initScreenSizeGroup();
		this.cat = new Category();
		settingsVolume = cat.isSoundOn();
		updateVolume();
		facebookService.showFacebookUser();
		notificationsService.setNotifications();
		
		start = new Start(this);
        setScreen(start);
        
        fullStar = new Texture("stars/star_yellow.png");
		emptyStar = new Texture("stars/star_gray.png");		
		settingsVolume = cat.isSoundOn();
		
		clickedSound = Gdx.audio.newSound(Gdx.files.internal("sounds/clicked.mp3"));
		correctAnswerSound = Gdx.audio.newSound(Gdx.files.internal("sounds/correctAnswer.mp3"));
	    wrongAnswerSound = Gdx.audio.newSound(Gdx.files.internal("sounds/wrongAnswer.mp3"));
	    levelFinishedSound = Gdx.audio.newSound(Gdx.files.internal("sounds/levelFinished.mp3"));
	    categoryFinishedSound = Gdx.audio.newSound(Gdx.files.internal("sounds/levelFinished.mp3"));
	    
	    setUpSliderDrawables();
	    
	    System.out.println(settingsVolume);
		//actionResolver.showToast("Toast example", 5000);
	}

	@Override
	/** Method called by the game loop from the application every time rendering should be performed. Game logic updates are usually also performed in this method. **/
	public void render () {
		super.render();
	}
	
	/** This method is called every time the game screen is re-sized and the game is not in the paused state. It is also called once just after the create() method.
	The parameters are the new width and height the screen has been resized to in pixels. **/
	public void resize (int width, int height) { 
		
	}
	
	/** On Android this method is called when the Home button is pressed or an incoming call is received.A good place to save the game state. **/
	public void pause () { 
		super.pause();
	}

	/** This method is only called on Android, when the application resumes from a paused state. **/
	public void resume () {
		
	}

	/** Called when the application is destroyed. It is preceded by a call to pause(). **/
	public void dispose () { 
		
	}
	
	/**
	 * Gets the skin
	 * 
	 * @return the skin from skins.json
	 */
	public Skin getSkin() {
		TextureAtlas atlas = new TextureAtlas("atlas/buttons.atlas");		
		Skin skin = new Skin(Gdx.files.internal("skins/skins.json"), atlas);
		return skin;
	}
	
	/**
	 * Check in which screen size group the screen is
	 * (how big the screen is) and initializes the screenSizeGroup
	 */
	public void initScreenSizeGroup(){
		int screenWidth = Gdx.graphics.getWidth();
		if(screenWidth < 400) screenSizeGroup = "screen320";
		else if(400 <= screenWidth && screenWidth < 510) screenSizeGroup = "screen480";
		else if(510 <= screenWidth && screenWidth < 630) screenSizeGroup = "screen540";
		else if(630 <= screenWidth && screenWidth < 900) screenSizeGroup = "screen720";
		else screenSizeGroup = "screen1080"; //900 <= screenWidth
	}

	public void setFacebookService(FacebookService facebookService) {
		this.facebookService = facebookService;
	}
	
	/**
	 * Sets the phoneVolume to vol and updates the volume of the app
	 * @param vol
	 */
	public void updatePhoneVolume(boolean vol) {
		phoneVolume = vol;
		updateVolume();
	}
	
	/**
	 * Sets the settingsVolume to vol and updates the volume of the app
	 * @param vol
	 */
	public void updateSettingsVolume(boolean vol) {
		settingsVolume = vol;
		cat.setSound(vol);
		updateVolume();
	}
	
	/**
	 * Updates the volume of the app, according to the phoneVolume and settingsVolume
	 */
	private void updateVolume() {
		if(phoneVolume&settingsVolume) {
			volume = 1.0f;
		}
		else {
			volume = 0.0f;
		}
	}

	/**
	 * @return the user's Facebook information
	 */
	public FacebookUser getUser() {
		return user;
	}

	/**
	 * @param user
	 * Sets the user's Facebook information
	 */
	public void setUser(FacebookUser user) {
		this.user = user;
	}
	
	/**
	 * Sets up the drawables for the sound slider
	 */
	public void setUpSliderDrawables() {
		/*
		Pixmap pm1 = new Pixmap((int)(scrWidth/4.5f),(int)(scrWidth/9f),Format.RGBA8888);
		pm1.setColor(Color.GREEN);
		pm1.fill();
		backgroundOn = new TextureRegionDrawable(new TextureRegion(new Texture(pm1)));
		
		Pixmap pm2 = new Pixmap((int)(scrWidth/4.5f),(int)(scrWidth/9f),Format.RGBA8888);
		pm2.setColor(Color.RED);
		pm2.fill();
		backgroundOff = new TextureRegionDrawable(new TextureRegion(new Texture(pm2)));
		
		Pixmap pm3 = new Pixmap((int)(scrWidth/9f),(int)(scrWidth/9f),Format.RGBA8888);
		pm3.setColor(new Color(60/255f,60/255f,60/255f,1));
		pm3.fill();
		knob = new TextureRegionDrawable(new TextureRegion(new Texture(pm3)));
		*/
		
		backgroundOn = skin.getDrawable("backgroundOn");
		backgroundOff = skin.getDrawable("backgroundOff");
		knob = skin.getDrawable("sliderKnob");	
		
		
		int textHeight = (int)(skin.getFont(screenSizeGroup+"-M").getBounds("Sound").height*1.5f);		
		backgroundOn.setMinHeight(textHeight);
		backgroundOff.setMinHeight(textHeight);
		knob.setMinHeight(textHeight);
		knob.setMinWidth(textHeight);
	}
}
