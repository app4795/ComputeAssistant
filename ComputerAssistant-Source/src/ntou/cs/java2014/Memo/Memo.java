package ntou.cs.java2014.Memo;
import ntou.cs.java2014.Interface.Start;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
class DatePanel extends JPanel {
	CardLayout clayout;

	JPanel panel00;// 初始畫面

	JPanel panel01;// 年切換

	JPanel pane000;// 切換用 初始畫面+年切換

	JPanel panel1;

	JPanel panel2;// 顯示月

	JPanel panel31;// 星期

	JPanel panel3;// 顯示日期

	JPanel panel30;//

	JLabel[] daynumlabel;// 月份總天數

	JLabel lbm, rbm;

	JLabel monthm;// now

	JLabel nowtodaym;// today

	int syear, smonth, smnum;// 年,月,月份總天數

	xDate1 xdate;

	zYearselect zyear1;

	JPanel exitminpanel;

	JFrame ffx;

	JLabel exitlabel;

	JLabel minlabel;

	JLabel helplabel;
	java.net.URL imgURL;
	ImageIcon lefticon, righticon, exiticon, lefticon2, righticon2, closeicon,
			close2icon, delicon, del2icon, helpicon, help2icon, starxicon;

	//Image mintitleicon;

	boolean clockxxx = false;// 更新用

	private int DELAY = 1000;

	private Timer timer;
	Memo taaa = new Memo();;
	// Thread animator;
	public DatePanel(){
		taaa.abca.clockxxx=true;
	}

	

