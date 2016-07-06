package com.topgroup.blankapp.view.security.bean;

import com.topgroup.commons.security.model.User;
import com.topgroup.commons.vaadin.annotation.FieldForm;

@SuppressWarnings("serial")
public class BlankPassBean extends PasswordBean {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@FieldForm(messageKey = "user.search.username", readOnly = true)
	public String getUsername() {
		return user.getUsername();
	}

	@FieldForm(messageKey = "user.search.name", readOnly = true)
	public String getName() {
		return user.getName();
	}

	@FieldForm(messageKey = "user.search.lastName", readOnly = true)
	public String getLastName() {
		return user.getLastName();
	}

	@Override
	public void initialize() {

	}

}
