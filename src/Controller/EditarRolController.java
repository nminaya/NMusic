package Controller;

import Model.Rol;
import View.EditarRolView;

public class EditarRolController {
	private EditarRolView view;
	
	public void openView() throws Exception {
		view = new EditarRolView();
		view.setResizable(false);
		view.setBounds(100, 100, 503, 349);
		view.setTitle("NeOnMusic - Crear Rol");
		view.setView();
		view.setVisible(true);
	}
	
	public void setRol(Rol rol){
		view.setRol(rol);
	}
}
