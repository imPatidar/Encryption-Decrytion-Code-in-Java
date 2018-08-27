/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgtry;

import java.util.Scanner;
import java.io.*;

public class Encryption_Decryption {

	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.println("--- File Encryption Decryption ---\n");
		
		char update = '\0';
		
		do {
			programLoop();
			System.out.print("\nFormat another file? (y/n): > ");
			update = scan.next().charAt(0);
		} 
		while (update == 'y');
		
		System.out.println("\nEnd of Process...");
	}
	
	
	public static void programLoop() {
        
		System.out.print('\n');
		int shift = -1;
		char selection = '\0';
		String inputFileName = null;
		String outputFileName = null;
		
		do {
			System.out.print("Encrypt or Decrypt a file? (e/d): > ");
			selection = scan.next().charAt(0);
		} 
		while (selection != 'e' && selection != 'd');
		
		System.out.print("\nEnter a value for the shift: > ");
		shift = scan.nextInt();
		
		scan.nextLine(); // pushes from the current line ( referenced above -> scan.nextInt(); )
		
		System.out.print("\nEnter input file path: e.g. C:\\Users\\user\\desktop\\file.txt \n > ");
		inputFileName = scan.nextLine();
		
		System.out.print("\nEnter output file path, or type 'n' to output to the inputed file: \n > ");
		outputFileName = scan.nextLine();
		
		if (outputFileName.charAt(0) == 'n') 
            outputFileName = inputFileName; // bad - fix this.
		// else nothing changes.
		
		System.out.println("\nRead from file: " + inputFileName);
		System.out.println("Wrote to file:	" + outputFileName);
		
		switch (selection) {
			case 'e':
				writeEncryptionToFile(inputFileName, outputFileName, shift);
				break;
				
			case 'd':
				writeDecryptionToFile(inputFileName, outputFileName, shift);
				break;
				
			default:
				System.out.println("\nSomething went wrong... :(");
				break;
		}
		
		System.out.println("\nFile process completed\n");
		return;
	}
	
	
	/* Function writes the excrypted message to an output file. */
	public static void writeEncryptionToFile(String inputFileName, String outputFileName, int shift) {
        
		String contents = getFileContents(inputFileName);
		String encryptedText = encryptString(contents, shift);
		BufferedWriter writeFile;
		
		try {
			writeFile = new BufferedWriter(new FileWriter(outputFileName));
			writeFile.write(encryptedText);
			writeFile.close();
		}
		
		catch (Exception ex) {
			System.out.println("\nAn error occured writing to the file!");
		}
	}
	
	
	/* Function writes the decrypted message to an output file. */
	public static void writeDecryptionToFile(String inputFileName, String outputFileName, int shift) {
        
		String contents = getFileContents(inputFileName);
		String decryptedText = decryptString(contents, shift);
		BufferedWriter writeFile;
		
		try {
			writeFile = new BufferedWriter(new FileWriter(outputFileName));
			writeFile.write(decryptedText);
			writeFile.close();
		}
		
		catch (Exception ex) {
			System.out.println("\nAn error occured writing to the file!");
		}
	}
	
	
	/* Function to open and read the file and storing the contents
	 	in a String, and returing the string */
	public static String getFileContents(String fileName) {
        
		Scanner readFile;
		String contents = "";
		
		try {
			readFile = new Scanner(new File(fileName));
			contents = readFile.useDelimiter("\0").next(); // store contents in String.
			readFile.close();
		}
		
		catch (FileNotFoundException ex) {
			System.out.println("\nCannot open file!");
		}
		
		return contents;
	}
	
	
	/* Function to encrypt and return a passed String */
	public static String encryptString(String text, int shift) {
        
		char[] encryptedText = new char[text.length()];
		int i = 0;
		int j = 0;
		
		// apply the character shift to each character
		for (i = 0; i < text.length(); i++) {
            
			if (text.charAt(i) != ' ') {
				encryptedText[j] = determineNextCharInEncryption(text.charAt(i), shift);
			}
			
			else 
				encryptedText[j] = ' ';
			
			j++;
		}
		
		// return the new encrypted string
		return new String(encryptedText);
	}
	
	
	/* Function to decrypt and return a passed String */
	public static String decryptString(String text, int shift) {
        
		char[] decryptedText = new char[text.length()];
		int i = 0;
		int j = 0;
		
		// apply the character shift to character
		for (i = 0; i < text.length(); i++) {
            
			if (text.charAt(i) != ' ') {
				decryptedText[j] = determineNextCharInDecryption(text.charAt(i), shift);
			}
			
			else
				decryptedText[j] = ' ';
			
			j++;
		}
		
		// return the new decrypted string
		return new String(decryptedText);
	}
	
	
	/* Finds the next character, applying the shift,
	 * and calling the determineCase function. */
	static char determineNextCharInEncryption(char ch, int shift) {
        
		int findCase = determineCase(ch);
		int asciiVal = -1;
		
		// lowercase
		if (findCase == 1) {
            
			if ((int)ch + shift > 122) {
				asciiVal = 97 + (((int)ch + shift) - 122 - 1);
				ch = (char)asciiVal;
			}
			
			else
				ch = (char)(ch + shift);
		}
		
		// uppercase
		else if (findCase == 0) {
            
			if ((int)ch + shift > 90) {
				asciiVal = 65 + (((int)ch + shift) - 90 - 1);
				ch = (char)asciiVal;
			}
			
			else
				ch = (char)(ch + shift);
		}
		
		// if character is undefined returns character.
		return ch;
	}
	
	
	/* Finds the next character, applying the shift,
		and calling the determineCase function. */
	public static char determineNextCharInDecryption(char ch, int shift) {
        
		int findCase = determineCase(ch);
		int asciiVal = -1;
		
		// lowercase
		if (findCase == 1) {
            
			if ((int)ch - shift < 97) {
				asciiVal = 122 + (((int)ch - shift) - 97 + 1);
				ch = (char)asciiVal;
			}
			
			else
				ch = (char)(ch - shift);
		}
		
		// uppercase
		else if (findCase == 0) {
            
			if ((int)ch - shift < 65) {
				asciiVal = 90 + (((int)ch - shift) - 65 + 1);
				ch = (char)asciiVal;
			}
			
			else
				ch = (char)(ch - shift);
		}
		
		// if character is undefined returns character.
		return ch;
	}
	
	
	/* Function to determine the case of character
	 	using ascii values.		*/
	public static int determineCase(char ch) {
		
		if ((int)ch >= 97 && (int)ch <= 122)
			return 1;	// lowercase return.
		
		else if ((int)ch >= 65 && (int)ch <= 90)
			return 0;	// uppercase return.
		
		// default invalid case
		return -1;
	}
}