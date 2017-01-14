package Model;

public class ArticuloVenta {
	private int item;
	private String instrumento;
	private int cantidad;
	private double precio;
	private double monto;
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public void setInstrumento(String instrumento) {
		this.instrumento = instrumento;
	} 
	public void setItem(int item) {
		this.item = item;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getCantidad() {
		return cantidad;
	}
	public String getInstrumento() {
		return instrumento;
	}
	public int getItem() {
		return item;
	}
	public double getMonto() {
		return monto;
	}
	public double getPrecio() {
		return precio;
	}
}
