package Model;

public class Privilegio {
	private int privilegioID;
	private String nombrePrivilegio;
	
	public void setNombrePrivilegio(String nombrePrivilegio) {
		this.nombrePrivilegio = nombrePrivilegio;
	}
	public void setPrivilegioID(int privilegioID) {
		this.privilegioID = privilegioID;
	}
	
	public String getNombrePrivilegio() {
		return nombrePrivilegio;
	}
	public int getPrivilegioID() {
		return privilegioID;
	}
}
