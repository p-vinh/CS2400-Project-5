public class AirportInfo {
	private String iata;
	private String city;
	private String airportName;
	private String state;

	public AirportInfo() {
	}

	public AirportInfo(String label) {
		String[] temp = label.split(",");
		iata = temp[0];
		city = temp[1];
		airportName = temp[2];
		state = temp[3];
	}
	
	public AirportInfo(String iata, String city, String airportName, String state) {
		this.iata = iata;
		this.city = city;
		this.airportName = airportName;
		this.state = state;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return getCity() + " " + getAirportName() + " " + getState();
	}


	
}