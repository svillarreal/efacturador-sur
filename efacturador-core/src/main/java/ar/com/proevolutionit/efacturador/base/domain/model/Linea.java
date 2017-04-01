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
@Table(name = "linea")
public class Linea extends BaseEntity<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String COMPROBANTE = "comprobante";

		public static final String PRODUCTO = "producto";

		public static final String ALICUOTA = "alicuota";

		public static final String CANTIDAD = "cantidad";

		public static final String UNIDAD_MEDIDA = "unidadMedida";

	}

	private Comprobante comprobante;

	private Producto producto;

	private Alicuota alicuota;

	private BigDecimal cantidad;

	private UnidadMedida unidadMedida;

	@Id
	@Column(name = "c_linea")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_comprobante")
	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_producto")
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_alicuota")
	public Alicuota getAlicuota() {
		return alicuota;
	}

	public void setAlicuota(Alicuota alicuota) {
		this.alicuota = alicuota;
	}

	@Column(name = "n_cantidad")
	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_unidad_medida")
	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

}
