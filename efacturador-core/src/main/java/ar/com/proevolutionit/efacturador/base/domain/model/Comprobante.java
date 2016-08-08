package ar.com.proevolutionit.efacturador.base.domain.model;

import java.util.Date;

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
@Table(name = "comprobante")
public class Comprobante extends Auditable<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String PUNTO_VENTA = "puntoVenta";

		public static final String TIPO_COMPROBANTE = "tipoComprobante";

		public static final String FECHA_COMPROBANTE = "fechaComprobante";

		public static final String MONEDA_EXTRANJERA = "monedaExtranjera";

		public static final String CONCEPTO = "concepto";

		public static final String CLIENTE = "cliente";

		public static final String PRECISION_CANTIDAD = "precisionCantidad";

		public static final String PRECISION_PRECIO_UNITARIO = "precisionPrecioUnitario";

	}

	private PuntoVenta puntoVenta;

	private TipoComprobante tipoComprobante;

	private Date fechaComprobante;

	private Boolean monedaExtranjera;

	private Concepto concepto;

	private Cliente cliente;

	private Integer precisionCantidad;

	private Integer precisionPrecioUnitario;

	@Id
	@Column(name = "c_comprobante")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_punto_venta")
	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_tipo_comprobante")
	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	@Column(name = "f_comprobante")
	public Date getFechaComprobante() {
		return fechaComprobante;
	}

	public void setFechaComprobante(Date fechaComprobante) {
		this.fechaComprobante = fechaComprobante;
	}

	@Column(name = "s_moneda_extranjera")
	public Boolean getMonedaExtranjera() {
		return monedaExtranjera;
	}

	public void setMonedaExtranjera(Boolean monedaExtranjera) {
		this.monedaExtranjera = monedaExtranjera;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_concepto")
	public Concepto getConcepto() {
		return concepto;
	}

	public void setConcepto(Concepto concepto) {
		this.concepto = concepto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_cliente")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Column(name = "n_precision_cantidad")
	public Integer getPrecisionCantidad() {
		return precisionCantidad;
	}

	public void setPrecisionCantidad(Integer precisionCantidad) {
		this.precisionCantidad = precisionCantidad;
	}

	@Column(name = "n_precision_precio_unitario")
	public Integer getPrecisionPrecioUnitario() {
		return precisionPrecioUnitario;
	}

	public void setPrecisionPrecioUnitario(Integer precisionPrecioUnitario) {
		this.precisionPrecioUnitario = precisionPrecioUnitario;
	}

}
