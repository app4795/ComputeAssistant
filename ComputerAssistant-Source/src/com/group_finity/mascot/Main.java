package com.group_finity.mascot;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.group_finity.mascot.config.Configuration;
import com.group_finity.mascot.config.Entry;
import com.group_finity.mascot.exception.BehaviorInstantiationException;
import com.group_finity.mascot.exception.CantBeAliveException;
import com.group_finity.mascot.exception.ConfigurationException;
import com.sun.jna.Platform;

/**
 * �v���O�����̃G���g���|�C���g.
 */
public class Main {

	private static final Logger log = Logger.getLogger(Main.class.getName());
	private PopupMenu trayPopup;
	private TrayIcon trayIcon;
	static final String BEHAVIOR_GATHER = "�}�E�X�̎���ɏW�܂�";

	static {
		try {
			LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private static Main instance = new Main();

	public static Main getInstance() {
		return instance;
	}

	private final Manager manager = new Manager();

	private final Configuration configuration = new Configuration();

	public static void main(PopupMenu trayPopup,TrayIcon trayIcon) {;
		getInstance().run(trayPopup,trayIcon);
	}

	public void run(PopupMenu trayPopup,TrayIcon trayIcon) {
		this.trayPopup=trayPopup;
		this.trayIcon=trayIcon;
		// �ݒ��ǂݍ���
		loadConfiguration();

		// �g���C�A�C�R�����쐬����
		createTrayIcon();

		// ���߂�����C�쐬����
		createMascot();

		getManager().start();
	}

	/**
	 * �ݒ�t�@�C����ǂݍ���.
	 */
	private void loadConfiguration() {

		try {
			log.log(Level.INFO, "�ݒ�t�@�C����ǂݍ���({0})", "/Behavior.xml");

			final Document actions = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					Main.class.getResourceAsStream("/Behavior.xml"));

			log.log(Level.INFO, "�ݒ�t�@�C����ǂݍ���({0})", "/Actions.xml");

			this.getConfiguration().load(new Entry(actions.getDocumentElement()));

			final Document behaviors = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
					Main.class.getResourceAsStream("/Actions.xml"));

			this.getConfiguration().load(new Entry(behaviors.getDocumentElement()));

			this.getConfiguration().validate();

		} catch (final IOException e) {
			log.log(Level.SEVERE, "�ݒ�t�@�C���̓ǂݍ��݂Ɏ��s", e);
			exit();
		} catch (final SAXException e) {
			log.log(Level.SEVERE, "�ݒ�t�@�C���̓ǂݍ��݂Ɏ��s", e);
			exit();
		} catch (final ParserConfigurationException e) {
			log.log(Level.SEVERE, "�ݒ�t�@�C���̓ǂݍ��݂Ɏ��s", e);
			exit();
		} catch (final ConfigurationException e) {
			log.log(Level.SEVERE, "�ݒ�t�@�C���̋L�q�Ɍ�肪����܂�", e);
			exit();
		}
	}

	/**
	 * �g���C�A�C�R�����쐬����.
	 * @throws AWTException
	 * @throws IOException
	 */
	private void createTrayIcon() {
		// �u���₷�v���j���[�A�C�e��
		final MenuItem increaseMenu = new MenuItem("����");
		increaseMenu.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				createMascot();
			}
		});

		// �u���܂�I�v���j���[�A�C�e��
		final MenuItem gatherMenu = new MenuItem("�W���I");
		gatherMenu.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				gatherAll();
			}
		});

		// �u��C�����c���v���j���[�A�C�e��
		final MenuItem oneMenu = new MenuItem("�������");
		oneMenu.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				remainOne();
			}
		});

		// �u�΂��΂��v���j���[�A�C�e��
		final MenuItem closeMenu = new MenuItem("萕�");
		closeMenu.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				exit();
			}
		});

		// �|�b�v�A�b�v���j���[���쐬
		trayPopup.add(new MenuItem("-"));
		trayPopup.add(increaseMenu);
		trayPopup.add(gatherMenu);
		trayPopup.add(oneMenu);
		trayPopup.add(new MenuItem("-"));
		trayPopup.add(closeMenu);
		trayIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					createMascot();
				}
			}
		});
	}

	/**
	 * ���߂�����C�쐬����.
	 */
	public void createMascot() {

		log.log(Level.INFO, "�}�X�R�b�g���쐬");

		// �}�X�R�b�g��1�쐬
		final Mascot mascot = new Mascot();

		// �͈͊O����J�n
		mascot.setAnchor(new Point(-1000, -1000));
		// �����_���Ȍ�����
		mascot.setLookRight(Math.random() < 0.5);

		try {
			mascot.setBehavior(getConfiguration().buildBehavior(null, mascot));

			this.getManager().add(mascot);

		} catch (final BehaviorInstantiationException e) {
			log.log(Level.SEVERE, "�ŏ��̍s���̏������Ɏ��s���܂���", e);
			mascot.dispose();
		} catch (final CantBeAliveException e) {
			log.log(Level.SEVERE, "���������邱�Ƃ��o���Ȃ���", e);
			mascot.dispose();
		}

	}

	public void gatherAll() {
		Main.this.getManager().setBehaviorAll(Main.this.getConfiguration(), BEHAVIOR_GATHER);
	}

	public void remainOne() {
		Main.this.getManager().remainOne();
	}

	public void restoreIE() {
		NativeFactory.getInstance().getEnvironment().restoreIE();
	}

	public void exit() {

		this.getManager().disposeAll();
		this.getManager().stop();

		System.exit(0);
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}

	private Manager getManager() {
		return this.manager;
	}

}
