package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import messages.ActionMessage;
import messages.EditMessage;
import messages.ListMessage;
import utils.Parser;

import java.awt.BorderLayout;
/**
 * @author Clash of Devs
 * @version 1.0
 * Represents the shared files with me screen
 */
public class SharedWithMe {
	private ImageIcon logo = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("clash_icon.png")));	
	private JFrame frame;
	private JPanel userPanel, filePanel, listPanel;
	public static int x = 30;
	private JTree tree;
	private JList<String> list;
	private JTextPane txtpnFolderPath;
	private JTextPane txtpnUserName;
	private JButton btnUpload, btnDownload, btnLogout;
	private Client client;
	private JButton btnBack;


	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public SharedWithMe(Client client) throws Exception{
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
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
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


		createTree();
		createList(client.getUsername());


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		listPanel.add(scrollPane);
		scrollPane.setViewportView(list);


		upload();
		download();
		logout();
		userLabel();
		folderPathLabel();

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




	}
	public void createTree() throws Exception{

		this.tree = new JTree();
		this.tree.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		this.tree.setOpaque(false);
		this.tree.setBackground(null);
		GridBagConstraints gbc_tree = new GridBagConstraints();
		gbc_tree.gridwidth = 3;
		gbc_tree.gridheight = 9;
		gbc_tree.insets = new Insets(0, 0, 5, 5);
		gbc_tree.fill = GridBagConstraints.BOTH;
		gbc_tree.gridx = 1;
		gbc_tree.gridy = 5;
		frame.getContentPane().add(tree, gbc_tree);
		tree.setLayout(new BorderLayout(0, 0));
		this.tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Shared With Me"), true));
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					updateList(null);
					txtpnFolderPath.setText("");
					frame.repaint();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

	}
	public void createList(String pathname) throws Exception{
		String[] no_files = {"No files."};

		ListMessage message = new ListMessage(client.getUsername(), client.getPassword(), "LIST SHARED", null);
		ListMessage response = (ListMessage) message.send(client.getHostName(), client.getPortNumber());
		if(response == null) this.list = new JList<String>(no_files);
		else {
			String[] list = new String[response.getList().size()];
			list = response.getList().toArray(list);
			this.list = new JList<String>(list);
		}
		this.list.setBorder(null);
		this.list.setBackground(Color.WHITE);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent y) {
				if(y.getClickCount() == 2) {
					try {
						String aux = list.getSelectedValue();
						if(! aux.equals("No files selected.")) {
							if(!aux.contains(".")) {
								updateList(aux);
								txtpnFolderPath.setText(aux);
								frame.repaint();
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		//para pintar o pannel com os tipos de ficheiro 
		ListSelectionListener listen = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!list.isSelectionEmpty()) {
					try {
						if(txtpnFolderPath.getText().equals("")) {
							EditMessage tosend = new EditMessage(client.getUsername(), client.getPassword(),"CHECK EDIT", (String)list.getSelectedValuesList().get(0));
							EditMessage response = (EditMessage) tosend.send(client.getHostName(), client.getPortNumber());
							btnUpload.setEnabled(response.getEdit());
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
	public void updateList(String pathname) throws Exception{
		String no_files = "No files.";
		ListMessage message = new ListMessage(client.getUsername(), client.getPassword(), "LIST SHARED", pathname);
		ListMessage response = (ListMessage) message.send(client.getHostName(), client.getPortNumber());
		DefaultListModel<String> model = new DefaultListModel<String>();
		list.setModel(model);
		if(response == null) {
			model.addElement(no_files);
			//System.out.println("Here");
		}
		else
			for(String aux : response.getList())
				model.addElement(aux);

	}
	public String getTreePath(TreePath path) {
		/*String ret = "";
		for(int i = 0; i < path.getPathCount(); i++) {
			ret += path.getPathComponent(i) + File.separator;
		}
		return ret;*/
		return "" + path.getLastPathComponent();
	}
	public void upload() {
		btnUpload = new JButton("Upload");
		GridBagConstraints gbc_btnUpload = new GridBagConstraints();
		gbc_btnUpload.fill = GridBagConstraints.BOTH;
		gbc_btnUpload.insets = new Insets(0, 0, 5, 0);
		gbc_btnUpload.gridx = 6;
		gbc_btnUpload.gridy = 10;
		frame.getContentPane().add(btnUpload, gbc_btnUpload);

		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				try {
					if(list.isSelectionEmpty() || list.getSelectedValuesList().get(0).equals("No files.")) {
						JOptionPane.showMessageDialog(frame,
								"Nothing is selected.",
								"Error",
								JOptionPane.ERROR_MESSAGE, logo);
					}
					else if(list.getSelectedValuesList().size() > 1) {
						JOptionPane.showMessageDialog(frame,
								"You can only edit one file at a time",
								"Error",
								JOptionPane.ERROR_MESSAGE, logo);
					}
					else {
						JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
						chooser.setMultiSelectionEnabled(false);
						chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						chooser.setDialogTitle("Upload");
						int returnValue = chooser.showDialog(null, "Upload");

						if (returnValue == JFileChooser.APPROVE_OPTION) {
							Parser utils = new Parser();
							if(chooser.getSelectedFile().getName().equals(utils.parser(list.getSelectedValue())))
									uploadPrepare(chooser.getSelectedFile());
							else {
								JOptionPane.showMessageDialog(frame,
										"You can only edit the file " + list.getSelectedValue(),
										"Error",
										JOptionPane.ERROR_MESSAGE, logo);
							}
						}
					}
				}catch (Exception e2) {
					e2.printStackTrace();
				}	
			}
		});
	}
	public void download() {
		btnDownload = new JButton("Download");
		GridBagConstraints gbc_btnDownload = new GridBagConstraints();
		gbc_btnDownload.insets = new Insets(0, 0, 5, 0);
		gbc_btnDownload.fill = GridBagConstraints.BOTH;
		gbc_btnDownload.gridx = 6;
		gbc_btnDownload.gridy = 11;
		frame.getContentPane().add(btnDownload, gbc_btnDownload);

		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e3) {

				ActionMessage message = null;
				try {
					if(list.isSelectionEmpty() || list.getSelectedValuesList().get(0).equals("No files.")) {
						JOptionPane.showMessageDialog(frame,
								"Nothing is selected.",
								"Error",
								JOptionPane.ERROR_MESSAGE, logo);
					}
					else {
						for(String aux : list.getSelectedValuesList()) {
							if(aux.contains(".")){ //selected value is file
								message = new ActionMessage(client.getUsername(), client.getPassword(), "DOWNLOAD", txtpnFolderPath.getText() + aux);
								System.out.println(message.toString());
								message.send(client.getHostName(), client.getPortNumber());
							}
							else if(!aux.contains(".")){ //selected value is folder
								message = new ActionMessage(client.getUsername(), client.getPassword(), "DOWNLOAD FOLDER", txtpnFolderPath.getText() + aux);
								System.out.println(message.toString());
								message.send(client.getHostName(), client.getPortNumber());
							}
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
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
	public void folderPathLabel() {
		txtpnFolderPath = new JTextPane();
		txtpnFolderPath.setEditable(false);
		txtpnFolderPath.setOpaque(false);
		txtpnFolderPath.setText("");
		GridBagConstraints gbc_txtpnFolderPath = new GridBagConstraints();
		gbc_txtpnFolderPath.fill = GridBagConstraints.BOTH;
		gbc_txtpnFolderPath.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnFolderPath.gridwidth = 2;
		gbc_txtpnFolderPath.gridx = 4;
		gbc_txtpnFolderPath.gridy = 4;
		frame.getContentPane().add(txtpnFolderPath, gbc_txtpnFolderPath);
	}
	public void uploadPrepare(File f) {
		ActionMessage message = null;
		try {
			message = new ActionMessage(client.getUsername(), client.getPassword(), "UPLOAD SHARED", list.getSelectedValue() , f);
			System.out.println(message.toString());
			message.send(client.getHostName(), client.getPortNumber());
			JOptionPane.showMessageDialog(frame,
					"Uploaded the file " + list.getSelectedValue() + " successfully",
					"Success",
					JOptionPane.INFORMATION_MESSAGE, logo);

		} catch (Exception exc1) {
			exc1.printStackTrace();
		}
	}
	public String takeRootOut (String pathname) {
		String delims = "[\\\\/]";
		String []tokens = pathname.split(delims);	//De forma a ser compativel em Windows/Unix
		System.out.println("Tokens Length "+tokens.length);
		if(tokens.length == 1) return pathname;
		String correctPath = "";
		for(int i=1 ; i< tokens.length ; i++) {
			if(i == tokens.length - 1)
				correctPath += tokens[i];
			else
				correctPath += tokens[i] + File.separator;
		}
		//System.out.println("Pathname > "+correctPath);
		return correctPath;
	}
}
