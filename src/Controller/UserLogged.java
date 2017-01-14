package Controller;

import Model.Usuario;

public class UserLogged {
	private Usuario usuario;
	private static UserLogged instance = null;
	
	public void setUser(Usuario user) {
		this.usuario = user;		
	}
	
	public Usuario getUser(){
		return this.usuario;
	}
	
	public void logOffUser() {
		this.usuario = null;
	}
	
	public static UserLogged getInstance() {
		if(instance == null) {
          	instance = new UserLogged();
	    }
	     return instance;
	}
}
