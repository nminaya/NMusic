package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

import Controller.Helper;
import Controller.LoginController;

public class LoginView extends JFrame implements ActionListener {
	private JButton loginButton;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private JLabel usernameLabel;
	private JLabel passLabel;
	private BufferedImage logoImage;

	public LoginView() throws IOException {
		setLayout(null); //Para usar coordenadas exactas
		setView();
	}

	private void setView() throws IOException {

		logoImage = ImageIO.read(this.getClass().getResource("/img/logo.png"));
		JLabel lImage = new JLabel(new ImageIcon(logoImage));
		lImage.setBounds(120, -40, 400, 400);
		add(lImage);
		
		usernameLabel = new JLabel();
		usernameLabel.setText("Username:");
		usernameLabel.setBounds(10, 325, 150, 40);
		add(usernameLabel);

		textUsername = new JTextField();
        textUsername.setBounds(100,330,500,30);
        textUsername.setToolTipText("Intriduzca su Username.");
        add(textUsername);

        passLabel = new JLabel();
		passLabel.setText("Password:");
		passLabel.setBounds(10, 385, 150, 40);
		add(passLabel);

        textPassword = new JPasswordField();
        textPassword.setBounds(100,390,500,30);
        textPassword.setToolTipText("Intriduzca su Contraseña.");
        add(textPassword);

		loginButton = new JButton("Iniciar Sesión");
		loginButton.setBounds(250, 430, 150, 50);
		loginButton.setToolTipText("Iniciar sesión.");
		add(loginButton); //Añadiendo botón al JPanel
		loginButton.addActionListener(this); //Creando evento

		//Icono
		this.setIconImage(new ImageIcon(getClass().getResource("/img/log.jpg")).getImage());
	}

	public void actionPerformed(ActionEvent e) {
		String username = textUsername.getText();
		String pass = new String(textPassword.getPassword());
		
		LoginController loginHandler = new LoginController();
		boolean isValidCredential = loginHandler.isValidCredential(username, pass);
		
		if (isValidCredential) {
			this.setVisible(false);
			Helper.showMessage("Ha iniciado sesion: " + username);
			loginHandler.openMainWindow();
		}
		else {
			Helper.showMessage("Usuario o Contrasena INVALIDO.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
