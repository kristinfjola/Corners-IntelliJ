/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	26.02.2015
 * @goal 	LevelStars is a data transfer object that keeps count of the stars 
 * 			and playability of all the levels in a specific category.
 */
package data;

import com.google.gson.JsonArray;

public class LevelStars {
	private int[] stars;
	private double averageStars;
	private String categoryName;
	
	/**
	 * @param array
	 * Constructs the object and sets the stars param with the stuff from array
	 */
	public LevelStars(JsonArray array){
		int[] numbers = new int[array.size()];
		for (int i = 0; i < array.size(); ++i) {
		    numbers[i] = array.get(i).getAsInt();
		}
		setStars(numbers);
	}
	
	/**
	 * @return the name of the category
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 */
	public void setCategoryName(String categorieName) {
		this.categoryName = categorieName;
	}

	/**
	 * @return array of the stars in the category
	 */
	public int[] getStars() {
		return stars;
	}
	
	/**
	 * @param level
	 * @return star count of level
	 */
	public int getStarsByLevel(int level) {
		return stars[level];
	}
	
	
	/**
	 * @param stars
	 */
	public void setStars(int[] stars) {
		this.stars = stars;
	}
	
	/**
	 * @return average stars of category
	 */
	public double getAverageStars() {
		calcAverageStars();
		return averageStars;
	}
	
	/**
	 * Function that calculates the average stars of a category
	 */
	public void calcAverageStars() {
		int count = 0;
		double sumStars = 0.0;
		for(int i = 0; i < stars.length; i++){
			if(stars[i] != -1 && stars[i] != 0){
				count++;
				sumStars += stars[i];
			}
		}
		if(count == 0)
			this.averageStars = 0;
		else
			this.averageStars = sumStars / count;
	}
	
	/**
	 * @return number of levels that have been finished in a category
	 */
	public int getLevelsFinished(){
		int finished = 0;
		for(int i = 0; i < stars.length; i++){
			if(stars[i] != -1 && stars[i] != 0){
				finished++;
			}
		}
		return finished;
	}
}
