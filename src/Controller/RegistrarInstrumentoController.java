package Controller;

import Model.IViewController;
import View.RegistrarInstrumentoView;

public class RegistrarInstrumentoController implements IViewController {
	private RegistrarInstrumentoView view;

	public void openView() throws Exception {
		view = new RegistrarInstrumentoView();
		view.setResizable(false);
		view.setSize(625, 320);
		view.setTitle("NeOnMusic - Registrar Instrumento");
		view.setView();
		view.setVisible(true);
	}

}
