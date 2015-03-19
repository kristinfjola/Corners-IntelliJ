/**
 * @author: Edda Bjork Konradsdottir
 * @date: 	05.02.2015
 * @goal: 	The activity for the game, keeps track of which screen
 * 			to show
 */

package com.corners.game;

import screens.Categories;
import screens.Levels;
import screens.Play;
import screens.Settings;
import screens.Start;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MainActivity extends Game {
	public Categories categories;
	public Levels levels;
	public Play play;
	public Settings settings;
	public Start start;
	public Skin skin;
	public String screenSizeGroup;
	public Texture fullStar;
	public Texture emptyStar;
	
	public FacebookService facebookService;
	public ActionResolver actionResolver;
	public ActivityRequestHandler activityRequestHandler;
	
	@Override
	/** Method called once when the application is created. **/
	public void create () {
		skin = getSkin();
		screenSizeGroup = getScreenSizeGroup();
		start = new Start(this);
        setScreen(start);
        fullStar = new Texture("stars/star_yellow.png");
		emptyStar = new Texture("stars/star_gray.png");
		
		//actionResolver.showToast("Tap screen to open !AlertBox!", 5000);
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
	 * (how big the screen is)
	 * 
	 * @return screen size group
	 */
	public String getScreenSizeGroup(){
		int screenWidth = Gdx.graphics.getWidth();
		if(screenWidth < 400) return "screen320";
		else if(400 <= screenWidth && screenWidth < 510) return "screen480";
		else if(510 <= screenWidth && screenWidth < 630) return "screen540";
		else if(630 <= screenWidth && screenWidth < 900) return "screen720";
		else return "screen1080"; //900 <= screenWidth
	}

	public void setFacebookService(FacebookService facebookService) {
		this.facebookService = facebookService;
	}
}
