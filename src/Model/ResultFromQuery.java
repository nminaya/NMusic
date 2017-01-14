package Model;

public class ResultFromQuery {
	private String[] columns;
	private String[][] rows;

	public ResultFromQuery(String[] columns, String[][] rows) {
		this.columns = columns;
		this.rows = rows;
	}

	public String[] getColumns(){
		return columns;
	}

	public String[][] getRows(){
		return rows;
	}
}
