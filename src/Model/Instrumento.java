package Model;

public class Instrumento {
	private long instrumentoID;
	private String nombreInstrumento;
	private double precio;
	private int cantidadDisponibe;
	private Marca marca;
	private Categoria categoria;
	
	public void setCantidadDisponibe(int cantidadDisponibe) {
		this.cantidadDisponibe = cantidadDisponibe;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public void setInstrumentoID(long instrumentoID) {
		this.instrumentoID = instrumentoID;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	public void setNombreInstrumento(String nombreInstrumento) {
		this.nombreInstrumento = nombreInstrumento;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public int getCantidadDisponibe() {
		return cantidadDisponibe;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public long getInstrumentoID() {
		return instrumentoID;
	}
	public Marca getMarca() {
		return marca;
	}
	public String getNombreInstrumento() {
		return nombreInstrumento;
	}
	public double getPrecio() {
		return precio;
	}
}
