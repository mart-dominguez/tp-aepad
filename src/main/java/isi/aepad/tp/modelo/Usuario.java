package isi.aepad.tp.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String mail;
	
	@XmlTransient
	@OneToMany(mappedBy="cliente")
	private List<Factura> compras;

	
	
	@OneToMany(mappedBy="cliente")
	private List<Pago> pagos;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@XmlTransient
	public List<Factura> getCompras() {
		return compras;
	}
	public void setCompras(List<Factura> compras) {
		this.compras = compras;
	}

	@XmlTransient
	public List<Pago> getPagos() {
		return pagos;
	}
	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

//	@Override
//	public String toString() {
//		return "Usuario [id=" + id + ", mail=" + mail + "]";
//	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
