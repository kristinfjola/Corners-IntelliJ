/**
 * @author: Edda Bjork Konradsdottir
 * @date: 	05.02.2015
 * @goal: 	The activity for the game, keeps track of which screen
 * 			to show
 */

package com.corners.game;

import logic.Category;
import screens.Categories;
import screens.Character;
import screens.Friends;
import screens.Levels;
import screens.Play;
import screens.Settings;
import screens.Start;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import data.DataHelper;

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
	public Character character;
	
	public Texture fullStar;
	public Texture emptyStar;
	public Texture background;
	public Friends friends;
	public FacebookUser user;
	
	// services
	public FacebookService facebookService;
	public Dialogs actionResolver;
	public ActivityRequestHandler activityRequestHandler;
	public Notifications notificationsService;
	public DataHelper data;
	
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
		this.data = new DataHelper();
		this.character = new Character(this);
		this.background = new Texture("background/grass.png");
		settingsVolume = data.isSoundOn();
		updateVolume();
		setNotifications();
		facebookService.showFacebookUser();
		
		start = new Start(this);
        setScreen(start);
        
        fullStar = new Texture("stars/star_yellow.png");
		emptyStar = new Texture("stars/star_gray.png");		
		settingsVolume = data.isSoundOn();
		
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
		data.setSound(vol);
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
		int textHeight = (int)(skin.getFont(screenSizeGroup+"-M").getBounds("Sound").height*1.5f);
		
		backgroundOn = new NinePatchDrawable(getPatch("slider/backgroundOn.9.png",Integer.MAX_VALUE,textHeight));
		backgroundOn.setRightWidth(0);
		backgroundOn.setLeftWidth(0);
		backgroundOn.setMinHeight(textHeight);
		
		backgroundOff = new NinePatchDrawable(getPatch("slider/backgroundOff.9.png",Integer.MAX_VALUE,textHeight));
		backgroundOff.setRightWidth(0);
		backgroundOff.setLeftWidth(0);
		backgroundOff.setMinHeight(textHeight);
		
		knob = new NinePatchDrawable(getPatch("slider/sliderKnob.9.png",Integer.MAX_VALUE,textHeight));
		knob.setMinHeight(textHeight);
		knob.setMinWidth(textHeight);
	}
	
	/**
	 * @param fname is the directory to the 9patch
	 * @param maxHeight is the max height the patch should have
	 * @param maxWidth is the max width the patch should have
	 * @return a 9patch with the max height of maxHeight, max width maxWidth
	 * and where the stretchable area is a 1x1 pixel in the center
	 */
	public NinePatch getPatch(String fname, float maxWidth, float maxHeight) {
		Texture texture = new Texture(Gdx.files.internal(fname));
	    int width = texture.getWidth()-2;
	    int height = texture.getHeight()-2;
	    int left = (width-1)/2;
	    int right = (width-1)/2;
	    int top = (height-1)/2;
	    int bottom = (height-1)/2;
	    TextureRegion region = new TextureRegion(texture,1,1,width,height);
	    NinePatch patch = new NinePatch(region,left,right,top,bottom);

	    float scale;
	    if(maxHeight<maxWidth) scale = maxHeight/height;
	    else scale = maxWidth/width;
	    if(scale<1) patch.scale(scale,scale);
	    
	    return patch;
	}
	
	/**
	 * turns on notifications and makes sure that they're on the first
	 * time the user opens the app
	 */
	public void setNotifications(){
		notificationsService.cancelNotifications();
		if(data.isNotificationOn()){
			notificationsService.setNotifications();
		}
	}
	
	/**
	 * Rounds stars to the nearest quarter of an integer and puts together a string
	 * @param stars
	 * @return string that refers to the correct image based on the number stars, 
	 * e.g. "1_75-stars"
	 */
	public String getStarAmount(double stars) {
		String starAmount="";
		
		double starsX4 = stars*4;
		double roundedStarsX4 = Math.round(starsX4); 
		double roundedStars = roundedStarsX4/4;
		int wholeStars = (int) Math.floor(roundedStars);
		
		starAmount+=""+wholeStars;
		
		if(roundedStars-wholeStars==0.25) starAmount+="_25";
		else if(roundedStars-wholeStars==0.5) starAmount+="_5";
		else if(roundedStars-wholeStars==0.75) starAmount+="_75";
		
		starAmount+="-stars";
		
		return starAmount;
	}
}
