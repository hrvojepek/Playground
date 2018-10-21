package tvz.naprednaJava.rozi.AutoServis.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import tvz.naprednaJava.rozi.AutoServis.enums.FormMode;
import tvz.naprednaJava.rozi.AutoServis.model.User;

@Data
@NoArgsConstructor
public class UserForm {

	private User user;

	private FormMode formMode;

	private String newPassword;

	private String newPasswordConfirm;

	public UserForm(FormMode mode) {
		super();
		this.setFormMode(mode);
	}

	public UserForm(FormMode mode, User user) {
		super();
		this.setFormMode(mode);
		this.setUser(user);
	}
}
