package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import Controller.CrearReporteController;
import Controller.Helper;

public class CrearReporteView extends JFrame {
	private JPanel contentPane;
	private JFormattedTextField desdeText;
	private JFormattedTextField hastaText;

	public CrearReporteView() {
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
	}

	public void setView() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCrearReporteDe = new JLabel("Crear Reporte de Ventas");
		lblCrearReporteDe.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblCrearReporteDe.setBounds(12, 13, 434, 51);
		contentPane.add(lblCrearReporteDe);

		JLabel lblRangoDeFechas = new JLabel("Rango de Fechas");
		lblRangoDeFechas.setBounds(12, 90, 107, 16);
		contentPane.add(lblRangoDeFechas);

		JSeparator separator = new JSeparator();
		separator.setBounds(12, 110, 410, 2);
		contentPane.add(separator);

		JLabel lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(12, 130, 56, 16);
		contentPane.add(lblDesde);

		desdeText = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
		desdeText.setBounds(57, 127, 134, 22);
		desdeText.setValue(Helper.dateAddDays(new Date(), -90));
		getContentPane().add(desdeText);
		desdeText.setColumns(10);

		JLabel lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(203, 130, 56, 16);
		contentPane.add(lblHasta);

		hastaText = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
		hastaText.setBounds(249, 127, 154, 22);
		hastaText.setValue(new Date());
		getContentPane().add(hastaText);
		hastaText.setColumns(10);

		JButton btnCrearReporte = new JButton("Crear Reporte");
		btnCrearReporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isValidForm = !Helper.stringIsNullOrEmpty(desdeText.getText()) 
									&& !Helper.stringIsNullOrEmpty(hastaText.getText());
				
				if(isValidForm) {
					CrearReporteController controller = CrearReporteController.getInstance();
					try {
						controller.openReporteVentas(desdeText.getText(), hastaText.getText());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnCrearReporte.setBounds(149, 174, 134, 25);
		contentPane.add(btnCrearReporte);
	}
}
