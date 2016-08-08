package ar.com.proevolutionit.efacturador.base.domain.model;

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
@Table(name = "producto")
public class Producto extends BaseEntity<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String EMPRESA = "empresa";

		public static final String PUNTO_VENTA = "puntoVenta";

		public static final String CODIGO_PRODUCTO = "codigoProducto";

		public static final String DESCRIPCION = "descripcion";
	}

	private Empresa empresa;

	private PuntoVenta puntoVenta;

	private String codigoProducto;

	private String descripcion;

	@Id
	@Column(name = "c_producto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_empresa")
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_punto_venta")
	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	@Column(name = "d_codigo_producto")
	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	@Column(name = "d_descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
