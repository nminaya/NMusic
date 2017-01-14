package Controller;

import Model.IViewController;
import Model.Usuario;
import View.EditarUsuarioView;

public class EditarUsuarioController implements IViewController{

	private EditarUsuarioView view;
	
	public void openView() throws Exception {
		view = new EditarUsuarioView();
		view.setResizable(false);
		view.setSize(539, 475);
		view.setTitle("NeOnMusic - Editar Usuario");
		view.setView();
		view.setVisible(true);	
	}
	
	public void setUsuario(Usuario user){
		view.setUser(user);
	}
}
