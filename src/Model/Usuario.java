package Model;

import java.util.Date;

public class Usuario {
	private int usuarioID;
	private String nombre;
	private String apellido;
	private Date fechaNac;
	private Rol rol;
	private Credencial credencial;
	
	public void setUsuarioID(int usuarioID) {
		this.usuarioID = usuarioID;
	}	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setCredencial(Credencial credencial) {
		this.credencial = credencial;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public String getApellido() {
		return apellido;
	}
	public Credencial getCredencial() {
		return credencial;
	}
	public Date getFechaNac() {
		return fechaNac;
	}
	public Rol getRol() {
		return rol;
	}
	public String getNombre() {
		return nombre;
	}
	public int getUsuarioID() {
		return usuarioID;
	}
}
