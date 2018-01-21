package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;

import messages.AuthenticationMessage;

import java.awt.Color;

/**
 * @author Clash of Devs
 * @version 1.0
 * Represents the login screen
 */
public class Login {
	private ImageIcon logo = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("clash_icon.png")));	
	private JTextPane txtpnWelcomeBack;
	private JPasswordField passwordField;
	private JFormattedTextField txtFieldUsername;
	private JButton btnCancel, btnLogin;
	private JFrame login;
	
	public JFrame getLogin() {
		return login;
	}
	public void setLogin(JFrame login) {
		this.login = login;
	}
	public Login() throws Exception{
		initialize();
	}
	private void initialize() throws Exception {
		BufferedImage loginimage;
		try {
			loginimage = ImageIO.read(getClass().getResourceAsStream("clash.png"));

		}catch(IOException e1) {

			e1.printStackTrace();
			return;
		}

		login = new JFrame();
		login.getContentPane().setBackground(Color.WHITE);
		login.getContentPane().setFont(new Font("Tempus Sans ITC", Font.PLAIN, 11));
		login.setTitle("Clash of Clouds");
		login.setBounds(100, 100, 645, 510);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{236, 0, 0, 0, 154, 89, 0};
		gridBagLayout.rowHeights = new int[]{0, 114, 24, 0, 0, 41, 32, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		login.getContentPane().setLayout(gridBagLayout);
	

		JPanel panel = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {	
				super.paintComponent(g);
				g.drawImage(loginimage, 20, -5, null);
			}
		};
		panel.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 6;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		login.getContentPane().add(panel, gbc_panel);

		cancel();
		username();
		password();
		welcomeBack();
		login();

	}
	public JFrame setLogin() {
		return login;
	}	
	public void cancel() {
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Init window = new Init();
					window.getFrmClashOfClouds().setVisible(true);
					login.dispose();	
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 5;
		login.getContentPane().add(btnCancel, gbc_btnCancel);
	}
	public void password() {
		JLabel labelPassword = new JLabel();
		labelPassword.setText("Password");
		GridBagConstraints gbc_labelPassword = new GridBagConstraints();
		gbc_labelPassword.insets = new Insets(0, 0, 5, 5);
		gbc_labelPassword.gridx = 3;
		gbc_labelPassword.gridy = 4;
		login.getContentPane().add(labelPassword, gbc_labelPassword);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("Type Your password here");
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 4;
		gbc_passwordField.gridy = 4;
		login.getContentPane().add(passwordField, gbc_passwordField);
		passwordField.addKeyListener(new KeyAdapter(){ //login when enter key is hit
			@Override
			public void keyPressed(KeyEvent a) {
				if(a.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
		});
	}
	public void username() {
		JLabel labelUsername = new JLabel();
		labelUsername.setText("Username");
		GridBagConstraints gbc_labelUsername = new GridBagConstraints();
		gbc_labelUsername.insets = new Insets(0, 0, 5, 5);
		gbc_labelUsername.gridx = 3;
		gbc_labelUsername.gridy = 3;
		login.getContentPane().add(labelUsername, gbc_labelUsername);

		txtFieldUsername = new JFormattedTextField();
		GridBagConstraints gbc_txtFieldUsername = new GridBagConstraints();
		gbc_txtFieldUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtFieldUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFieldUsername.gridx = 4;
		gbc_txtFieldUsername.gridy = 3;
		login.getContentPane().add(txtFieldUsername, gbc_txtFieldUsername);
	}
	public void welcomeBack() {
		txtpnWelcomeBack = new JTextPane();
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
	}
	public void login() throws Exception{
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_name;
				char[] password;
				try {
					user_name = txtFieldUsername.getText();
					password = passwordField.getPassword();

					if (user_name.equals("") || password.length == 0) { 
						JOptionPane.showMessageDialog(login, "These fields are mandatory.","Clash of Clouds", JOptionPane.ERROR_MESSAGE, logo);
						return;
					}
					String hostName;
					try(	
							BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + "config.txt"));
							){
						hostName = in.readLine();
						int portNumber = Integer.parseInt(in.readLine());
						System.out.println(hostName);
						System.out.println(portNumber);

						Client client = new Client(hostName, portNumber, user_name, String.valueOf(password));
						System.out.println(client.getHostName());
						AuthenticationMessage message;
						try {
							message = new AuthenticationMessage(client.getUsername(), client.getPassword(), "LOGIN");
							String result = (String)message.send(client.getHostName(), client.getPortNumber());
							if(!result.equals("ACK RECEIVED")) {
								JOptionPane.showMessageDialog(login, "Invalid Credentials. Please Try Again.", "Clash of Clouds", JOptionPane.ERROR_MESSAGE, logo);
								txtFieldUsername.setText("");
								passwordField.setText("");
								return;
							}
							MainExplorer main = new MainExplorer(client);
							main.getFrame().setVisible(true);
							login.dispose();	
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (FileNotFoundException e2) {
						System.err.println("Couldn't find the file");
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				} catch (Exception e4) {
					e4.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 4;
		gbc_btnLogin.gridy = 5;
		btnLogin.addKeyListener(new KeyAdapter(){ //login when enter key is hit
			@Override
			public void keyPressed(KeyEvent a) {
				if(a.getKeyCode() == KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
		});
		login.getContentPane().add(btnLogin, gbc_btnLogin);
	}
}