package Model;

public class DetalleVenta {
	private String nombreArticulo;
	private int cantidad;
	private double precio;
	private double monto;
	private long ventaID;
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public void setVentaID(long ventaID) {
		this.ventaID = ventaID;
	}
	public int getCantidad() {
		return cantidad;
	}
	public double getMonto() {
		return monto;
	}
	public String getNombreArticulo() {
		return nombreArticulo;
	}
	public double getPrecio() {
		return precio;
	}
	public long getVentaID() {
		return ventaID;
	}
}
