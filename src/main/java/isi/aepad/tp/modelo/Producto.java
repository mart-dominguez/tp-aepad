package isi.aepad.tp.modelo;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class Producto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String descripcion;
	private Double precio;
	
	@XmlTransient
	@OneToMany(mappedBy="producto")
	private List<OrdenCompraDetalle> compras;
	
	@XmlTransient
	@OneToMany(mappedBy="producto")
	private List<FacturaDetalle> ventas;
	
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="PRODUCTO_CATEGORIA", joinColumns=@JoinColumn(name="ID_PRODUCTO"),inverseJoinColumns=@JoinColumn(name="ID_CATEGORIA"))
	private List<Categoria> categoria;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	@XmlTransient
	public List<OrdenCompraDetalle> getCompras() {
		return compras;
	}
	public void setCompras(List<OrdenCompraDetalle> compras) {
		this.compras = compras;
	}
	
	@XmlTransient
	public List<FacturaDetalle> getVentas() {
		return ventas;
	}
	public void setVentas(List<FacturaDetalle> ventas) {
		this.ventas = ventas;
	}
	
	@XmlTransient
	public List<Categoria> getCategoria() {
		return categoria;
	}
	public void setCategoria(List<Categoria> categoria) {
		this.categoria = categoria;
	}
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
		Producto other = (Producto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
