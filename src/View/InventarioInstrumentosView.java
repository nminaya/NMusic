package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controller.Helper;
import Controller.InventarioInstrumentosController;
import Controller.RegistrarVentaController;
import Model.Instrumento;

public class InventarioInstrumentosView extends JFrame {
	private JLabel listaInstrumentosLabel;
	private JLabel filtrarLabel;
	private JTextField filtrarField;
	private JButton registrarInstrumentoButton;
	private JSeparator separador;
	private JScrollPane scrollPane;
	private JButton addButton;
	private JTable instrumentosTable;

	private String[] columnNames;

	public InventarioInstrumentosView() {
		columnNames = new String[] { "ID", "Instrumento", "Precio", "Cantidad Disponible", "Marca", "Categoria" };
		setLayout(null);
		setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
	}

	public void setView() {
		listaInstrumentosLabel = new JLabel();
		listaInstrumentosLabel.setFont(new java.awt.Font("Tahoma", 0, 36));
		listaInstrumentosLabel.setBounds(10, 10, 400, 50);
		listaInstrumentosLabel.setText("Lista de Instrumentos");
		add(listaInstrumentosLabel);

		filtrarLabel = new JLabel();
		filtrarLabel.setBounds(10, 70, 400, 50);
		filtrarLabel.setText("Filtrar: ");
		add(filtrarLabel);

		filtrarField = new JTextField();
		filtrarField.setBounds(60, 83, 340, 20);
		filtrarField.setToolTipText("Filtrar lista de instrumentos para que solo aparezcan los que coinciden con lo instruducido en este campo.");
		filtrarField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (Character.isLowerCase(keyChar)) {
					e.setKeyChar(Character.toUpperCase(keyChar));
				}
				try {
					updateTable(filtrarField.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		add(filtrarField);
		
		registrarInstrumentoButton = new JButton();
		registrarInstrumentoButton.setText("Registrar Instrumento");
		registrarInstrumentoButton.setBounds(410, 83, 200, 20);
		registrarInstrumentoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					InventarioInstrumentosController.getInstance().openRegistrarInstrumento();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(registrarInstrumentoButton);

		separador = new JSeparator();
		separador.setBounds(10, 105, 600, 20);
		add(separador);

		addButton = new JButton("Editar");
		addButton.setToolTipText("Editar instrumento seleccionado.");
		addButton.setBounds(260, 385, 100, 30);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indexRow = instrumentosTable.getSelectedRow();
				long instrumentID = Long.parseLong((String) instrumentosTable.getModel().getValueAt(indexRow, 0));
				try {
					InventarioInstrumentosController.getInstance().openEditarInstrumento(instrumentID);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(addButton);
	}

	public void setTable(String[][] rows) {
		DefaultTableModel model = new DefaultTableModel();

		for (int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}
		for (int i = 0; i < rows.length; i++) {
			model.addRow(rows[i]);
		}
		instrumentosTable = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane = new JScrollPane(instrumentosTable);
		scrollPane.setBounds(10, 120, 600, 260);
		add(scrollPane);
	}

	public void updateTable(String filter) throws Exception {

		Instrumento[] instrumentos = DataAccess.InterfaceDAL.getInstance().getInstrumentos(filter);

		String[][] rows = Helper.convertInstrumentstoArrayRow(instrumentos);

		remove(scrollPane);
		DefaultTableModel model = new DefaultTableModel();

		for (int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}
		for (int i = 0; i < rows.length; i++) {
			model.addRow(rows[i]);
		}
		instrumentosTable = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane = new JScrollPane(instrumentosTable);
		scrollPane.setBounds(10, 120, 600, 260);
		add(scrollPane);
	}
}
