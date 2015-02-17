package ntou.cs.java2014.Translate;
import com.memetix.mst.language.Language;

import ntou.cs.java2014.Interface.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
public class Translate extends BasisFrame implements Start{
	private JComboBox<String> original,target;
	private JLabel originalText,targetText;
	private JPanel mid;
	private JCheckBox check;
	private static final String menu[]={"中文","英文","日文"};
	private static final Language arguments[]={Language.CHINESE_TRADITIONAL,Language.ENGLISH,Language.JAPANESE};
	public Translate(){
		int set;
		original=new JComboBox<>(menu);
		target=new JComboBox<>(menu);
		check=new JCheckBox("開啟");
		check.setBackground(new Color(222, 245, 129));
		originalText=new JLabel("從");
		targetText=new JLabel("翻譯至");
		mid=new JPanel();
		mid.setLayout(new GridLayout(2,4,0,0));
		mid.add(originalText);
		mid.add(targetText);
		mid.add(check);
		mid.add(original);
		mid.add(target);
		mid.setBackground(new Color(222, 245, 129));
		File sfile = new File("Translate\\mydata\\set.txt");
		if (sfile.exists() == false){
			System.out.println("null");
			try {
				sfile.createNewFile();
				System.out.println("createNewFile");
				FileWriter fw = new FileWriter(sfile);
				BufferedWriter outFile = new BufferedWriter(fw);
				outFile.write(String.format("000"));
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
				original.setSelectedIndex(set/100);
				target.setSelectedIndex(set/10%10);
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
		fbsave.addMouseListener(new savebutton(frame,fbsave,original,target,check));
		fbre.addMouseListener(new rebutton(frame,fbre,original,target,check));
		frame.pack();
		frame.setVisible(true);
	}
	public void start(){
		main("翻譯設定");
	}
	public String translation(String originalString){
		com.memetix.mst.translate.Translate.setClientId("papago89");
		com.memetix.mst.translate.Translate.setClientSecret("/ACMzwM7XezJcAw8+C8KGPBEQx3NBgDSf+Bb/ZlPPHw=");
	    String translatedText;
		try {
			translatedText = com.memetix.mst.translate.Translate.execute(originalString,arguments[original.getSelectedIndex()],arguments[target.getSelectedIndex()]);
		    System.out.println(translatedText);
		    return translatedText;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	public boolean isOpen(){
		return check.isSelected();
	}
	public class savebutton extends BasisFrame.savebutton{
		JComboBox original,target;
		JCheckBox check;
		public savebutton(JFrame f2temp,JLabel f2label,JComboBox original,JComboBox target,JCheckBox check){
			super(f2temp,f2label);
			this.original=original;
			this.target=target;
			this.check=check;
		}
		public void mouseReleased(MouseEvent e){
			System.out.println("儲存");
			System.out.println("savefilego");
			File sfile = new File("Translate\\mydata\\set.txt");
			try {
				FileWriter fw = new FileWriter(sfile);
				BufferedWriter outFile = new BufferedWriter(fw);
				outFile.write(String.format("%d%d%d",original.getSelectedIndex(),target.getSelectedIndex(),check.isSelected()?1:0));
				outFile.close();
			} catch (IOException ex){
				System.out.println(ex);
			}
		}
	}
	public class rebutton extends  BasisFrame.rebutton{
		JComboBox original,target;
		JCheckBox check;
		public rebutton(JFrame f2temp,JLabel f2label,JComboBox original,JComboBox target,JCheckBox check){
			super(f2temp,f2label);
			this.original=original;
			this.target=target;
			this.check=check;
		}
		public void mouseReleased(MouseEvent e){
			int set;
			System.out.println("復原到儲存前");
			File sfile = new File("Translate\\mydata\\set.txt");
			try{
				Scanner scan = new Scanner(sfile);
				if(scan.hasNextLine()){
					set=scan.nextInt();
					original.setSelectedIndex(set/100);
					target.setSelectedIndex(set/10%10);
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