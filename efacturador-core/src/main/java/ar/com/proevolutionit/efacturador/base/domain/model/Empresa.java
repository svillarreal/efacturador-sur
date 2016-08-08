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
@Table(name = "empresa")
public class Empresa extends BaseEntity<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String RAZON_SOCIAL = "razonSocial";

		public static final String CUIT = "cuit";

		public static final String DOMICILIO = "domicilio";
	}

	private String razonSocial;

	private String cuit;

	private Domicilio domicilio;

	@Id
	@Column(name = "c_empresa")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Override
	public Integer getId() {
		return id;
	}

	@Column(name = "d_razon_social")
	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	@Column(name = "d_cuit")
	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_domicilio")
	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

}
