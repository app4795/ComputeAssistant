package ntou.cs.java2014.WikiSearch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ntou.cs.java2014.Interface.*;
public class WikiSearch extends BasisFrame implements Start{
	private JPanel mid;
	private JCheckBox check;
	public WikiSearch(){
		int set;
		check=new JCheckBox("開啟");
		check.setBackground(new Color(222, 245, 129));
		mid=new JPanel();
		mid.setLayout(new GridLayout());
		mid.setBackground(new Color(222, 245, 129));
		mid.add(check);
		File sfile = new File("WikiSearch\\mydata\\set.txt");
		if (sfile.exists() == false){
			System.out.println("null");
			try {
				sfile.createNewFile();
				System.out.println("createNewFile");
				FileWriter fw = new FileWriter(sfile);
				BufferedWriter outFile = new BufferedWriter(fw);
				outFile.write(String.format("0"));
				outFile.close();
			}
			catch (IOException ex){
				System.out.println(ex);
			}
		}
		try {
			Scanner scan = new Scanner(sfile);
			if(scan.hasNextLine()){
				set=scan.nextInt();
				check.setSelected(set%2==1?true:false);
				scan.close();
			}
		} catch (IOException ee){
			System.out.println(ee);
		}
	}
	public void createAndShowGUI(String title){
		super.createAndShowGUI(title);
		masterp.add(mid,BorderLayout.CENTER);
		fbsave.addMouseListener(new savebutton(frame,fbsave,check));
		fbre.addMouseListener(new rebutton(frame,fbre,check));
		frame.pack();
		frame.setVisible(true);
	}
	public void start(){
		main("Wiki搜尋設定");
	}
	public void wikiSearch(String str){
		String message = "http://en.wikipedia.org/w/index.php?title=Special%3ASearch&profile=default&search="+str+"&fulltext=Search";
		JTextField popText=new JTextField(message);
		popText.selectAll();
		popText.copy();
	}
	public boolean isOpen(){
		return check.isSelected();
	}
	public class savebutton extends BasisFrame.savebutton{
		JCheckBox check;
		public savebutton(JFrame f2temp,JLabel f2label,JCheckBox check){
			super(f2temp,f2label);
			this.check=check;
		}
		public void mouseReleased(MouseEvent e){
			System.out.println("儲存");
			System.out.println("savefilego");
			File sfile = new File("WikiSearch\\mydata\\set.txt");
			try {
				FileWriter fw = new FileWriter(sfile);
				BufferedWriter outFile = new BufferedWriter(fw);
				outFile.write(String.format("%d",check.isSelected()?1:0));
				outFile.close();
			} catch (IOException ex){
				System.out.println(ex);
			}
		}
	}
	public class rebutton extends  BasisFrame.rebutton{
		JCheckBox check;
		public rebutton(JFrame f2temp,JLabel f2label,JCheckBox check){
			super(f2temp,f2label);
			this.check=check;
		}
		public void mouseReleased(MouseEvent e){
			int set;
			System.out.println("復原到儲存前");
			File sfile = new File("WikiSearch\\mydata\\set.txt");
			try{
				Scanner scan = new Scanner(sfile);
				if(scan.hasNextLine()){
					set=scan.nextInt();
					check.setSelected(set%2==1?true:false);
					scan.close();
				}
			}
			catch (IOException ee){
				System.out.println(ee);
			}
		}
	}
}
