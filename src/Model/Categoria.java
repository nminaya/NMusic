package Model;

public class Categoria {
	private long categoriaID;
	private String nombreCategoria;
	
	public void setCategoriaID(long categoriaID) {
		this.categoriaID = categoriaID;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	public long getCategoriaID() {
		return categoriaID;
	}
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	
	@Override
	public String toString(){
		return nombreCategoria;
	}
	
	@Override
	public boolean equals(Object categoria){
		return ((Categoria) categoria).getCategoriaID() == this.categoriaID;
	}
}
