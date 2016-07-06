package com.topgroup.blankapp.view.security.bean;

import com.topgroup.commons.vaadin.annotation.FieldForm;
import com.topgroup.commons.vaadin.view.bean.ViewBean;

public class InhabilitarBean implements ViewBean {

	private static final long serialVersionUID = -4119943218497283145L;

	private Integer idUser;

	@FieldForm(messageKey = "user.inhabilitar.username", readOnly = true)
	private String username;

	@FieldForm(messageKey = "user.inhabilitar.motivo", required = true)
	private String motivo;

	public InhabilitarBean(){
		super();
	}
	
	public InhabilitarBean(Integer idUser, String username) {
		super();
		this.idUser = idUser;
		this.username = username;
	}

	@Override
	public void initialize() {
	}

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

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
