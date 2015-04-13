/**
 * @author 	Kristin Fjola Tomasdottir
 * @date 	30.03.2015
 * @goal 	DataHelper contains functions to access and change the database
 */
package data;

import logic.Category;

public class DataHelper {

	private Data data = new Data();

	/**
	 * Constructor for DataHelper
	 */
	public DataHelper(){
		DataProcessor.getData(data);
	}
	
	/**
	 * Saves the data to a file in assets
	 * @param data
	 */
	public void saveData() {
		DataProcessor.setData(data);
	}
	
	/**
	 * 
	 * @return name of 'Carl' from database
	 */
	public String getName(){
		return data.getName();
	}
	
	/**
	 * Sets the name of the avatar to newName in database
	 * @param newName 
	 */
	public void setName(String newName){
		data.setName(newName);
		saveData();
	}
	
	/**
	 * 
	 * @return true if sound is on, else false
	 */
	public boolean isSoundOn(){
		return data.isSoundOn();
	}
	
	/**
	 * Sets the sound on or off in database
	 * @param bool
	 */
	public void setSound(boolean bool){
		data.setSound(bool);
		saveData();
	}
	
	/**
	 * @return true if notifications are on, else false
	 */
	public boolean isNotificationOn(){
		return data.getNotifications();
	}
	
	/**
	 * @param bool
	 * sets notifications on to bool
	 */
	public void setNotification(boolean bool){
		data.setNotifications(bool);
		saveData();
	}
	
	/**
	 * @param level - stars received for level in category
	 * @return number of stars in specific level
	 */
	public int getStarsByLevel(int level, Category cat){
		LevelStars stars = getStars(cat);
		return stars.getStarsByLevel(level);
	}
	
	/**
	 * @return stars received for current category
	 */
	public LevelStars getStars(Category cat){
		return data.getStarsByString(cat.getType());
	}
	
	/**
	 * 
	 * @return the array of value of stars in particular category
	 */
	public int[] getStarsArray(Category cat){
		LevelStars levelStars = getStars(cat);
		int[] stars = levelStars.getStars();
		return stars;
	}
	
	/**
	 * 
	 * @return the average stars of a particular category
	 */
	public double getAverageStars(Category cat){
		LevelStars levelStars = getStars(cat);
		return levelStars.getAverageStars();
	}
	
	/**
	 * 
	 * @return the count of finished levels in particular category
	 */
	public int getLevelsFinished(Category cat){
		LevelStars levelStars = getStars(cat);
		return levelStars.getLevelsFinished();
	}
	
	/**
	 * Updates the stars of a level to newStars in a category
	 * and saves it to the database
	 * @param level
	 * @param newStars
	 */
	public void updateStars(int level, int newStars, Category cat){
		LevelStars stars = getStars(cat);
		stars.updateStars(level, newStars);
	}
	
	/**
	 * @return number of finished levels in all the categories
	 */
	public int getAllFinished(){
		return data.getAllFinished();
	}
	
	/**
	 * @return average stars of all the levels
	 */
	public double getAllAverageStars() {
		return data.getAverageStars();
	}
	
	/**
	 * @param category
	 * @return stars of the levels in category as LevelStar object
	 */
	public LevelStars getStarsByString(String category){
		return data.getStarsByString(category);
	}
	
	/**
	 * 
	 * @param catType
	 * @param level
	 * @return number of stars in a level
	 */
	public int getStarsOfALevel(String catType, int level){
		return getStarsByString(catType).getStarsOfALevel(level);
	}
	
	/**
	 * @param level
	 * @param stars
	 */
	public void updateStars(int level, int stars, String catType){
		data.getStarsByString(catType).updateStars(level, stars);
	}
}
