package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controller.Helper;
import Model.Venta;

public class ReporteVentasView extends JFrame {
	private JPanel contentPane;
	private JTextField desdeField;
	private JTextField hastaField;
	private JLabel lblTotal;
	private JButton btnExport;
	private double total;
	
	private String[] columnNames;
	private Venta[] ventas;
	private JScrollPane scrollPane;
	private JTable reporteTable;

	public ReporteVentasView() {
		columnNames = new String[] { "ID", "Descricion", "Fecha", "Monto"};
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
		total = 0;
	}
	
	public void setView() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblReporteDeVentas = new JLabel("Reporte de Ventas");
		lblReporteDeVentas.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblReporteDeVentas.setBounds(12, 13, 315, 51);
		contentPane.add(lblReporteDeVentas);
		
		lblTotal = new JLabel("Total: ");
		lblTotal.setBounds(450, 250, 100, 20);
		contentPane.add(lblTotal);
		
		btnExport = new JButton("Exportaar CSV");
		btnExport.setBounds(250, 13, 50, 51);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Helper.generateReport(ventas);
				
			}
		});
		//contentPane.add(btnExport);
		
	}
	
	public void setTable(String[][] rows, Venta[] ventas) {
		DefaultTableModel model = new DefaultTableModel();

		this.ventas = ventas;
		
		for (int i = 0; i < columnNames.length; i++) {
			model.addColumn(columnNames[i]);
		}
		for (int i = 0; i < rows.length; i++) {
			model.addRow(rows[i]);
			setTotal(rows[i]);
		}
		reporteTable = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane = new JScrollPane(reporteTable);
		scrollPane.setBounds(12, 77, 601, 160);
		contentPane.add(scrollPane);
	}

	private void setTotal(String[] row){
		total = total + Double.parseDouble(row[3]);
		
		lblTotal.setText("Total:  " + total);
	}
}
