package com.topgroup.blankapp.view.security.bean;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

import com.topgroup.commons.security.model.Participation;
import com.topgroup.commons.security.model.Profile;
import com.topgroup.commons.vaadin.annotation.FieldForm;

@SuppressWarnings("serial")
public class UserBean extends PasswordBean {

	private Integer idUser;

	@FieldForm(messageKey = "user.form.username", required = true)
	private String username;

	@FieldForm(messageKey = "user.form.name", required = true)
	@Pattern(regexp = "[a-zA-Z]*", message = "errors.caracteresespecialesynumeros")
	private String name;

	@FieldForm(messageKey = "user.form.lastName", required = true)
	@Pattern(regexp = "[a-zA-Z]*", message = "errors.caracteresespecialesynumeros")
	private String lastName;

	@FieldForm(messageKey = "user.form.profile", required = true)
	private Profile profile;
		
	@FieldForm(messageKey = "user.form.sector", required = true)
	private String sector;

	@FieldForm(messageKey = "user.form.email", required = true)
	@Email(message = "errors.email")
	private String email;

	@FieldForm(messageKey = "user.form.phone", required = true)
	@Pattern(regexp = "[0-9]*", message = "errors.caracteresespecialesyletras")
	private String phone;

	@FieldForm(messageKey = "user.form.fax")
	@Pattern(regexp = "[0-9]*", message = "errors.caracteresespecialesyletras")
	private String fax;
	
	@FieldForm(messageKey = "user.form.profile", required = true)
	private String profileName;
	
	private boolean enabled;

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@FieldForm(messageKey = "user.form.profile", required = true)
	public String getProfileName() {
		return this.profileName;
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

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
		
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setUser(Participation participation) {
		setIdUser(participation.getIduser());
		setUsername(participation.getUsername());
		setName(participation.getName());
		setLastName(participation.getLastName());
		setProfile(participation.getProfile());
		setProfileName(participation.getProfile().getName());
		setSector(participation.getSector());
		setEmail(participation.getEmail());
		setPhone(participation.getPhone());
		setFax(participation.getFax());
		setEnabled(participation.isEnabled());
	}

	@Override
	public void initialize() {
	}

	public boolean isNew() {
		return (getIdUser() == null);
	}

}
