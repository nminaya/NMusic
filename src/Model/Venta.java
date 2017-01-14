package Model;

import java.util.Date;

public class Venta {
	private long ventaID;
	private String descripcionVenta;
	private Date fechaVenta;
	private DetalleVenta[] detalleVenta;
	
	public void setDescripcionVenta(String descripcionVenta) {
		this.descripcionVenta = descripcionVenta;
	}
	public void setDetalleVenta(DetalleVenta[] detalleVenta) {
		this.detalleVenta = detalleVenta;
	}
	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}
	public void setVentaID(long ventaID) {
		this.ventaID = ventaID;
	}
	public String getDescripcionVenta() {
		return descripcionVenta;
	}
	public DetalleVenta[] getDetalleVenta() {
		return detalleVenta;
	}
	public Date getFechaVenta() {
		return fechaVenta;
	}
	public long getVentaID() {
		return ventaID;
	}
	
}
