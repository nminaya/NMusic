package Controller;

import View.CrearRolView;

public class CrearRolController {
	private CrearRolView view;
	
	public void openView() throws Exception {
		view = new CrearRolView();
		view.setResizable(false);
		view.setBounds(100, 100, 503, 349);
		view.setTitle("NeOnMusic - Crear Rol");
		view.setView();
		view.setVisible(true);
	}
}
