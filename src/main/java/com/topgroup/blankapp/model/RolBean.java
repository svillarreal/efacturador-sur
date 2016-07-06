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
import com.topgroup.commons.security.model.Access;
import com.topgroup.commons.security.model.Role;
import com.topgroup.commons.security.model.System;

@SuppressWarnings("serial")
@EntityKey("rolBean")
@EditableAttributes( { "name", "abbreviation", "accesses" })
@TableAttributes( { @TableAttribute(attribue = "id", messageKey = "rol.codigo.label"), 
	@TableAttribute(attribue = "name", messageKey = "rol.name.label"), 
	@TableAttribute(attribue = "abbreviation", messageKey = "rol.abreviatura.label") })
@FilterAttributes( { "name" })
@UrlView(listUrl = "rol/rolBeanSearch", editUrl = "rol/rolBeanEdit")
@DisplayLabel(label="name")
@SuccessMessages(createKey = "rol.create.message.success", deleteKey = "rol.delete.message.success", editKey = "rol.edit.message.success")
public class RolBean extends Role implements CrudViewBean {
	
	public RolBean() {
		super.setAccesses(new HashSet<Access>());
	}

	public RolBean(Integer id) {
		super.setAccesses(new HashSet<Access>());
		super.setId(id);
	}
	
	public Collection<Access> getAccessRoles() {
		Collection<Access> accesses = new ArrayList<Access>();
		Iterator<Access> it = getAccesses().iterator();
		while (it.hasNext()) {
			Access acceso = (Access) it.next();
			accesses.add(acceso);
		}
		return accesses;
	}

	@DisplayLabel(messageKey = "rol.name.label")
	@Required
	public String getName(){
		return super.getName();
	}
	
	@DisplayLabel(messageKey = "rol.abreviatura.label")
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
		setAccesses(null);
	}

}
