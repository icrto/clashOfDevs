package main_quest;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;

public class GUI_login {

	private JFrame login;
	private JPasswordField passwordField;

	public GUI_login() {
		initialize();
	}
	
	private void initialize() {
		
		BufferedImage loginimage;
		try {
			loginimage=ImageIO.read(getClass().getResourceAsStream("unnamed.png"));
			
		}catch(IOException e1) {
			
			e1.printStackTrace();
			return;
		}
		
		login = new JFrame();
		login.getContentPane().setFont(new Font("Tempus Sans ITC", Font.PLAIN, 11));
		login.setTitle("Clash of Clouds");
		login.setBounds(100, 100, 645, 510);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{236, 0, 0, 0, 154, 89, 0};
		gridBagLayout.rowHeights = new int[]{0, 130, 24, 0, 0, 41, 32, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		login.getContentPane().setLayout(gridBagLayout);
		
		JTextPane txtpnWelcomeBack = new JTextPane();
		txtpnWelcomeBack.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 44));
		txtpnWelcomeBack.setEditable(false);
		txtpnWelcomeBack.setText("Welcome Back!");
		txtpnWelcomeBack.setOpaque(false);
		GridBagConstraints gbc_txtpnWelcomeBack = new GridBagConstraints();
		gbc_txtpnWelcomeBack.gridwidth = 6;
		gbc_txtpnWelcomeBack.insets = new Insets(0, 0, 5, 0);
		gbc_txtpnWelcomeBack.gridx = 0;
		gbc_txtpnWelcomeBack.gridy = 1;
		login.getContentPane().add(txtpnWelcomeBack, gbc_txtpnWelcomeBack);
		
		JTextPane txtpnUsername = new JTextPane();
		txtpnUsername.setEditable(false);
		txtpnUsername.setText("UserName");
		txtpnUsername.setOpaque(false);
		GridBagConstraints gbc_txtpnUsername = new GridBagConstraints();
		gbc_txtpnUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnUsername.gridx = 3;
		gbc_txtpnUsername.gridy = 3;
		login.getContentPane().add(txtpnUsername, gbc_txtpnUsername);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		GridBagConstraints gbc_formattedTextField = new GridBagConstraints();
		gbc_formattedTextField.insets = new Insets(0, 0, 5, 5);
		gbc_formattedTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField.gridx = 4;
		gbc_formattedTextField.gridy = 3;
		login.getContentPane().add(formattedTextField, gbc_formattedTextField);
		
		JPanel panel = new JPanel() {
			
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {	
	            super.paintComponent(g);
				g.drawImage(loginimage, 20, -5, null);
			}
	    };
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 5;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		login.getContentPane().add(panel, gbc_panel);
		JTextPane txtpnPassword = new JTextPane();
		txtpnPassword.setEditable(false);
		txtpnPassword.setText("Password");
		txtpnPassword.setOpaque(false);
		GridBagConstraints gbc_txtpnPassword = new GridBagConstraints();
		gbc_txtpnPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnPassword.gridx = 3;
		gbc_txtpnPassword.gridy = 4;
		login.getContentPane().add(txtpnPassword, gbc_txtpnPassword);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("Type Your password here");
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 4;
		gbc_passwordField.gridy = 4;
		login.getContentPane().add(passwordField, gbc_passwordField);
		
		JButton btnE = new JButton("Cancel");
		btnE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GUI window = new GUI();
					window.getFrmClashOfClouds().setVisible(true);
					login.dispose();	
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnE = new GridBagConstraints();
		gbc_btnE.insets = new Insets(0, 0, 5, 5);
		gbc_btnE.gridx = 3;
		gbc_btnE.gridy = 5;
		login.getContentPane().add(btnE, gbc_btnE);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_name;
				char[] password;
				try {
					user_name=formattedTextField.getText();
					password=passwordField.getPassword();
					
					if (user_name.equals("") || password.length==0) { 
						System.out.println("estou aqui");
						JOptionPane.showMessageDialog(login, "Preencha os campos obrigatórios.");	
					}
					GUI_main_page main = new GUI_main_page();
					main.getFrame().setVisible(true);
					login.dispose();	
				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 4;
		gbc_btnLogin.gridy = 5;
		login.getContentPane().add(btnLogin, gbc_btnLogin);
	}

	public JFrame setLogin() {
		return login;
	}	
		
}