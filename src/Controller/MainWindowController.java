package Controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import Model.IViewController;
import View.LoginView;

public class MainWindowController {
	private PrivilegiosUserHandler userPrivilegios;
	private RegistrarVentaController registrarVentaController;
	private IViewController inventarioInstrumentosController;
	private IViewController listaUsuariosController;
	private ListaRolesController listaRolesController;
	private CrearReporteController crearReporteController;
	public MainWindowController() {
		userPrivilegios = new PrivilegiosUserHandler();
	}
	
	public void openRevistrarVentaView() throws Exception {
		if (userPrivilegios.canRegistrarVenta()) {
			registrarVentaController = RegistrarVentaController.getInstance();
			registrarVentaController.openView();
		} else {
			Helper.showMessage("Este usuario no puede registrar ventas.");
		}
	}

	public void openInventarioInstrumentosView() throws Exception {
		if (userPrivilegios.canRegistrarInstrumento()) {
			inventarioInstrumentosController = InventarioInstrumentosController.getInstance();
			inventarioInstrumentosController.openView();
		} else {
			Helper.showMessage("Este usuario no puede ver listas de instrumentos.");
		}
	}

	public void openListaUsuariosView() throws Exception {
		if (userPrivilegios.canCrearUsuario()) {
			listaUsuariosController = ListaUsuariosController.getInstance();
			listaUsuariosController.openView();
		} else {
			Helper.showMessage("Este usuario no puede ver listas de usuarios.");
		}
	}

	public void openCrearReporte() throws Exception {
		if (userPrivilegios.canCrearReporte()) {
			crearReporteController = CrearReporteController.getInstance();
			crearReporteController.openView();
		} else {
			Helper.showMessage("Este usuario no puede crear reportes.");
		}
	}

	public void openListaRoles() {
		if (userPrivilegios.canCrearRoles()) {
			listaRolesController =  new ListaRolesController();
			try {
				listaRolesController.openView();
			} catch (Exception e) {
				Helper.showMessage(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			Helper.showMessage("Este usuario no puede ver Roles.");
		}
	}
	
	public boolean logOffUser() {
		int optionSelected = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir?", "Cerrar Sesion", JOptionPane.YES_NO_OPTION);
		
		if(optionSelected == JOptionPane.YES_OPTION) {
			UserLogged.getInstance().logOffUser();
			return true;
		}
		
		return false;
	}

	public void openLogginView() throws IOException {
		LoginView loginView = new LoginView();
        loginView.setSize(650,550);
        loginView.setResizable(false);
        loginView.setLocationRelativeTo(null);
        loginView.setTitle("NeOn Music - Login");
        loginView.setDefaultCloseOperation(LoginView.EXIT_ON_CLOSE);
        loginView.setVisible(true);
	}
}
