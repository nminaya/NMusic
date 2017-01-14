package Model;

public class Credencial {
	private long credencialID;
	private String username;
	private String password;
		
	public void setCredencialID(long credencialID) {
		this.credencialID = credencialID;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public long getCredencialID() {
		return credencialID;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
}
