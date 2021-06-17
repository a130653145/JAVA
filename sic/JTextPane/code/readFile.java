package ntou.cs.java2021.finalproject;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Scanner;

import java.awt.Color ;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class readFile {
		private FileReader fileData;
		private PrintWriter Write;
		private String realInputToChangeFileName;
		private JTextPane textPane;
		private String needReadFilePathAndName;
		private JFrame appFrame;
		public readFile(JFrame inFrame,JTextPane inPane,String inputNameToChangeTextBox1)
		{
			appFrame=inFrame;
			textPane = inPane;
			needReadFilePathAndName=inputNameToChangeTextBox1;
		}
		
		public void addColoredText(JTextPane pane, String text, Color Color) 
		{
			StyledDocument doc = pane.getStyledDocument();
			
			Style style = pane.addStyle("Color Style", null);
			
			StyleConstants.setForeground(style, Color);
			try {
				doc.insertString(doc.getLength(), text, style);
			} 
			
			catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		
		
		public void displayFileText()
		{
					addColoredText(textPane,"open file\n",Color.black);
					try
					{
						textPane.setText("");
						fileData = new FileReader(needReadFilePathAndName);
					}
					catch(IOException ex)
					{
						
						addColoredText(textPane,"open error",Color.red);
						//textPane.setText("open error");
						System.out.println("open error\n");
						JOptionPane.showMessageDialog(appFrame,"input named error or open error");
						return;
					}
					
					//textPane.setText("open success\n");
					System.out.println("open success\n");
					addColoredText(textPane,"open success\n",Color.green);
					//JOptionPane.showMessageDialog(appFrame,"open success");

					Scanner scn =new Scanner(fileData);
					addColoredText(textPane,"read file"+needReadFilePathAndName+":\n",Color.black);
					while(scn.hasNext())
					{
						String fileStringLine = scn.nextLine();	
						addColoredText(textPane,fileStringLine+"\n",Color.black);
					}
					try
					{
						//addColoredText(textPane,"read success\n",Color.green);
						fileData.close(); 
					}
					catch( IOException ex)
					{
						System.out.println("close error\n");
						JOptionPane.showMessageDialog(appFrame,"close error");
						//textPane.setText(textPane.getText()+"close error\n");
						addColoredText(textPane,"close error\n",Color.red);
						return;
					}		
		}
}