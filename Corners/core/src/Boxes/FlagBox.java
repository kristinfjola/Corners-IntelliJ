/**
 * @author: Kristin Fjola Tomasdottir
 * @date	09.02.2015
 * @goal: 	A class for representing question and answer boxes 
 * 			for the category Flags
 */
package boxes;

public class FlagBox extends Box{

	private String country;
	private String capital;
	
	/**
	 * @param width	- width of box
	 * @param height - height of box
	 * @param country - country to display on box
	 */
	public FlagBox(int width, int height, String country, String capital){
		super(width, height);
		//this.country = country;
		//this.capital = capital;
		this.setCountry(country);
		this.setCapital(capital);
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

	/**
	 * 
	 * @return capital of country
	 */
	public String getCapital() {
		return capital;
	}

	/**
	 * 
	 * @param capital
	 */
	public void setCapital(String capital) {
		this.capital = capital;
	}
	
	
}
