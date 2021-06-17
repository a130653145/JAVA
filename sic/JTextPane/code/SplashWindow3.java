package ntou.cs.java2021.finalproject;

// Displays text using font.
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
//全螢幕
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.font.FontRenderContext;

import java.awt.Frame;


class SplashWindow3 extends JWindow
{
	public SplashWindow3(String filename, Frame f, int waitTime)
	{
		super(f);
		JLabel l = new JLabel(new ImageIcon(filename));
		getContentPane().add(l, BorderLayout.CENTER);
		pack();
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle bounds = new Rectangle(screenSize);
		
		Dimension labelSize = l.getPreferredSize();
		setLocation(screenSize.width/2 - (labelSize.width/2),screenSize.height/2 - (labelSize.height/2));
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				String imgPath="\\"+System.getProperty("user.dir");//先拿到這個執行中檔案的位置
				int findCodePathToGetRealPath = 0;
				findCodePathToGetRealPath=imgPath.indexOf("\\code");
		
				imgPath=imgPath.substring(0,findCodePathToGetRealPath);
				imgPath=imgPath+"\\img\\";
				System.out.println(imgPath+"SICADDR.jpg");
				try
				{
					Thread.sleep(2000);//等他2秒
				}
				catch(Exception eee)
				{
					eee.printStackTrace();
				}

				setVisible(false);
				dispose();//關掉GUI
				
			}
		});
		final int pause = waitTime;
		final Runnable closerRunner = new Runnable()
		{
			public void run()
			{
				setVisible(false);
				dispose();
			}
		};
		Runnable waitRunner = new Runnable()
		{
			public void run()
			{
				try
				{
					Thread.sleep(pause);
					SwingUtilities.invokeAndWait(closerRunner);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					// 能夠捕獲InvocationTargetException
					// 能夠捕獲InterruptedException
				}
			}
		};
		setBounds(bounds);
		setVisible(true);
		Thread splashThread = new Thread(waitRunner, "SplashThread");
		splashThread.start();
	}
}