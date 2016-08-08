package ar.com.proevolutionit.efacturador.base.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author svillarreal
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "bonificacion")
public class Bonificacion extends BaseEntity<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String CLIENTE = "cliente";

		public static final String PUNTO_VENTA = "puntoVenta";

		public static final String BONIFICACION = "bonificacion";

		public static final String PRECIO = "precio";

	}

	private Cliente cliente;

	private PuntoVenta puntoVenta;

	private BigDecimal bonificacion;

	private Precio precio;

	@Id
	@Column(name = "c_bonificacion")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_cliente")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_punto_venta")
	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	@Column(name = "i_bonificacion")
	public BigDecimal getBonificacion() {
		return bonificacion;
	}

	public void setBonificacion(BigDecimal bonificacion) {
		this.bonificacion = bonificacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_precio")
	public Precio getPrecio() {
		return precio;
	}

	public void setPrecio(Precio precio) {
		this.precio = precio;
	}

}
