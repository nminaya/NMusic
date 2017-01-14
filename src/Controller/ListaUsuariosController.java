package Controller;

import Model.IViewController;
import Model.Usuario;
import View.ListaUsuariosView;

public class ListaUsuariosController implements IViewController {
	
	private ListaUsuariosView view;
	
	private static ListaUsuariosController instance = null;
	
	public static ListaUsuariosController getInstance(){
		if(instance == null){
			instance = new ListaUsuariosController();
		}
		
		return instance;
	}
	
	public void openView() throws Exception {
		view = new ListaUsuariosView();
		view.setResizable(false);
		view.setSize(610, 500);
		view.setTitle("NeOnMusic - Lista de Usuarios");
		
		Usuario[] usuarios = DataAccess.InterfaceDAL.getInstance().getAllUsers();
		String[][] rowUsuarios = Helper.convertUsuariostoArrayRows(usuarios);

		view.setView();
		view.setTable(rowUsuarios);
		view.setVisible(true);
	}

	public void refreshView(){
		try {
			view.updateTable("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openCrearUsuarios() throws Exception{
		IViewController controller = new CrearUsuarioController();
		controller.openView();
	}
	
	public void openEditarUsuario(int userID) throws Exception {
		Usuario user = DataAccess.InterfaceDAL.getInstance().getUser("usuarioID = " + userID);
		
		EditarUsuarioController controller = new EditarUsuarioController();
		controller.openView();
		controller.setUsuario(user);
	}
}