	public DatePanel(JFrame ffxx){
		// animator=new Thread(this);
		ffx = ffxx;
		clayout = new CardLayout();
		zyear1 = new zYearselect();

		setLayout(new BorderLayout());
		setBackground(Color.black);
		setForeground(Color.black);

		timer = new Timer(DELAY, new ReboundListener());

		
		imgURL = DatePanel.class.getResource("/images/left.png");
		lefticon = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/right.png");
		righticon = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/left2.png");
		lefticon2 = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/right2.png");
		righticon2 = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/001_05.png");
		exiticon = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/close1.png");
		closeicon = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/close.png");
		close2icon = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/del.png");
		delicon = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/del2.png");
		del2icon = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/question.png");
		helpicon = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/question2.png");
		help2icon = new ImageIcon(imgURL);
		
		imgURL = DatePanel.class.getResource("/images/button_favorite_01.png");
		starxicon = new ImageIcon(imgURL);

		/*
		 * lefticon = new ImageIcon("/images/left.png");
		righticon = new ImageIcon("/images/right.png");
		lefticon2 = new ImageIcon("/images/left2.png");
		righticon2 = new ImageIcon("/images/right2.png");
		exiticon = new ImageIcon("/images/001_05.png");
		closeicon = new ImageIcon("/images/close1.png");
		close2icon = new ImageIcon("/images/close.png");
		delicon = new ImageIcon("/images/del.png");
		del2icon = new ImageIcon("/images/del2.png");
		helpicon = new ImageIcon("/images/question.png");
		help2icon = new ImageIcon("/images/question2.png");
		starxicon = new ImageIcon("/images/button_favorite_01.png");
		 */
		
		panel01 = new JPanel();
		panel01.setLayout(new BorderLayout());
		panel01.setBackground(Color.white);

		panel00 = new JPanel();
		panel00.setLayout(new BorderLayout());
		panel00.setBackground(Color.white);

		panel1 = new JPanel();
		panel1.setBackground(Color.white);
		panel1.setForeground(Color.white);
		panel1.setLayout(new BorderLayout());
		// panel1.setPreferredSize(new Dimension(300, 300));
		panel2 = new JPanel();
		panel2.setBackground(Color.white);
		panel2.setForeground(Color.white);
		panel2.setLayout(new BorderLayout());
		// panel2.setPreferredSize(new Dimension(500, 100));
		panel3 = new JPanel();

		panel3.setBackground(Color.white);
		panel3.setForeground(Color.black);
		panel3.setLayout(new GridLayout(5, 7));
		// panel3.setPreferredSize(new Dimension(500, 100));
		panel30 = new JPanel(new BorderLayout());
		panel31 = new JPanel(new GridLayout(1, 7));
		panel30.add(panel31, BorderLayout.NORTH);
		panel30.add(panel3, BorderLayout.SOUTH);

		panel00.add(panel1, BorderLayout.NORTH);
		panel00.add(panel2, BorderLayout.CENTER);
		panel00.add(panel30, BorderLayout.SOUTH);
		xdate = new xDate1();
		syear = xdate.xtodayy;
		smonth = xdate.xtodaym;
		smnum = xdate.getdatenum();
		showdaynum(smnum, xdate.xtodayd, xdate.nowtoday);
		zyear1.setnowselectyear(xdate.xtodayy);
		nowtodaym = new JLabel("現在時間:" + xdate.xtodayy + "年" + xdate.xtodaym
				+ "月" + xdate.xtodayd + "日");

		nowtodaym.addMouseListener(new moveframe1(ffx));
		nowtodaym.addMouseMotionListener(new moveframe1(ffx));
		nowtodaym.setBackground(new Color(222, 245, 129));
		int sday = xdate.xtodayd;
		nowtodaym.setForeground(new Color(86, 195, 220));
		nowtodaym.setOpaque(true);
		nowtodaym.setHorizontalAlignment(JLabel.RIGHT);
		exitlabel = new JLabel();
		exitlabel.addMouseListener(new exitlabelgo(ffx));
		exitlabel.setIcon(closeicon);

		// exitlabel.setOpaque(false);
		minlabel = new JLabel();
		minlabel.addMouseListener(new minlabelgo(ffxx));
		minlabel.setIcon(delicon);

		helplabel = new JLabel();
		helplabel.setIcon(helpicon);
		helplabel.addMouseListener(new helpgo(ffxx));

		exitminpanel = new JPanel();
		exitminpanel.setBackground(new Color(222, 245, 129));

		exitminpanel.add(exitlabel);
		exitminpanel.add(minlabel);
		exitminpanel.add(helplabel);
		panel1.add(exitminpanel, BorderLayout.WEST);
		panel1.add(nowtodaym);
		// lbm = new JLabel(" < ");
		lbm = new JLabel();
		lbm.setIcon(lefticon);
		lbm.setHorizontalAlignment(JLabel.CENTER);
		lbm.addMouseListener(new lbm());
		// rbm = new JLabel(" > ");
		rbm = new JLabel();
		rbm.setIcon(righticon);
		rbm.setHorizontalAlignment(JLabel.CENTER);
		rbm.addMouseListener(new rbm());
		monthm = new JLabel(syear + "年" + smonth + "月");
		addweekpanel(syear, smonth);
		monthm.setHorizontalAlignment(JLabel.CENTER);
		monthm.setBackground(Color.white);
		monthm.setForeground(Color.black);
		monthm.addMouseListener(new selectyear());
		monthm.setOpaque(true);
		panel2.add(lbm, BorderLayout.WEST);
		panel2.add(monthm, BorderLayout.CENTER);
		panel2.add(rbm, BorderLayout.EAST);
		pane000 = new JPanel();
		pane000.setLayout(clayout);
		pane000.add(panel00, "level 1");
		pane000.add(zyear1, "level 2");

		pane000.setBorder(new LineBorder(new Color(177, 219, 16), 5, true));
		pane000.setBackground(new Color(177, 219, 16));
		add(pane000);
		timer.start();
		// animator.start();
	}

