package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Color;
/**
 * @author Clash of Devs
 * @version 1.0
 * This represents the initial screen displayed
 *
 */
public class Init {

	private JFrame frmClashOfClouds;

	
	public JFrame getFrmClashOfClouds() {
		return frmClashOfClouds;
	}

	public void setFrmClashOfClouds(JFrame frmClashOfClouds) {
		this.frmClashOfClouds = frmClashOfClouds;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				Init window = new Init();
				window.frmClashOfClouds.setVisible(true);
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Init() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @param client 
	 */
	private void initialize() {

		frmClashOfClouds = new JFrame();
		frmClashOfClouds.getContentPane().setBackground(Color.WHITE);
		frmClashOfClouds.getContentPane().setFont(new Font("Tempus Sans ITC", Font.PLAIN, 11));
		frmClashOfClouds.setResizable(false);
		frmClashOfClouds.setTitle("Clash of Clouds");
		frmClashOfClouds.setBounds(100, 100, 645, 510);
		frmClashOfClouds.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{236, 0, 154, 89, 0};
		gridBagLayout.rowHeights = new int[]{62, 0, 0, 41, 0, 41, 32, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		frmClashOfClouds.getContentPane().setLayout(gridBagLayout);

		BufferedImage loginimage;
		try {
			loginimage=ImageIO.read(getClass().getResourceAsStream("clash.png"));

		}catch(IOException e1) {

			e1.printStackTrace();
			return;
		}

		JTextArea txtrWelcomeToClash = new JTextArea();
		txtrWelcomeToClash.setFont(new Font("Lucida Grande", Font.PLAIN, 44));
		txtrWelcomeToClash.setBackground(SystemColor.control);
		txtrWelcomeToClash.setEditable(false);
		txtrWelcomeToClash.setWrapStyleWord(true);
		txtrWelcomeToClash.setRows(2);
		txtrWelcomeToClash.setText("Welcome to Clash of Clouds!");
		txtrWelcomeToClash.setOpaque(false);
		GridBagConstraints gbc_txtrWelcomeToClash = new GridBagConstraints();
		gbc_txtrWelcomeToClash.anchor = GridBagConstraints.SOUTH;
		gbc_txtrWelcomeToClash.gridwidth = 4;
		gbc_txtrWelcomeToClash.insets = new Insets(0, 0, 5, 0);
		gbc_txtrWelcomeToClash.gridx = 0;
		gbc_txtrWelcomeToClash.gridy = 1;
		frmClashOfClouds.getContentPane().add(txtrWelcomeToClash, gbc_txtrWelcomeToClash);

		JButton btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 11));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Register window = new Register();
					window.getFrame().setVisible(true);
					frmClashOfClouds.dispose();	
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
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
		gbc_panel.gridwidth = 2;
		gbc_panel.gridheight = 7;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		frmClashOfClouds.getContentPane().add(panel, gbc_panel);
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.anchor = GridBagConstraints.SOUTH;
		gbc_btnRegister.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegister.gridx = 2;
		gbc_btnRegister.gridy = 3;
		frmClashOfClouds.getContentPane().add(btnRegister, gbc_btnRegister);

		JTextPane txtpnOr = new JTextPane();
		txtpnOr.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		txtpnOr.setEditable(false);
		txtpnOr.setText("OR");
		txtpnOr.setOpaque(false);
		GridBagConstraints gbc_txtpnOr = new GridBagConstraints();
		gbc_txtpnOr.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnOr.gridx = 2;
		gbc_txtpnOr.gridy = 4;
		frmClashOfClouds.getContentPane().add(txtpnOr, gbc_txtpnOr);

		JButton btnLogin = new JButton("  Login ");
		btnLogin.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 11));
		btnLogin.setHorizontalAlignment(SwingConstants.TRAILING);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Login window = new Login();
					window.setLogin().setVisible(true);
					frmClashOfClouds.dispose();	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 5;
		frmClashOfClouds.getContentPane().add(btnLogin, gbc_btnLogin);

		JTextPane txtpnBeAppartOf = new JTextPane();
		txtpnBeAppartOf.setEditable(false);
		txtpnBeAppartOf.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtpnBeAppartOf.setText("Be a part of something NEW!");
		txtpnBeAppartOf.setOpaque(false);
		GridBagConstraints gbc_txtpnBeAppartOf = new GridBagConstraints();
		gbc_txtpnBeAppartOf.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnBeAppartOf.gridx = 2;
		gbc_txtpnBeAppartOf.gridy = 8;
		frmClashOfClouds.getContentPane().add(txtpnBeAppartOf, gbc_txtpnBeAppartOf);
	}

}
