package Model;

public class Marca {
	private long marcaID;
	private String nombreMarca;
	
	public void setMarcaID(long marcaID) {
		this.marcaID = marcaID;
	}
	public void setNombreMarca(String nombreMarca) {
		this.nombreMarca = nombreMarca;
	}
	public long getMarcaID() {
		return marcaID;
	}
	public String getNombreMarca() {
		return nombreMarca;
	}
	
	@Override
	public String toString() {
		return nombreMarca;
	}
	
	@Override
	public boolean equals(Object marca) {
		return ((Marca) marca).getMarcaID() == this.marcaID;
	}
}
