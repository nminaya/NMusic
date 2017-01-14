package neonMusic;

import DataAccess.InterfaceDAL;
import View.LoginView;
import View.MainWindowView;

public class Skeleton0 {
	
	public static void main(String args[]) throws Exception {
		LoginView loginView = new LoginView();
        loginView.setSize(650,550);
        loginView.setResizable(false);
        loginView.setLocationRelativeTo(null);
        loginView.setTitle("NeOn Music - Login");
        loginView.setDefaultCloseOperation(LoginView.EXIT_ON_CLOSE);
        loginView.setVisible(true);
        
        //Hacer conexion a base de datos
        InterfaceDAL dal = DataAccess.InterfaceDAL.getInstance();
        dal.connect();
	}
}
