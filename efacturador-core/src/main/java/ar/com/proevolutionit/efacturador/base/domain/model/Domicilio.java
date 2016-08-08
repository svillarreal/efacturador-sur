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
@Table(name = "domicilio")
public class Domicilio extends BaseEntity<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String CALLE = "calle";

		public static final String ALTURA = "altura";

		public static final String UNIDAD_EDIFICIO = "unidadEdificio";

		public static final String PISO = "piso";

		public static final String DPTO = "dpto";

		public static final String CODIGO_POSTAL = "codigoPostal";

		public static final String PROVINCIA = "provincia";

		public static final String DATOS_ADICIONALES = "datosAdicionales";

	}

	private String calle;

	private String altura;

	private String piso;

	private String dpto;

	private String codigoPostal;

	private Provincia provincia;

	private String datosAdicionales;

	@Id
	@Column(name = "c_domicilio")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		return id;
	}

	@Column(name = "d_calle")
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	@Column(name = "d_numero")
	public String getAltura() {
		return altura;
	}

	public void setAltura(String numero) {
		this.altura = numero;
	}

	@Column(name = "d_piso")
	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	@Column(name = "d_dpto")
	public String getDpto() {
		return dpto;
	}

	public void setDpto(String dpto) {
		this.dpto = dpto;
	}

	@Column(name = "d_codigo_postal")
	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_provincia")
	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	@Column(name = "d_datos_adicionales")
	public String getDatosAdicionales() {
		return datosAdicionales;
	}

	public void setDatosAdicionales(String datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
	}

}
