package com.topgroup.blankapp.view.security.bean;

import javax.validation.constraints.Size;

import com.topgroup.commons.vaadin.annotation.FieldForm;
import com.topgroup.commons.vaadin.view.bean.ViewBean;

@SuppressWarnings("serial")
public abstract class PasswordBean implements ViewBean {

	private String password;

	private String confirmPassword;

	@Size(min = 8, message = "security.user.error.invalidPassword.lengthMin")
	@FieldForm(messageKey = "user.blankpass.password", required = true)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@FieldForm(messageKey = "user.blankpass.confirmPassword", required = true)
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean validarPassword() {
		return getPassword() != null && getPassword().equals(getConfirmPassword());
	}

}
