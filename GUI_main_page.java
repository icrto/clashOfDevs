//FALTA BOTAO LOGOUT E FORMATAR EM GRID

package main_quest;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.Graphics;
import java.awt.SystemColor;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI_main_page {
	
	public static int x=30;

	private JFrame frame;

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public GUI_main_page() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		BufferedImage pdf, zip, interrogation, txt, user;
		try {
			pdf=ImageIO.read(getClass().getResourceAsStream("pdf.png"));
			zip=ImageIO.read(getClass().getResourceAsStream("zip.png"));
			interrogation=ImageIO.read(getClass().getResourceAsStream("interrogation.png"));
			txt=ImageIO.read(getClass().getResourceAsStream("txt.png"));
			user=ImageIO.read(getClass().getResourceAsStream("user.png"));
		}catch(IOException e1) {
			
			e1.printStackTrace();
			return;
		}
		frame = new JFrame();
		frame.setTitle("Clash of Clouds");
		frame.setBounds(100, 100, 645, 510);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTree tree = new JTree();
		tree.setBackground(SystemColor.window);
		tree.setOpaque(false);
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("JTree") {
				{
					//a alterar consoante o diretorio do utilizador
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("colors");
						node_1.add(new DefaultMutableTreeNode("blue"));
						node_1.add(new DefaultMutableTreeNode("violet"));
						node_1.add(new DefaultMutableTreeNode("red"));
						node_1.add(new DefaultMutableTreeNode("yellow"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("sports");
						node_1.add(new DefaultMutableTreeNode("basketball"));
						node_1.add(new DefaultMutableTreeNode("soccer"));
						node_1.add(new DefaultMutableTreeNode("football"));
						node_1.add(new DefaultMutableTreeNode("hockey"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("food");
						node_1.add(new DefaultMutableTreeNode("hot dogs"));
						node_1.add(new DefaultMutableTreeNode("pizza"));
						node_1.add(new DefaultMutableTreeNode("ravioli"));
						node_1.add(new DefaultMutableTreeNode("bananas"));
					add(node_1);
				}
			}
		));
		tree.setBounds(6, 81, 212, 389);
		frame.getContentPane().add(tree);
		
		JTextPane txtpnUserName = new JTextPane();
		txtpnUserName.setText("User Name");
		txtpnUserName.setBounds(56, 25, 68, 16);
		txtpnUserName.setOpaque(false);
		frame.getContentPane().add(txtpnUserName);
		
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
	        protected void paintComponent(Graphics g) {	
	            super.paintComponent(g);
	        		g.drawImage(user, 0, 0, null);
	            
		}
	    };
		panel.setBounds(6, 16, 38, 38);
		frame.getContentPane().add(panel);
		
		JTextPane txtpnFolderPath = new JTextPane();
		
		txtpnFolderPath.setText("Folder path");
		txtpnFolderPath.setBounds(227, 42, 76, 16);
		txtpnFolderPath.setOpaque(false);
		frame.getContentPane().add(txtpnFolderPath);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(227, 81, 249, 389);
		frame.getContentPane().add(panel_1);
		
		//para a lista
		String file_info []= {"oi 45KB .zip", "ola 56MB .txt","oi 88MB .pdf","oi 45kb .zip","oi 45kb .zip","oi 45kb .zip","oi 45kb .zip","oi 45kb .zip","oi 45kb .zip"};
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		panel_1.add(scrollPane);
		JList list = new JList(file_info);
		list.setBorder(null);
		list.setBackground(SystemColor.window);
		scrollPane.setViewportView(list);
		list.setLayoutOrientation(list.VERTICAL);
			//para correr em thrad
		ListSelectionListener listen = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//so é preciso o repaint()?
				JList list = (JList) e.getSource();
				if( ((String)list.getSelectedValuesList().get(0)).contains(".pdf") ) {
					x=2;
					frame.repaint();
				}	
				else if( ((String)list.getSelectedValuesList().get(0)).contains(".zip") ) {
					x=1;
					frame.repaint();
				}
				else if( ((String)list.getSelectedValuesList().get(0)).contains(".txt") ) {
					x=0;
					frame.repaint();
				}
				else {
					x=3;
					frame.repaint();
				}
				
			}
			
		};
		list.addListSelectionListener(listen);
		
		JButton btnNewButton = new JButton("Create Folder");
		btnNewButton.setBounds(504, 318, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Upload Files");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(504, 359, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Share");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(504, 400, 117, 29);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Download");
		btnNewButton_3.setBounds(504, 441, 117, 29);
		frame.getContentPane().add(btnNewButton_3);
		
		//panel com  as imagens dos tipos de ficheiros
		JPanel panel_2 = new JPanel() {
		private static final long serialVersionUID = 1L;
		@Override
        protected void paintComponent(Graphics g) {	
            super.paintComponent(g);
            //aqui é que faço a distinçao
            if(x==0) 
        			g.drawImage(txt, 20, -5, null);
            if(x==1) 
    				g.drawImage(zip, 20, -5, null);
            if(x==2) 
    				g.drawImage(pdf, 20, -5, null);
            if(x==3) 
    				g.drawImage(interrogation, 20, -5, null);
            
	}
    };
		panel_2.setBounds(488, 81, 139, 158);
		frame.getContentPane().add(panel_2);
		
		JButton btnNewButton_4 = new JButton("Log Out");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GUI window = new GUI();
					window.getFrmClashOfClouds().setVisible(true);
					frame.dispose();	
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_4.setBounds(533, 25, 94, 29);
		frame.getContentPane().add(btnNewButton_4);
	}
}