	public void addweekpanel(int weekyear, int weekmonth){
		/*
		 * 傳入:年,月 輸出:月份第一天星期幾到第七天
		 */
		panel31.removeAll();
		JLabel[] weekp = new JLabel[8];
		String[] weekstring = { "", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		Calendar weekg = Calendar.getInstance();
		weekg.set(Calendar.YEAR, weekyear);
		weekg.set(Calendar.MONTH, weekmonth - 1);
		for (int i = 1; i < 8; i++){
			weekg.set(Calendar.DATE, i);
			// System.out.print(weekg.getTime());
			// System.out.println(weekstring[weekg.get(Calendar.DAY_OF_WEEK)]);
			weekp[i] = new JLabel(weekstring[weekg.get(Calendar.DAY_OF_WEEK)]);
			weekp[i].setHorizontalAlignment(JLabel.CENTER);
			weekp[i].setForeground(Color.black);
			weekp[i].setBackground(Color.white);
			weekp[i].setOpaque(true);
			panel31.add(weekp[i]);
		}
	}

	// public void showdaynum(){
	// smnum = xdate.getdatenum();
	// showdaynum(smnum, xdate.xtodayd, xdate.nowtoday);
	// ffx.repaint();
	// }
	public void showdaynum(int adaynum, int atoday, boolean anowtoday){// 產生該月份日期數量

		// panel3.removeAll();
		// daynumlabel = new JLabel[adaynum];
		// panel3.removeAll();
		daynumlabel = new JLabel[35];

		// for (int i = 0; i < 35; i++){
		// daynumlabel[i] = new JLabel("");
		// daynumlabel[i].removeAll();
		// }
		for (int i = 0; i < adaynum; i++){
			daynumlabel[i] = new JLabel(i + 1 + "");
			daynumlabel[i].setPreferredSize(new Dimension(50, 50));
			daynumlabel[i].setHorizontalAlignment(JLabel.CENTER);
			daynumlabel[i].setBackground(Color.white);
			if (atoday == i + 1 && anowtoday == true)
				daynumlabel[i].setForeground(new Color(86, 195, 220));

			// try {
			
			File sfila = new File("Memo\\mydata/" + xdate.xtodayy + ""
					+ xdate.xtodaym + "" + i + ".txt");
			if (sfila.exists() == true){
				System.out.println("Memo\\mydata/" + xdate.xtodayy + ""
						+ xdate.xtodaym + "" + i + ".txt");
				if (i - 1 > 9){
					daynumlabel[i - 1].setIconTextGap(-32);// 圖片與文字的間距(象素為單位)
				} else {
					daynumlabel[i - 1].setIconTextGap(-28);
				}
				daynumlabel[i - 1].setIcon(starxicon);
			}

			daynumlabel[i].addMouseListener(new zdays());
			daynumlabel[i].setOpaque(true);
			panel3.add(daynumlabel[i]);
		}
		int seti2 = 0;
		for (int i = adaynum; i < 35; i++){
			seti2 += 1;
			daynumlabel[i] = new JLabel((i + 1) % adaynum + "");
			daynumlabel[i].setPreferredSize(new Dimension(50, 50));
			daynumlabel[i].setHorizontalAlignment(JLabel.CENTER);
			daynumlabel[i].setBackground(Color.white);
			daynumlabel[i].setForeground(Color.lightGray);

			int tmpma = xdate.xtodaym + 1;
			File sfila = new File("Memo\\mydata/" + xdate.xtodayy + "" + tmpma + ""
					+ seti2 + ".txt");
			if (sfila.exists() == true){
				System.out.println("Memo\\mydata/" + xdate.xtodayy + "" + tmpma + ""
						+ seti2 + ".txt");
				daynumlabel[i].setIconTextGap(-28);
				daynumlabel[i].setIcon(starxicon);
			}

			daynumlabel[i].addMouseListener(new zdays(1));
			daynumlabel[i].setOpaque(true);
			panel3.add(daynumlabel[i]);
		}
	}

	private class ReboundListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			if (clockxxx == true){
				// if (xdate.xtodaym + 1 > 12){
				// xdate.xtodaym = 1;
				// xdate.setmonth(xdate.xtodaym);

				// } else {
				// xdate.setmonth(++xdate.xtodaym);
				// }
				panel3.removeAll();
				smnum = xdate.getdatenum();
				showdaynum(smnum, xdate.xtodayd, xdate.nowtoday);
				monthm.setText(xdate.xtodayy + "年" + xdate.xtodaym + "月");
				addweekpanel(xdate.xtodayy, xdate.xtodaym);
				clockxxx = false;
			}
			// for (int i = 0; i < 35; i++){
			// daynumlabel[i].repaint();
			// panel3.repaint();
			// }
		}
	}

	class helpgo extends MouseAdapter {
		JFrame fedit;

		int nowx = 0, nowy = 0;

		int endx = 0, endy = 0;

		public helpgo(JFrame f2temp){
			fedit = f2temp;
			// public helpgo(){

		}

		public void mousePressed(MouseEvent e){
		}

		public void mouseEntered(MouseEvent e){
			helplabel.setIcon(help2icon);

			// fchangecolor.setForeground(Color.blue);
		}

		public void mouseExited(MouseEvent e){
			helplabel.setIcon(helpicon);
			// fchangecolor.setForeground(Color.black);
		}

		public void mouseReleased(MouseEvent e){
			JOptionPane.showMessageDialog(fedit, "說明:\n"
					+ "此行事曆含記事功能，點選日期即可編輯\n" + "在顯示年及月份上,點擊即可切換年份\n"
					+ "左右兩邊的箭頭圖示,可切換月份\n", "說明", JOptionPane.QUESTION_MESSAGE);
		}
	}

	class exitlabelgo extends MouseAdapter {
		private JFrame frame;
		public exitlabelgo(JFrame frame){
			this.frame=frame;
		}

		public void mousePressed(MouseEvent e){
		}

		public void mouseEntered(MouseEvent e){
			exitlabel.setIcon(close2icon);
			// fchangecolor.setForeground(Color.blue);
		}

		public void mouseExited(MouseEvent e){
			exitlabel.setIcon(closeicon);
			// fchangecolor.setForeground(Color.black);
		}

		public void mouseReleased(MouseEvent e){
			frame.setVisible(false);
			frame.dispose();
		}
	}

	class minlabelgo extends MouseAdapter {
		JFrame fedit;

		int nowx = 0, nowy = 0;

		int endx = 0, endy = 0;

		public minlabelgo(JFrame f2temp){
			fedit = f2temp;
		}

		public void mousePressed(MouseEvent e){
		}

		public void mouseEntered(MouseEvent e){
			minlabel.setIcon(del2icon);

			// fchangecolor.setForeground(Color.blue);
		}

		public void mouseExited(MouseEvent e){
			minlabel.setIcon(delicon);
			// fchangecolor.setForeground(Color.black);
		}

		public void mouseReleased(MouseEvent e){
			// if (e.getSource() == fff2)
			// fedit.
			fedit.setExtendedState(JFrame.HIDE_ON_CLOSE);
			// setIconImage
		}
	}

	class moveframe1 implements MouseListener, MouseMotionListener {
		JFrame fedit;

		int nowx = 0, nowy = 0;

		int endx = 0, endy = 0;

		public moveframe1(JFrame f2temp){
			fedit = f2temp;
		}

		public void mousePressed(MouseEvent event){
			endx = event.getXOnScreen();
			endy = event.getYOnScreen();
			fedit.setLocation(endx - 180, endy - 30);
		}

		public void mouseDragged(MouseEvent event){
			endx = event.getXOnScreen();
			endy = event.getYOnScreen();
			fedit.setLocation(endx - 180, endy - 30);
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

	class zdays extends MouseAdapter {
		Color tmp1;

		int tmp2;

		// public void mousePressed(MouseEvent e){

		// }
		int xxx = 0;

		public zdays(int xr){
			xxx = xr;
		}

		public zdays(){
			xxx = 0;
		}

		public void mouseEntered(MouseEvent e){
			for (int i = 0; i < daynumlabel.length; i++){
				if (e.getSource() == daynumlabel[i]){
					tmp1 = daynumlabel[i].getForeground();
					tmp2 = i;
					daynumlabel[i].setForeground(Color.blue);
				}
			}
		}

		public void mouseExited(MouseEvent e){
			daynumlabel[tmp2].setForeground(tmp1);
		}

		public void mouseReleased(MouseEvent e){
			int vv = 0;
			for (int i = 0; i < daynumlabel.length; i++){
				if (e.getSource() == daynumlabel[i]){
					vv = Integer.parseInt(daynumlabel[i].getText());
					// xdate.setmonth(vv);// getselectyear();
					// monthm.setText(xdate.xtodayy + "年" + xdate.xtodaym +
					// "月");
				}
			}

			EditFrame editf2show = new EditFrame();
			// String stra = "事件日期:2011年3月3日";
			// String strb = "行事曆編輯中";
			// String stra = xdate.xtodayy + "年" + xdate.xtodaym + "月" + vv +
			// "日"
			// + " ";
			// editf2show.main(stra + strb, stra + strb);

			int setframe2y = xdate.xtodayy;
			int setframe2m = xdate.xtodaym + xxx;
			int setframe2d = vv;

			editf2show.main(setframe2y,setframe2m,setframe2d);
		}
	}

	private class selectyear extends MouseAdapter {// implements ActionListener
		/*
		 * step1.顯示錢後一定範圍的年+-15年 <~這裡只能做這個 step2.滑鼠點選年 step3.畫面回到panel00初始畫面
		 */
		public void mousePressed(MouseEvent e){

		}

		public void mouseEntered(MouseEvent e){
			monthm.setForeground(Color.blue);
		}

		public void mouseExited(MouseEvent e){
			monthm.setForeground(Color.black);
		}

		public void mouseReleased(MouseEvent e){
			clayout.next(pane000);
		}
	}

	private class lbm extends MouseAdapter {
		public void mousePressed(MouseEvent e){

		}

		public void mouseEntered(MouseEvent e){
			lbm.setForeground(Color.blue);
			lbm.setIcon(lefticon2);
		}

		public void mouseExited(MouseEvent e){
			lbm.setForeground(Color.black);
			lbm.setIcon(lefticon);
		}

		public void mouseReleased(MouseEvent e){
			if (xdate.xtodaym - 1 < 1){
				xdate.xtodaym = 12;
				xdate.setmonth(xdate.xtodaym);
			} else {
				xdate.setmonth(--xdate.xtodaym);
			}

			panel3.removeAll();
			smnum = xdate.getdatenum();
			showdaynum(smnum, xdate.xtodayd, xdate.nowtoday);
			monthm.setText(xdate.xtodayy + "年" + xdate.xtodaym + "月");
			addweekpanel(xdate.xtodayy, xdate.xtodaym);
		}
	}

	private class rbm extends MouseAdapter {
		public void mousePressed(MouseEvent e){

		}

		public void mouseEntered(MouseEvent e){
			rbm.setForeground(Color.blue);
			rbm.setIcon(righticon2);
		}

		public void mouseExited(MouseEvent e){
			rbm.setForeground(Color.black);
			rbm.setIcon(righticon);
		}

		public void mouseReleased(MouseEvent e){
			if (xdate.xtodaym + 1 > 12){
				xdate.xtodaym = 1;
				xdate.setmonth(xdate.xtodaym);

			} else {
				xdate.setmonth(++xdate.xtodaym);
			}

			panel3.removeAll();
			smnum = xdate.getdatenum();
			showdaynum(smnum, xdate.xtodayd, xdate.nowtoday);
			monthm.setText(xdate.xtodayy + "年" + xdate.xtodaym + "月");
			addweekpanel(xdate.xtodayy, xdate.xtodaym);
		}
	}

	// ------------------------------------------------------------------------------------------------
	class zYearselect extends JPanel {
		/*
		 * step1.選擇 年 step2.傳出參數 年 step3.cardlayout.next
		 */
		private int zyearinput1;

		private int zyearinput2;

		private int zyearoutput;

		JLabel[] zyearnumlabel;

		int cgsyear;

		int cssyear;

		public void setnowselectyear(int zyear1){
			// step1.選擇 年
			zyearinput1 = zyear1;// 目前年
			cgsyear = 0;
			int za = 17;
			int zcal = 0;
			zyearnumlabel = new JLabel[35];
			for (int i = 0; i < 17; i++){
				zcal = zyearinput1 - za;
				zyearnumlabel[i] = new JLabel(zcal + "");
				zyearnumlabel[i].setPreferredSize(new Dimension(50, 50));
				zyearnumlabel[i].setHorizontalAlignment(JLabel.CENTER);
				zyearnumlabel[i].setForeground(Color.lightGray);
				zyearnumlabel[i].setBackground(Color.white);
				zyearnumlabel[i].setOpaque(true);
				zyearnumlabel[i].addMouseListener(new zyears());
				add(zyearnumlabel[i]);
				za -= 1;
			}
			za = 0;
			for (int i = 17; i < zyearnumlabel.length; i++){
				zcal = zyearinput1 + za;
				zyearnumlabel[i] = new JLabel(zcal + "");
				zyearnumlabel[i].setPreferredSize(new Dimension(50, 50));
				zyearnumlabel[i].setHorizontalAlignment(JLabel.CENTER);
				zyearnumlabel[i].setForeground(Color.black);
				if (17 == i)
					zyearnumlabel[i].setForeground(new Color(86, 195, 220));
				zyearnumlabel[i].setBackground(Color.white);
				zyearnumlabel[i].setOpaque(true);
				zyearnumlabel[i].addMouseListener(new zyears());
				add(zyearnumlabel[i]);
				za += 1;

			}
		}

		public int getselectyear(){
			zyearoutput = cgsyear;
			// step2.傳出參數 設定年
			// step3.cardlayout.next
			return zyearoutput;
		}

		public void setselectyear(int zyear2){

			zyearinput2 = zyear2;// 設定年
		}

		public zYearselect(){
			// 將現在年做記號
			zyearnumlabel = new JLabel[35];// 18
			setLayout(new GridLayout(5, 7));
		}

		class zyears extends MouseAdapter {
			Color tmpz1;

			int tmpz2;

			public void mousePressed(MouseEvent e){

			}

			public void mouseEntered(MouseEvent e){
				for (int i = 0; i < zyearnumlabel.length; i++){
					if (e.getSource() == zyearnumlabel[i]){
						tmpz1 = zyearnumlabel[i].getForeground();
						tmpz2 = i;
						zyearnumlabel[i].setForeground(Color.blue);
					}
				}
			}

			public void mouseExited(MouseEvent e){
				zyearnumlabel[tmpz2].setForeground(tmpz1);
			}

			public void mouseReleased(MouseEvent e){
				zyearoutput = Integer.parseInt(zyearnumlabel[tmpz2].getText());
				cgsyear = zyearoutput;
				xdate.setyear(cgsyear);
				syear = xdate.xtodayy;
				smonth = xdate.xtodaym;
				panel3.removeAll();
				smnum = xdate.getdatenum();
				showdaynum(smnum, xdate.xtodayd, xdate.nowtoday);
				monthm.setText(xdate.xtodayy + "年" + xdate.xtodaym + "月");
				addweekpanel(xdate.xtodayy, xdate.xtodaym);
				clayout.first(pane000);
			}
		}

	}
}

class xDate1 {
	private int xyear;

	private int xmonth;

	public int xdatenum;

	public int xtodayy;

	public int xtodaym;

	public int xtodayd;

	public boolean nowtoday;

	Calendar xcal;

	int a, b, c;

	public xDate1(){
		nowtoday = true;
		xcal = Calendar.getInstance();
		xyear = xcal.get(Calendar.YEAR);
		xmonth = xcal.get(Calendar.MONTH) + 1;
		xtodayd = xcal.get(Calendar.DATE);

		a = xcal.get(Calendar.YEAR);
		b = xcal.get(Calendar.MONTH) + 1;
		c = xcal.get(Calendar.DATE);

		xtodayy = xyear;
		xtodaym = xmonth;
		System.out.println("xyear:" + xyear + " xmonth:" + xmonth + " * "
				+ xtodayy + "/" + xtodaym + "/" + xtodayd + " <-start");

	}

	public int getdatenum(){// 取得該月份天數數量
		xcal.set(Calendar.YEAR, xyear);
		xcal.set(Calendar.MONTH, xmonth - 1);

		if (a == xtodayy && xtodaym == b)
			nowtoday = true;
		else {
			nowtoday = false;
		}
		xdatenum = xcal.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println("xyear:" + xyear + " xmonth:" + xmonth + " * "
				+ xtodayy + "/" + xtodaym + "/" + xtodayd + " " + xdatenum);
		return xdatenum;
	}

	public void setyear(int xyear){// 設定年
		if (xyear > 0 && xyear < 9999){
			this.xyear = xyear;
			xtodayy = xyear;
		}
	}

	public void setmonth(int xmonth){// 設定月
		if (xmonth > 0 && xmonth < 14){
			this.xmonth = xmonth;
			xtodaym = xmonth;
		}
	}
}

public class Memo implements Start{
	static DatePanel abca;
	private static void createAndShowGUI(){
		// Create and set up the window.
		JFrame frame = new JFrame("行事曆");

		// Set up the content pane.
		//DatePanel 
		abca = new DatePanel(frame);
		frame.getContentPane().add(abca);
		// frame.getContentPane().add(new DatePanel(frame));
		frame.setResizable(false);
		frame.setUndecorated(true);
		
		
		java.net.URL imgURL1;
		imgURL1 = EditFrame.class.getResource("/images/star.png");
		ImageIcon icon1 = new ImageIcon(imgURL1);
		Image im = icon1.getImage();
		
		//Image im = Toolkit.getDefaultToolkit().getImage("/images/star.png");
		frame.setIconImage(im);
		// frame.setBackground(new Color(177, 219, 16));
		// frame.setBorder(new LineBorder(new Color(86, 195, 220), 5, true));
		// frame.setBackground(new Color(86, 195, 220));
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public void start(){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGUI();
			}
		});
	}
}