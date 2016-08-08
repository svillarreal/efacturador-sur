package ar.com.proevolutionit.efacturador.base.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author svillarreal
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "condicion_venta")
public class CondicionVenta extends BaseEntity<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String DESCRIPCION = "descripcion";
	}

	private String descripcion;

	@Id
	@Column(name = "c_condicion_venta")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		return id;
	}

	@Column(name = "d_descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
