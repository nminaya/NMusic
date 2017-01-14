package Model;

public class Rol {
	private int rolID;
	private String nombreRol;
	private Privilegio[] privilegios;
	
	public void setRolID(int rolID) {
		this.rolID = rolID;
	}
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	public void setPrivilegios(Privilegio[] privilegios) {
		this.privilegios = privilegios;
	}
	public String getNombreRol() {
		return nombreRol;
	}
	public Privilegio[] getPrivilegios() {
		return privilegios;
	}
	public int getRolID() {
		return rolID;
	}
	
	@Override
	public String toString(){
		return nombreRol;
	}
	
	@Override
	public boolean equals(Object rol){
		return ((Rol) rol).getRolID() == this.rolID;
	}
}
