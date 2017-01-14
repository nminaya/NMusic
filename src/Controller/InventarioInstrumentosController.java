package Controller;

import Model.IViewController;
import Model.Instrumento;
import View.InventarioInstrumentosView;

public class InventarioInstrumentosController implements IViewController {
	private static InventarioInstrumentosController instance = null;
	private InventarioInstrumentosView inventarioInstrumentosView;
	
	public static InventarioInstrumentosController getInstance(){
		if(instance == null)
			instance = new InventarioInstrumentosController();
		
		return instance;
	}
	public void openView() throws Exception {
		inventarioInstrumentosView = new InventarioInstrumentosView();
		inventarioInstrumentosView.setResizable(false);
		inventarioInstrumentosView.setSize(625, 465);
		inventarioInstrumentosView.setTitle("NeOnMusic - Inventario Instrumentos");
		inventarioInstrumentosView.setView();
		Instrumento[] instrumentos = DataAccess.InterfaceDAL.getInstance().getAllInstrumentos();
		inventarioInstrumentosView.setTable(Helper.convertInstrumentstoArrayRow(instrumentos));
		inventarioInstrumentosView.setVisible(true);
	}

	public void openRegistrarInstrumento() throws Exception {
		IViewController controller = new RegistrarInstrumentoController();
		controller.openView();
	}
	
	public void refreshView(){
		try {
			inventarioInstrumentosView.updateTable("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openEditarInstrumento(long instrumentoID) throws Exception {
		EditarInstrumentoController controller = new EditarInstrumentoController();
		controller.openView();
		controller.setInstrumento(instrumentoID);
	}
}
