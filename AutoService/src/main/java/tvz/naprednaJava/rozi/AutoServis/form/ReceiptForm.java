package tvz.naprednaJava.rozi.AutoServis.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.model.Receipt;

@Data
@NoArgsConstructor
public class ReceiptForm {

	private Receipt receipt;

	private FormMode mode;

	private Long reservationId;

	private Long serviceStationsId;

	public ReceiptForm(FormMode mode) {
		super();
		this.mode = mode;
	}

	public ReceiptForm(FormMode mode, Receipt receipt) {
		super();
		this.mode = mode;
		this.receipt = receipt;
	}
}
