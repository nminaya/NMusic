package Controller;

import Model.*;

public class PrivilegiosUserHandler {
	private static final String REGISTRAR_VENTA = "Registrar Venta";
	private static final String REGISTRAR_INSTRUMENTO = "Registrar Instrumento";
	private static final String EDITAR_INSTRUMENTO = "Editar Instrumento";
	private static final String CREAR_USUARIO = "Crear Usuarios";
	private static final String CREAR_REPORTE = "Crear Reportes";
	private static final String CREAR_ROLES = "Crear Roles";
	
	public boolean canSeeOpcionesDeNegocio(){
		Usuario user = UserLogged.getInstance().getUser();
		String[] opcionesDeNegocio = {REGISTRAR_VENTA, EDITAR_INSTRUMENTO};
		for (Privilegio priv : user.getRol().getPrivilegios()) {
			for(int i = 0; i < opcionesDeNegocio.length; i++) {
				String nombrePrivilegio = opcionesDeNegocio[i];
				if(priv.getNombrePrivilegio().equals(nombrePrivilegio)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canSeeOpcionesAvanzadas() {
		Usuario user = UserLogged.getInstance().getUser();
		String[] opcionesAvanzadas = {CREAR_USUARIO, CREAR_REPORTE, CREAR_ROLES};
		for (Privilegio priv : user.getRol().getPrivilegios()) {
			for(int i = 0; i < opcionesAvanzadas.length; i++) {
				String nombrePrivilegio = opcionesAvanzadas[i];
				if(priv.getNombrePrivilegio().equals(nombrePrivilegio)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canRegistrarVenta(){
		Usuario user = UserLogged.getInstance().getUser();
		for(Privilegio priv : user.getRol().getPrivilegios()){
			if(priv.getNombrePrivilegio().equals(REGISTRAR_VENTA)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canRegistrarInstrumento(){
		Usuario user = UserLogged.getInstance().getUser();
		for(Privilegio priv : user.getRol().getPrivilegios()){
			if(priv.getNombrePrivilegio().equals(REGISTRAR_INSTRUMENTO)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canCrearUsuario(){
		Usuario user = UserLogged.getInstance().getUser();
		for(Privilegio priv : user.getRol().getPrivilegios()){
			if(priv.getNombrePrivilegio().equals(CREAR_USUARIO)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canCrearReporte(){
		Usuario user = UserLogged.getInstance().getUser();
		for(Privilegio priv : user.getRol().getPrivilegios()){
			if(priv.getNombrePrivilegio().equals(CREAR_REPORTE)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean canCrearRoles(){
		Usuario user = UserLogged.getInstance().getUser();
		for(Privilegio priv : user.getRol().getPrivilegios()){
			if(priv.getNombrePrivilegio().equals(CREAR_ROLES)){
				return true;
			}
		}
		
		return false;
	}
}
