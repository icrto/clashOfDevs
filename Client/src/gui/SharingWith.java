package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import messages.ListMessage;
import messages.PermissionMessage;

import java.awt.BorderLayout;
/**
 * @author Clash of Devs
 * @version 1.0
 * Represents the files sharing with others screen 
 */
public class SharingWith {
	private ImageIcon logo = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("clash_icon.png")));	
	private JFrame frame;
	private JPanel userPanel, filePanel, listPanel;
	public static int x = 30;
	private JList<String> list;
	private JTextPane txtpnUserName;
	private JButton btnChange, btnLogout, btnBack, btnRemove;
	private Client client;

	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public SharingWith(Client client) throws Exception{
		this.client = client;
		initialize();
	}
	private void initialize() throws Exception{
		BufferedImage pdf, zip, interrogation, txt, user, folder, jpg, mp3, mp4, png, ppt, doc, xls, gif;

		try {
			pdf = ImageIO.read(getClass().getResourceAsStream("pdf.png"));
			zip = ImageIO.read(getClass().getResourceAsStream("zip.png"));
			interrogation = ImageIO.read(getClass().getResourceAsStream("interrogation.png"));
			txt = ImageIO.read(getClass().getResourceAsStream("txt.png"));
			user = ImageIO.read(getClass().getResourceAsStream("user.png"));
			folder = ImageIO.read(getClass().getResourceAsStream("folder.png"));
			jpg = ImageIO.read(getClass().getResourceAsStream("jpg.png"));
			mp3 = ImageIO.read(getClass().getResourceAsStream("mp3.png"));
			mp4 = ImageIO.read(getClass().getResourceAsStream("mp4.png"));
			png = ImageIO.read(getClass().getResourceAsStream("png.png"));
			ppt = ImageIO.read(getClass().getResourceAsStream("ppt.png"));
			doc = ImageIO.read(getClass().getResourceAsStream("doc.png"));
			xls = ImageIO.read(getClass().getResourceAsStream("xls.png"));
			gif = ImageIO.read(getClass().getResourceAsStream("gif.png"));


		}catch(IOException e1) {
			e1.printStackTrace();
			return;
		}
		frame = new JFrame();
		frame.setResizable(false);
		frame.setMinimumSize(new Dimension(645, 510));
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setTitle("Clash of Clouds");
		frame.setBounds(100, 100, 645, 510);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{32, 0, 126, 45, 249, 2, 123, 0};
		gridBagLayout.rowHeights = new int[]{25, 0, -15, 0, 16, 158, 35, 35, 0, 29, 0, 29, 29, 0, 29, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		userPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {	
				super.paintComponent(g);
				g.drawImage(user, 0, 0, null);

			}
		};
		userPanel.setBackground(Color.WHITE);
		userPanel.setLayout(null);
		GridBagConstraints gbc_userPanel = new GridBagConstraints();
		gbc_userPanel.gridwidth = 2;
		gbc_userPanel.fill = GridBagConstraints.BOTH;
		gbc_userPanel.insets = new Insets(0, 0, 5, 5);
		gbc_userPanel.gridheight = 4;
		gbc_userPanel.gridx = 1;
		gbc_userPanel.gridy = 1;
		frame.getContentPane().add(userPanel, gbc_userPanel);
		{
			btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						MainExplorer main = new MainExplorer(client);
						main.getFrame().setVisible(true);
						frame.dispose();	

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			GridBagConstraints gbc_btnBack = new GridBagConstraints();
			gbc_btnBack.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnBack.insets = new Insets(0, 0, 5, 0);
			gbc_btnBack.gridx = 6;
			gbc_btnBack.gridy = 4;
			frame.getContentPane().add(btnBack, gbc_btnBack);
		}




		listPanel = new JPanel();
		listPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_listPanel = new GridBagConstraints();
		gbc_listPanel.fill = GridBagConstraints.BOTH;
		gbc_listPanel.insets = new Insets(0, 0, 5, 5);
		gbc_listPanel.gridheight = 9;
		gbc_listPanel.gridx = 4;
		gbc_listPanel.gridy = 5;
		frame.getContentPane().add(listPanel, gbc_listPanel);
		listPanel.setLayout(new BorderLayout(0, 0));


		createList(client.getUsername());


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		listPanel.add(scrollPane);
		scrollPane.setViewportView(list);


		changePermissions();
		removePermission();
		logout();
		userLabel();

		//panel com  as imagens dos tipos de ficheiros
		filePanel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {	
				super.paintComponent(g);
				//aqui é que faço a distinçao
				if(x == 0) 
					g.drawImage(txt, 20, -5, null);
				if(x == 1) 
					g.drawImage(zip, 20, -5, null);
				if(x == 2) 
					g.drawImage(pdf, 20, -5, null);
				if(x == 3) 
					g.drawImage(interrogation, 20, -5, null);
				if(x == 4)
					g.drawImage(folder, 20, -5, null);
				if(x == 5)
					g.drawImage(jpg, 20, -5, null);
				if(x == 6)
					g.drawImage(mp3, 20, -5, null);
				if(x == 7)
					g.drawImage(mp4, 20, -5, null);
				if(x == 8)
					g.drawImage(png, 20, -5, null);
				if(x == 9)
					g.drawImage(ppt, 20, -5, null);
				if(x == 10)
					g.drawImage(doc, 20, -5, null);
				if(x == 11)
					g.drawImage(xls, 20, -5, null);
				if(x == 12)
					g.drawImage(gif, 20, -5, null);
			}
		};
		filePanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_filePanel = new GridBagConstraints();
		gbc_filePanel.fill = GridBagConstraints.BOTH;
		gbc_filePanel.insets = new Insets(0, 0, 5, 0);
		gbc_filePanel.gridwidth = 2;
		gbc_filePanel.gridx = 5;
		gbc_filePanel.gridy = 5;
		frame.getContentPane().add(filePanel, gbc_filePanel);

		//para pintar o pannel com os tipos de ficheiro 
		ListSelectionListener listen = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!list.isSelectionEmpty()) {
					if( ((String)list.getSelectedValuesList().get(0)).contains(".pdf") ) {
						x = 2;
						frame.repaint();
					}	
					else if( ((String)list.getSelectedValuesList().get(0)).contains(".zip") ) {
						x = 1;
						frame.repaint();
					}
					else if( (list.getSelectedValuesList().get(0)).contains(".txt") ) {
						x = 0;
						frame.repaint();
					}
					else if(!list.getSelectedValuesList().get(0).contains(".")){
						x = 4;
						frame.repaint();
					}
					else if(list.getSelectedValuesList().get(0).contains(".jpg")){
						x = 5;
						frame.repaint();
					}
					else if(list.getSelectedValuesList().get(0).contains(".mp3")){
						x = 6;
						frame.repaint();
					}
					else if(list.getSelectedValuesList().get(0).contains(".mp4")){
						x = 7;
						frame.repaint();
					}
					else if(list.getSelectedValuesList().get(0).contains(".png")){
						x = 8;
						frame.repaint();
					}
					else if(list.getSelectedValuesList().get(0).contains(".ppt")){
						x = 9;
						frame.repaint();
					}
					else if(list.getSelectedValuesList().get(0).contains(".doc")){
						x = 10;
						frame.repaint();
					}
					else if(list.getSelectedValuesList().get(0).contains(".xls")){
						x = 11;
						frame.repaint();
					}
					else if(list.getSelectedValuesList().get(0).contains(".gif")){
						x = 12;
						frame.repaint();
					}
					else {
						x = 3;
						frame.repaint();
					}

				}
			}

		};
		list.addListSelectionListener(listen);


	}
	public void updateList() throws Exception{
		String no_files = "No files.";

		ListMessage message = new ListMessage(client.getUsername(), client.getPassword(), "LIST SHARING WITH", null);
		ListMessage response = (ListMessage) message.send(client.getHostName(), client.getPortNumber());
		DefaultListModel<String> model = new DefaultListModel<String>();
		list.setModel(model);
		if(response.getList().size() == 0) {
			model.addElement(no_files);
		}
		else
			for(String aux : response.getList())
				model.addElement(aux);

	}	
	public void createList(String pathname) throws Exception{
		String[] no_files = {"No files."};

		ListMessage message = new ListMessage(client.getUsername(), client.getPassword(), "LIST SHARING WITH", null);
		ListMessage response = (ListMessage) message.send(client.getHostName(), client.getPortNumber());
		if(response == null) this.list = new JList<String>(no_files);
		else {
			String[] list = new String[response.getList().size()];
			list = response.getList().toArray(list);
			this.list = new JList<String>(list);
		}
		this.list.setBorder(null);
		this.list.setBackground(Color.WHITE);
	}
	public void removePermission() {
		btnRemove = new JButton("Unshare");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PermissionMessage message = null;
				try {
					if(list.isSelectionEmpty() || list.getSelectedValuesList().get(0).equals("No files.")) {
						JOptionPane.showMessageDialog(frame,
								"Nothing is selected.",
								"Error",
								JOptionPane.ERROR_MESSAGE, logo);
					}
					else if(list.getSelectedValuesList().size() > 1) {
						JOptionPane.showMessageDialog(frame,
								"You can only select one file at a time",
								"Error",
								JOptionPane.ERROR_MESSAGE, logo);
					}
					else {
						String aux = list.getSelectedValuesList().get(0);
						message = new PermissionMessage(client.getUsername(), client.getPassword(), "USERS", aux);
						System.out.println(message.toString());
						PermissionMessage response = (PermissionMessage) message.send(client.getHostName(), client.getPortNumber());
						ArrayList<JCheckBox> params = new ArrayList<JCheckBox>();
						for(int i = 0; i < response.getEmailsList().size(); i++) {
							params.add(new JCheckBox(response.getEmailsList().get(i)));
						}
						Object[] obj = (Object[]) params.toArray(new Object[params.size()]);
						int n = JOptionPane.showConfirmDialog(frame, obj, "Remove Permissions?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, logo);
						if(n == JOptionPane.YES_OPTION) {
							ArrayList<Boolean> delete = new ArrayList<Boolean>();
							for(JCheckBox checkbox : params) {
								if(checkbox.isSelected()) 
									delete.add(true);
								else
									delete.add(false);
							}
							PermissionMessage permission = new PermissionMessage(client.getUsername(), client.getPassword(), "DELETE PERMISSIONS", aux, response.getEmailsList(), delete);
							String ack = (String) permission.send(client.getHostName(), client.getPortNumber());
							if(ack.equals("ACK RECEIVED")) {
								JOptionPane.showMessageDialog(frame,
										"Permissions deleted successfully",
										"Success",
										JOptionPane.INFORMATION_MESSAGE, logo);
							}
							else {
								JOptionPane.showMessageDialog(frame,
										"An error occurred",
										"Error",
										JOptionPane.ERROR_MESSAGE, logo);
							}
							updateList();
							frame.repaint();
						}

					}
				} catch (Exception e6) {
					e6.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove.gridx = 6;
		gbc_btnRemove.gridy = 9;
		frame.getContentPane().add(btnRemove, gbc_btnRemove);
	}
	public void changePermissions() {

		btnChange = new JButton("Change Permissions");
		GridBagConstraints gbc_btnChange = new GridBagConstraints();
		gbc_btnChange.fill = GridBagConstraints.BOTH;
		gbc_btnChange.insets = new Insets(0, 0, 5, 0);
		gbc_btnChange.gridx = 6;
		gbc_btnChange.gridy = 10;
		frame.getContentPane().add(btnChange, gbc_btnChange);

		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				PermissionMessage message = null;
				try {
					if(list.isSelectionEmpty() || list.getSelectedValuesList().get(0).equals("No files.")) {
						JOptionPane.showMessageDialog(frame,
								"Nothing is selected.",
								"Error",
								JOptionPane.ERROR_MESSAGE, logo);
					}
					else if(list.getSelectedValuesList().size() > 1) {
						JOptionPane.showMessageDialog(frame,
								"You can only select one file at a time",
								"Error",
								JOptionPane.ERROR_MESSAGE, logo);
					}
					else {
						String aux = list.getSelectedValuesList().get(0);
						message = new PermissionMessage(client.getUsername(), client.getPassword(), "USERS", aux);
						System.out.println(message.toString());
						PermissionMessage response = (PermissionMessage) message.send(client.getHostName(), client.getPortNumber());
						ArrayList<JCheckBox> params = new ArrayList<JCheckBox>();
						for(int i = 0; i < response.getEmailsList().size(); i++) {
							params.add(new JCheckBox(response.getEmailsList().get(i)));
							params.get(i).setSelected(response.getEditList().get(i));
						}
						Object[] obj = (Object[]) params.toArray(new Object[params.size()]);
						int n = JOptionPane.showConfirmDialog(frame, obj, "Change Permissions?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, logo);
						if(n == JOptionPane.YES_OPTION) {
							ArrayList<Boolean> can_edit = new ArrayList<Boolean>();
							for(JCheckBox checkbox : params) {
								if(checkbox.isSelected()) 
									can_edit.add(true);
								else
									can_edit.add(false);
							}
							PermissionMessage permission = new PermissionMessage(client.getUsername(), client.getPassword(), "EDIT PERMISSIONS", aux, response.getEmailsList(), can_edit);
							String ack = (String) permission.send(client.getHostName(), client.getPortNumber());
							if(ack.equals("ACK RECEIVED")) {
								JOptionPane.showMessageDialog(frame,
										"Permissions changed successfully",
										"Success",
										JOptionPane.INFORMATION_MESSAGE, logo);
							}
							else {
								JOptionPane.showMessageDialog(frame,
										"An error occurred",
										"Error",
										JOptionPane.ERROR_MESSAGE, logo);
							}
						}

					}
				} catch (Exception e6) {
					e6.printStackTrace();
				}
			}
		});
	}
	public void logout() {
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e4) {
				try {
					Init window = new Init();
					window.getFrmClashOfClouds().setVisible(true);
					frame.dispose();	

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogout.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogout.gridx = 6;
		gbc_btnLogout.gridy = 3;
		frame.getContentPane().add(btnLogout, gbc_btnLogout);
	}
	public void userLabel() {
		txtpnUserName = new JTextPane();
		txtpnUserName.setBounds(59, 16, 94, 16);
		userPanel.add(txtpnUserName);
		txtpnUserName.setEditable(false);
		txtpnUserName.setText(client.getUsername());
		txtpnUserName.setOpaque(false);
	}
	public String takeRootOut (String pathname) {
		String delims = "[\\\\/]";
		String []tokens = pathname.split(delims);	//De forma a ser compativel em Windows/Unix
		String correctPath = "";
		for(int i=1 ; i< tokens.length ; i++) {
			if(i == tokens.length - 1)
				correctPath += tokens[i];
			else
				correctPath += tokens[i] + File.separator;
		}
		return correctPath;
	}
}

