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
@Table(name = "precio")
public class Precio extends BaseEntity<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String PRODUCTO = "producto";

		public static final String IMPORTE_PRECIO = "importePrecio";
	}

	private Producto producto;

	private BigDecimal importePrecio;

	@Id
	@Column(name = "c_precio")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_producto")
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Column(name = "i_precio")
	public BigDecimal getImportePrecio() {
		return importePrecio;
	}

	public void setImportePrecio(BigDecimal importePrecio) {
		this.importePrecio = importePrecio;
	}

}
