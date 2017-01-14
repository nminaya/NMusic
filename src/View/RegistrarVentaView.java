package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.derby.client.am.DateTime;

import Controller.Helper;
import Controller.RegistrarVentaController;
import Model.DetalleVenta;
import Model.Venta;
import DataAccess.InterfaceDAL;

public class RegistrarVentaView extends JFrame {
	private JLabel registrarVentaLabel;
	private JLabel descripcionVentaLabel;
	private JTextArea descripcionVentaText;
	private JLabel listaArticulosLabel;
	private JSeparator separador;
	private JButton addArticuloButton;
	private JLabel totalLabel;
	private JLabel total;
	private JButton registrarVentaButton;
	private JScrollPane scrollPane;
	private JTable articulosTable;
	private String[] columnNames;
	private String[][] rowsTable;

	public RegistrarVentaView() throws Exception {
		setLayout(null);
		setLocationRelativeTo(null);
		columnNames = new String[] { "Item", "ID", "Instrumento", "Cantidad", "Precio", "Monto" };
		rowsTable = new String[][] {};
		setView();
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
	}

	public void setView() throws Exception {
		registrarVentaLabel = new JLabel();
		registrarVentaLabel.setFont(new java.awt.Font("Tahoma", 0, 36));
		registrarVentaLabel.setBounds(10, 10, 400, 50);
		registrarVentaLabel.setText("Registrar Venta");
		add(registrarVentaLabel);

		descripcionVentaLabel = new JLabel();
		descripcionVentaLabel.setBounds(10, 70, 400, 50);
		descripcionVentaLabel.setText("Descripción de Venta:");
		add(descripcionVentaLabel);

		descripcionVentaText = new JTextArea();
		descripcionVentaText.setToolTipText("Descripción que defina con más detalle la venta que se está registrando.");
		descripcionVentaText.setBounds(10, 115, 600, 75);
		add(descripcionVentaText);

		listaArticulosLabel = new JLabel();
		listaArticulosLabel.setBounds(10, 190, 400, 50);
		listaArticulosLabel.setText("Lista de articulos: ");
		add(listaArticulosLabel);

		separador = new JSeparator();
		separador.setBounds(10, 225, 600, 20);
		add(separador);

		addArticuloButton = new JButton();
		addArticuloButton.setText("Añadir Artículo");
		addArticuloButton.setToolTipText("Añadis articulo a la lista de articulos de la venta que se está registrando.");
		addArticuloButton.setBounds(460, 202, 150, 20);
		addArticuloButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RegistrarVentaController.getInstance().openAddArticulos();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(addArticuloButton);

		articulosTable = new JTable(rowsTable, columnNames);
		scrollPane = new JScrollPane(articulosTable);
		scrollPane.setBounds(10, 235, 600, 130);
		add(scrollPane);

		registrarVentaButton = new JButton("Registrar Venta");
		registrarVentaButton.setToolTipText("Registar Venta.");
		registrarVentaButton.setBounds(240, 380, 150, 30);
		registrarVentaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saveVenta();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					Helper.showMessage(e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

				}				
			}
		});
		add(registrarVentaButton);

		totalLabel = new JLabel();
		totalLabel.setText("Total: ");
		totalLabel.setBounds(460, 380, 50, 20);
		add(totalLabel);

		total = new JLabel();
		total.setBounds(500, 380, 70, 20);
		add(total);
	}

	public void updateTable(String[][] rows) throws Exception {

		remove(scrollPane);

		rowsTable = rows;

		DefaultTableModel model = new DefaultTableModel();

		for (int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}
		for (int i = 0; i < rowsTable.length; i++) {
			model.addRow(rowsTable[i]);
		}
		articulosTable = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 2;
			}
		};

		articulosTable.removeColumn(articulosTable.getColumnModel().getColumn(1));
		articulosTable.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				try {
					onCantidadUpdated(e.getFirstRow());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		scrollPane = new JScrollPane(articulosTable);
		scrollPane.setBounds(10, 235, 600, 130);
		add(scrollPane);

		updateTotal();
	}
	
	private void onCantidadUpdated(int row) throws Exception{
		String[][] rowsUpdated = rowsTable;
		
		int cantidad = Integer.parseInt(articulosTable.getModel().getValueAt(row, 3).toString()); 
		double precio = Double.parseDouble(articulosTable.getModel().getValueAt(row, 4).toString());
		rowsUpdated[row][5] = Double.toString(cantidad * precio);
		rowsUpdated[row][3] = Integer.toString(cantidad);
		updateTable(rowsUpdated);
	}

	private void updateTotal() {
		double totalAll = 0;
		for (int i = 0; i < rowsTable.length; i++) {
			totalAll += Double.parseDouble(rowsTable[i][5]);
		}
		total.setText(Double.toString(totalAll));
	}

	private void saveVenta() throws Exception{
		String descripcionVenta = descripcionVentaText.getText();
		if(descripcionVenta.length() == 0){
			Helper.showMessage("Proporcione descripción de Venta.");
			return;
		}
		if(rowsTable.length == 0){
			Helper.showMessage("Añada articulos a esta Venta.");
			return;
		}
		Venta venta = new Venta();
		venta.setDescripcionVenta(descripcionVentaText.getText());
		venta.setFechaVenta(null);
		
		DetalleVenta[] detalle = new DetalleVenta[rowsTable.length];
		
		for (int i = 0; i < rowsTable.length; i++) {
			detalle[i] = new DetalleVenta();
			detalle[i].setNombreArticulo(rowsTable[i][2]);
			detalle[i].setCantidad(Integer.parseInt(rowsTable[i][3]));
			detalle[i].setPrecio(Double.parseDouble(rowsTable[i][4]));
			detalle[i].setMonto(Double.parseDouble(rowsTable[i][5]));
			
			long instrumentID = Long.parseLong(rowsTable[i][1]);
			InterfaceDAL.getInstance().restarCantidadDisponibleInstrumento(instrumentID, detalle[i].getCantidad());
		}
		
		venta.setDetalleVenta(detalle);
		
		InterfaceDAL.getInstance().insertVenta(venta);
		
		Helper.showMessage("Venta Registrada.");
		
		this.dispose();
	}
	
	public String[][] getRowsTable() {
		return rowsTable;
	}
}
