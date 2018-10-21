package tvz.naprednaJava.rozi.AutoServis.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.model.Repair;

@Data
@NoArgsConstructor
public class RepairForm {

	private Repair repair;

	private FormMode mode;

	public RepairForm(FormMode mode) {
		super();
		this.mode = mode;
	}

	public RepairForm(FormMode mode, Repair repair) {
		super();
		this.mode = mode;
		this.repair = repair;
	}
}
