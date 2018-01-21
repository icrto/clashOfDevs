package gui;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

/**
 * @author Clash of Devs
 * @version 1.0
 * This class represent the handler to enable drag and drop support to the JList that makes the navigation on the system.
 *
 */
@SuppressWarnings("serial")
class FileListTransferHandler extends TransferHandler {
	/**
	 * It has two objects that references the panels that call upon it, page being the page that handles the dropped data.
	 */
	@SuppressWarnings("unused")
	private JList<String> list;
	private MainExplorer page;

	/**
	 * Constructor that references the parameters
	 * @param list
	 * @param page
	 */
	public FileListTransferHandler(JList<String> list, MainExplorer page) {
		this.list = list;
		this.page = page;
	}

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
	 */
	public int getSourceActions(JComponent c) {
		return NONE;
	}

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#canImport(javax.swing.TransferHandler.TransferSupport)
	 */
	public boolean canImport(TransferSupport ts) {
		return ts.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
	}

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#importData(javax.swing.TransferHandler.TransferSupport)
	 */
	public boolean importData(TransferSupport ts) {
		try {

			@SuppressWarnings("unchecked")
			List<String> data = (List<String>) ts.getTransferable().getTransferData(
					DataFlavor.javaFileListFlavor);
			if (data.size() < 1) {
				return false;
			}

			for (Object item : data) {
				File file = (File) item;
				page.uploadPrepare(file);
			}

			return true;

		} catch (UnsupportedFlavorException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
}
