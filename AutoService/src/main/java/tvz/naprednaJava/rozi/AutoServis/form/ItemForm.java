package tvz.naprednaJava.rozi.AutoServis.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.model.Item;

@Data
@NoArgsConstructor
public class ItemForm {

	private Item item;

	private FormMode mode;
	
	private String unitsInStock;

	public ItemForm(FormMode mode) {
		super();
		this.mode = mode;
	}

	public ItemForm(FormMode mode, Item item) {
		super();
		this.mode = mode;
		this.item = item;
	}
}
