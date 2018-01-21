package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import messages.ActionMessage;
import messages.ListFolderMessage;
import messages.ListMessage;
import messages.ShareMessage;
import utils.Parser;
import utils.Zip;
/**
 * @author Clash of Devs
 * @version 1.0
 * Represents the file explorer screen
 */
public class MainExplorer {
	private ImageIcon logo = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("clash_icon.png")));	
	private JFrame frame;
	private JPanel userPanel, filePanel, listPanel;
	public static int x = 30;
	private JTree tree;
	private JList<String> list;
	private JTextPane txtpnFolderPath;
	private JTextPane txtpnUserName;
	private JButton btnCreateFolder, btnUpload, btnDownload, btnShare, btnLogout, btnDelete;
	private Client client;
	private JButton btnSharedWithMe;
	private JButton btnSharingWith;
	private JScrollPane scrollPaneTree;


	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public MainExplorer(Client client) throws Exception{
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
		gridBagLayout.columnWidths = new int[]{0, 56, 63, 0, 249, 2, 123, 22, 0};
		gridBagLayout.rowHeights = new int[]{37, 37, 38, 16, 158, 35, 35, 29, 0, 29, 29, 0, 19, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_userPanel.gridheight = 2;
		gbc_userPanel.gridx = 1;
		gbc_userPanel.gridy = 1;
		frame.getContentPane().add(userPanel, gbc_userPanel);
		txtpnUserName = new JTextPane();
		txtpnUserName.setBounds(59, 16, 94, 16);
		userPanel.add(txtpnUserName);
		txtpnUserName.setEditable(false);
		txtpnUserName.setText(client.getUsername());
		txtpnUserName.setOpaque(false);
		{




			listPanel = new JPanel();
			listPanel.setBackground(Color.WHITE);
			GridBagConstraints gbc_listPanel = new GridBagConstraints();
			gbc_listPanel.fill = GridBagConstraints.BOTH;
			gbc_listPanel.insets = new Insets(0, 0, 5, 5);
			gbc_listPanel.gridheight = 8;
			gbc_listPanel.gridx = 4;
			gbc_listPanel.gridy = 4;
			frame.getContentPane().add(listPanel, gbc_listPanel);
			listPanel.setLayout(new BorderLayout(0, 0));


			createTree();
			createList(client.getUsername());


			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
			listPanel.add(scrollPane);
			scrollPane.setViewportView(list);


			createFolder();
			upload();
			share();
			download();
			delete();
			logout();
			sharingWith();
			sharedWithMe();
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
			gbc_filePanel.insets = new Insets(0, 0, 5, 5);
			gbc_filePanel.gridwidth = 2;
			gbc_filePanel.gridx = 5;
			gbc_filePanel.gridy = 4;
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
			list.addListSelectionListener(listen);}


	}
	public void createTree() throws Exception{
		scrollPaneTree = new JScrollPane();
		scrollPaneTree.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
		GridBagConstraints gbc_scrollPaneTree = new GridBagConstraints();
		gbc_scrollPaneTree.gridwidth = 2;
		gbc_scrollPaneTree.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTree.gridheight = 8;
		gbc_scrollPaneTree.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneTree.gridx = 1;
		gbc_scrollPaneTree.gridy = 4;
		frame.getContentPane().add(scrollPaneTree, gbc_scrollPaneTree);

		this.tree = new JTree();
		scrollPaneTree.setViewportView(tree);
		this.tree.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		this.tree.setOpaque(false);
		this.tree.setBackground(null);
		tree.setLayout(new BorderLayout(0, 0));
		ListFolderMessage folders = new ListFolderMessage(client.getUsername(), client.getPassword(), "LIST FOLDERS", client.getUsername());
		ListFolderMessage response = (ListFolderMessage) folders.send(client.getHostName(), client.getPortNumber());
		this.tree.setModel(new DefaultTreeModel(fillTree(response.getFileList(), response.getHierarchyList(), 0), true));
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					if(tree.getSelectionPath() != null) {
						updateList(getTreePath(tree.getSelectionPath()));
						txtpnFolderPath.setText(getTreePath(tree.getSelectionPath()));
						frame.repaint();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	public DefaultMutableTreeNode fillTree(ArrayList<String> list, ArrayList<Integer> hierarchy, int i) throws Exception {
		Parser utils = new Parser();
		if(i == list.size() - 1) return new DefaultMutableTreeNode(utils.parser(list.get(i))); //because of out of bounds error
		else if(list.get(i + 1).indexOf(list.get(i)) == -1 ) return new DefaultMutableTreeNode(utils.parser(list.get(i)));
		else return new DefaultMutableTreeNode(utils.parser(list.get(i))) {
			private static final long serialVersionUID = 1L;
			{
				for(int j = i + 1; j < list.size(); j++) {
					System.out.println("Quem é que eu sou" + list.get(i) + " " + hierarchy.get(i));
					System.out.println("Quem é que eu estou a procurar" + list.get(j)  + " " + hierarchy.get(j) );
					if(list.get(j).indexOf(list.get(i)) == 0 && (hierarchy.get(i) == hierarchy.get(j) - 1)) add(fillTree(list, hierarchy, j));
				}
			}
		};
	}
	public void createList(String pathname) throws Exception{
		String[] no_files = {"No files."};

		ListMessage message = new ListMessage(client.getUsername(), client.getPassword(), "LIST", pathname);
		ListMessage response = (ListMessage) message.send(client.getHostName(), client.getPortNumber());
		if(response.getList().size() == 0) this.list = new JList<String>(no_files);
		else {
			String[] list = new String[response.getList().size()];
			list = response.getList().toArray(list);
			this.list = new JList<String>(list);
		}
		this.list.setBorder(null);
		this.list.setBackground(Color.WHITE);
		this.list.setDragEnabled(true);
		this.list.setTransferHandler(new FileListTransferHandler(this.list, this)); //DRAG N DROP
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent y) {
				if(y.getClickCount() == 2) {
					try {
						String aux = list.getSelectedValue();
						if(! aux.equals("No files selected.")) {
							if(!aux.contains(".")) {
								updateList(txtpnFolderPath.getText() + aux);
								txtpnFolderPath.setText(txtpnFolderPath.getText() + aux + File.separator);
								frame.repaint();
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
	public void updateList(String pathname) throws Exception{
		String no_files = "No files.";

		ListMessage message = new ListMessage(client.getUsername(), client.getPassword(), "LIST", pathname);
		ListMessage response = (ListMessage) message.send(client.getHostName(), client.getPortNumber());
		DefaultListModel<String> model = new DefaultListModel<String>();
		list.setModel(model);
		if(response.getList().size() == 0) {
			model.addElement(no_files);
			//System.out.println("Here");
		}
		else
			for(String aux : response.getList())
				model.addElement(aux);

	}
	public String getTreePath(TreePath path) {
		String ret = "";
		for(int i = 0; i < path.getPathCount(); i++) {
			ret += path.getPathComponent(i) + File.separator;
		}
		return ret;
	}
	public void createFolder() throws Exception{
		btnCreateFolder = new JButton("Create Folder");
		GridBagConstraints gbc_btnCreateFolder = new GridBagConstraints();
		gbc_btnCreateFolder.fill = GridBagConstraints.BOTH;
		gbc_btnCreateFolder.insets = new Insets(0, 0, 5, 5);
		gbc_btnCreateFolder.gridx = 6;
		gbc_btnCreateFolder.gridy = 7;
		frame.getContentPane().add(btnCreateFolder, gbc_btnCreateFolder);

		btnCreateFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputValue = (String) JOptionPane.showInputDialog(frame, "Please insert the new folder name", "Instruction", JOptionPane.QUESTION_MESSAGE, logo, null , (Object)"");
				if(inputValue != null && !inputValue.equals("")) {
					ActionMessage message2;
					try {
						frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						message2 = new ActionMessage(client.getUsername(), client.getPassword(), "CREATE FOLDER", txtpnFolderPath.getText() + inputValue);
						String response = (String) message2.send(client.getHostName(), client.getPortNumber());
						if(response.equals("FILE/FOLDER ALREADY EXISTS")) {
							frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							JOptionPane.showMessageDialog(frame,
									"File Already Exists. Please Select Another Name.",
									"Error",
									JOptionPane.ERROR_MESSAGE, logo);
						}
						else if(response.equals("ACK RECEIVED")) {
							ListFolderMessage folders = new ListFolderMessage(client.getUsername(), client.getPassword(), "LIST FOLDERS", client.getUsername());
							ListFolderMessage response1 = (ListFolderMessage) folders.send(client.getHostName(), client.getPortNumber());
							tree.setModel(new DefaultTreeModel(fillTree(response1.getFileList(), response1.getHierarchyList(), 0) , true));
							updateList(txtpnFolderPath.getText());
							frame.repaint();
							frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});
	}
	public void sharingWith() {
		btnSharingWith = new JButton("Sharing With");
		btnSharingWith.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SharingWith sharing;
				try {
					sharing = new SharingWith(client);
					sharing.getFrame().setVisible(true);
					frame.dispose();	
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnSharingWith = new GridBagConstraints();
		gbc_btnSharingWith.fill = GridBagConstraints.BOTH;
		gbc_btnSharingWith.insets = new Insets(0, 0, 5, 5);
		gbc_btnSharingWith.gridx = 6;
		gbc_btnSharingWith.gridy = 5;
		frame.getContentPane().add(btnSharingWith, gbc_btnSharingWith);
	}
	public void sharedWithMe() {
		btnSharedWithMe = new JButton("Shared With Me");
		btnSharedWithMe.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				SharedWithMe shared;
				try {
					shared = new SharedWithMe(client);
					shared.getFrame().setVisible(true);
					frame.dispose();	
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		GridBagConstraints gbc_btnSharedWithMe = new GridBagConstraints();
		gbc_btnSharedWithMe.fill = GridBagConstraints.BOTH;
		gbc_btnSharedWithMe.insets = new Insets(0, 0, 5, 5);
		gbc_btnSharedWithMe.gridx = 6;
		gbc_btnSharedWithMe.gridy = 6;
		frame.getContentPane().add(btnSharedWithMe, gbc_btnSharedWithMe);
	}
	public void upload() {
		btnUpload = new JButton("Upload");
		GridBagConstraints gbc_btnUpload = new GridBagConstraints();
		gbc_btnUpload.fill = GridBagConstraints.BOTH;
		gbc_btnUpload.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpload.gridx = 6;
		gbc_btnUpload.gridy = 8;
		frame.getContentPane().add(btnUpload, gbc_btnUpload);

		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				chooser.setMultiSelectionEnabled(true);
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setDialogTitle("Upload");

				int returnValue = chooser.showDialog(null, "Upload");

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					for(File f : chooser.getSelectedFiles()) {
						uploadPrepare(f);
					}
				}

			}
		});
	}
	public void share() {
		btnShare = new JButton("Share");
		GridBagConstraints gbc_btnShare = new GridBagConstraints();
		gbc_btnShare.fill = GridBagConstraints.BOTH;
		gbc_btnShare.insets = new Insets(0, 0, 5, 5);
		gbc_btnShare.gridx = 6;
		gbc_btnShare.gridy = 10;
		frame.getContentPane().add(btnShare, gbc_btnShare);

		btnShare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				ShareMessage message = null;
				try {
					if(list.getSelectedValuesList().size() == 0 || list.getSelectedValuesList().get(0).equals("No files.")) {
						JOptionPane.showMessageDialog(frame,
								"Nothing is selected.",
								"Error",
								JOptionPane.ERROR_MESSAGE, logo);
					}

					else if(list.getSelectedValuesList().size() > 1) {
						JOptionPane.showMessageDialog(frame,
								"You can only share one file at a time",
								"Error",
								JOptionPane.ERROR_MESSAGE, logo);
					}
					else {
						String aux = list.getSelectedValuesList().get(0);
						String inputValue = (String) JOptionPane.showInputDialog(frame, "Please insert the email of the person you want to share this file with", "Instruction",  JOptionPane.QUESTION_MESSAGE, logo, null , (Object)"");
						if(inputValue != null) {
							if(inputValue.isEmpty()) {
								JOptionPane.showMessageDialog(frame,
										"No email entered. Please insert an email",
										"Error",
										JOptionPane.ERROR_MESSAGE, logo);
							}
							else {
								message = new ShareMessage(client.getUsername(), client.getPassword(), "SHARE", txtpnFolderPath.getText() + aux, inputValue);
								System.out.println(message.toString());
								String str = (String) message.send(client.getHostName(), client.getPortNumber());
								if(str.equals("USER NOT FOUND")) {
									JOptionPane.showMessageDialog(frame,
											"No user found with this email. Please insert a valid email",
											"Error",
											JOptionPane.ERROR_MESSAGE, logo);
								}
								else if(str.equals("ALREADY SHARING")) {
									JOptionPane.showMessageDialog(frame,
											"You are already sharing this file/folder with this user",
											"Error",
											JOptionPane.ERROR_MESSAGE, logo);
								}
								else {
									JOptionPane.showMessageDialog(frame,
											"Shared the file " + txtpnFolderPath.getText() + aux + " successfully",
											"Success",
											JOptionPane.INFORMATION_MESSAGE, logo);
								}
							}
						}
					}
				} catch(Exception e6) {
					e6.printStackTrace();
				}
			}
		});
	}
	public void download() {
		btnDownload = new JButton("Download");
		GridBagConstraints gbc_btnDownload = new GridBagConstraints();
		gbc_btnDownload.insets = new Insets(0, 0, 5, 5);
		gbc_btnDownload.fill = GridBagConstraints.BOTH;
		gbc_btnDownload.gridx = 6;
		gbc_btnDownload.gridy = 9;
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
						frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						for(String aux : list.getSelectedValuesList()) {
							if(aux.contains(".")){ //selected value is file
								message = new ActionMessage(client.getUsername(), client.getPassword(), "DOWNLOAD", txtpnFolderPath.getText() + aux);
								System.out.println(message.toString());
								message.send(client.getHostName(), client.getPortNumber());
								frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

							}
							else if(!aux.contains(".")){ //selected value is folder
								message = new ActionMessage(client.getUsername(), client.getPassword(), "DOWNLOAD FOLDER", txtpnFolderPath.getText() + aux);
								System.out.println(message.toString());
								message.send(client.getHostName(), client.getPortNumber());
								frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							}
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
	}
	public void delete() {
		btnDelete = new JButton("Delete");
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 5);
		gbc_btnDelete.fill = GridBagConstraints.BOTH;
		gbc_btnDelete.gridx = 6;
		gbc_btnDelete.gridy = 11;
		frame.getContentPane().add(btnDelete, gbc_btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e5) {
				if(list.isSelectionEmpty() || list.getSelectedValuesList().get(0).equals("No files.")) {
					JOptionPane.showMessageDialog(frame,
							"Nothing is selected.",
							"Error",
							JOptionPane.ERROR_MESSAGE, logo);
				}
				else {
					int n = JOptionPane.showConfirmDialog(frame,
							"Are you sure you want to delete this item?",
							"Confirmation",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							logo);
					if(n == JOptionPane.YES_OPTION) {
						ActionMessage message = null;
						try {

							frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
							for(String aux : list.getSelectedValuesList()) {
								message = new ActionMessage(client.getUsername(), client.getPassword(), "DELETE", txtpnFolderPath.getText() + aux);
								System.out.println(message.toString());
								message.send(client.getHostName(), client.getPortNumber());
							}
							ListFolderMessage folders = new ListFolderMessage(client.getUsername(), client.getPassword(), "LIST FOLDERS", client.getUsername());
							ListFolderMessage response = (ListFolderMessage) folders.send(client.getHostName(), client.getPortNumber());
							tree.setModel(new DefaultTreeModel(fillTree(response.getFileList(), response.getHierarchyList(), 0), true));
							updateList(txtpnFolderPath.getText());
							frame.repaint();
							frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}

		});
	}
	public void logout() {
		btnLogout = new JButton("Logout");
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogout.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogout.gridx = 6;
		gbc_btnLogout.gridy = 1;
		frame.getContentPane().add(btnLogout, gbc_btnLogout);
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
	}
	public void folderPathLabel() {
		txtpnFolderPath = new JTextPane();
		txtpnFolderPath.setEditable(false);
		txtpnFolderPath.setText(client.getUsername()+ File.separator);
		txtpnFolderPath.setOpaque(false);
		GridBagConstraints gbc_txtpnFolderPath = new GridBagConstraints();
		gbc_txtpnFolderPath.fill = GridBagConstraints.BOTH;
		gbc_txtpnFolderPath.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnFolderPath.gridwidth = 2;
		gbc_txtpnFolderPath.gridx = 4;
		gbc_txtpnFolderPath.gridy = 3;
		frame.getContentPane().add(txtpnFolderPath, gbc_txtpnFolderPath);	
	}
	public void uploadPrepare(File f) {
		ActionMessage message = null;
		try {
			if(f.isFile())
				message = new ActionMessage(client.getUsername(), client.getPassword(), "UPLOAD", txtpnFolderPath.getText() + f.getName() , f);
			else{ //f is a directory
				System.out.println("DIR NAME" + f.getAbsolutePath());
				System.out.println("NAME ZIP FILE " + f.getName() + ".zip");
				new Zip(f.getAbsolutePath(), f.getName() + ".zip");
				File novo = new File( f.getName() + ".zip");
				message = new ActionMessage(client.getUsername(), client.getPassword(), "UPLOAD FOLDER", 
						txtpnFolderPath.getText() + f.getName() + ".zip" , novo);
				novo.delete();
			}
			System.out.println(message.toString());
			String response = (String) message.send(client.getHostName(), client.getPortNumber());
			if(response.equals("FILE/FOLDER ALREADY EXISTS")) {
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				JOptionPane.showMessageDialog(frame,
						"File Already Exists. Please Select Another Name.",
						"Error",
						JOptionPane.ERROR_MESSAGE, logo);
			}
			else if(response.equals("ACK RECEIVED")) {
				ListFolderMessage folders = new ListFolderMessage(client.getUsername(), client.getPassword(), "LIST FOLDERS", client.getUsername());
				ListFolderMessage response1 = (ListFolderMessage) folders.send(client.getHostName(), client.getPortNumber());
				tree.setModel(new DefaultTreeModel(fillTree(response1.getFileList(), response1.getHierarchyList(),0) , true));
				updateList(txtpnFolderPath.getText());
				frame.repaint();
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

		} catch (Exception exc1) {
			exc1.printStackTrace();
		}
	}
}
