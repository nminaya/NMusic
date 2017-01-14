package Controller;

import Model.Rol;
import View.ListaRolesView;

public class ListaRolesController {
	private ListaRolesView view;
	private static ListaRolesController instance = null;
	
	public static ListaRolesController getInstance(){
		if(instance == null)
			instance = new ListaRolesController();
		
		return instance;
	}
		
	public void openView() throws Exception{
		view = new ListaRolesView();
		view.setResizable(false);
		view.setSize(610, 500);
		view.setTitle("NeOnMusic - Lista de Roles");
		
		Rol[] roles = DataAccess.InterfaceDAL.getInstance().getAllRoles();
		String[][] rowsRoles = Helper.convertRolestoArrayRows(roles);
	
		view.setView();
		view.setTable(rowsRoles);
		view.setVisible(true);
	}

	public void openCrearRol() throws Exception {
		CrearRolController controller = new CrearRolController();
		controller.openView();
	}
	
	public void openEditarRol(int rolID) throws Exception{
		EditarRolController controller = new EditarRolController();
		controller.openView();
		
		Rol rol = DataAccess.InterfaceDAL.getInstance().getRol(rolID);
		controller.setRol(rol);
	}
}
