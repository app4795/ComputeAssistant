package com.group_finity.mascot;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.group_finity.mascot.config.Configuration;
import com.group_finity.mascot.exception.BehaviorInstantiationException;
import com.group_finity.mascot.exception.CantBeAliveException;

/**
 *
 * �}�X�R�b�g�̃��X�g���Ǘ����A�^�C�~���O�����I�u�W�F�N�g.
 * �e�}�X�R�b�g���񓯊��ɓ����Ƃ��낢�덢��(�E�B���h�E�𓊂��鎞�Ȃ�)�̂ŁA���̃N���X���S�̂̃^�C�~���O�����킹��.
 * {@link #tick()} ���\�b�h���A�܂��ŐV�̊������擾���A���ꂩ��S�Ẵ}�X�R�b�g�𓮂����B
 *
 * @author Yuki Yamada
 */
public class Manager {

	private static final Logger log = Logger.getLogger(Manager.class.getName());

	/**
	 * �^�C�}�̎��s�Ԋu.
	 */
	public static final int TICK_INTERVAL = 40;

	/**
	 * �}�X�R�b�g�̈ꗗ.
	 */
	private final List<Mascot> mascots = new ArrayList<Mascot>();

	/**
	 * �ǉ������\��̃}�X�R�b�g�̃��X�g.
	 * {@link ConcurrentModificationException} ��h�����߁A�}�X�R�b�g�̒ǉ��� {@link #tick()} ���Ƃɂ��������ɔ��f�����.
	 */
	private final Set<Mascot> added = new LinkedHashSet<Mascot>();

	/**
	 * �ǉ������\��̃}�X�R�b�g�̃��X�g.
	 * {@link ConcurrentModificationException} ��h�����߁A�}�X�R�b�g�̍폜�� {@link #tick()} ���Ƃɂ��������ɔ��f�����.
	 */
	private final Set<Mascot> removed = new LinkedHashSet<Mascot>();

	/**
	 * �Ō�̃}�X�R�b�g���폜�������Ƀv���O�������I�����ׂ����ǂ���.
	 * �g���C�A�C�R���̍쐬�Ɏ��s�������Ȃǂ́A�}�X�R�b�g�����Ȃ��Ȃ�����v���O�������I�����Ȃ��ƁA�v���Z�X�������Ǝc���Ă��܂�.
	 */
	private boolean exitOnLastRemoved;

	/**
	 * {@link #tick()}�����[�v����X���b�h.
	 */
	private transient Thread thread;

