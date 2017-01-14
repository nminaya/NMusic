package Controller;

import Model.IViewController;
import Model.Instrumento;
import View.EditarInstrumentoView;

public class EditarInstrumentoController implements IViewController {
	private EditarInstrumentoView view;

	public void openView() throws Exception {
		view = new EditarInstrumentoView();
		view.setResizable(false);
		view.setSize(625, 350);
		view.setTitle("NeOnMusic - Editar Instrumento");
		view.setView();
		view.setVisible(true);
	}

	public void setInstrumento(long instrumentoID) throws Exception {
		Instrumento instrumento = DataAccess.InterfaceDAL.getInstance().getInstrumento(instrumentoID);
		view.setInstrumento(instrumento);
	}

	
}
