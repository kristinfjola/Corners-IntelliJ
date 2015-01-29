package com.corners.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

public class MainActivity extends Game {
	Start start;
	Categories categories;
	Levels levels;
	Play play;
	Settings settings;
	
	@Override
	/** Method called once when the application is created. **/
	public void create () {
		start = new Start(this);
		categories = new Categories(this);
		levels = new Levels(this);
		play = new Play(this);
		settings = new Settings(this);
        setScreen(start);
	}

	@Override
	/** Method called by the game loop from the application every time rendering should be performed. Game logic updates are usually also performed in this method. **/
	public void render () {
	}
	
	/** This method is called every time the game screen is re-sized and the game is not in the paused state. It is also called once just after the create() method.
	The parameters are the new width and height the screen has been resized to in pixels. **/
	public void resize (int width, int height) { 
		
	}
	
	/** On Android this method is called when the Home button is pressed or an incoming call is received.A good place to save the game state. **/
	public void pause () { 
		
	}

	/** This method is only called on Android, when the application resumes from a paused state. **/
	public void resume () {
		
	}

	/** Called when the application is destroyed. It is preceded by a call to pause(). **/
	public void dispose () { 
		
	}
}
