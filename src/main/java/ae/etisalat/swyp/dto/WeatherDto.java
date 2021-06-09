package ae.etisalat.swyp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class WeatherDto {

	private TempeartureDto main;
	

	public TempeartureDto getMain() {
		return main;
	}

	public void setMain(TempeartureDto main) {
		this.main = main;
	}

	

	
	
	
}
