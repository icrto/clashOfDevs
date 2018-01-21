package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import messages.RegisterMessage;

import java.awt.Color;

@SuppressWarnings("serial")
/**
 * @author Clash of Devs
 * @version 1.0
 * Represents the register screen
 */
public class Register extends JFrame{
	private ImageIcon logo;
	private JFrame frmRegister;
	public JFrame getFrame() {
		return frmRegister;
	}

	public void setFrame(JFrame frame) {
		this.frmRegister = frame;
	}

	private JTextField textName, textEmail, textAddress, textPhone, textUsername;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldConfirm;
	private JButton btnConfirm, btnBack;
	private JLabel labelName, labelEmail, labelPassword, labelConfirmPassword, labelOptionalData, labelAddress, labelPhoneNumber, labelUsername;
	private JPanel centerPanel;


	public Register() throws IOException{
		initialize();
	}
	private void initialize() throws IOException {
		logo = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("clash_icon.png")));	
		frmRegister = new JFrame();
		frmRegister.getContentPane().setBackground(Color.WHITE);
		frmRegister.setResizable(false);
		frmRegister.setTitle("Clash of Clouds");
		frmRegister.setBounds(100, 100, 645, 510);
		frmRegister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegister.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(383, 127, 202, 277);
		frmRegister.getContentPane().add(panel);

		JTextPane txtpnWhatWeOffer = new JTextPane();
		txtpnWhatWeOffer.setEditable(false);
		txtpnWhatWeOffer.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		panel.add(txtpnWhatWeOffer);
		txtpnWhatWeOffer.setText("What we offer:\r\n  -25GB of free space \r\n  -Access your files anywhere\r\n  -Share the files with anyone");
		txtpnWhatWeOffer.setOpaque(false);

		txtFields();
		txtPanes();
		welcome();
		back();
		confirm();

	}
	public void txtFields() {

		centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setBounds(179, 99, 144, 251);
		frmRegister.getContentPane().add(centerPanel);
		centerPanel.setLayout(null);

		textName = new JTextField();
		textName.setBounds(0, 28, 144, 16);
		centerPanel.add(textName);
		textName.setColumns(10);

		textEmail = new JTextField();
		textEmail.setBounds(0, 56, 144, 16);
		centerPanel.add(textEmail);
		textEmail.setColumns(10);

		textAddress = new JTextField();
		textAddress.setBounds(0, 207, 144, 16);
		centerPanel.add(textAddress);
		textAddress.setColumns(10);

		textPhone = new JTextField();
		textPhone.setBounds(0, 235, 144, 16);
		centerPanel.add(textPhone);
		textPhone.setColumns(10);

		textUsername = new JTextField();
		textUsername.setBounds(0, 0, 144, 16);
		centerPanel.add(textUsername);
		textUsername.setColumns(10);
		passwordField = new JPasswordField();
		passwordField.setBounds(0, 83, 144, 16);
		centerPanel.add(passwordField);

		passwordFieldConfirm = new JPasswordField();
		passwordFieldConfirm.setBounds(0, 111, 144, 16);
		centerPanel.add(passwordFieldConfirm);
		passwordFieldConfirm.addKeyListener(new KeyAdapter(){ // confirm when enter key is hit
			@Override
			public void keyPressed(KeyEvent a) {
				if(a.getKeyCode() == KeyEvent.VK_ENTER) {
					btnConfirm.doClick();
				}
			}
		});
		textPhone.addKeyListener(new KeyAdapter(){ // confirm when enter key is hit
			@Override
			public void keyPressed(KeyEvent a) {
				if(a.getKeyCode() == KeyEvent.VK_ENTER) {
					btnConfirm.doClick();
				}
			}
		});
		textAddress.addKeyListener(new KeyAdapter(){ // confirm when enter key is hit
			@Override
			public void keyPressed(KeyEvent a) {
				if(a.getKeyCode() == KeyEvent.VK_ENTER) {
					btnConfirm.doClick();
				}
			}
		});
	}
	public void txtPanes() {

		labelName = new JLabel();
		labelName.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		labelName.setText("Name:");
		labelName.setOpaque(false);
		labelName.setBounds(28, 127, 45, 16);
		frmRegister.getContentPane().add(labelName);


		labelEmail = new JLabel();
		labelEmail.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		labelEmail.setText("Email:");
		labelEmail.setOpaque(false);
		labelEmail.setBounds(28, 155, 38, 16);
		frmRegister.getContentPane().add(labelEmail);

		labelPassword = new JLabel();
		labelPassword.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		labelPassword.setText("Password:");
		labelPassword.setOpaque(false);
		labelPassword.setBounds(28, 182, 63, 16);
		frmRegister.getContentPane().add(labelPassword);

		labelConfirmPassword = new JLabel();
		labelConfirmPassword.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		labelConfirmPassword.setText("Confirm Password:");
		labelConfirmPassword.setOpaque(false);
		labelConfirmPassword.setBounds(28, 210, 118, 16);
		frmRegister.getContentPane().add(labelConfirmPassword);

		labelOptionalData = new JLabel();
		labelOptionalData.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		labelOptionalData.setText("Optional Data");
		labelOptionalData.setOpaque(false);
		labelOptionalData.setBounds(28, 278, 93, 16);
		frmRegister.getContentPane().add(labelOptionalData);

		labelAddress = new JLabel();
		labelAddress.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		labelAddress.setText("Address:");
		labelAddress.setOpaque(false);
		labelAddress.setBounds(28, 306, 55, 16);
		frmRegister.getContentPane().add(labelAddress);

		labelPhoneNumber = new JLabel();
		labelPhoneNumber.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		labelPhoneNumber.setText("Phone Number:");
		labelPhoneNumber.setOpaque(false);
		labelPhoneNumber.setBounds(28, 334, 96, 16);
		frmRegister.getContentPane().add(labelPhoneNumber);

		labelUsername = new JLabel();
		labelUsername.setText("Username:");
		labelUsername.setOpaque(false);
		labelUsername.setFont(new Font("Dialog", Font.PLAIN, 13));
		labelUsername.setBounds(28, 99, 82, 16);
		frmRegister.getContentPane().add(labelUsername);

	}
	public void welcome(){
		JTextPane txtpnWelco = new JTextPane();
		txtpnWelco.setEditable(false);
		txtpnWelco.setFont(new Font("Lucida Grande", Font.BOLD, 40));
		txtpnWelco.setText("Welcome to Clash of Clouds!");
		txtpnWelco.setOpaque(false);
		txtpnWelco.setBounds(28, 29, 611, 49);
		frmRegister.getContentPane().add(txtpnWelco);
	}
	public void back() {
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Init window = new Init();
					window.getFrmClashOfClouds().setVisible(true);
					frmRegister.dispose();	

				} catch (Exception e1) {
					e1.printStackTrace();
				}	
			}
		});
		btnBack.setBounds(28, 434, 82, 29);
		frmRegister.getContentPane().add(btnBack);
	}
	public void confirm() {

		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user_name, email, name;
				char[] password, confirm_password;
				try {
					user_name = textUsername.getText();
					name = textName.getText();
					email = textEmail.getText();
					password = passwordField.getPassword(); 
					confirm_password = passwordFieldConfirm.getPassword();
					if (name.equals("") || user_name.equals("") || password.length == 0 || confirm_password.length == 0 || email.equals("")) { 
						JOptionPane.showMessageDialog(frmRegister, "These fields are mandatory.", "Clash of Clouds", JOptionPane.ERROR_MESSAGE, logo);
						return;
					}
					else if(!Arrays.equals(password, confirm_password)) {
						JOptionPane.showMessageDialog(frmRegister, "Passwords don't match.", "Clash of Clouds", JOptionPane.ERROR_MESSAGE, logo);
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

						Client client = new Client(hostName, portNumber, "", "");
						System.out.println(client.getHostName());
						ArrayList<String> fields = new ArrayList<String>();
						fields.add(name);
						fields.add(email);
						fields.add(textAddress.getText());
						fields.add(textPhone.getText());
						System.out.println(fields.get(0));
						System.out.println(fields.get(1));
						System.out.println(fields.get(2));
						System.out.println(fields.get(3));
						RegisterMessage message;
						try {
							message = new RegisterMessage(user_name, String.valueOf(password), "REGISTER", fields);
							String result = (String)message.send(client.getHostName(), client.getPortNumber());
							if(!result.equals("ACK RECEIVED")) {
								JOptionPane.showMessageDialog(frmRegister, "User Name/Email Already Exists. Please Try Again.", "Clash of Clouds", JOptionPane.ERROR_MESSAGE, logo);
								return;
							}
							else {
								JOptionPane.showMessageDialog(frmRegister, "Successful Register", "Clash of Clouds", JOptionPane.INFORMATION_MESSAGE, logo);
								client.setUsername(user_name);
								client.setPassword(String.valueOf(password));
								MainExplorer main = new MainExplorer(client);
								main.getFrame().setVisible(true);
								frmRegister.dispose();	
								return;
							}
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
		btnConfirm.addKeyListener(new KeyAdapter(){ // confirm when enter key is hit
			@Override
			public void keyPressed(KeyEvent a) {
				if(a.getKeyCode() == KeyEvent.VK_ENTER) {
					btnConfirm.doClick();
				}
			}
		});
		btnConfirm.setBounds(137, 434, 75, 29);
		frmRegister.getContentPane().add(btnConfirm);
	}
}
