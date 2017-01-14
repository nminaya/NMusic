package Controller;

import View.RegistrarVentaView;

public class RegistrarVentaController {
	private RegistrarVentaView registrarVentaView;

	private static RegistrarVentaController instance = null;

	public static RegistrarVentaController getInstance() {
		if (instance == null) {
			instance = new RegistrarVentaController();
		}
		return instance;
	}

	public RegistrarVentaView getRegistrarVentaView() {
		return registrarVentaView;
	}

	public void openView() throws Exception {
		registrarVentaView = new RegistrarVentaView();
		registrarVentaView.setResizable(false);
		registrarVentaView.setSize(625, 465);
		registrarVentaView.setTitle("NeOnMusic - Registrar Venta");
		registrarVentaView.setVisible(true);
	}

	public void openAddArticulos() throws Exception {
		ListaArticulosController articulosController = new ListaArticulosController();
		articulosController.openView();
	}
}
