package View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.Helper;
import Model.Privilegio;
import Model.Rol;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DatabaseMetaData;

import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CrearRolView extends JFrame {

	private JPanel contentPane;
	private JTextField nombreRol;
	private JCheckBox[] privilegiosChbx;
	private JButton btnGuardar;

	public CrearRolView() {
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
		privilegiosChbx = new JCheckBox[5];
	}

	public void setView() {

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCrearRol = new JLabel("Crear Rol");
		lblCrearRol.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblCrearRol.setBounds(12, 13, 308, 58);
		contentPane.add(lblCrearRol);

		JLabel lblNombreDeRol = new JLabel("Nombre de Rol:");
		lblNombreDeRol.setBounds(22, 84, 115, 16);
		contentPane.add(lblNombreDeRol);

		nombreRol = new JTextField();
		nombreRol.setBounds(129, 81, 346, 22);
		contentPane.add(nombreRol);
		nombreRol.setColumns(10);

		JLabel lblPrivilegios = new JLabel("Privilegios");
		lblPrivilegios.setBounds(22, 123, 81, 16);
		contentPane.add(lblPrivilegios);

		JSeparator separator = new JSeparator();
		separator.setBounds(22, 145, 453, 2);
		contentPane.add(separator);

		privilegiosChbx[0] = new JCheckBox("Registrar Venta");
		privilegiosChbx[0].setBounds(24, 156, 139, 25);
		contentPane.add(privilegiosChbx[0]);

		privilegiosChbx[1] = new JCheckBox("Crear Reportes");
		privilegiosChbx[1].setBounds(24, 200, 139, 25);
		contentPane.add(privilegiosChbx[1]);

		privilegiosChbx[2] = new JCheckBox("Registrar Instrumento");
		privilegiosChbx[2].setBounds(167, 156, 164, 25);
		contentPane.add(privilegiosChbx[2]);

		privilegiosChbx[3] = new JCheckBox("Crear Roles");
		privilegiosChbx[3].setBounds(167, 200, 113, 25);
		contentPane.add(privilegiosChbx[3]);

		privilegiosChbx[4] = new JCheckBox("Crear Usuarios");
		privilegiosChbx[4].setBounds(352, 156, 113, 25);
		contentPane.add(privilegiosChbx[4]);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(183, 264, 97, 25);
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRol();
			}
		});
		contentPane.add(btnGuardar);
	}

	private void saveRol() {
		boolean isValidForm = validateForm();

		if (isValidForm) {
			Rol rol = new Rol();
			rol.setNombreRol(nombreRol.getText());
			rol.setPrivilegios(getPrivilegiosSelected());

			try {
				DataAccess.InterfaceDAL.getInstance().saveRol(rol);
				Helper.showMessage("Rol Guardado");
				this.dispose();
			} catch (Exception e) {
				Helper.showMessage(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		} else {
			Helper.showMessage("Complete todo el formulario.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean validateForm() {
		boolean theReturn = !Helper.stringIsNullOrEmpty(nombreRol.getText()) && (privilegiosChbx[0].isSelected()
				|| privilegiosChbx[1].isSelected() || privilegiosChbx[2].isSelected() || privilegiosChbx[3].isSelected()
				|| privilegiosChbx[4].isSelected());
		return theReturn;
	}

	private Privilegio[] getPrivilegiosSelected() {
		int selectedsCount = 0;

		for (int i = 0; i < privilegiosChbx.length; i++) {
			if (privilegiosChbx[i].isSelected()) {
				selectedsCount++;
			}
		}

		Privilegio[] privilegios = new Privilegio[selectedsCount];

		for (int i = 0, j = 0; i < privilegiosChbx.length; i++) {
			if (privilegiosChbx[i].isSelected()) {
				try {
					privilegios[j] = DataAccess.InterfaceDAL.getInstance().getPrivilegio(privilegiosChbx[i].getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				j++;
			}
		}

		return privilegios;
	}
}
