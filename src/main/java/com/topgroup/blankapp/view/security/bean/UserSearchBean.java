package com.topgroup.blankapp.view.security.bean;

import com.topgroup.commons.security.model.Profile;
import com.topgroup.commons.vaadin.annotation.FieldForm;
import com.topgroup.commons.vaadin.view.bean.ViewBean;

@SuppressWarnings("serial")
public class UserSearchBean implements ViewBean {

	@FieldForm(messageKey = "user.search.username")
	private String username;

	@FieldForm(messageKey = "user.search.name")
	private String name;

	@FieldForm(messageKey = "user.search.lastName")
	private String lastName;

	@FieldForm(messageKey = "user.search.profile")
	private Profile profile;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public void initialize() {
		username = "";
		name = "";
		lastName = "";
		profile = null;
	}

}
