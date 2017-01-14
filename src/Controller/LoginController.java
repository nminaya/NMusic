package Controller;

import DataAccess.InterfaceDAL;
import Model.Credencial;
import View.MainWindowView;

public class LoginController {

	public boolean isValidCredential(String username, String password) {
		try {
			InterfaceDAL db = InterfaceDAL.getInstance();
			
			Credencial cred = db.getCredencial(username, password);
			
			if(cred != null) {
				UserLogged user = UserLogged.getInstance();
				user.setUser(db.getUser("credencialID = " + cred.getCredencialID()));
				return true;
			}
			else{
				return false;
			}

		}
		catch(Exception ex) {
			return false;
		}
	}
	
	public void openMainWindow() {
		MainWindowView mainWindow = new MainWindowView();
		mainWindow.setResizable(false);
		mainWindow.setBounds(500,300,625,400);
		mainWindow.setTitle("NeOnMusic - Menú Principal");
		mainWindow.setDefaultCloseOperation(MainWindowView.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);
	}
}
