package DataAccess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import Controller.Helper;
import Model.*;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class InterfaceDAL {
	private static InterfaceDAL instance = null;
	private Connection conn;
	private String url;
	private boolean isConected = false;

	public static InterfaceDAL getInstance() {
		if (instance == null) {
			instance = new InterfaceDAL();
		}
		return instance;
	}

	public InterfaceDAL() {
		url = Helper.getTextFromTxt("sql/connectToDB.txt");
	}

	public void closeConection() throws Exception {
		conn.close();
	}

	public void connect() throws Exception {
		try {
			conn = DriverManager.getConnection(url);
			isConected = true;
		} catch (SQLException e) {
			Helper.showMessage("Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Categoria[] getAllCategorias() throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Categorias");
		if (result.getRows().length != 0) {
			Categoria[] categorias = new Categoria[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				Categoria categoria = new Categoria();
				categoria.setCategoriaID(Long.parseLong(result.getRows()[i][0]));
				categoria.setNombreCategoria(result.getRows()[i][1]);
				categorias[i] = categoria;
			}
			return categorias;
		}
		return null;
	}

	public Instrumento[] getAllInstrumentos() throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Instrumentos");
		if (result.getRows().length != 0) {
			Instrumento[] instrumentos = new Instrumento[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				Instrumento instrument = new Instrumento();
				instrument.setInstrumentoID(Long.parseLong(result.getRows()[i][0]));
				instrument.setNombreInstrumento(result.getRows()[i][1]);
				instrument.setPrecio(Double.parseDouble(result.getRows()[i][2]));
				instrument.setCantidadDisponibe(Integer.parseInt(result.getRows()[i][3]));
				instrument.setCategoria(getCategoria(Long.parseLong(result.getRows()[i][4])));
				instrument.setMarca(getMarca(Long.parseLong(result.getRows()[i][5])));
				instrumentos[i] = instrument;
			}

			return instrumentos;
		}
		return null;
	}

	public Marca[] getAllMarcas() throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Marcas");
		if (result.getRows().length != 0) {
			Marca[] marcas = new Marca[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				Marca marca = new Marca();
				marca.setMarcaID(Long.parseLong(result.getRows()[i][0]));
				marca.setNombreMarca(result.getRows()[i][1]);
				marcas[i] = marca;
			}
			return marcas;
		}
		return null;
	}

	public Rol[] getAllRoles() throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Roles");
		if (result.getRows().length != 0) {
			Rol[] roles = new Rol[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				Rol rol = new Rol();
				rol.setRolID(Integer.parseInt(result.getRows()[i][0]));
				rol.setNombreRol(result.getRows()[i][1]);
				rol.setPrivilegios(getPrivilegios(rol.getRolID()));
				roles[i] = rol;
			}
			return roles;
		}
		return null;
	}

	public Usuario[] getAllUsers() throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Usuarios");

		if (result.getRows().length != 0) {
			Usuario[] usuarios = new Usuario[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				Usuario us = new Usuario();
				us.setUsuarioID(Integer.parseInt(result.getRows()[i][0]));
				us.setNombre(result.getRows()[i][1]);
				us.setApellido(result.getRows()[i][2]);
				us.setFechaNac(new SimpleDateFormat("yyyy-MM-dd").parse(result.getRows()[i][3]));
				us.setCredencial(this.getCredencial(Long.parseLong(result.getRows()[i][4])));
				us.setRol(getRol(Integer.parseInt(result.getRows()[i][5])));

				usuarios[i] = us;
			}

			return usuarios;
		}
		return null;
	}

	public Categoria getCategoria(long categoriaID) throws Exception {
		ResultFromQuery result = this
				.makeQuery("SELECT nombreCategoria FROM Categorias WHERE categoriaID = " + categoriaID);
		if (result.getRows().length != 0) {
			Categoria categoria = new Categoria();
			categoria.setCategoriaID(categoriaID);
			categoria.setNombreCategoria(result.getRows()[0][0]);

			return categoria;
		}
		return null;
	}

	public Credencial getCredencial(long credencialID) throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Credenciales WHERE credencialID = " + credencialID);

		Credencial cred = new Credencial();
		if (result.getRows().length != 0) {
			cred.setCredencialID(Long.parseLong(result.getRows()[0][0].toString()));
			cred.setUsername(result.getRows()[0][1].toString());
			cred.setPassword(result.getRows()[0][2].toString());

			return cred;
		} else {
			return null;
		}
	}

	public Credencial[] getCredencial(String username) throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Credenciales WHERE username = '" + username + "'");

		if (result.getRows().length != 0) {
			Credencial[] credenciales = new Credencial[result.getRows().length];

			for (int i = 0; i < result.getRows().length; i++) {
				Credencial cred = new Credencial();
				cred.setCredencialID(Long.parseLong(result.getRows()[i][0].toString()));
				cred.setUsername(result.getRows()[i][1].toString());
				cred.setPassword(result.getRows()[i][2].toString());

				credenciales[i] = cred;
			}

			return credenciales;
		} else {
			return null;
		}
	}

	public Credencial getCredencial(String username, String password) throws Exception {

		ResultFromQuery result = this.makeQuery(
				"SELECT * FROM Credenciales" + " where username = '" + username + "' and pass = '" + password + "'");
		Credencial cred = new Credencial();

		if (result.getRows().length != 0) {
			cred.setCredencialID(Long.parseLong(result.getRows()[0][0].toString()));
			cred.setUsername(result.getRows()[0][1].toString());
			cred.setPassword(result.getRows()[0][2].toString());

			return cred;
		} else {
			return null;
		}
	}

	public Instrumento getInstrumento(long instrumentoID) throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Instrumentos where instrumentoID = " + instrumentoID);
		if (result.getRows().length != 0) {
			Instrumento instrumento = new Instrumento();
			instrumento.setInstrumentoID(instrumentoID);
			instrumento.setNombreInstrumento(result.getRows()[0][1]);
			instrumento.setPrecio(Double.parseDouble(result.getRows()[0][2]));
			instrumento.setCantidadDisponibe(Integer.parseInt(result.getRows()[0][3]));
			instrumento.setCategoria(getCategoria(Long.parseLong(result.getRows()[0][4])));
			instrumento.setMarca(getMarca(Long.parseLong(result.getRows()[0][5])));
			return instrumento;
		}
		return null;
	}

	public Instrumento[] getInstrumentos(String filterWord) throws Exception {
		ResultFromQuery result = this
				.makeQuery("SELECT * FROM Instrumentos " + Helper.constructWhere("INSTRUMENTOS", filterWord));
		if (result.getRows().length != 0) {
			Instrumento[] instrumentos = new Instrumento[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				Instrumento instrument = new Instrumento();
				instrument.setInstrumentoID(Long.parseLong(result.getRows()[i][0]));
				instrument.setNombreInstrumento(result.getRows()[i][1]);
				instrument.setPrecio(Double.parseDouble(result.getRows()[i][2]));
				instrument.setCantidadDisponibe(Integer.parseInt(result.getRows()[i][3]));
				instrument.setCategoria(getCategoria(Long.parseLong(result.getRows()[i][4])));
				instrument.setMarca(getMarca(Long.parseLong(result.getRows()[i][5])));
				instrumentos[i] = instrument;
			}

			return instrumentos;
		}
		return null;
	}

	public Marca getMarca(long marcaID) throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT nombreMarca FROM Marcas WHERE marcaID = " + marcaID);
		if (result.getRows().length != 0) {
			Marca marca = new Marca();
			marca.setMarcaID(marcaID);
			marca.setNombreMarca(result.getRows()[0][0]);
			return marca;
		}
		return null;
	}

	public Privilegio getPrivilegio(int privilegioID) throws Exception {
		ResultFromQuery result = this
				.makeQuery("SELECT nombreprivilegio FROM Privilegios WHERE privilegioID = " + privilegioID);

		Privilegio priv = new Privilegio();
		if (result.getRows().length != 0) {
			priv.setPrivilegioID(privilegioID);
			priv.setNombrePrivilegio(result.getRows()[0][0]);

			return priv;
		} else {
			return null;
		}
	}

	public Privilegio getPrivilegio(String nombrePrivilegio) throws Exception {
		ResultFromQuery result = this
				.makeQuery("SELECT * FROM Privilegios WHERE nombrePrivilegio = '" + nombrePrivilegio + "'");

		Privilegio priv = new Privilegio();
		if (result.getRows().length != 0) {
			priv.setPrivilegioID(Integer.parseInt(result.getRows()[0][0]));
			priv.setNombrePrivilegio(result.getRows()[0][1]);

			return priv;
		} else {
			return null;
		}
	}

	public Privilegio[] getPrivilegios(int rolID) throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT privilegioID FROM Roles_privilegios WHERE rolID = " + rolID);

		if (result.getRows().length != 0) {
			Privilegio[] privilegios = new Privilegio[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				privilegios[i] = getPrivilegio(Integer.parseInt(result.getRows()[i][0]));
			}
			return privilegios;
		} else {
			return null;
		}
	}
	
	public Rol getRol(int rolID) throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Roles WHERE rolID = " + rolID);
		if (result.getRows().length != 0) {
			Rol rol = new Rol();
			rol.setRolID(rolID);
			rol.setNombreRol(result.getRows()[0][1]);
			rol.setPrivilegios(getPrivilegios(rolID));

			return rol;
		}
		return null;
	}

	public Rol[] getRoles(String filterValue) throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Roles " + Helper.constructWhere("ROLES", filterValue));
		if (result.getRows().length != 0) {
			Rol[] roles = new Rol[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				Rol rol = new Rol();
				rol.setRolID(Integer.parseInt(result.getRows()[i][0]));
				rol.setNombreRol(result.getRows()[i][1]);
				rol.setPrivilegios(getPrivilegios(rol.getRolID()));
				roles[i] = rol;
			}
			return roles;
		}
		return null;
	}

	public Usuario getUser(String whereCondition) throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Usuarios WHERE " + whereCondition);

		if (result.getRows().length != 0) {
			Usuario us = new Usuario();
			us.setUsuarioID(Integer.parseInt(result.getRows()[0][0]));
			us.setNombre(result.getRows()[0][1]);
			us.setApellido(result.getRows()[0][2]);
			us.setFechaNac(new SimpleDateFormat("yyyy-MM-dd").parse(result.getRows()[0][3]));
			us.setCredencial(this.getCredencial(Long.parseLong(result.getRows()[0][4])));
			us.setRol(getRol(Integer.parseInt(result.getRows()[0][5])));
			return us;
		}
		return null;
	}

	public Usuario[] getUsers(String filter) throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM Usuarios " + Helper.constructWhere("USUARIOS", filter));
		if (result.getRows().length != 0) {
			Usuario[] usuarios = new Usuario[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				Usuario us = new Usuario();
				us.setUsuarioID(Integer.parseInt(result.getRows()[i][0]));
				us.setNombre(result.getRows()[i][1]);
				us.setApellido(result.getRows()[i][2]);
				us.setFechaNac(new SimpleDateFormat("yyyy-MM-dd").parse(result.getRows()[i][3]));
				us.setCredencial(this.getCredencial(Long.parseLong(result.getRows()[i][4])));
				us.setRol(getRol(Integer.parseInt(result.getRows()[i][5])));
				usuarios[i] = us;
			}

			return usuarios;
		}
		return null;
	}

	public Venta[] getVentas(String fromDate, String toDate) throws Exception {
		String sql = "SELECT * FROM vendedor.ventas where fechaventa between '" + fromDate + "' AND '" + toDate + "'";

		ResultFromQuery result = this.makeQuery(sql);

		if (result.getRows().length != 0) {
			Venta[] ventas = new Venta[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				Venta venta = new Venta();
				venta.setVentaID(Integer.parseInt(result.getRows()[i][0]));
				venta.setDescripcionVenta(result.getRows()[i][1]);
				venta.setFechaVenta(new SimpleDateFormat("yyyy-MM-dd").parse(result.getRows()[i][2]));
				venta.setDetalleVenta(this.getDetalleVenta(venta.getVentaID()));

				ventas[i] = venta;
			}
			return ventas;
		}
		return new Venta[0];
	}

	private DetalleVenta[] getDetalleVenta(long ventaID) throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT * FROM vendedor.detalleventas where ventaID = " + ventaID);
		if (result.getRows().length != 0) {
			DetalleVenta[] detalle = new DetalleVenta[result.getRows().length];
			for (int i = 0; i < result.getRows().length; i++) {
				DetalleVenta det = new DetalleVenta();
				det.setNombreArticulo(result.getRows()[i][0]);
				det.setCantidad(Integer.parseInt(result.getRows()[i][1]));
				det.setPrecio(Double.parseDouble(result.getRows()[i][2]));
				det.setMonto(Double.parseDouble(result.getRows()[i][3]));
				det.setVentaID(ventaID);

				detalle[i] = det;
			}

			return detalle;
		}
		return null;
	}

	public void insertInstrumento(Instrumento instrumento) throws SQLException {
		Statement stat = conn.createStatement();
		String sql = "INSERT INTO Instrumentos (nombreInstrumento, cantidadDisponible, precio, categoriaID, marcaID)"
				+ "VALUES ('" + instrumento.getNombreInstrumento() + "', " + instrumento.getCantidadDisponibe() + ", "
				+ instrumento.getPrecio() + ", " + instrumento.getCategoria().getCategoriaID() + ", "
				+ instrumento.getMarca().getMarcaID() + ")";

		stat.execute(sql);
	}

	public void insertUser(Usuario usuario) throws Exception {
		Statement stat = conn.createStatement();

		String sqlCredencial = "INSERT INTO Credenciales (username, pass) VALUES (" + "'"
				+ usuario.getCredencial().getUsername() + "'," + "'" + usuario.getCredencial().getPassword() + "')";
		stat.execute(sqlCredencial);

		usuario.getCredencial().setCredencialID(getCredencialIDInserted());

		Calendar cal = Calendar.getInstance();
		cal.setTime(usuario.getFechaNac());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String date = year + "-" + month + "-" + day;

		String sql = "INSERT INTO Usuarios (nombre, apellido, fechaNacimiento, credencialID, rolID) VALUES " + "('"
				+ usuario.getNombre() + "', '" + usuario.getApellido() + "', '" + date + "',"
				+ usuario.getCredencial().getCredencialID() + ", " + usuario.getRol().getRolID() + ")";

		stat.execute(sql);
	}

	public void insertVenta(Venta venta) throws Exception {
		Statement stat = conn.createStatement();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String sql = "INSERT INTO vendedor.Ventas (nombreVenta, fechaVenta) VALUES('" + venta.getDescripcionVenta()
				+ "', '" + year + "-" + month + "-" + day + "')";

		stat.execute(sql);

		venta.setVentaID((long) (getVendaIDInserted()));

		insertDetalleVenta(venta);
	}

	public ResultFromQuery makeQuery(String query) throws Exception {

		Statement stat = conn.createStatement();

		ResultSet result = stat.executeQuery(query);

		int columnsCount = result.getMetaData().getColumnCount();

		int rowCount = getNumberRows(query);

		String[] columns = getColumnsName(query);

		String[][] rows = new String[rowCount][columnsCount];

		int forRowCount = 0;

		while (result.next()) {
			for (int i = 1; i <= columnsCount; i++) {
				rows[forRowCount][i - 1] = result.getString(i);
			}

			forRowCount++;
		}
		return new ResultFromQuery(columns, rows);
	}

	public void restarCantidadDisponibleInstrumento(long instrumentoID, int cantidad) throws Exception {
		Instrumento instrumento = getInstrumento(instrumentoID);
		instrumento.setCantidadDisponibe(instrumento.getCantidadDisponibe() - cantidad);
		updateInstrumento(instrumento);
	}

	public void saveRol(Rol rol) throws Exception {
		Statement stat = conn.createStatement();
		String sql = "INSERT INTO Roles (nombreRol) VALUES ('" + rol.getNombreRol() + "')";

		stat.execute(sql);

		rol.setRolID(getLastRolIDInserted());

		sql = "INSERT INTO roles_privilegios VALUES ";

		for (int i = 0; i < rol.getPrivilegios().length; i++) {
			sql += "(" + rol.getRolID() + "," + rol.getPrivilegios()[i].getPrivilegioID() + "),";
		}

		// remover coma
		sql = sql.substring(0, sql.length() - 1);

		stat.execute(sql);
	}

	public void updateRol(Rol rol) throws Exception {
		Statement stat = conn.createStatement();
		String sql = "UPDATE Roles set nombreRol = '" + rol.getNombreRol() + "' WHERE rolID = " + rol.getRolID();

		stat.execute(sql);

		stat.execute("DELETE FROM roles_privilegios WHERE rolID = " + rol.getRolID());

		sql = "INSERT INTO roles_privilegios VALUES ";

		for (int i = 0; i < rol.getPrivilegios().length; i++) {
			sql += "(" + rol.getRolID() + "," + rol.getPrivilegios()[i].getPrivilegioID() + "),";
		}

		// remover coma
		sql = sql.substring(0, sql.length() - 1);

		stat.execute(sql);
	}

	public void updateInstrumento(Instrumento instrumento) throws SQLException {
		Statement stat = conn.createStatement();
		String sql = "UPDATE instrumentos set " + "nombreinstrumento = '" + instrumento.getNombreInstrumento() + "',"
				+ "precio = " + instrumento.getPrecio() + "," + "cantidaddisponible = "
				+ instrumento.getCantidadDisponibe() + "," + "categoriaid = "
				+ instrumento.getCategoria().getCategoriaID() + "," + "marcaID = " + instrumento.getMarca().getMarcaID()
				+ " Where instrumentoid = " + instrumento.getInstrumentoID();
		stat.execute(sql);
	}

	public void updateUser(Usuario user) throws SQLException {
		Statement stat = conn.createStatement();

		Calendar cal = Calendar.getInstance();
		cal.setTime(user.getFechaNac());
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String date = year + "-" + month + "-" + day;

		String sql = "UPDATE Usuarios set " + "nombre = '" + user.getNombre() + "', " + "apellido = '"
				+ user.getApellido() + "', " + "fechaNacimiento = '" + date + "', " + "rolID = "
				+ user.getRol().getRolID() + " " + "WHERE usuarioID = " + user.getUsuarioID();
		stat.execute(sql);

		String sqlCredencial = "UPDATE Credenciales set " + "username = '" + user.getCredencial().getUsername() + "', "
				+ "pass = '" + user.getCredencial().getPassword() + "' " + "WHERE credencialID = "
				+ user.getCredencial().getCredencialID();

		stat.execute(sqlCredencial);

	}

	public boolean isConected(){
		return isConected;
	}
	
	private String[] getColumnsName(String sql) throws Exception {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();

		int columnCount = rsmd.getColumnCount();
		String[] columns = new String[columnCount];
		// The column count starts from 1
		for (int i = 1; i <= columnCount; i++) {
			columns[i - 1] = rsmd.getColumnName(i);
		}

		return columns;
	}

	private long getCredencialIDInserted() throws Exception {
		ResultFromQuery result = this
				.makeQuery("SELECT credencialID FROM Credenciales order by credencialID desc FETCH FIRST ROW ONLY");
		if (result.getRows().length != 0) {
			long credencialID = Long.parseLong(result.getRows()[0][0]);
			return credencialID;
		}
		return 0;
	}

	private int getLastRolIDInserted() throws Exception {
		ResultFromQuery result = this.makeQuery("SELECT rolID FROM Roles order by rolID desc FETCH FIRST ROW ONLY");
		if (result.getRows().length != 0) {
			int privilegioID = Integer.parseInt(result.getRows()[0][0]);
			return privilegioID;
		}
		return 0;
	}

	private int getNumberRows(String sql) {
		try {
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultset = statement.executeQuery(sql);
			if (resultset.last()) {
				return resultset.getRow();
			} else {
				return 0;
			}
		} catch (Exception e) {
			System.out.println("Error getting row count");
			e.printStackTrace();
		}
		return 0;
	}

	private int getVendaIDInserted() throws Exception {
		ResultFromQuery result = this
				.makeQuery("SELECT ventaID FROM vendedor.ventas order by ventaid desc FETCH FIRST ROW ONLY");
		if (result.getRows().length != 0) {
			int ventaID = Integer.parseInt(result.getRows()[0][0]);
			return ventaID;
		}
		return 0;
	}

	private void insertDetalleVenta(Venta venta) throws SQLException {
		Statement stat = conn.createStatement();
		String sql = "INSERT INTO vendedor.detalleVentas VALUES ";
		for (int i = 0; i < venta.getDetalleVenta().length; i++) {
			sql += "('" + venta.getDetalleVenta()[i].getNombreArticulo() + "',"
					+ venta.getDetalleVenta()[i].getCantidad() + "," + venta.getDetalleVenta()[i].getPrecio() + ","
					+ venta.getDetalleVenta()[i].getMonto() + "," + venta.getVentaID() + ")";

			if (i != (venta.getDetalleVenta().length - 1)) {
				sql += ",";
			}
		}

		stat.executeLargeUpdate(sql);
	}

}
