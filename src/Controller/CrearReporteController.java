package Controller;

import Model.IViewController;
import Model.Venta;
import View.CrearReporteView;
import View.ReporteVentasView;

public class CrearReporteController implements IViewController {

	private CrearReporteView view;
	private ReporteVentasView reporteView;
	
	private static CrearReporteController instance = null;
	
	public static CrearReporteController getInstance() {
		if(instance == null)
			instance = new CrearReporteController();
		
		return instance;
	}

	public void openView() throws Exception {
		view = new CrearReporteView();
		view.setResizable(false);
		view.setBounds(100, 100, 450, 267);
		view.setTitle("NeOnMusic - Crear Reporte");
		view.setView();
		view.setVisible(true);
	}
	
	public void openReporteVentas(String from, String to) throws Exception {	
		Venta[] ventas = DataAccess.InterfaceDAL.getInstance().getVentas(from, to);
		
		if(ventas.length != 0) {
			String[][] rowVentas = Helper.convertVentasToReporteRows(ventas);
			
			reporteView = new ReporteVentasView();
			reporteView.setResizable(false);
			reporteView.setBounds(150, 150, 635, 311);
			reporteView.setTitle("NeOnMusic - Reporte de Ventas");
			reporteView.setView();
			
			reporteView.setTable(rowVentas, ventas);
			
			reporteView.setVisible(true);
		}
		else {
			Helper.showMessage("No hay registro de ventas en el rango de fechas introducido.");
		}
		
		
	}
	
}
