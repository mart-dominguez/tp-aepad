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
	public List<Factura> getCompras() {
		return compras;
	}
	public void setCompras(List<Factura> compras) {
		this.compras = compras;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", mail=" + mail + "]";
	}
	
	
}
