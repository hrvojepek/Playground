package tvz.naprednaJava.rozi.AutoServis.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.model.Station;

@Data
@NoArgsConstructor
public class StationForm {

	private Station station;
	
	private FormMode mode;
	
	private String openFrom;
	
	private String openUntil;
	
	public StationForm(FormMode mode) {
		super();
		this.mode = mode;
	}
	
	public StationForm(FormMode mode, Station station) {
		super();
		this.mode = mode;
		this.station = station;
	}
}
