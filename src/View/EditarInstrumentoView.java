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
import Controller.InventarioInstrumentosController;
import Model.Categoria;
import Model.Instrumento;
import Model.Marca;

public class EditarInstrumentoView extends JFrame {
	private JLabel instrumentoIDLabel;
	private JLabel instrumentoIDValue;
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

	public EditarInstrumentoView() {
		setLayout(null);
		setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
	}

	public void setView() {
		registrarInstrumentoLabel = new JLabel();
		registrarInstrumentoLabel.setFont(new java.awt.Font("Tahoma", 0, 36));
		registrarInstrumentoLabel.setBounds(10, 10, 400, 50);
		registrarInstrumentoLabel.setText("Editar Instrumento");
		add(registrarInstrumentoLabel);

		instrumentoIDLabel = new JLabel("ID: ");
		instrumentoIDLabel.setBounds(20, 80, 150, 20);
		add(instrumentoIDLabel);

		instrumentoIDValue = new JLabel();
		instrumentoIDValue.setBounds(150, 80, 150, 20);
		add(instrumentoIDValue);

		nombreLabel = new JLabel();
		nombreLabel.setBounds(20, 110, 150, 20);
		nombreLabel.setText("Nombre: ");
		add(nombreLabel);

		nombreField = new JTextField();
		nombreField.setBounds(150, 110, 450, 20);
		add(nombreField);

		precioLabel = new JLabel();
		precioLabel.setBounds(20, 140, 150, 20);
		precioLabel.setText("Precio: ");
		add(precioLabel);

		precioField = new DoubleJTextField();
		precioField.setBounds(150, 140, 450, 20);
		add(precioField);

		cantidadDisponibleLabel = new JLabel();
		cantidadDisponibleLabel.setBounds(20, 170, 150, 20);
		cantidadDisponibleLabel.setText("Cantidad Disponible: ");
		add(cantidadDisponibleLabel);

		cantidadDisponibleField = new NumberJTextField();
		cantidadDisponibleField.setBounds(150, 170, 450, 20);
		add(cantidadDisponibleField);

		marcaLabel = new JLabel("Marca: ");
		marcaLabel.setBounds(20, 200, 150, 20);
		add(marcaLabel);

		marca = getMarcas();
		marca.setBounds(150, 200, 450, 20);
		add(marca);

		categoriaLabel = new JLabel("Categoria: ");
		categoriaLabel.setBounds(20, 240, 150, 20);
		add(categoriaLabel);

		categoria = getCategorias();
		categoria.setBounds(150, 240, 450, 20);
		add(categoria);

		registrarInstrumentoButton = new JButton("Guardar");
		registrarInstrumentoButton.setBounds(200, 270, 200, 30);
		registrarInstrumentoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveChanges();
			}
		});
		add(registrarInstrumentoButton);
	}

	public void setInstrumento(Instrumento instrumento) {
		instrumentoIDValue.setText(String.valueOf(instrumento.getInstrumentoID()));
		nombreField.setText(instrumento.getNombreInstrumento());
		precioField.setText(String.valueOf(instrumento.getPrecio()));
		cantidadDisponibleField.setText(String.valueOf(instrumento.getCantidadDisponibe()));
		marca.setSelectedItem(instrumento.getMarca());
		categoria.setSelectedItem(instrumento.getCategoria());
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

	public void saveChanges() {
		boolean isValidForm = validateForm();
		if (isValidForm) {
			try {
				String nombreInstrumento = nombreField.getText();
				int cantidadDisponible = Integer.parseInt(cantidadDisponibleField.getText());
				double precioInstrumento = Double.parseDouble(precioField.getText());

				Instrumento instrumento = new Instrumento();
				instrumento.setInstrumentoID(Long.parseLong(instrumentoIDValue.getText()));
				instrumento.setNombreInstrumento(nombreInstrumento);
				instrumento.setCantidadDisponibe(cantidadDisponible);
				instrumento.setPrecio(precioInstrumento);
				instrumento.setMarca((Marca) marca.getSelectedItem());
				instrumento.setCategoria((Categoria) categoria.getSelectedItem());

				DataAccess.InterfaceDAL.getInstance().updateInstrumento(instrumento);

				Helper.showMessage("Cambios Guardados.");
				InventarioInstrumentosController.getInstance().refreshView();
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
