package ntou.cs.java2021.finalproject;

// Displays text using font.
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


import java.awt.Color ;

//全螢幕
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
//改右上角icon 
//import java.awt.Toolkit;
import java.io.File;
//改右上角icon
public class finalProject {

	private JFrame appFrame; // 定義為靜態變數以便main使用 並把這個視窗命名為 appFrame
	private JButton startToChangeButton; // button to start // 這裡定義按鈕元件  以便讓ActionListener使用
	private JButton readOutputFileButton; // button to read  // 這裡定義按鈕元件  以便讓ActionListener使用
	private JButton readInputFileButton; // button to read  // 這裡定義按鈕元件  以便讓ActionListener使用
	private JButton descriptionButton; // button to read  // 這裡定義按鈕元件  以便讓ActionListener使用
	private JButton upTextsizeButton; // button to read  // 這裡定義按鈕元件  以便讓ActionListener使用
	private JButton downTextsizeButton; // button to read  // 這裡定義按鈕元件  以便讓ActionListener使用

	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JTextPane text; // displays example text 
	//其實上面最原始 是用	JTextArea text 寫檔
	//但之後改成 JTextPane (因為他能逐一分別上文字顏色)
	//下面的 text.setText("XXXX");改成 addColoredText(text,"XXXX",Color.XXX);
	//JTextPane JTextPane 都能通用 text.setText("XXXX"); 但是是黑的
	private JTextField inputNameToChangeTextBox1;
	private JTextField inputNameToReadTextBox1; 	
	private JTextField inputNameToReadTextBox2; 
	private String textBefore;
	
	private String imgPath;
	private String inputFilePath;
	private String outputFilePath;
			
	private int textSize=20;
	
	public void addColoredText(JTextPane pane, String text, Color Color) {
        StyledDocument doc = pane.getStyledDocument();
		
        Style style = pane.addStyle("Color Style", null);
		
        StyleConstants.setForeground(style, Color);
        try 
		{
            doc.insertString(doc.getLength(), text, style);
        } 
		
        catch (BadLocationException e) {
            e.printStackTrace();
        }
	}
	
