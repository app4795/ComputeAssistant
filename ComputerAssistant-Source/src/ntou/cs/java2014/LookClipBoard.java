package ntou.cs.java2014;
import ntou.cs.java2014.Translate.*;
import ntou.cs.java2014.WikiSearch.*;
import ntou.cs.java2014.OnTop.OnTop;
import ntou.cs.java2014.SuperClipBoard.*;

import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.*;
public class LookClipBoard implements ClipboardOwner{
	private Clipboard sysClipBoard;
	private Transferable clipcontent;
	private Translate translate;
	private WikiSearch wikiSearch;
	private OnTop onTop;
	private SuperClipBoard superClipBoard;
	private TrayIcon trayIcon;
	public LookClipBoard(Translate translate,WikiSearch wikiSearch,SuperClipBoard superClipBoard,OnTop onTop,TrayIcon trayIcon){
		super();
		initialize();
		this.translate=translate;
		this.wikiSearch=wikiSearch;
		this.onTop=onTop;
		this.trayIcon=trayIcon;
		this.superClipBoard=superClipBoard;
	}
	private void initialize(){
		sysClipBoard = Toolkit.getDefaultToolkit().getSystemClipboard(); //獲取系統剪貼簿
		clipcontent = sysClipBoard.getContents(null); //取得剪貼簿內容
		
		sysClipBoard.setContents(clipcontent, this); //設定剪貼簿內容並註冊擁有者		
												    //擁有者可以用lostOwnership方法取得剪貼簿改變消息
	}
	public void lostOwnership(Clipboard clipboard, Transferable contents){
		try{
			Thread.sleep(20);   //讓執行緒小睡，等待剪貼簿準備好，這行很重要!!
			clipcontent = clipboard.getContents(null);	//再次獲得剪貼板內容
			clipboard.setContents(clipcontent, this);	//要一直監聽所以要在註冊一次
		}
		catch(Exception e){
			 System.out.println("Exception: " + e);  
		}
		
		
		//以下是簡單處理程式就不另外寫成函式了!
		try{
			if(clipcontent.isDataFlavorSupported(DataFlavor.stringFlavor)){
				String clipData= (String) clipcontent.getTransferData(DataFlavor.stringFlavor),output=new String();
				System.out.println(clipData);
				if(superClipBoard.isOpen()){
					superClipBoard.superClipBoard(clipData);
				}
				else if(onTop.isOpen()){
				}
				else{
					if(clipData.length()<4||clipData.substring(0,4).compareTo("http")!=0){
						if(translate.isOpen())
							output=output+"翻譯:"+translate.translation(clipData)+"\n";
						if(wikiSearch.isOpen()){
							output=output+"已複製網址";
							wikiSearch.wikiSearch(clipData);
						}
						if(translate.isOpen()||wikiSearch.isOpen()){
							trayIcon.displayMessage(clipData,output,TrayIcon.MessageType.INFO);
						}
					}
				}
			}
		}
		catch(Exception e){
			 System.out.println("Exception: " + e);  
		}
	}
}