	public Manager() {

		// ����� Windows ��œ��� Java �̃o�O���C�����邽�߂̏��u
		// �Z�������� Thread.sleep ��p�ɂɌĂԂ� Windows �̎��v������
		// ���� Thread.sleep ���Ă�ł���Ƃ��̖�������ł���.
		new Thread() {
			{
				this.setDaemon(true);
				this.start();
			}

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(Integer.MAX_VALUE);
					} catch (final InterruptedException ex) {
					}
				}
			}
		};
	}

	/**
	 * �X���b�h���J�n����.
	 */
	public void start() {

		if ( thread!=null && thread.isAlive() ) {
			// �����X���b�h�������Ă���
			return;
		}

		thread = new Thread() {
			@Override
			public void run() {

				// �O��̎���
				long prev = System.nanoTime() / 1000000;
				try {
					for (;;) {
						for (;;) {
							// ���݂̎���
							// TICK_INTERVAL �o�܂Ń��[�v.
							final long cur = System.nanoTime() / 1000000;
							if (cur - prev >= TICK_INTERVAL) {
								if (cur > prev + TICK_INTERVAL * 2) {
									prev = cur;
								} else {
									prev += TICK_INTERVAL;
								}
								break;
							}
							Thread.sleep(1, 0);
						}

						// �}�X�R�b�g�����𓮂���.
						tick();
					}
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		};
		thread.setDaemon(false);

		thread.start();
	}

	/**
	 * �X���b�h���~����.
	 */
	public void stop() {
		if ( thread==null || !thread.isAlive() ) {
			// ���������Ă��Ȃ�
			return;
		}

		thread.interrupt();

		try {
			thread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * �}�X�R�b�g����t���[���i�߂�.
	 */
	private void tick() {

		// �܂��������X�V
		NativeFactory.getInstance().getEnvironment().tick();

		synchronized (this.getMascots()) {

			// �ǉ����ׂ��}�X�R�b�g��ǉ�
			for (final Mascot mascot : this.getAdded()) {
				this.getMascots().add(mascot);
			}
			this.getAdded().clear();

			// �폜���ׂ��}�X�R�b�g���폜
			for (final Mascot mascot : this.getRemoved()) {
				this.getMascots().remove(mascot);
			}
			this.getRemoved().clear();

			// �}�X�R�b�g�̎��Ԃ�i�߂�
			for (final Mascot mascot : this.getMascots()) {
				mascot.tick();
			}

			// �}�X�R�b�g�̊G��ʒu���ŐV�ɂ���.
			for (final Mascot mascot : this.getMascots()) {
				mascot.apply();
			}
		}

		if (isExitOnLastRemoved() && this.getMascots().size() == 0) {
			// exitOnLastRemoved �� true �Ń}�X�R�b�g����C�����Ȃ��Ȃ����̂ŏI��.
			Main.getInstance().exit();
		}
	}

	/**
	 * �}�X�R�b�g��ǉ�����.
	 * �ǉ��͎��� {@link #tick()} �̃^�C�~���O�ōs����.
	 * @param mascot �ǉ�����}�X�R�b�g.
	 */
	public void add(final Mascot mascot) {
		synchronized (this.getAdded()) {
			this.getAdded().add(mascot);
			this.getRemoved().remove(mascot);
		}
		mascot.setManager(this);
	}

	/**
	 * �}�X�R�b�g���폜����.
	 * �폜�͎��� {@link #tick()} �̃^�C�~���O�ōs����.
	 * @param mascot �폜����}�X�R�b�g.
	 */
	public void remove(final Mascot mascot) {
		synchronized (this.getAdded()) {
			this.getAdded().remove(mascot);
			this.getRemoved().add(mascot);
		}
		mascot.setManager(null);
	}

	/**
	 * �S�Ẵ}�X�R�b�g�̍s����ݒ肷��.
	 * @param configuration
	 * @param name
	 */
	public void setBehaviorAll(final Configuration configuration, final String name) {
		synchronized (this.getMascots()) {
			for (final Mascot mascot : this.getMascots()) {
				try {
					mascot.setBehavior(configuration.buildBehavior(name));
				} catch (final BehaviorInstantiationException e) {
					log.log(Level.SEVERE, "���̍s���̏������Ɏ��s���܂���", e);
					mascot.dispose();
				} catch (final CantBeAliveException e) {
					log.log(Level.SEVERE, "���������邱�Ƃ��o���Ȃ���", e);
					mascot.dispose();
				}
			}
		}
	}

	/**
	 * ��C�����c���đ���S�č폜����.
	 */
	public void remainOne() {
		synchronized (this.getMascots()) {
			for (int i = this.getMascots().size() - 1; i > 0; --i) {
				this.getMascots().get(i).dispose();
			}
		}
	}

	/**
	 * �S�č폜����.
	 */
	public void disposeAll() {
		synchronized (this.getMascots()) {
			for (int i = this.getMascots().size() - 1; i >= 0; --i) {
				this.getMascots().get(i).dispose();
			}
		}
	}

	/**
	 * �}�X�R�b�g�̌��ݐ����擾����.
	 * @return �}�X�R�b�g�̌��ݐ�.
	 */
	public int getCount() {
		synchronized (this.getMascots()) {
			return this.getMascots().size();
		}
	}

	public void setExitOnLastRemoved(boolean exitOnLastRemoved) {
		this.exitOnLastRemoved = exitOnLastRemoved;
	}

	public boolean isExitOnLastRemoved() {
		return exitOnLastRemoved;
	}

	private List<Mascot> getMascots() {
		return this.mascots;
	}

	private Set<Mascot> getAdded() {
		return this.added;
	}

	private Set<Mascot> getRemoved() {
		return this.removed;
	}

}
