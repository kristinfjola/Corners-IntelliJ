/**
 * @author 	Steinunn Fridgeirsdottir
 * @date 	15.03.2015
 * @goal 	The class is a DTO for the values of the flags in the boxes
 * 			in the Flags category of the game.
 */
package logic;

public class Flag {
	private String country;
	private String capital;
	private String flag;
	
	/**
	 * @param country
	 * @param capital
	 * @param flag
	 */
	public Flag(String country, String capital, String flag){
		setCountry(country);
		setCapital(capital);
		setFlag(flag);
	}
	
	/**
	 * @return country of the Flag
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * @param country - the country of the Flag
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return Capital of the country of the Flag
	 */
	public String getCapital() {
		return capital;
	}
	
	/**
	 * @param capital - the capital of the flag
	 */
	public void setCapital(String capital) {
		this.capital = capital;
	}
	
	/**
	 * @return Flag of the country
	 */
	public String getFlag() {
		return flag;
	}
	
	/**
	 * @param flag - of the country
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
