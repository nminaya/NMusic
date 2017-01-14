package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import Controller.Helper;
import Model.Credencial;
import Model.Rol;
import Model.Usuario;

public class CrearUsuarioView extends JFrame {
	private JTextField nombreField;
	private JTextField apellidoField;
	private JFormattedTextField fechaNacField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JComboBox<Rol> rolList;

	public CrearUsuarioView() {
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
	}

	public void setView() {
		getContentPane().setLayout(null);

		JLabel lblCrearUsuario = new JLabel("Crear Usuario");
		lblCrearUsuario.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblCrearUsuario.setBounds(12, 13, 268, 42);
		getContentPane().add(lblCrearUsuario);

		JLabel lblInformacinBsica = new JLabel("Informaci\u00F3n B\u00E1sica");
		lblInformacinBsica.setBounds(22, 63, 137, 16);
		getContentPane().add(lblInformacinBsica);

		JSeparator separator = new JSeparator();
		separator.setBounds(25, 85, 470, 2);
		getContentPane().add(separator);

		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setBounds(32, 95, 56, 16);
		getContentPane().add(lblNewLabel);

		nombreField = new JTextField();
		nombreField.setBounds(147, 95, 348, 22);
		getContentPane().add(nombreField);
		nombreField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Apellido:");
		lblNewLabel_1.setBounds(32, 130, 56, 16);
		getContentPane().add(lblNewLabel_1);

		apellidoField = new JTextField();
		apellidoField.setBounds(147, 127, 348, 22);
		getContentPane().add(apellidoField);
		apellidoField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Fecha Nacimiento:");
		lblNewLabel_2.setBounds(32, 170, 106, 16);
		getContentPane().add(lblNewLabel_2);

		fechaNacField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
		fechaNacField.setBounds(147, 167, 348, 22);
		fechaNacField.setValue(new Date());
		getContentPane().add(fechaNacField);
		fechaNacField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Rol:");
		lblNewLabel_3.setBounds(32, 212, 56, 16);
		getContentPane().add(lblNewLabel_3);

		rolList = getRoles();
		rolList.setBounds(147, 212, 348, 22);
		getContentPane().add(rolList);

		JLabel lblCredenciales = new JLabel("Credenciales:");
		lblCredenciales.setBounds(22, 266, 95, 16);
		getContentPane().add(lblCredenciales);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(25, 285, 470, 2);
		getContentPane().add(separator_1);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(32, 300, 85, 16);
		getContentPane().add(lblUsername);

		usernameField = new JTextField();
		usernameField.setBounds(147, 300, 348, 22);
		getContentPane().add(usernameField);
		usernameField.setColumns(10);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(32, 342, 85, 16);
		getContentPane().add(lblContrasea);

		passwordField = new JPasswordField();
		passwordField.setBounds(147, 339, 348, 22);
		getContentPane().add(passwordField);

		JButton btnCrear = new JButton("Crear");
		btnCrear.setBounds(210, 386, 97, 25);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveUser();
			}
		});
		getContentPane().add(btnCrear);
	}

	private JComboBox<Rol> getRoles() {
		try {
			Rol[] roles = DataAccess.InterfaceDAL.getInstance().getAllRoles();
			JComboBox<Rol> rolesList = new JComboBox<Rol>();
			for (Rol rol : roles) {
				rolesList.addItem(rol);
			}

			return rolesList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private void saveUser() {
		boolean isValidForm = validateForm();

		if (isValidForm) {
			try {
				boolean userExist = usernameExist(usernameField.getText());
				if (userExist) {
					throw new Exception("Este usuario ya existe.");
				}
				Usuario user = new Usuario();
				user.setNombre(nombreField.getText());
				user.setApellido(apellidoField.getText());
				user.setFechaNac(new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacField.getText()));

				Credencial cred = new Credencial();
				cred.setUsername(usernameField.getText());
				cred.setPassword(new String(passwordField.getPassword()));

				user.setCredencial(cred);

				Rol rol = (Rol) rolList.getSelectedItem();

				user.setRol(rol);

				DataAccess.InterfaceDAL.getInstance().insertUser(user);
				
				Helper.showMessage("Usuario Creado.");
				this.dispose();

			} catch (Exception e) {
				Helper.showMessage(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		} else {
			Helper.showMessage("Complete todo el formulario.");
		}
	}

	private boolean validateForm() {
		boolean theReturn = !(Helper.stringIsNullOrEmpty(nombreField.getText()))
				&& !(Helper.stringIsNullOrEmpty(apellidoField.getText()))
				&& !(Helper.stringIsNullOrEmpty(fechaNacField.getText()))
				&& !(Helper.stringIsNullOrEmpty(usernameField.getText()))
				&& !(Helper.stringIsNullOrEmpty(passwordField.getText()));
		return theReturn;
	}

	private boolean usernameExist(String username) throws Exception {
		Credencial[] credencial = DataAccess.InterfaceDAL.getInstance().getCredencial(username);
		return credencial.length != 0;
	}
}
