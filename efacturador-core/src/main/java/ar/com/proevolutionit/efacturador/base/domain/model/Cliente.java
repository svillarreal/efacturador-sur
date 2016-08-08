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
@Table(name = "cliente")
public class Cliente extends BaseEntity<Integer> {

	public interface Attribute {
		public static final String ID = "id";

		public static final String EMPRESA = "empresa";

		public static final String CUIT = "cuit";

		public static final String DOMICILIO = "domicilio";

		public static final String EMAIL = "email";

	}

	private Empresa empresa;

	private String cuit;

	private Domicilio domicilio;

	private String email;

	@Id
	@Column(name = "c_cliente")
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

	@Column(name = "d_email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
