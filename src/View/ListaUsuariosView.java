package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controller.CrearUsuarioController;
import Controller.Helper;
import Controller.ListaArticulosController;
import Controller.ListaUsuariosController;
import Model.IViewController;
import Model.Usuario;

public class ListaUsuariosView extends JFrame {
	private JPanel contentPane;
	private JTextField filtrarField;
	private String[] columnNames;
	private JTable usuariosTable;
	private JPanel panel;
	private JButton editarButton;
	private JScrollPane scrollPane;
	
	public ListaUsuariosView(){
		columnNames = new String[] { "ID", "Nombre", "Apellido", "Username", "Rol"};
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
	}
	
	public void setView(){
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Lista de Usuarios");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblNewLabel.setBounds(12, 13, 314, 44);
		panel.add(lblNewLabel);
		
		JLabel lblFiltrar = new JLabel("Filtrar:");
		lblFiltrar.setBounds(12, 74, 48, 16);
		panel.add(lblFiltrar);
		
		filtrarField = new JTextField();
		filtrarField.setBounds(57, 71, 392, 22);
		filtrarField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				try {
					updateTable(filtrarField.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		panel.add(filtrarField);
		filtrarField.setColumns(10);
		
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.setBounds(461, 70, 122, 25);
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ListaUsuariosController.getInstance().openCrearUsuarios();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
		panel.add(btnCrearUsuario);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 103, 570, 2);
		panel.add(separator);
		
		editarButton = new JButton("Editar");
		editarButton.setBounds(250, 420, 122, 25);
		editarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indexRow = usuariosTable.getSelectedRow();
				int userID = Integer.parseInt((String) usuariosTable.getModel().getValueAt(indexRow, 0));				
				try {
					ListaUsuariosController.getInstance().openEditarUsuario(userID);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(editarButton);
	}
	
	public void setTable(String[][] rows) {
		DefaultTableModel model = new DefaultTableModel();

		for (int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}
		for (int i = 0; i < rows.length; i++) {
			model.addRow(rows[i]);
		}
		usuariosTable = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane = new JScrollPane(usuariosTable);
		scrollPane.setBounds(12, 115, 575, 290);
		panel.add(scrollPane);
	}
	
	public void updateTable(String filter) throws Exception {
		Usuario[] usuarios = DataAccess.InterfaceDAL.getInstance().getUsers(filter);
		
		String[][] rows = Helper.convertUsuariostoArrayRows(usuarios);
		
		panel.remove(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel();

		for (int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}
		for (int i = 0; i < rows.length; i++) {
			model.addRow(rows[i]);
		}
		usuariosTable = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane = new JScrollPane(usuariosTable);
		scrollPane.setBounds(12, 115, 575, 290);
		panel.add(scrollPane);
	}

}
