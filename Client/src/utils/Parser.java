package utils;

import java.io.File;

/**
 * @author Clash of Devs
 * @version 1.0
 * This class represents a set of utility functions to analyze and parse a string that represents a pathname of a file
 *
 */
public class Parser {

	/**
	 * Function used to get a file path from a specified pathname
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
		
	/**
	 * Function used to get the first folder from a specified pathname
	 * @param pathname Represents a pathname from a file
	 * @return Returns a string representing the first folder from a specified pathname
	 */
	public String getFirstToken(String pathname) {
		String delims = "[\\\\/]";
		String []tokens = pathname.split(delims);
		return tokens[0];
	}

	/**
	 * Function that returns the parent and child from a pathname
	 * @param pathname Represents a pathname from a file
	 * @return Returns a string representing the parent and child
	 */
	public String getParent(String pathname) {
		String delims = "[\\\\/]";
		String []tokens = pathname.split(delims);
		if(tokens.length == 1) return tokens[0];
		else return tokens[tokens.length-2]+File.separator+tokens[tokens.length-1];
	}
	
	/**
	 * Function that returns the path of the parent node
	 * @param fullPathname String that represents the pathname of the node
	 * @return String with the path of the parent node
	 */
	public String getParentDirPathname(String fullPathname) {
		String delims = "[\\\\/]";
		String []tokens = fullPathname.split(delims);
		String dirPathname = tokens[0];
		for(int i=1; i < tokens.length - 1; i++)
			dirPathname += File.separator + tokens[i] ;

		return dirPathname;
	}
	
	/**
	 * Function used to increase compatibility between system
	 * @param pathname String that represents a pathname from a node 
	 * @return String representing the correct pathname for the user's System
	 */
	public String processPath(String pathname) {
		String delims = "[\\\\/]";
		String []tokens = pathname.split(delims);	//De forma a ser compativel em Windows/Unix
		String correctPath = "";
		for(String s : tokens) {
			correctPath+=File.separator+s;
		}
		//System.out.println("Pathname > "+correctPath);
		return correctPath;
	}
	
	/**
	 * Function used to exclude the first folder from a specific pathname
	 * @param pathname String that represents a pathname from a specific node
	 * @return String representing the desired pathname
	 */
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
		//System.out.println("Pathname > "+correctPath);
		return correctPath;
	}
	
	/**
	 * Function that corrects the pathname to use in the database
	 * @param pathname String that represents a pathname from a specific node
	 * @return String representing the pathname to be used in the database
	 */
	public String parserToDB(String pathname) {
		String delims = "[\\\\/]";
		String []tokens = pathname.split(delims);	//De forma a ser compativel em Windows/Unix
		if(tokens.length == 1) return pathname;
		String correctPath = tokens[0]+"/";
		for(int i=1; i<tokens.length-1; i++) {
			correctPath+=tokens[i]+"/";
		}
		correctPath+=tokens[tokens.length-1];
		//System.out.println("ParserToDB > "+correctPath);
		return correctPath;
	}
	
	/*public int countChar(String str, char substr) {
		char[] array = str.toCharArray();
		int count = 1;
		for(int i=0; i<str.length(); i++) {
			if(array[i] == substr)
				count++;
		}
		return count;
	}*/
	
	/**
	 * Function to get the pathname to extract the zip file
	 * @param pathname String that represents a pathname from a specific node
	 * @return String representing the pathname without the file extension
	 */
	public String removeExtension (String pathname) {
		String delims = "[.]";
		String []tokens = pathname.split(delims);	//De forma a ser compativel em Windows/Unix
		String correctPath = "";
		for(int i=0 ; i< tokens.length -1 ; i++) {
			if(i == tokens.length - 2)
				correctPath += tokens[i];
			else
				correctPath += tokens[i] + File.separator;
		}
		System.out.println("Remove Extension > "+correctPath);
		return correctPath;
	}
}