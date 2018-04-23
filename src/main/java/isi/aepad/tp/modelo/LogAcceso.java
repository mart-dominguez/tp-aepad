package isi.aepad.tp.modelo;

public class LogAcceso {

	private String clase;
	private String metodo;
	private Long millisInicio;
	private Long millisFin;
	private Long duracion;
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	public Long getMillisInicio() {
		return millisInicio;
	}
	public void setMillisInicio(Long millisInicio) {
		this.millisInicio = millisInicio;
	}
	public Long getMillisFin() {
		return millisFin;
	}
	public void setMillisFin(Long millisFin) {
		this.millisFin = millisFin;
	}
	public Long getDuracion() {
		return duracion;
	}
	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}
	
	
}