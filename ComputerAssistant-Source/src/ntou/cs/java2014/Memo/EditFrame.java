package ntou.cs.java2014.Memo;
import ntou.cs.java2014.Interface.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class EditFrame extends BasisFrame{
	JTextArea ftextarea;
	public JScrollPane fscroll;
	public void createAndShowGUI(int year,int month,int day){
		super.createAndShowGUI(String.format("%d年%d月%d日",year,month,day));
		String title=String.format("%d年%d月%d日",year,month,day);
		JScrollPane fscroll;
		JLabel fdatelabel;
		fdatelabel = new JLabel(title+"編輯中");// ***************************
		fdatelabel.setHorizontalAlignment(JLabel.CENTER);
		fdatelabel.setFont(new Font("", Font.BOLD, 20));
		ftextarea = new JTextArea();
		ftextarea.setRows(9);
		fscroll = new JScrollPane(ftextarea);
		fscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		ftextarea.setColumns(15);
		ftextarea.setFont(new Font("", Font.BOLD, 20));
		ftextarea.setLineWrap(true);
		ftextarea.setBackground(new Color(222, 245, 129));
		masterp.add(fscroll,BorderLayout.CENTER);
		fbsave.addMouseListener(new savebutton(frame,fbsave,ftextarea,year,month,day));
		fbre.addMouseListener(new rebutton(frame,fbre,ftextarea,year,month,day));
		File sfile = new File("Memo\\mydata\\"+year+month+day+".txt");
		if (sfile.exists() == false){
			System.out.println("null");
		} else {
			try {
				Scanner scan = new Scanner(sfile);
				String info = "";
				while (scan.hasNext())
					info += scan.nextLine() + "\n";
				scan.close();
				ftextarea.setText(info);
			} catch (IOException ee){
				System.out.println(ee);
			}
		}
		frame.pack();

		frame.setVisible(true);
	}
	public void main(int args1, int args2, int args3){
		final int getframe1y = args1;
		final int getframe1m = args2;
		final int getframe1d = args3;
		javax.swing.SwingUtilities.invokeLater(new Runnable(){

			public void run(){
				createAndShowGUI(getframe1y,getframe1m,getframe1d);
			}
		});
	}
	public class savebutton extends BasisFrame.savebutton{
		DatePanel aaafdsa;
		int getf1y, getf1m, getf1d;
		JTextArea setftextarea;
		public savebutton(JFrame f2temp, JLabel f2label, JTextArea saveftextarea,
				int saveyy, int savemm, int savedd){
			super(f2temp,f2label);
			getf1y = saveyy;
			getf1m = savemm;
			getf1d = savedd;
			setftextarea = saveftextarea;
		}
		public void mouseReleased(MouseEvent e){
			System.out.println("儲存");
			System.out.println("savefilego");
			File sfile = new File("Memo\\mydata\\" + getf1y + "" + getf1m + "" + getf1d
					+ ".txt");
			if (setftextarea.getText() == null
					|| setftextarea.getText().trim().equals("")){
				System.out.println("空值不能存");
				sfile.delete();
			} else {
				if (sfile.isFile()){
					System.out.println("not create ");
				} else {
					try {
						sfile.createNewFile();
					} catch (IOException ex){
						System.out.println(ex);
					}
					System.out.println("createNewFile");
				}
				try {
					FileWriter fw = new FileWriter(sfile);
					BufferedWriter outFile = new BufferedWriter(fw);
					outFile.write(setftextarea.getText());
					outFile.close();
				} catch (IOException ex){
					System.out.println(ex);
				}
			}
			aaafdsa = new DatePanel();
			JOptionPane.showMessageDialog(fedit, "資料變更成功!!\n\n"+"備註:假如星星標記無消失或無法點選日期時\n"+"點選任一年或月的切換鈕即可", "儲存",
					JOptionPane.QUESTION_MESSAGE);
		}
	}
	public class rebutton extends  BasisFrame.rebutton{
		JTextArea getftextarea;
		int getf1y, getf1m, getf1d;
		public rebutton(JFrame f2temp, JLabel f2label, JTextArea setftextarea,
				int setf1y, int setf1m, int setf1d){
			super(f2temp,f2label);
			getftextarea = setftextarea;
			getf1y = setf1y;
			getf1m = setf1m;
			getf1d = setf1d;
		}
		public void mouseReleased(MouseEvent e){
			System.out.println("復原到儲存前");
			File sfile = new File("Memo\\mydata\\" + getf1y + "" + getf1m + "" + getf1d
					+ ".txt");
			if (sfile.exists() == false){
				System.out.println("null");
			} else {
				try {
					Scanner scan = new Scanner(sfile);
					String info = "";
					while (scan.hasNext())
						info += scan.nextLine() + "\n";
					getftextarea.setText(info);
				} catch (IOException ee){
					System.out.println(ee);
				}
			}
		}
	}
}