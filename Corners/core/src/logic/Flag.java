package logic;

public class Flag {
	private String country;
	private String capital;
	private String flag;
	
	public Flag(String country, String capital, String flag){
		setCountry(country);
		setCapital(capital);
		setFlag(flag);
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}
