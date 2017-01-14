package Controller;

import Model.Instrumento;
import View.ListaArticulosView;

public class ListaArticulosController {
	private ListaArticulosView view;
	
	public void openView() throws Exception {
		view = new ListaArticulosView();
		view.setResizable(false);
		view.setSize(625, 460);
		view.setTitle("NeOnMusic - Lista Articulos");
		view.setView();
		Instrumento[] instrumentos = DataAccess.InterfaceDAL.getInstance().getAllInstrumentos();
		view.setTable(Helper.convertInstrumentstoArrayRow(instrumentos));
		view.setVisible(true);
	}
}
