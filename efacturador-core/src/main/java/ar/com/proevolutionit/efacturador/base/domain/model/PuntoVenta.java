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
@Table(name = "punto_venta")
public class PuntoVenta extends BaseEntity<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String EMPRESA = "empresa";

		public static final String TIPO_COMPROBANTE = "tipoComprobante";

		public static final String ALICUOTA = "alicuota";

		public static final String DESCRIPCION = "descripcion";

	}

	private Empresa empresa;

	private TipoComprobante tipoComprobante;

	private Alicuota alicuota;

	private String descripcion;

	@Id
	@Column(name = "c_punto_venta")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_empresa")
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_tipo_comprobante")
	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_alicuota")
	public Alicuota getAlicuota() {
		return alicuota;
	}

	public void setAlicuota(Alicuota alicuota) {
		this.alicuota = alicuota;
	}

}
