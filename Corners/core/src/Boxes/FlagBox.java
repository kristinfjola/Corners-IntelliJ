/**
 * @author: Kristin Fjola Tomasdottir
 * @date	09.02.2015
 * @goal: 	A class for representing question and answer boxes 
 * 			for the category Flags
 */
package boxes;

public class FlagBox extends Box{

	String country;
	
	/**
	 * @param width	- width of box
	 * @param height - height of box
	 * @param country - country to display on box
	 */
	public FlagBox(int width, int height, String country){
		super(width, height);
		this.country = country;
	}

	/**
	 * @return country for box
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
