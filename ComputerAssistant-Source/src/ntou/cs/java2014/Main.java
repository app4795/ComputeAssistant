package ntou.cs.java2014;
import ntou.cs.java2014.Memo.*;
import ntou.cs.java2014.Translate.*;
import ntou.cs.java2014.WikiSearch.*;
import ntou.cs.java2014.SuperClipBoard.*;
import ntou.cs.java2014.OnTop.*;
import ntou.cs.java2014.Interface.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
public class Main{
	private LookClipBoard lookClipBoard;
	private Start arr[];
	private Memo memo;
	private Translate translate;
	private WikiSearch wikiSearch;
	private SuperClipBoard superClipBoard;
	private OnTop onTop;
	private SystemTray tray;
	private TrayIcon trayIcon;
	private PopupMenu popup;
	private Menu menu;
	private ArrayList<MenuItem> menuItem;
	private static final String buttonname[]={"翻譯","WikiSearch","視窗置頂","多重剪貼簿","行事曆"};
	public Main(){
		int i;
		arr=new Start[5];
		arr[0]=translate=new Translate();
		arr[1]=wikiSearch=new WikiSearch();
		arr[2]=onTop=new OnTop();
		arr[3]=superClipBoard=new SuperClipBoard();
		arr[4]=memo=new Memo();
        popup = new PopupMenu();
		trayIcon = null;
	    if (SystemTray.isSupported()) {
	    	tray=SystemTray.getSystemTray();
	        URL imgURL1 = Main.class.getResource("/images/ministar.png");
	        if(imgURL1!=null){
			    trayIcon = new TrayIcon(new ImageIcon(imgURL1).getImage(),"ComputerAssistant", popup);	
	        }
	        try {
	            tray.add(trayIcon);
	        } catch (AWTException e) {
	            System.err.println(e);
	        }
	    } else {
	        System.out.println("NULL");
	    }
		lookClipBoard=new LookClipBoard(translate,wikiSearch,superClipBoard,onTop,trayIcon);
		menuItem=new ArrayList<MenuItem>();
		for(i=0;i<5;++i){
			menuItem.add(new MenuItem(buttonname[i]));
			menuItem.get(i).addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					arr[menuItem.indexOf(e.getSource())].start();
				}
			});
			popup.add(menuItem.get(i));
		}
		com.group_finity.mascot.Main.main(popup,trayIcon);
	}
	public static void main(String args[]){
		Main main=new Main();
		File file=new File("Memo");
		if  (!file .exists()  && !file .isDirectory()){       
		    System.out.println("//不存在");  
		    file .mkdir();    
		}
		file =new File("Memo\\mydata");
		if  (!file .exists()  && !file .isDirectory()){       
		    System.out.println("//不存在");  
		    file .mkdir();    
		}
		file=new File("Translate");
		if  (!file .exists()  && !file .isDirectory()){       
		    System.out.println("//不存在");  
		    file .mkdir();    
		}
		file =new File("Translate\\mydata");
		if  (!file .exists()  && !file .isDirectory()){       
		    System.out.println("//不存在");  
		    file .mkdir();    
		}
		file=new File("WikiSearch");
		if  (!file .exists()  && !file .isDirectory()){       
		    System.out.println("//不存在");  
		    file .mkdir();    
		}
		file =new File("WikiSearch\\mydata");
		if  (!file .exists()  && !file .isDirectory()){       
		    System.out.println("//不存在");  
		    file .mkdir();    
		}
	}
}
