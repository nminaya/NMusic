package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import Controller.Helper;
import Controller.MainWindowController;

public class MainWindowView extends JFrame {
	
	private JButton registrarVentaButton;
	private JButton inventarioInstrumentos;
	private JButton listaUsuariosButton;
	private JButton crearReporteButton;
	private JButton listaRolesButton;
	private JButton cerrarSesionButton;
	private JButton helpButton;
	private JLabel menuPrincipalLabel;
	private JLabel opcionesNegocioLabel;
	private JLabel opcionesAvanzadasLabel;
	private JSeparator separador1;
	private JSeparator separador2;
	
	private MainWindowController controller;
	
	public MainWindowView() {
		controller = new MainWindowController();
		setLayout(null);
		setView();
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
	}

	public void setView() {
		separador1 = new JSeparator();
		separador2 = new JSeparator();
		menuPrincipalLabel = new JLabel();
		opcionesNegocioLabel = new JLabel();
		opcionesAvanzadasLabel = new JLabel();
		registrarVentaButton = new JButton();
		inventarioInstrumentos = new JButton();
		listaUsuariosButton = new JButton();
		crearReporteButton = new JButton();
		listaRolesButton = new JButton();
		cerrarSesionButton = new JButton();
		helpButton = new JButton();

		menuPrincipalLabel.setFont(new java.awt.Font("Tahoma", 0, 36));
		menuPrincipalLabel.setText("Menú Principal");
		opcionesNegocioLabel.setText("Opciones de Negocio");
		inventarioInstrumentos.setText("Inventario de Instrumentos");
		crearReporteButton.setText("Crear Reporte");
		opcionesAvanzadasLabel.setText("Opciones Avanzadas");
		registrarVentaButton.setText("Registrar Venta");
		listaRolesButton.setText("Lista de Roles");
		listaUsuariosButton.setText("Lista de Usuarios");
		cerrarSesionButton.setText("Cerrar Sesion");
		helpButton.setText("Ayuda");

		menuPrincipalLabel.setBounds(10, 10, 400, 40);
		add(menuPrincipalLabel);
		
		opcionesNegocioLabel.setBounds(10, 70, 200, 20);
		add(opcionesNegocioLabel);
		
		separador1.setBounds(10, 90, 600, 20);
		add(separador1);
		
		registrarVentaButton.setBounds(10, 105, 290, 50);
		registrarVentaButton.setToolTipText("Ir a Registar Venta.");
		registrarVentaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.openRevistrarVentaView();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(registrarVentaButton);
		
		inventarioInstrumentos.setBounds(320, 105, 290, 50);
		inventarioInstrumentos.setToolTipText("Ir a Inventario de Instrumentos.");
		inventarioInstrumentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.openInventarioInstrumentosView();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(inventarioInstrumentos);
		
		opcionesAvanzadasLabel.setBounds(10, 170, 400, 40);
		add(opcionesAvanzadasLabel);
		
		separador2.setBounds(10, 200, 600, 20);
		add(separador2);
		
		listaUsuariosButton.setBounds(10, 215, 290, 50);
		listaUsuariosButton.setToolTipText("Ir a Lista de Usuarios.");
		listaUsuariosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.openListaUsuariosView();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		add(listaUsuariosButton);
		
		crearReporteButton.setBounds(320, 215, 290, 50);
		crearReporteButton.setToolTipText("Ir a Crear Reporte.");
		crearReporteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller.openCrearReporte();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		add(crearReporteButton);
		
		listaRolesButton.setBounds(10, 290, 290, 50);
		listaRolesButton.setToolTipText("Ir a Lista de Roles.");
		listaRolesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.openListaRoles();
			}
		});
		add(listaRolesButton);
		
		cerrarSesionButton.setBounds(490, 10, 120, 30);
		cerrarSesionButton.setToolTipText("Cerrar sesion");
		cerrarSesionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean logOffUser = controller.logOffUser();		
				if(logOffUser) {
					disposeView();
					try {
						controller.openLogginView();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		add(cerrarSesionButton);
		
		helpButton.setBounds(490, 45, 120, 30);
		helpButton.setToolTipText("Ver manual de usuario.");
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Helper.openHelpFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		add(helpButton);
	}
	
	private void disposeView() {
		this.dispose();
	}
}
