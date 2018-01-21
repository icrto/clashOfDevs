package utils;

import java.util.zip.*;
import java.io.*;

/**
 * @author Clash of Devs
 * @version 1.0
 * This class is used to zip and unzip folders
 *
 */
public class Zip {

	/**
	 * Generic constructer used when unziping folders
	 */
	public Zip() {}

	/**
	 * Constructor for zipping specified folder
	 * @param originalPath Represents the pathname of the folder to zip
	 * @param nameDestZipFile Represents the name of the new zipped file
	 * @throws IOException throws exception on error.
	 */
	public Zip(String originalPath, String nameDestZipFile) throws IOException {
		try {
			ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(nameDestZipFile));
			addFileToZip("", originalPath, zip);
			zip.close();
		}
		catch(IOException e) {
			System.err.println("Couldn't zip the file");
		}
	}

	/**
	 * Add files to zip
	 * @param parentPath String that represents the pathname of the parent's directory 
	 * @param srcFile String that represents the pathname of the file to add
	 * @param zip Object used to write the zip
	 * @throws IOException
	 */
	private void addFileToZip(String parentPath, String srcFile, ZipOutputStream zip) throws IOException {
		try {
			File folder = new File(srcFile);
			if (folder.isDirectory()) {
				System.out.println("FOLDER IS DIR > " + parentPath + "/" + folder.getName() + "/");
				if(parentPath.equals("")) zip.putNextEntry(new ZipEntry(folder.getName() + "/"));
				else zip.putNextEntry(new ZipEntry(parentPath + "/" + folder.getName() + "/"));
				//System.out.println("GET PATH > " + parentPath + File.separator + folder.getName());
				for(String child: folder.list()) {
					if(parentPath.equals(""))	addFileToZip(folder.getName(), srcFile + "/" + child, zip);
					else addFileToZip(parentPath + "/" + folder.getName(), srcFile + "/" + child, zip);
				}
			}
			else {
				System.out.println("FILE > "+ parentPath + "/" + folder.getName() + "/");
				byte[] buf = new byte[1024];
				int len;
				FileInputStream in = new FileInputStream(srcFile);
				zip.putNextEntry(new ZipEntry(parentPath + "/" + folder.getName()));
				while ((len = in.read(buf)) > 0) {
					zip.write(buf, 0, len);
				}
				in.close();
			}
		}
		catch(IOException e) {
			System.err.println("Coudn't add file to zip");
		}
	}

	/**
	 * Unzips the file
	 * @param zipFilePath String that represents the pathname of the zip selected to unzip
	 * @param destDirectory String that represents the pathname of the extracted folder/file from the zip
	 * @throws IOException throws exception on error.
	 */
	public void unzip(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));

		ZipEntry entry = zipIn.getNextEntry();
		System.out.println("TESTE >"+entry.getName());
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			System.out.println(filePath);
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
				System.out.println(filePath);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				System.out.println(filePath);
				dir.mkdirs();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}

	/**
	 * Function that extracts the file from zip entry
	 * @param zipIn Object that reads data from the zip
	 * @param filePath String that represents the pathname to where extract the files
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		try {

			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
			byte[] bytesIn = new byte[4098];
			int read = 0;
			while ((read = zipIn.read(bytesIn)) != -1) {
				bos.write(bytesIn, 0, read);
			}
			bos.close();
		}
		catch(IOException e) {
			System.err.println("Coudn't extract the file");
		}
	}

	/**
	 * Function used to get a filepath from a specified pathname
	 * @param pathname Represents a generic pathname to be parsed
	 * @return filename Returns filename extracted from the pathname
	 */
	public String parser(String pathname) {
		String delims = "[\\\\/]";
		String []tokens = pathname.split(delims);
		String filename = tokens[tokens.length-1];
		System.out.println("parser > "+filename);
		return filename;

	}

}