package isi.aepad.tp.modelo;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class OrdenCompra {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@OneToMany(mappedBy="orden")
	private List<OrdenCompraDetalle> detalles;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public List<OrdenCompraDetalle> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<OrdenCompraDetalle> detalles) {
		this.detalles = detalles;
	}
	
	
}
