package com.topgroup.blankapp.view.security.bean;

import com.topgroup.commons.vaadin.annotation.FieldForm;

@SuppressWarnings("serial")
public class ChangePassBean extends BlankPassBean {

	@FieldForm(messageKey = "user.changepass.oldPassword", required = true)
	private String oldPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
