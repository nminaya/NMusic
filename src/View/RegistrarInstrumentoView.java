package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.Helper;
import Model.Categoria;
import Model.Instrumento;
import Model.Marca;

public class RegistrarInstrumentoView extends JFrame implements ActionListener {
	private JLabel registrarInstrumentoLabel;
	private JLabel nombreLabel;
	private JLabel precioLabel;
	private JLabel cantidadDisponibleLabel;
	private JLabel marcaLabel;
	private JLabel categoriaLabel;
	private JTextField nombreField;
	private JTextField precioField;
	private JTextField cantidadDisponibleField;
	private JComboBox<Marca> marca;
	private JComboBox<Categoria> categoria;
	private JButton registrarInstrumentoButton;

	public RegistrarInstrumentoView() {
		setLayout(null);
		setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
	}

	public void setView() {
		registrarInstrumentoLabel = new JLabel();
		registrarInstrumentoLabel.setFont(new java.awt.Font("Tahoma", 0, 36));
		registrarInstrumentoLabel.setBounds(10, 10, 400, 50);
		registrarInstrumentoLabel.setText("Registrar Instrumento");
		add(registrarInstrumentoLabel);

		nombreLabel = new JLabel();
		nombreLabel.setBounds(20, 80, 150, 20);
		nombreLabel.setText("Nombre: ");
		add(nombreLabel);

		nombreField = new JTextField();
		nombreField.setBounds(150, 80, 450, 20);
		add(nombreField);

		precioLabel = new JLabel();
		precioLabel.setBounds(20, 110, 150, 20);
		precioLabel.setText("Precio: ");
		add(precioLabel);

		precioField = new DoubleJTextField();
		precioField.setBounds(150, 110, 450, 20);
		add(precioField);

		cantidadDisponibleLabel = new JLabel();
		cantidadDisponibleLabel.setBounds(20, 140, 150, 20);
		cantidadDisponibleLabel.setText("Cantidad Disponible: ");
		add(cantidadDisponibleLabel);

		cantidadDisponibleField = new NumberJTextField();
		cantidadDisponibleField.setBounds(150, 140, 450, 20);
		add(cantidadDisponibleField);

		marcaLabel = new JLabel("Marca: ");
		marcaLabel.setBounds(20, 170, 150, 20);
		add(marcaLabel);

		marca = getMarcas();
		marca.setBounds(150, 170, 450, 20);
		add(marca);

		categoriaLabel = new JLabel("Categoria: ");
		categoriaLabel.setBounds(20, 200, 150, 20);
		add(categoriaLabel);

		categoria = getCategorias();
		categoria.setBounds(150, 200, 450, 20);
		add(categoria);

		registrarInstrumentoButton = new JButton("Registrar Instrumento");
		registrarInstrumentoButton.setBounds(200, 240, 200, 30);
		registrarInstrumentoButton.addActionListener(this);
		add(registrarInstrumentoButton);
	}

	private JComboBox<Marca> getMarcas() {
		try {
			Marca[] marcas = DataAccess.InterfaceDAL.getInstance().getAllMarcas();
			JComboBox<Marca> marcasBox = new JComboBox<Marca>();
			for (Marca marca : marcas) {
				marcasBox.addItem(marca);
			}
			return marcasBox;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private JComboBox<Categoria> getCategorias() {
		try {
			Categoria[] categorias = DataAccess.InterfaceDAL.getInstance().getAllCategorias();
			JComboBox<Categoria> categoriasBox = new JComboBox<Categoria>();
			for (Categoria categoria : categorias) {
				categoriasBox.addItem(categoria);
			}
			return categoriasBox;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean validateForm() {
		boolean theReturn = !(Helper.stringIsNullOrEmpty(nombreField.getText()))
				&& !(Helper.stringIsNullOrEmpty(cantidadDisponibleField.getText()))
				&& !(Helper.stringIsNullOrEmpty(precioField.getText()));
		return theReturn;
	}

	public void actionPerformed(ActionEvent e) {
		boolean isValidForm = validateForm();
		if (isValidForm) {
			try {
				String nombreInstrumento = nombreField.getText();
				int cantidadDisponible = Integer.parseInt(cantidadDisponibleField.getText());
				double precioInstrumento = Double.parseDouble(precioField.getText());

				Instrumento instrumento = new Instrumento();
				instrumento.setNombreInstrumento(nombreInstrumento);
				instrumento.setCantidadDisponibe(cantidadDisponible);
				instrumento.setPrecio(precioInstrumento);
				instrumento.setMarca((Marca) marca.getSelectedItem());
				instrumento.setCategoria((Categoria) categoria.getSelectedItem());

				DataAccess.InterfaceDAL.getInstance().insertInstrumento(instrumento);
				Helper.showMessage("Instrumento registrado.");
				this.dispose();
			} catch (Exception e1) {
				e1.printStackTrace();
				Helper.showMessage(e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			Helper.showMessage("Complete todo el formulario.");
		}
	}
}

class NumberJTextField extends JTextField {
	public NumberJTextField() {
		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();

				if (!isNumber(ch) && ch != '\b') {
					e.consume();
				}
			}
		});

	}

	private boolean isNumber(char ch) {
		return ch >= '0' && ch <= '9';
	}

}

class DoubleJTextField extends JTextField {
	public DoubleJTextField() {
		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();

				if (!isNumber(ch) && !isValidSignal(ch) && !validatePoint(ch) && ch != '\b') {
					e.consume();
				}
			}
		});

	}

	private boolean isNumber(char ch) {
		return ch >= '0' && ch <= '9';
	}

	private boolean isValidSignal(char ch) {
		if ((getText() == null || "".equals(getText().trim())) && ch == '-') {
			return true;
		}

		return false;
	}

	private boolean validatePoint(char ch) {
		if (ch != '.') {
			return false;
		}

		if (getText() == null || "".equals(getText().trim())) {
			setText("0.");
			return false;
		} else if ("-".equals(getText())) {
			setText("-0.");
		}

		return true;
	}
}
