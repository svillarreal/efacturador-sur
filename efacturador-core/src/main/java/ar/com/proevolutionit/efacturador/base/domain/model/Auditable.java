package ar.com.proevolutionit.efacturador.base.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.topgroup.commons.utils.model.Entity;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class Auditable<PK extends Serializable> extends BaseEntity<PK> {

	public interface Attribute extends Entity.Attribute {
		public static final String FECHA_ALTA = "fechaAlta";

		public static final String FECHA_MODIFICACION = "fechaModificacion";

		public static final String FECHA_BAJA = "fechaBaja";

		public static final String ACTIVO = "activo";
	}

	protected boolean activo = true;

	protected Integer idUsuarioAlta;

	protected Date fechaAlta;

	protected Integer idUsuarioModificacion;

	protected Date fechaModificacion;

	protected Integer idUsuarioBaja;

	protected Date fechaBaja;

	@Column(name = "s_activo", nullable = false)
	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Column(name = "n_usu_alta", updatable = false)
	public Integer getIdUsuarioAlta() {
		return idUsuarioAlta;
	}

	public void setIdUsuarioAlta(Integer idUsuarioAlta) {
		this.idUsuarioAlta = idUsuarioAlta;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_alta", length = 3594, updatable = false)
	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Column(name = "n_usu_modif")
	public Integer getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}

	public void setIdUsuarioModificacion(Integer idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_modif", length = 3594)
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@Column(name = "n_usu_baja")
	public Integer getIdUsuarioBaja() {
		return idUsuarioBaja;
	}

	public void setIdUsuarioBaja(Integer idUsuarioBaja) {
		this.idUsuarioBaja = idUsuarioBaja;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_baja", length = 3594)
	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public void audit(Integer idUsuario) {
		Date fecha = new Date();
		if (isNew()) {
			setIdUsuarioAlta(idUsuario);
			setFechaAlta(fecha);
		} else {
			setIdUsuarioModificacion(idUsuario);
			setFechaModificacion(fecha);
		}
	}

	public void delete(Integer idUsuario) {
		setActivo(Boolean.FALSE);
		setIdUsuarioBaja(idUsuario);
		setFechaBaja(new Date());
	}

	public void unDelete() {
		this.activo = true;
		this.idUsuarioBaja = null;
		this.fechaBaja = null;
	}

}
