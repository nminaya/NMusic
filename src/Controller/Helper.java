package Controller;

import java.io.File;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Desktop;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import DataAccess.InterfaceDAL;
import Model.DetalleVenta;
import Model.Instrumento;
import Model.ResultFromQuery;
import Model.Rol;
import Model.Usuario;
import Model.Venta;

public class Helper {
	
	public static String[][] convertVentasToReporteRows(Venta[] ventas) {
		String[][] ventasRows = new String[ventas.length][4];

		for (int i = 0; i < ventas.length; i++) {
			ventasRows[i][0] = String.valueOf(ventas[i].getVentaID());
			ventasRows[i][1] = ventas[i].getDescripcionVenta();
			ventasRows[i][2] = new SimpleDateFormat("yyyy-MM-dd").format(ventas[i].getFechaVenta());
			ventasRows[i][3] = String.valueOf(getSumMontoDetalleVenta(ventas[i].getDetalleVenta()));
		}

		return ventasRows;
	}
	
	public static String[][] convertInstrumentstoArrayRow(Instrumento[] instrumentos) {

		String[][] rowsInstrumentos = new String[instrumentos.length][6];

		for (int i = 0; i < instrumentos.length; i++) {
			rowsInstrumentos[i][0] = String.valueOf(instrumentos[i].getInstrumentoID());
			rowsInstrumentos[i][1] = instrumentos[i].getNombreInstrumento();
			rowsInstrumentos[i][2] = String.valueOf(instrumentos[i].getPrecio());
			rowsInstrumentos[i][3] = String.valueOf(instrumentos[i].getCantidadDisponibe());
			rowsInstrumentos[i][4] = instrumentos[i].getMarca().getNombreMarca();
			rowsInstrumentos[i][5] = instrumentos[i].getCategoria().getNombreCategoria();
		}

		return rowsInstrumentos;
	}
	
	public static String[][] convertUsuariostoArrayRows(Usuario[] usuarios){
		String[][] rowsUsuarios = new String[usuarios.length][6];

		for (int i = 0; i < usuarios.length; i++) {
			rowsUsuarios[i][0] = String.valueOf(usuarios[i].getUsuarioID());
			rowsUsuarios[i][1] = usuarios[i].getNombre();
			rowsUsuarios[i][2] = usuarios[i].getApellido();
			rowsUsuarios[i][3] = usuarios[i].getCredencial().getUsername();
			rowsUsuarios[i][4] = usuarios[i].getRol().getNombreRol();
		}

		return rowsUsuarios;
	}
	
	public static String[][] convertRolestoArrayRows(Rol[] roles){
		String[][] rowRoles = new String[roles.length][2];

		for (int i = 0; i < rowRoles.length; i++) {
			rowRoles[i][0] = String.valueOf(roles[i].getRolID());
			rowRoles[i][1] = roles[i].getNombreRol();
		}

		return rowRoles;
	}
	
	public static String constructWhere(String tableName, String filterValue) throws Exception {
		ResultFromQuery result = InterfaceDAL.getInstance().makeQuery(
				"select b.columnname from sys.systables a, sys.syscolumns b where a.tableid = b.referenceid "
						+ "AND a.tablename = '" + tableName + "'");
		if (result.getRows().length != 0) {
			StringBuilder where = new StringBuilder();
			where.append(" WHERE ");
			for (int i = 0; i < result.getRows().length; i++) {
				where.append("CAST(");
				where.append(result.getRows()[i][0]);
				where.append(" AS CHAR(50))");
				where.append(" like '%");
				where.append(filterValue);
				where.append("%' ");
				if(i != (result.getRows().length - 1)){
					where.append("OR ");
				}
			}
			return where.toString();
		}

		return "";
	}

	public static boolean stringIsNullOrEmpty(String s) {
	    return s == null || s.length() == 0;
	}

	public static boolean stringIsNullOrWhitespace(String s) {
	    return s == null || stringIsWhitespace(s);
	}
	
	public static boolean stringIsWhitespace(String s) {
	    int length = s.length();
	    if (length > 0) {
	        for (int i = 0; i < length; i++) {
	            if (!Character.isWhitespace(s.charAt(i))) {
	                return false;
	            }
	        }
	        return true;
	    }
	    return false;
	}
	
	public static String getTextFromTxt(String path) {
		BufferedReader br = null;

		String text = "";

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(path));

			while ((sCurrentLine = br.readLine()) != null) {
				text = text + sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return text;
	}

	public static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public static void showMessage(String message, String tittle, int messageType) {
		JOptionPane.showMessageDialog(null, message, tittle, messageType);
	}
	
	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}
		
	public static Date dateAddDays(Date date, int days)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, days); 
	    return cal.getTime();
	}
	
	public static void openHelpFile() throws IOException{
		File file = new File("help/help.pdf");
		Desktop desktop = Desktop.getDesktop();
		desktop.open(file);
	}
	
	public static void generateReport(Object object){
		String jsonString = "{\"infile\": " + toJson(object) + "}";

		
			
		JSONObject output;
		try {
			output = new JSONObject(jsonString);

			JSONArray docs = output.getJSONArray("infile");
			File file = new File("reporte.csv");
			String csv = CDL.toString(docs);
			
			FileUtils.writeStringToFile(file, csv);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static double getSumMontoDetalleVenta(DetalleVenta[] detalle) {
		double monto = 0;
		
		if(detalle != null){
			for (int i = 0; i < detalle.length; i++) {
				monto += detalle[i].getMonto();
			}
		}
			
		return monto;
	}
}

