package com.topgroup.blankapp.model;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.topgroup.commons.crud.annotations.Required;
import com.topgroup.commons.crud.model.CrudViewBean;
import com.topgroup.commons.security.model.Access;

public class AccessRowBean extends Access implements CrudViewBean {

	private static final long serialVersionUID = 1L;

	private AccessId id;
	
	@Required
	@NotNull
	private String idAcceso;
	
	private String descripcion;

	public AccessRowBean() {
		super();
	}

	public AccessRowBean(AccessId id) {
		super();
		this.id = id;
	}

	public AccessId getId() {
		return id;
	}

	public void setId(AccessId id) {
		this.id = id;
	}

	@Transient
	public String getIdAcceso() {
		return idAcceso == null ? (id == null ? null : id.toString()) : idAcceso;
	}

	public void setIdAcceso(String idAcceso) {
		this.idAcceso = idAcceso;
		if (idAcceso != null) {
			this.id = new AccessId(idAcceso);
			super.setActionId(this.id.getActionId());
			super.setResourceId(this.id.getResourceId());
		}
	}

	@Transient
	public String getCompositeId() {
		return id == null ? null : id.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AccessRowBean))
			return false;
		AccessRowBean other = (AccessRowBean) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	public String getDescripcion() {
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}

	@Override
	public void initialize() {
		setIdAcceso(null);
	}
}
