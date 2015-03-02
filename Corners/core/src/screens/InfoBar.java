package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
	ClickListener leftListener;
	ClickListener rightListener;
	
	public InfoBar(MainActivity main) {
		this.main = main;
		barWidth = screenWidth;
	 	barHeight = screenHeight/10;
	 	leftListener = new ClickListener() {
	 		public void clicked (InputEvent event, float x, float y) {}
		};
		rightListener = new ClickListener() {
			public void clicked (InputEvent event, float x, float y) {}
		};
	}
	
	public Table getInfoBar(){
		Pixmap pm = new Pixmap(1, 1, Format.RGBA8888);
	 	pm.setColor(new Color(20/255f, 120/255f, 113/255f, 1));
	 	pm.fillRectangle(0,0,1,1);
	 
	 	Table infoBarTable = new Table(main.skin);
	 	infoBarTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pm))));
	 	
	 	Button leftButton = new Button(main.skin);
	 	Label leftLabel = new Label(leftText, main.skin, main.screenSizeGroup+"-M");
	 	leftLabel.setAlignment(Align.center);
	 	leftButton.stack(new Image(new Texture("barInfo/"+leftImage+".png")), leftLabel).expand().fill();
	 	
	 	Button rightButton = new Button(main.skin);
	 	Label rightLabel = new Label(rightText, main.skin, main.screenSizeGroup+"-M");
	 	rightLabel.setAlignment(Align.center);
	 	rightButton.stack(new Image(new Texture("barInfo/"+rightImage+".png")), rightLabel).expand().fill();
	 	rightButton.addListener(rightListener);
	 	
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
	
	public void setLeftListener(ClickListener leftListener) {
		this.leftListener = leftListener;
	}
	
	public void setRightListener(ClickListener rightListener) {
		this.rightListener = rightListener;
	}
}
