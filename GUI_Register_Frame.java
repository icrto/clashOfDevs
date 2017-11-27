package main_quest;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class GUI_Register_Frame extends JFrame{

	private JFrame frmClashOfClouds;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	//metodos para jframe
	public JFrame getFrame() {
		return frmClashOfClouds;
	}

	public void setFrame(JFrame frame) {
		this.frmClashOfClouds = frame;
	}

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Register_Frame window = new GUI_Register_Frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	
	public GUI_Register_Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		BufferedImage loginimage;
		try {
			//loginimage=ImageIO.read(new File("/Users/tiagopinto/Desktop/LPRO/img/unnamed.png"));
			loginimage = ImageIO.read(getClass().getResourceAsStream("unnamed.png"));
		}catch(IOException e1) {
			
			e1.printStackTrace();
			return;
		}
		
		frmClashOfClouds = new JFrame();
		frmClashOfClouds.getContentPane().setFocusTraversalKeysEnabled(false);
		frmClashOfClouds.setResizable(false);
		frmClashOfClouds.setTitle("Clash of Clouds");
		frmClashOfClouds.setBounds(100, 100, 645, 510);
		frmClashOfClouds.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 54, 0, 0, 0, 0, 0, 0, 73, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmClashOfClouds.getContentPane().setLayout(gridBagLayout);
		
		JTextPane txtpnWelcomeToClash = new JTextPane();
		txtpnWelcomeToClash.setOpaque(false);
		txtpnWelcomeToClash.setEditable(false);
		txtpnWelcomeToClash.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 43));
		txtpnWelcomeToClash.setText(" Welcome to Clash of Clouds!");
		GridBagConstraints gbc_txtpnWelcomeToClash = new GridBagConstraints();
		gbc_txtpnWelcomeToClash.fill = GridBagConstraints.BOTH;
		gbc_txtpnWelcomeToClash.gridwidth = 17;
		gbc_txtpnWelcomeToClash.insets = new Insets(0, 0, 5, 0);
		gbc_txtpnWelcomeToClash.gridx = 0;
		gbc_txtpnWelcomeToClash.gridy = 1;
		frmClashOfClouds.getContentPane().add(txtpnWelcomeToClash, gbc_txtpnWelcomeToClash);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("What we offer:\r\n- 25GB of free space \r\n- Access your files anywhere\r\n- Share files with anyone");
		textPane.setOpaque(false);
		textPane.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		textPane.setEditable(false);
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.gridwidth = 4;
		gbc_textPane.gridheight = 6;
		gbc_textPane.insets = new Insets(0, 0, 5, 5);
		gbc_textPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPane.gridx = 11;
		gbc_textPane.gridy = 2;
		frmClashOfClouds.getContentPane().add(textPane, gbc_textPane);
		
		JLabel label = new JLabel("                                    ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 5;
		frmClashOfClouds.getContentPane().add(label, gbc_label);
		
		JTextPane txtpnName = new JTextPane();
		txtpnName.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		txtpnName.setText("Name");
		txtpnName.setEditable(false);
		txtpnName.setOpaque(false);
		GridBagConstraints gbc_txtpnName = new GridBagConstraints();
		gbc_txtpnName.fill = GridBagConstraints.BOTH;
		gbc_txtpnName.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnName.gridx = 3;
		gbc_txtpnName.gridy = 5;
		frmClashOfClouds.getContentPane().add(txtpnName, gbc_txtpnName);
		
		textField = new JTextField();
		textField.setFocusTraversalKeysEnabled(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 7;
		gbc_textField.gridy = 5;
		frmClashOfClouds.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JTextPane txtpnEmail = new JTextPane();
		txtpnEmail.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		txtpnEmail.setText("Email");
		txtpnEmail.setOpaque(false);
		txtpnEmail.setEditable(false);
		GridBagConstraints gbc_txtpnEmail = new GridBagConstraints();
		gbc_txtpnEmail.fill = GridBagConstraints.BOTH;
		gbc_txtpnEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnEmail.gridx = 3;
		gbc_txtpnEmail.gridy = 6;
		frmClashOfClouds.getContentPane().add(txtpnEmail, gbc_txtpnEmail);
		
		textField_1 = new JTextField();
		textField_1.setFocusTraversalKeysEnabled(false);
		textField_1.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 7;
		gbc_textField_1.gridy = 6;
		frmClashOfClouds.getContentPane().add(textField_1, gbc_textField_1);
		
		JTextPane txtpnPassword = new JTextPane();
		txtpnPassword.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		txtpnPassword.setEditable(false);
		txtpnPassword.setText("Password");
		txtpnPassword.setOpaque(false);
		GridBagConstraints gbc_txtpnPassword = new GridBagConstraints();
		gbc_txtpnPassword.fill = GridBagConstraints.BOTH;
		gbc_txtpnPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnPassword.gridx = 3;
		gbc_txtpnPassword.gridy = 7;
		frmClashOfClouds.getContentPane().add(txtpnPassword, gbc_txtpnPassword);
		
		passwordField = new JPasswordField();
		passwordField.setFocusTraversalKeysEnabled(false);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 2;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 7;
		gbc_passwordField.gridy = 7;
		frmClashOfClouds.getContentPane().add(passwordField, gbc_passwordField);
		
		JTextPane txtpnConfirmPassword = new JTextPane();
		txtpnConfirmPassword.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		txtpnConfirmPassword.setEditable(false);
		txtpnConfirmPassword.setText("Confirm Password");
		txtpnConfirmPassword.setOpaque(false);
		GridBagConstraints gbc_txtpnConfirmPassword = new GridBagConstraints();
		gbc_txtpnConfirmPassword.gridwidth = 3;
		gbc_txtpnConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnConfirmPassword.fill = GridBagConstraints.BOTH;
		gbc_txtpnConfirmPassword.gridx = 3;
		gbc_txtpnConfirmPassword.gridy = 8;
		frmClashOfClouds.getContentPane().add(txtpnConfirmPassword, gbc_txtpnConfirmPassword);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setFocusTraversalKeysEnabled(false);
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.gridwidth = 2;
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 7;
		gbc_passwordField_1.gridy = 8;
		frmClashOfClouds.getContentPane().add(passwordField_1, gbc_passwordField_1);
		
		JPanel panel = new JPanel() {
			
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {	
	            super.paintComponent(g);
				g.drawImage(loginimage, 20, -5, null);
			}
	    };
	    
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
					try {
					    Desktop.getDesktop().browse(new URL("https://www.youtube.com/embed/Vhh_GeBPOhs?start=5&end=15&autoplay=1&version=3").toURI());
					} catch (Exception e) {}
			}
		});
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 8;
		gbc_panel.gridheight = 12;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 9;
		gbc_panel.gridy = 8;
		frmClashOfClouds.getContentPane().add(panel, gbc_panel);
		
		JTextPane txtpnOptionalData = new JTextPane();
		txtpnOptionalData.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		txtpnOptionalData.setEditable(false);
		txtpnOptionalData.setText("Optional Data");
		txtpnOptionalData.setOpaque(false);
		GridBagConstraints gbc_txtpnOptionalData = new GridBagConstraints();
		gbc_txtpnOptionalData.gridwidth = 3;
		gbc_txtpnOptionalData.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnOptionalData.fill = GridBagConstraints.BOTH;
		gbc_txtpnOptionalData.gridx = 3;
		gbc_txtpnOptionalData.gridy = 10;
		frmClashOfClouds.getContentPane().add(txtpnOptionalData, gbc_txtpnOptionalData);
		
		JTextPane txtpnAddress = new JTextPane();
		txtpnAddress.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		txtpnAddress.setEditable(false);
		txtpnAddress.setOpaque(false);
		txtpnAddress.setText("Address");
		GridBagConstraints gbc_txtpnAddress = new GridBagConstraints();
		gbc_txtpnAddress.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnAddress.fill = GridBagConstraints.BOTH;
		gbc_txtpnAddress.gridx = 3;
		gbc_txtpnAddress.gridy = 11;
		frmClashOfClouds.getContentPane().add(txtpnAddress, gbc_txtpnAddress);
		
		textField_2 = new JTextField();
		textField_2.setFocusTraversalKeysEnabled(false);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 2;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 7;
		gbc_textField_2.gridy = 11;
		frmClashOfClouds.getContentPane().add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JTextPane txtpnPhoneNumber = new JTextPane();
		txtpnPhoneNumber.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		txtpnPhoneNumber.setEditable(false);
		txtpnPhoneNumber.setText("Phone Number");
		txtpnPhoneNumber.setOpaque(false);
		GridBagConstraints gbc_txtpnPhoneNumber = new GridBagConstraints();
		gbc_txtpnPhoneNumber.gridwidth = 3;
		gbc_txtpnPhoneNumber.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnPhoneNumber.fill = GridBagConstraints.BOTH;
		gbc_txtpnPhoneNumber.gridx = 3;
		gbc_txtpnPhoneNumber.gridy = 13;
		frmClashOfClouds.getContentPane().add(txtpnPhoneNumber, gbc_txtpnPhoneNumber);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					GUI window = new GUI();
					window.getFrmClashOfClouds().setVisible(true);
					frmClashOfClouds.dispose();	
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		textField_3 = new JTextField();
		textField_3.setFocusTraversalKeysEnabled(false);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.gridwidth = 2;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 7;
		gbc_textField_3.gridy = 13;
		frmClashOfClouds.getContentPane().add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		btnBack.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBack.anchor = GridBagConstraints.SOUTH;
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 3;
		gbc_btnBack.gridy = 15;
		frmClashOfClouds.getContentPane().add(btnBack, gbc_btnBack);
		
		JButton btnSend = new JButton("Confirm");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			String user_name, email;
			char[] password, confirm_password;
			try {
				user_name=textField.getText();
				email=textField_1.getText();
				password=passwordField.getPassword(); 
				confirm_password=passwordField_1.getPassword();
				if (user_name.equals("") || password.length==0 || confirm_password.length==0 || email.equals("")) { 
					JOptionPane.showMessageDialog(frmClashOfClouds, "Preencha os campos obrigatórios.");
					return;
				}
				else if(!Arrays.equals(password, confirm_password)) {
					JOptionPane.showMessageDialog(frmClashOfClouds, "Passwords are incongruent.");
					return;
				}
				GUI_main_page main = new GUI_main_page();
				main.getFrame().setVisible(true);
				frmClashOfClouds.dispose();	
			} catch (Exception e4) {
				e4.printStackTrace();
			}
				
			}			
		});
		btnSend.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.anchor = GridBagConstraints.SOUTH;
		gbc_btnSend.insets = new Insets(0, 0, 5, 5);
		gbc_btnSend.gridx = 7;
		gbc_btnSend.gridy = 15;
		frmClashOfClouds.getContentPane().add(btnSend, gbc_btnSend);
		
		
		
		
		
		
		
	}
}
