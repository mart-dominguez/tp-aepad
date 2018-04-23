package isi.aepad.tp.modelo;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrdenCompraDetalle {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="ID_PRODUCTO")
	private Producto producto;
	
	private Integer cantidad;
	private Double precioUnitarioCompra;
	
	@ManyToOne
	@JoinColumn(name="ID_ORDEN")
	private OrdenCompra orden;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getPrecioUnitarioCompra() {
		return precioUnitarioCompra;
	}
	public void setPrecioUnitarioCompra(Double precioUnitarioCompra) {
		this.precioUnitarioCompra = precioUnitarioCompra;
	}
	public OrdenCompra getOrden() {
		return orden;
	}
	public void setOrden(OrdenCompra orden) {
		this.orden = orden;
	}
	
	
}
