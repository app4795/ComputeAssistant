package ntou.cs.java2014.Interface;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
public class BasisFrame {
	public JFrame frame;
	public JLabel masterptitle;
	public ImageIcon icon;
	public JPanel masterp;
	public JPanel fpsouth;
	public JLabel fbsave;
	public JLabel fbre;
	public JLabel fbexit;
	public JPanel fpnorth;
	public JLabel ftitlelabel;
	public void createAndShowGUI(String title){
		java.net.URL imgURL1;
		imgURL1 = BasisFrame.class.getResource("/images/star.png");
		ImageIcon icon1 = new ImageIcon(imgURL1);
		Image im = icon1.getImage();
		frame = new JFrame(title);
		frame.setIconImage(im);
		frame.setLayout(new BorderLayout());
		frame.setUndecorated(true);
		java.net.URL imgURL;
		imgURL = BasisFrame.class.getResource("/images/00001.gif");
		icon = new ImageIcon(imgURL);
		masterptitle = new JLabel();
		masterp = new JPanel();
		masterp.setBorder(new LineBorder(new Color(177, 219, 16), 5, true));
		masterp.setBackground(new Color(177, 219, 16));
		masterp.setOpaque(true);
		masterp.setLayout(new BorderLayout());
		fpnorth = new JPanel(){
			protected void paintComponent(Graphics g){
				g.drawImage(icon.getImage(), 0, 0, null);

				super.paintComponent(g);
			}
		};
		fpnorth.setPreferredSize(new Dimension(300, 50));
		fpnorth.setOpaque(false);
		ftitlelabel = new JLabel(title);
		ftitlelabel.setHorizontalAlignment(JLabel.CENTER);
		ftitlelabel.setFont(new Font("", Font.BOLD, 20));
		fpnorth.add(ftitlelabel);
		fpnorth.addMouseListener(new moveframe(frame));
		fpnorth.addMouseMotionListener(new moveframe(frame));
		fpsouth = new JPanel();
		fpsouth.setLayout(new BorderLayout());
		fbsave = new JLabel("     –×‘¶         ");
		fbsave.setHorizontalAlignment(JLabel.CENTER);
		fbsave.setBackground(new Color(222, 245, 129));
		fbsave.setOpaque(true);
		fbre = new JLabel("•œŒ´“ž–×‘¶‘O");
		fbre.setHorizontalAlignment(JLabel.CENTER);
		fbre.setBackground(new Color(222, 245, 129));
		fbre.setOpaque(true);
		fbexit = new JLabel("   —£ŠJ   ");
		fbexit.setHorizontalAlignment(JLabel.CENTER);
		fbexit.setBackground(new Color(222, 245, 129));
		fbexit.setOpaque(true);
		fbexit.addMouseListener(new exitbutton(frame, fbexit));

		fpsouth.add(fbsave, BorderLayout.WEST);
		fpsouth.add(fbre, BorderLayout.CENTER);
		fpsouth.add(fbexit, BorderLayout.EAST);

		masterp.add(fpnorth, BorderLayout.NORTH);
		masterp.add(fpsouth, BorderLayout.SOUTH);
		frame.getContentPane().add(masterp, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
	public void main(String title){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGUI(title);
			}
		});
	}
	public class moveframe implements MouseListener, MouseMotionListener{
		public JFrame fedit;
		int nowx = 0, nowy = 0;
		int endx = 0, endy = 0;
		public moveframe(JFrame f2temp){
			fedit = f2temp;
		}
		public void mousePressed(MouseEvent event){
			endx = event.getXOnScreen();
			endy = event.getYOnScreen();
			fedit.setLocation(endx - 150, endy - 30);
		}
		public void mouseDragged(MouseEvent event){
			endx = event.getXOnScreen();
			endy = event.getYOnScreen();
			fedit.setLocation(endx - 150, endy - 30);
		}
		public void mouseClicked(MouseEvent event){
		}
		public void mouseReleased(MouseEvent event){
		}
		public void mouseEntered(MouseEvent event){
		}
		public void mouseExited(MouseEvent event){
		}
		public void mouseMoved(MouseEvent event){
		}
	}
	public class exitbutton extends MouseAdapter {
		public JFrame fedit;
		public JLabel fchangecolor;
		public exitbutton(JFrame f2temp, JLabel f2label){
			fedit = f2temp;
			fchangecolor = f2label;
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
			fedit.setVisible(false);
			fedit.dispose();
		}
	}
	public class savebutton extends MouseAdapter{
		public JFrame fedit;
		public JLabel fchangecolor;
		public savebutton(JFrame f2temp, JLabel f2label){
			fedit = f2temp;
			fchangecolor = f2label;
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
		}
	}
	public class rebutton extends MouseAdapter {
		public JFrame fedit;
		public JLabel fchangecolor;
		public rebutton(JFrame f2temp, JLabel f2label){
			fedit = f2temp;
			fchangecolor = f2label;
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
		}
	}
}