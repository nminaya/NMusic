package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Controller.Helper;
import Controller.RegistrarVentaController;
import Model.Instrumento;

public class ListaArticulosView extends JFrame {
	private JLabel listaInstrumentosLabel;
	private JLabel filtrarLabel;
	private JTextField filtrarField;
	private JSeparator separador;
	private JScrollPane scrollPane;
	private JButton addButton;
	private JTable articulosTable;

	private String[] columnNames;

	public ListaArticulosView() {
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
		filtrarField.setToolTipText("Filtrar lista de instrumentos para que solo aparezcan los que coinciden con lo introducido en el campo.");
		filtrarField.setBounds(60, 83, 550, 20);
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

		separador = new JSeparator();
		separador.setBounds(10, 105, 600, 20);
		add(separador);

		addButton = new JButton("Añadir");
		addButton.setToolTipText("Añadir a la lista de articulos de la venta.");
		addButton.setBounds(260, 385, 100, 30);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addSelectedItem();
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
		articulosTable = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane = new JScrollPane(articulosTable);
		scrollPane.setBounds(10, 120, 600, 260);
		add(scrollPane);
	}

	private void updateTable(String filter) throws Exception {

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
		articulosTable = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane = new JScrollPane(articulosTable);
		scrollPane.setBounds(10, 120, 600, 260);
		add(scrollPane);
	}

	private void addSelectedItem() throws Exception {
		RegistrarVentaController rvController = RegistrarVentaController.getInstance();

		String[][] oldRows = rvController.getRegistrarVentaView().getRowsTable();
		String[][] newRows = new String[oldRows.length + 1][6];

		for (int i = 0; i < newRows.length; i++) {
			if (i != (newRows.length - 1)) {
				for (int j = 0; j < 6; j++) {
					newRows[i][j] = oldRows[i][j];
				}
			} else {

				int index = articulosTable.getSelectedRow();
				TableModel model = articulosTable.getModel();

				newRows[i][0] = String.valueOf(i + 1);
				newRows[i][1] = model.getValueAt(index, 0).toString();
				newRows[i][2] = model.getValueAt(index, 1).toString();
				newRows[i][3] = "1";
				newRows[i][4] = model.getValueAt(index, 2).toString();
				newRows[i][5] = model.getValueAt(index, 2).toString();
			}
		}

		rvController.getRegistrarVentaView().updateTable(newRows);
		this.dispose();
	}
}
