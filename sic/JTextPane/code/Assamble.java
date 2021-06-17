package ntou.cs.java2021.finalproject;
import java.lang.Integer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.Color ;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class Assamble {
		private FileReader fileData;
		private PrintWriter Write;
		private String realInputToChangeFileName;
		private JTextPane textPane;
		private String fileName;
		private JFrame appFrame;
		public Assamble(JFrame inFrame,JTextPane inPane,String inputNameToChangeTextBox1)
		{
			appFrame=inFrame;
			textPane = inPane;
			fileName=inputNameToChangeTextBox1;
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
		
		
		public void write()
		{
					String imgPath,inputFilePath,outputFilePath;
					Path path = new Path();//先拿到執行中檔案的位置
					path.takePath();//先拿到執行中檔案的位置
					imgPath=path.getImgPath();
					inputFilePath=path.getInputFilePath();
					outputFilePath=path.getOutputFilePath();
				
					//PASS1 先把CODE 讀懂 並標出所有位置
					Scanner scanner = new Scanner(System.in);
					OP op=new OP();
					String[] op_TAB = op.getOpTab();
					String[] opCode = op.getOpCode();
							
					ArrayList<symBol> symBolTable = new ArrayList<>();//登記非指令碼的名字的記憶體位置EX:FIRST  1000 CLOOP  1003 ENDFIL  1015 EOF  102A 
					ArrayList<Integer> codeLength = new ArrayList<>();//一個指令的長度
					ArrayList<String> Location = new ArrayList<>();//位置
					ArrayList<String> targetAddr = new ArrayList<>();//目標位置(在寫讓人看得懂的指令位置)
					ArrayList<Data> Code = new ArrayList<>();//一行CODE EX: ADD M,5
					String realInputToChangeFileName;
					int findDotToGetRealName = 0;
					String stringFind=fileName;
					findDotToGetRealName=stringFind.indexOf(".");
					if(findDotToGetRealName==-1)
					{
						realInputToChangeFileName=fileName;
					}
					else
					{
						realInputToChangeFileName=stringFind.substring(0,findDotToGetRealName);
					}
					textPane.setText("");
					try
					{
						 fileData = new FileReader(inputFilePath+fileName);
						 System.out.println("open:"+inputFilePath+fileName);
						 addColoredText(textPane,"open:"+inputFilePath+fileName+"\n",Color.black);	 
					}
					catch(IOException ex)
					{
						System.out.println(inputFilePath+fileName);
						
						textPane.setText("");
						addColoredText(textPane,"open error",Color.red);
						System.out.println("open error");
						JOptionPane.showMessageDialog(appFrame,"input named error or open error");
						return;
					}
					//textPane.setText("open success\n");
					addColoredText(textPane,"open success\n",Color.green);
					System.out.println("open success\n");
					//JOptionPane.showMessageDialog(appFrame,"open success");	
					Scanner scn =new Scanner(fileData);
					int n = 0, num = 0, previousLoction = 0, j = 0, i = 0;
					String HexLoc,str1=" ",str2=" ",str3=" ";
					boolean isOpCode = false,isLine=false,b=false;
					addColoredText(textPane,"start read/load file\n",Color.black);
					while(scn.hasNext())
					{
						
						String fileStringLine = scn.next();
						
						if(fileStringLine.equals("START")||fileStringLine.equals("END")||fileStringLine.equals("WORD")||fileStringLine.equals("BYTE")||fileStringLine.equals("RESB")||fileStringLine.equals("RESW"))
						{
							str2=fileStringLine;
							isOpCode=true;
						}
						else
						{
							for(i=0;i<op_TAB.length;i++)
							{
								if(fileStringLine.equals(op_TAB[i]))
								{
									str2=fileStringLine;
									isOpCode=true;
									break;
								}
							}
						}
						if(fileStringLine.equals("."))
						{
							str1=".";
							isLine=true;
						}
						if(str2.equals("RSUB"))
						{
							isLine=true;
						}
						if(!isOpCode)
						{
							str1=fileStringLine;
						}
						else if(!str2.equals("RSUB"))
						{
								str3=scn.next();
								isLine=true;
								b=false;
						}
						if(isLine)
						{
							Code.add(new Data(str1,str2,str3,str1+str2+str3));
							str1=" ";str2=" ";str3=" ";
							isLine=false;
							isOpCode=false;
						}
					}
					try
					{
						 fileData.close();
						 addColoredText(textPane,"load "+fileName+" success\n",Color.GREEN);
						 //textPane.setText(textPane+"load "+fileName+" success\n");
						 
					}
					catch( IOException ex)
					{
						System.out.println("close error");
						JOptionPane.showMessageDialog(appFrame,"close error");
						//textPane.setText(textPane+"close error\n");
						addColoredText(textPane,"close error\n",Color.red);
						return;
					}
					

					
					for (i = 0; i < Code.size(); i++) 
					{
						// 計算位置//
						if (Code.get(i).getStr().contains(".")) 
						{
							Location.add("");
							HexLoc = (Integer.toHexString(previousLoction += Integer.parseInt(Integer.toString(num),16))).toUpperCase();
						} 
						else 
						{
							if (i != 1) 
							{
								HexLoc = (Integer.toHexString(previousLoction += Integer.parseInt(Integer.toString(num),16))).toUpperCase();
								if (i == Code.size() - 1)
									HexLoc = "";
							} 
							else// 起始位置從1開始
								HexLoc = Integer.toString(n);
							Location.add(HexLoc);
						}
						if (i == 0) //location初始值設定
						{
							n = Integer.parseInt(Code.get(i).getThirdCode());
							previousLoction = Integer.parseInt(Integer.toString(n), 16);
							Location.remove(0);
							Location.add(0, Integer.toString(n));
						}
						// 計算長度//
						if (Code.get(i).getStr().contains(".") || i == 0) 
						{
							num = 0;
						} 
						else if (Code.get(i).getSecondCode().equals("BYTE"))// 當運算子有BYTE時2種C和X 
						{ 
							if (Code.get(i).getThirdCode().contains("C")) // 當C'EOF'時長度為3
							{
								char c[] = Code.get(i).getThirdCode()
										.substring(Code.get(i).getThirdCode().indexOf('\'') + 1, Code.get(i).getThirdCode().length() - 1)
										.toCharArray();
								num = c.length;
							} else
								num = 1;// 當X'F1'時長度1
						} 
						else if (Code.get(i).getSecondCode().contains("RESW")) // 當RESW時數字*3
						{
							num = Integer.parseInt(Code.get(i).getThirdCode()) * 3;
						} 
						else if (Code.get(i).getSecondCode().contains("RESB")) 
						{
							num = Integer.parseInt(Integer.toHexString(Integer.parseInt(Code.get(i).getThirdCode())));
						} 
						else // 其餘運算子都是3包含WORD也是 byte
							num = 3;
						codeLength.add(num);
						
						
						// 建立symBolTable// 把所有非指令碼的東西 標出位置 EX:FIRST  1000 CLOOP  1003 ENDFIL  1015 EOF  102A
						if (!Code.get(i).getFirstCode().contains(" ") && i != 0 && !Code.get(i).getFirstCode().contains(".")) 
						{
							symBolTable.add(new symBol(Code.get(i).getFirstCode(), HexLoc));
						}
			 
					}
					// pass2建立目的碼(FINALL)//
					for (i = 0; i < Code.size(); i++) 
					{
						StringBuilder getAllTargetAddr = new StringBuilder("");
						for (j = 0; j < op_TAB.length; j++) {
							if (Code.get(i).getSecondCode().equals(op_TAB[j])) 
							{
								getAllTargetAddr.append(opCode[j]);
								break;
							}
						}
						for (j = 0; j < symBolTable.size(); j++) 
						{
							if (Code.get(i).getThirdCode().contains(",X")) 
							{
								if (symBolTable.get(j).getsymBolCode().equals(Code.get(i).getThirdCode().substring(0,Code.get(i).getThirdCode().length()-2)))
								{// BUFFER有x加8000
									// 先16進位->10進位(8000也要轉16進位->10進位)再相加，最後再轉16進位(用程式好作加法)
									getAllTargetAddr.append(Integer.toHexString(Integer.parseInt(symBolTable.get(j).getLocation(), 16) + Integer.parseInt("8000",16)));
									break;
								}
							} 
							else if (symBolTable.get(j).getsymBolCode().equals(Code.get(i).getThirdCode())) // 查SYMTAB若其目的碼小于6位元就補0
							{
								if (getAllTargetAddr.length() + symBolTable.get(j).getLocation().length() != 6) 
								{
									int len = getAllTargetAddr.length() + symBolTable.get(j).getLocation().length();
									for (int k = 0; k < 6 - len; k++)
										getAllTargetAddr.append("0");
									getAllTargetAddr.append(symBolTable.get(j).getLocation());
								} 
								else
									getAllTargetAddr.append(symBolTable.get(j).getLocation());
								break;
							}
						}
						// BYTE處理分為X和C
						if (Code.get(i).getSecondCode().equals("BYTE")) 
						{
							char c[] = Code.get(i).getThirdCode()
									.substring(Code.get(i).getThirdCode().indexOf('\'') + 1, Code.get(i).getThirdCode().length() - 1)
									.toCharArray();
							for (int k = 0; k < c.length; k++) 
							{
								if (Code.get(i).getThirdCode().contains("C"))
									getAllTargetAddr.append(Integer.toHexString(c[k]).toUpperCase());// ASCii由10->16目的碼
								else
									getAllTargetAddr.append(c[k]);
							}
						} 
						else if (Code.get(i).getSecondCode().equals("WORD")) // WORD處理Code.get(i).getThirdCode()補足6位元後直接輸出
						{
							getAllTargetAddr.append(Integer.toHexString(Integer.parseInt(Code.get(i).getThirdCode())).toUpperCase());
							if (getAllTargetAddr.length() < 6) //fomate3
							{
								getAllTargetAddr.reverse();
								int len = getAllTargetAddr.length();
								for (int k = 0; k < 6 - len; k++)
									getAllTargetAddr.append("0");//補0 -> 000006
								getAllTargetAddr.reverse();
							} 
							else if (getAllTargetAddr.length() > 6)// //fomate4
							{
								getAllTargetAddr = new StringBuilder(getAllTargetAddr.substring(2, 8));
							}
						} 
						else if (Code.get(i).getSecondCode().equals("RSUB"))// RSUB=>opCode後補滿0六位 
							getAllTargetAddr.append("0000");
						targetAddr.add(getAllTargetAddr.toString()); // 結果放入目的
						if(Code.get(i).getFirstCode().equals("."))//有註解的
						{
							targetAddr.remove(i);
							targetAddr.add("");
						}
							
					}
					targetAddr.remove(targetAddr.size() - 1);
					targetAddr.add("");
					/// 寫檔
					
					try 
					{
						Write = new PrintWriter(outputFilePath+realInputToChangeFileName+"_Final.txt");
						addColoredText(textPane,"write"+outputFilePath+realInputToChangeFileName+"_Final.txt"+"\n",Color.black);
						System.out.println(outputFilePath+realInputToChangeFileName);
					}
					catch( FileNotFoundException ER)
					{
						System.out.println(outputFilePath+realInputToChangeFileName);
						System.out.println("write error");
						JOptionPane.showMessageDialog(appFrame,"write error");
						//textPane.setText(textPane.getText()+"write error\n");
						addColoredText(textPane,"write error\n",Color.red);
						return;
					}
					
					Write.printf("%-14s%-9s%-9s%-9s%-10s\r\n","位置", "原始敘述","原始敘述", "原始敘述", "目的碼");//讀取時會空格錯誤
					System.out.printf("%-14s%-9s%-9s%-9s%-10s\r\n","位置","原始敘述","原始敘述", "原始敘述", "目的碼");//讀取時會空格錯誤
					//System.out.printf("位置      原始敘述       目的碼\n");
					//Write.printf("位置      原始敘述       目的碼\n");
					System.out.println("--------------------------------------------------");
					Write.println("-------------------------------------------------");
					for (j = 0; j < Code.size(); j++) 
					{
						Write.printf("%-16s%-13s%-13s%-13s%-10s\r\n", Location.get(j), Code.get(j).getFirstCode(),
								Code.get(j).getSecondCode(), Code.get(j).getThirdCode(), targetAddr.get(j));
						System.out.printf("%-16s%-13s%-13s%-13s%-10s\r\n", Location.get(j), Code.get(j).getFirstCode(),
								Code.get(j).getSecondCode(), Code.get(j).getThirdCode(), targetAddr.get(j));
					}
					Write.close();
					//textPane.setText(textPane+"write success to "+realInputToChangeFileName+"_Final.txt"+"\n");
					addColoredText(textPane,"write success to "+realInputToChangeFileName+"_Final.txt"+"\n",Color.green);
				

		}
}