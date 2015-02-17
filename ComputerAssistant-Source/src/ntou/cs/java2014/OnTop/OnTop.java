package ntou.cs.java2014.OnTop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ntou.cs.java2014.Interface.BasisFrame;
import ntou.cs.java2014.Interface.Start;
import ntou.cs.java2014.SuperClipBoard.SuperClipBoard.exitbutton;
public class OnTop extends BasisFrame implements Start {
	JTextArea ftextarea;
	public JScrollPane fscroll;
	private boolean check;
	public void createAndShowGUI(String title){
		super.createAndShowGUI(title);
		check=true;
		JScrollPane fscroll;
		ftextarea = new JTextArea();
		ftextarea.setRows(9);
		fscroll = new JScrollPane(ftextarea);
		fscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		ftextarea.setColumns(15);
		ftextarea.setFont(new Font("", Font.BOLD, 20));
		ftextarea.setLineWrap(true);
		ftextarea.setBackground(new Color(222, 245, 129));
		fbsave.addMouseListener(new savebutton(frame,fbsave));
		fbre.setText("");
		fbexit.addMouseListener(new exitbutton(frame,fbexit));
		masterp.add(fscroll,BorderLayout.CENTER);
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
	}
	public void start(){
		main("’u’¸");
	}
	public boolean isOpen(){
		return check;
	}
	public class savebutton extends BasisFrame.savebutton{
		public savebutton(JFrame f2temp, JLabel f2label){
			super(f2temp,f2label);
		}
		public void mouseReleased(MouseEvent e){
			ftextarea.selectAll();
			ftextarea.copy();
			fedit.setVisible(false);
			fedit.dispose();
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