	public finalProject() { // 構造器, 建立圖形介面
	
	
		appFrame = new JFrame("Font Test");
		
		
		
		Path path = new Path();//先拿到執行中檔案的位置
		path.takePath();//先拿到執行中檔案的位置
		imgPath=path.getImgPath();
		inputFilePath=path.getInputFilePath();
		outputFilePath=path.getOutputFilePath();
		System.out.println(path);

		//改右上角icon
		//appFrame.setIconImage(Toolkit.getDefaultToolkit().createImage("\\"+System.getProperty("user.dir")+"\\img\\"+"321.png"));
		appFrame.setIconImage(Toolkit.getDefaultToolkit().createImage(imgPath+"321.png"));
		
		appFrame.setVisible(true); // display Fram
		//載入動畫
		try
		{
			//載入畫面-歡迎使用
			//SplashWindow3 a3 = new SplashWindow3("\\"+System.getProperty("user.dir")+"\\img\\"+"SIC.png",appFrame,2000);
			SplashWindow3 a3 = new SplashWindow3(imgPath+"SIC.png",appFrame,2000);
			Thread.sleep(1900);//等他1.9秒
			//載入畫面-LOADING
			//SplashWindow3 a = new SplashWindow3("\\"+System.getProperty("user.dir")+"\\img\\"+"loading.gif",appFrame,2500);
			SplashWindow3 a = new SplashWindow3(imgPath+"loading.gif",appFrame,2500);
			Thread.sleep(2400);//等他2.4秒
		}
		catch(Exception e)
		{
			e.printStackTrace();
			// 能夠捕獲InvocationTargetException
			// 能夠捕獲InterruptedException
		}

		MyEventListner handler = new MyEventListner();//建立一個名為handler的觸發事件並觸發名為MyEventListner的FUNTION(下面實作)
		
		// create buttons and add action listeners

		startToChangeButton = new JButton("開始轉化目地碼"); 
		readOutputFileButton = new JButton("讀取(outputFile資料夾內)"); 
		readInputFileButton = new JButton("讀取(inputFile資料夾內)");
		descriptionButton= new JButton("說明");
		upTextsizeButton = new JButton("放大文字");// 
		downTextsizeButton = new JButton("縮小文字");// 
		upTextsizeButton.addActionListener(handler);//按鈕被出發會做 handler FUNTION
		downTextsizeButton.addActionListener(handler);//按鈕被出發會做 handler FUNTION
		startToChangeButton.addActionListener(handler);//按鈕被出發會做 handler FUNTION
		readOutputFileButton.addActionListener(handler);//按鈕被出發會做 handler FUNTION
		readInputFileButton.addActionListener(handler);//按鈕被出發會做 handler FUNTION
		descriptionButton.addActionListener(handler);//按鈕被出發會做 handler FUNTION
		// create text area and set initial font
		text = new JTextPane();
		addColoredText(text,"NOTHING",Color.RED);
		Font font = new Font("細明體", Font.BOLD, textSize);
		text.setFont(font);
		inputNameToChangeTextBox1= new JTextField ("SIC.txt",20);
		inputNameToReadTextBox1= new JTextField ("SIC_Final.txt",20);
		inputNameToReadTextBox2= new JTextField ("SIC.txt",20);
		// add GUI components to Frame
		JPanel changeInputFilePanel = new JPanel(); // used to get proper layout
		JPanel readOutputFilePanel = new JPanel(); // used to get proper layout
		JPanel readInputFilePanel = new JPanel(); // used to get proper layout
		JPanel textSizeAndDescriptionPanel = new JPanel(); // used to get proper layout
				
		JPanel groupPanels = new JPanel(); // used to get proper layout
		//定義元件 顯示文字按鈕
		label1=new JLabel("輸入你要轉化的SICCode(包括檔案類別)(請放入input資料夾內)如果沒有顯示write Succse to XXX_Final.txt 代表你有寫東西 但文件格式不對 EX:OAO.txt/SIC.txt");
		changeInputFilePanel.add(label1);
		changeInputFilePanel.add(inputNameToChangeTextBox1);
		changeInputFilePanel.add(startToChangeButton);
		label2=new JLabel("輸入你要顯示/讀取在output資料夾中的檔案(包括檔案類別)EX:123.txt/XXX_Final.txt(轉化後)");
		readOutputFilePanel.add(label2);
		readOutputFilePanel.add(inputNameToReadTextBox1);
		readOutputFilePanel.add(readOutputFileButton);
		label3=new JLabel("顯示/讀取檔案在input資料夾中的檔案(包括檔案類別)EX:SIC.txt/SIC.asm/OAO.txt");
		readInputFilePanel.add(label3);
		readInputFilePanel.add(inputNameToReadTextBox2);
		readInputFilePanel.add(readInputFileButton);
		
		textSizeAndDescriptionPanel.add(upTextsizeButton);
		textSizeAndDescriptionPanel.add(descriptionButton);
		textSizeAndDescriptionPanel.add(downTextsizeButton);
		
		GridLayout layout = new GridLayout(4,1);
		groupPanels.setLayout(layout);
		groupPanels.add(readInputFilePanel);
		groupPanels.add(changeInputFilePanel);
		groupPanels.add(readOutputFilePanel);
		groupPanels.add(textSizeAndDescriptionPanel);
		appFrame.add(groupPanels,BorderLayout.NORTH); // add buttons at top
		appFrame.add(new JScrollPane(text)); // allow scrolling 有下拉滾輪
		
		//顯示
		//載入成功畫面
		//SplashWindow3 a2 = new SplashWindow3("\\"+System.getProperty("user.dir")+"\\img\\"+"loadSucess.png",appFrame,2000);
		SplashWindow3 a2 = new SplashWindow3(imgPath+"loadSucess.png",appFrame,2000);
		//appFrame.setUndecorated(true); // 無邊框
		//appFrame.setSize(700, 600);//set size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle bounds = new Rectangle(screenSize);
		appFrame.setBounds(bounds);
		
		//用下面那個//可以全螢幕但是他在彈窗警告時會最小化
		//appFrame.getGraphicsConfiguration().getDevice().setFullScreenWindow(appFrame); // set Frame size
		
	}

	private class MyEventListner implements ActionListener  
	{
		// TODO
		public void actionPerformed(ActionEvent e)
		{
			 
				String buttonName = e.getActionCommand();
				if (buttonName.equals("開始轉化目地碼"))
				{
					Assamble textWrite =new Assamble(appFrame,text,inputNameToChangeTextBox1.getText());
					textWrite.write();
				}
				else if (buttonName.equals("讀取(outputFile資料夾內)"))
				{
					readFile read = new readFile(appFrame,text,outputFilePath+inputNameToReadTextBox1.getText());
					read.displayFileText();
				}
				else if (buttonName.equals("讀取(inputFile資料夾內)"))
				{
					readFile read = new readFile(appFrame,text,inputFilePath+inputNameToReadTextBox2.getText());
					read.displayFileText();
				}
				else if (buttonName.equals("說明"))
				{
						 SplashWindow3 a2 = new SplashWindow3(imgPath+"SICADDR.jpg",appFrame,6000);
				}
				else if (buttonName.equals("縮小文字"))
				{
					if(textSize>15)
						textSize--;
					Font font = new Font("細明體", Font.BOLD, textSize);
					text.setFont(font);
				}
				else if (buttonName.equals("放大文字"))
				{
					if(textSize<30)
					textSize++;
					Font font = new Font("細明體", Font.BOLD, textSize);
					text.setFont(font);
				}				
		}

	}
	public JFrame getFrame() {
		return appFrame;
	}

} // end class EcofontFrame
