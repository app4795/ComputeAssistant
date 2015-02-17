package ntou.cs.java2014.SuperClipBoard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ntou.cs.java2014.Interface.BasisFrame;
import ntou.cs.java2014.Interface.Start;

public class SuperClipBoard extends BasisFrame implements Start {
	private JScrollPane mid;
	private JPanel panel;
	private boolean check,isSelect;
	private int first,second;
	private ArrayList<JButton> text;
	public void createAndShowGUI(String title){
		super.createAndShowGUI(title);
		text=new ArrayList<JButton>();
		panel=new JPanel(new GridLayout(100,0));
		panel.setBackground(new Color(222, 245, 129));
		mid=new JScrollPane(panel);
		mid.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mid.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mid.setBackground(new Color(222, 245, 129));
		mid.setSize(500,600);
		masterp.add(mid,BorderLayout.CENTER);
		fbsave.addMouseListener(new savebutton(frame,fbsave));
		fbre.setText("");
		fbexit.addMouseListener(new exitbutton(frame,fbexit));
		frame.setBounds(0,0,500,700);
		check=true;
		isSelect=false;
		first=second=0;
		frame.setVisible(true);
	}
	public void start(){
		main("‘½d™’“\•ë");
	}
	public void superClipBoard(String str){
		JButton button=new JButton(String.format("%d:%s",text.size(),str.substring(0,str.length()>10?10:str.length())));
		button.setToolTipText(str);
		button.setSize(500,30);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				if(isSelect){
					second=text.indexOf(event.getSource());
					String temp=text.get(first).getText();
					text.get(first).setText(text.get(second).getText());
					text.get(second).setText(temp);
					temp=text.get(first).getToolTipText();
					text.get(first).setToolTipText(text.get(second).getToolTipText());
					text.get(second).setToolTipText(temp);
					isSelect=false;
				}
				else{
					first=text.indexOf(event.getSource());
					isSelect=true;
				}
			}
		});
		text.add(button);
		panel.add(button);
		panel.updateUI();
		panel.invalidate(); 
		panel.validate();
		panel.repaint();
		mid.repaint();
	}
	public boolean isOpen(){
		return check;
	}
	public class savebutton extends BasisFrame.savebutton{
		public savebutton(JFrame f2temp,JLabel f2label){
			super(f2temp,f2label);
		}
		public void mouseReleased(MouseEvent e){
			String str=new String();
			for(int i=0;i<text.size();++i)
				str+=String.format("%s\n",text.get(i).getToolTipText());
			JTextField popText=new JTextField(str);
			popText.selectAll();
			popText.copy();
			System.out.println("–×‘¶"+str);
			System.out.println("savefilego");
			isSelect=false;
			panel.removeAll();
			text.clear();
		}
	}
	public class exitbutton extends BasisFrame.exitbutton {
		public exitbutton(JFrame f2temp, JLabel f2label){
			super(f2temp,f2label);
		}
		public void mousePressed(MouseEvent e){
		}
		public void mouseEntered(MouseEvent e){
			fchangecolor.setForeground(Color.blue);
		}
		public void mouseExited(MouseEvent e){
			fchangecolor.setForeground(Color.black);
		}
		public void mouseReleased(MouseEvent e){
			System.out.println("—£ŠJ");
			check=false;
			fedit.setVisible(false);
			fedit.dispose();
		}
	}
}
