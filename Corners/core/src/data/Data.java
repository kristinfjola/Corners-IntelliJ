/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	26.02.2015
 * @goal 	Data is a data transfer object that keep track of the state ands stars of
 * 			all the categories in the game. It also holds the name of the avatar.
 */
package data;

public class Data {
	private LevelStars math;
	private LevelStars flags;
	private LevelStars colors;
	private LevelStars reset;
	private double averageStars;
	private String name; //name of avatar
	private Boolean sound;

	/**
	 * @return name of avatar
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name - the name of the avatar
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return a reset array for levels
	 */
	public LevelStars getReset() {
		return reset;
	}

	/**
	 * @param reset - reset array
	 */
	public void setReset(LevelStars reset) {
		this.reset = reset;
	}

	/**
	 * 
	 * @return true if sound is on, else false
	 */
	public Boolean getSound() {
		return sound;
	}

	/**
	 * 
	 * @param sound
	 */
	public void setSound(Boolean sound) {
		this.sound = sound;
	}

	/**
	 * @param category
	 * @return stars of the levels in category as LevelStar object
	 */
	public LevelStars getStarsByString(String category){
		if(category == "Math"){
			math.setCategoryName(category);
			return getMath();
		}
		if(category == "Flags"){
			flags.setCategoryName(category);
			return getFlags();
		}
		else{
			colors.setCategoryName(category);
			return getColors();
		}
	}
	
	/**
	 * @return LevelStars object for Math
	 */
	public LevelStars getMath() {
		return math;
	}

	/**
	 * @param math
	 */
	public void setMath(LevelStars math) {
		this.math = math;
	}

	/**
	 * @return LevelStars object for Flags
	 */
	public LevelStars getFlags() {
		return flags;
	}

	/**
	 * @param flags
	 */
	public void setFlags(LevelStars flags) {
		this.flags = flags;
	}

	/**
	 * @return LevelStars object for Colors
	 */
	public LevelStars getColors() {
		return colors;
	}

	/**
	 * @param colors
	 */
	public void setColors(LevelStars colors) {
		this.colors = colors;
	}

	/**
	 * @param averageStars
	 */
	public void setAverageStars(double averageStars) {
		this.averageStars = averageStars;
	}

	/**
	 * @return avegrage stars of all the levels
	 */
	public double getAverageStars() {
		calcAverageStars();
		return averageStars;
	}
	
	/**
	 * Function that calculates the average stars of all the levels combined
	 */
	public void calcAverageStars() {
		int count = getAllFinished();
		double sumStars = allStars(math) + allStars(colors) + allStars(flags);
		this.averageStars = sumStars/count;
	}
	
	/**
	 * @return number of finished levels in all the categories
	 */
	public int getAllFinished(){
		return finished(math) + finished(colors) + finished(flags);
	}
	
	/**
	 * @param cat - Levels stars objevt of a category
	 * @return number of levels finished in a category
	 */
	public int finished(LevelStars cat){
		return cat.getLevelsFinished();
	}
	
	/**
	 * @param cat - LevelStars object of a category
	 * @return sum of the stars in the category
	 */
	public double allStars(LevelStars cat){
		return cat.getAverageStars() * cat.getLevelsFinished();
	}

	/**
	 * Adds the average stars in each category
	 * to the database
	 */
	public void addAverageStars(){
		math.addAverageStars();
		colors.addAverageStars();
		flags.addAverageStars();
	}
	
	/**
	 * 
	 * @return boolean value that states if 
	 * the sound is on or not from db
	 */
	public boolean isSoundOn(){
		return this.sound;
	}
	
	@Override
	public String toString() {
		return "Data [math=" + math.toString() + ", flags=" + flags.toString() + ", colors=" + colors.toString()
				+ ", averageStars=" + averageStars + ", name=" + name + "]";
	}
}
