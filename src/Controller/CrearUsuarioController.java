package Controller;

import Model.IViewController;
import View.CrearUsuarioView;

public class CrearUsuarioController implements IViewController {

	private CrearUsuarioView view;

	public void openView() throws Exception {
		view = new CrearUsuarioView();
		view.setResizable(false);
		view.setSize(539, 475);
		view.setTitle("NeOnMusic - Crear Usuario");
		view.setView();
		view.setVisible(true);
	}

}
