package ar.com.proevolutionit.efacturador.base.persistence.filter;

import java.util.Date;

import java.io.Serializable;

import com.topgroup.commons.utils.lang.BaseFilter;

public abstract class AuditableFilter<PK extends Serializable> extends BaseFilter<PK> {

	protected Boolean activo = Boolean.TRUE;

	private Date fechaAlta;

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

}