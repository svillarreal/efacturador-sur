package ar.com.proevolutionit.efacturador.base.domain.model;

import java.math.BigDecimal;

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
@Table(name = "alicuota")
public class Alicuota extends BaseEntity<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String DESCRIPCION = "descripcion";

		public static final String VALOR = "valor";
	}

	private String descripcion;

	private BigDecimal valor;

	@Id
	@Column(name = "c_alicuota")
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

	@Column(name = "i_valor")
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
