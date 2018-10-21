package tvz.naprednaJava.rozi.AutoServis.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.model.Reservation;

@Data
@NoArgsConstructor
public class ReservationForm {

	private Reservation reservation;

	private FormMode mode;

	private String repairStartDate;

	private String estimatedRepairEndDate;

	public ReservationForm(FormMode mode) {
		super();
		this.mode = mode;
	}

	public ReservationForm(FormMode mode, Reservation reservation) {
		super();
		this.mode = mode;
		this.reservation = reservation;
	}
}
