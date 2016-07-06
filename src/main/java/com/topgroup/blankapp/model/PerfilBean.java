package com.topgroup.blankapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import com.topgroup.commons.crud.annotations.DisplayLabel;
import com.topgroup.commons.crud.annotations.EditableAttributes;
import com.topgroup.commons.crud.annotations.EntityKey;
import com.topgroup.commons.crud.annotations.FilterAttributes;
import com.topgroup.commons.crud.annotations.Required;
import com.topgroup.commons.crud.annotations.SuccessMessages;
import com.topgroup.commons.crud.annotations.TableAttribute;
import com.topgroup.commons.crud.annotations.TableAttributes;
import com.topgroup.commons.crud.annotations.UrlView;
import com.topgroup.commons.crud.model.CrudViewBean;
import com.topgroup.commons.security.model.Role;
import com.topgroup.commons.security.model.Profile;
import com.topgroup.commons.security.model.System;

@SuppressWarnings("serial")
@EntityKey("perfilBean")
@EditableAttributes( { "name", "abbreviation", "roles" })
@TableAttributes( { @TableAttribute(attribue = "id", messageKey = "perfil.codigo.label"), 
	@TableAttribute(attribue = "name", messageKey = "perfil.name.label"), 
	@TableAttribute(attribue = "abbreviation", messageKey = "perfil.abreviatura.label") })
@FilterAttributes( { "name" })
@UrlView(listUrl = "perfil/perfilBeanSearch", editUrl = "perfil/perfilBeanEdit")
@DisplayLabel(label="name")
@SuccessMessages(createKey = "perfil.create.message.success", deleteKey = "perfil.delete.message.success", editKey = "perfil.edit.message.success")
public class PerfilBean extends Profile implements CrudViewBean {
		
	public PerfilBean() {
		super.setRoles(new HashSet<Role>());
	}

	public PerfilBean(Integer id) {
		super.setRoles(new HashSet<Role>());
		super.setId(id);
	}

	public Collection<Role> getRolesPerfil() {
		Collection<Role> roles = new ArrayList<Role>();
		Iterator<Role> it = getRoles().iterator();
		while (it.hasNext()) {
			Role rol = (Role) it.next();
			roles.add(rol);
		}
		return roles;
	}
		
	@DisplayLabel(messageKey = "perfil.codigo.label")
	public Integer getId() {
		return super.getId();
	}
	
	@DisplayLabel(messageKey = "perfil.name.label")
	@Required
	public String getName(){
		return super.getName();
	}
	
	@DisplayLabel(messageKey = "perfil.abreviatura.label")
	public String getAbbreviation() {
		return super.getAbbreviation();
	}
		
	public Date getValidFrom() {
		return super.getValidFrom();
	}

	public Date getValidTo() {
		return super.getValidTo();
	}

	public System getSystem() {
		return super.getSystem();
	}
	
	@Override
	public void initialize() {
		setName("");
		setAbbreviation("");
		setSystem(null);
		setRoles(null);
	}
	
}