package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.corners.game.MainActivity;

public class InfoBar {
	float screenWidth = Gdx.graphics.getWidth();
	float screenHeight = Gdx.graphics.getHeight();
	float barHeight;
	float barWidth;
	MainActivity main;
	String leftText="";
	String middleText="";
	String rightText="";
	String leftImage="empty";
	String rightImage="empty";
	ChangeListener leftListener;
	ChangeListener rightListener;
	
	public InfoBar(MainActivity main) {
		this.main = main;
		barWidth = screenWidth;
	 	barHeight = screenHeight/10;
	 	leftListener = new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {}
		};
		rightListener = new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {}
		};
	}
	
	public Table getInfoBar(){
		Pixmap pm = new Pixmap(1, 1, Format.RGBA8888);
	 	pm.setColor(new Color(20/255f, 120/255f, 113/255f, 1));
	 	pm.fillRectangle(0,0,1,1);
	 
	 	Table infoBarTable = new Table(main.skin);
	 	infoBarTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm))));
	 	
	 	ImageTextButton leftButton = new ImageTextButton(leftText, main.skin, leftImage);
	 	ImageTextButton rightButton = new ImageTextButton(rightText, main.skin, rightImage);
	 	
	 	rightButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("You just clicked the right side!");
			}
		});
	 	
	 	//TODO get corrent info on stars and levels and change the image
	 	infoBarTable.add(leftButton).size(barHeight).expand().left();
	 	infoBarTable.add(middleText).expand();
	 	infoBarTable.add(rightButton).size(barHeight).expand().right();
	 	
	 	return infoBarTable;
	}
	
	public float getHeight(){
		return barHeight;
	}
	
	public float getWidth() {
		return barWidth;
	}
	
	public void setLeftText(String leftText) {
		this.leftText = leftText;
	}
	
	public void setMiddleText(String middleText) {
		this.middleText = middleText;
	}
	
	public void setRightText(String rightText) {
		this.rightText = rightText;
	}
	
	public void setLeftImage(String leftImage) {
		this.leftImage = leftImage;
	}
	
	public void setRightImage(String rightImage) {
		this.rightImage = rightImage;
	}
	
	public void setLeftListener(ChangeListener leftListener) {
		this.leftListener = leftListener;
	}
	
	public void setRightListener(ChangeListener rightListener) {
		this.rightListener = rightListener;
	}
}
