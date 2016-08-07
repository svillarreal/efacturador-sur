package ar.com.proevolutionit.efacturador.base.domain.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.topgroup.commons.utils.model.Entity;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> extends Entity<PK> {

	@Transient
	public boolean isNew() {
		return getId() == null;
	}

}
