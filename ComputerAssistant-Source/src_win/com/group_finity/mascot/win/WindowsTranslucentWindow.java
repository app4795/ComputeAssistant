package com.group_finity.mascot.win;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JWindow;

import com.group_finity.mascot.image.NativeImage;
import com.group_finity.mascot.image.TranslucentWindow;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.BaseTSD.LONG_PTR;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;

/**
 * ���l���摜�E�B���h�E.
 * {@link #setImage(WindowsNativeImage)} �Őݒ肵�� {@link WindowsNativeImage} ���f�X�N�g�b�v�ɕ\���ł���.
 *
 * {@link #setAlpha(int)} �ŕ\������Ƃ��̔Z�x���w��ł���.
 */
class WindowsTranslucentWindow extends JWindow implements TranslucentWindow {

	public static void main(String[] args) throws IOException {

		WindowsTranslucentWindow win = new WindowsTranslucentWindow();

		win.setVisible(true);
		win.setBounds(0, 0, 200, 200);

		BufferedImage img = ImageIO.read(new File("img/shime1.png"));
		WindowsNativeImage wi = new WindowsNativeImage(img);

		win.setImage(wi);

		win.repaint();
	}

	private static final long serialVersionUID = 1L;

	@Override
	public JWindow asJWindow() {
		return this;
	}

	/**
	 * ���l���摜��`�悷��.
	 * @param imageHandle �r�b�g�}�b�v�̃n���h��.
	 * @param alpha �\���Z�x. 0 = �܂������\�����Ȃ��A255 = ���S�ɕ\������.
	 */
	private void paint(final WinDef.HBITMAP imageHandle, final int alpha) {

		final WinDef.HWND hWnd = new WinDef.HWND(Native.getComponentPointer(this));

		if ( User32.INSTANCE.IsWindowVisible(hWnd) ) {

			final int exStyle = User32.INSTANCE.GetWindowLong(hWnd, WinUser.GWL_EXSTYLE);
			if ( (exStyle&WinUser.WS_EX_LAYERED)==0 ) {
				User32.INSTANCE.SetWindowLong(hWnd, WinUser.GWL_EXSTYLE, exStyle | WinUser.WS_EX_LAYERED);
			}

			// �摜�̓]����DC���쐬
			final WinDef.HDC clientDC= User32.INSTANCE.GetDC(hWnd);
			final WinDef.HDC memDC = GDI32.INSTANCE.CreateCompatibleDC(clientDC);
			final WinNT.HANDLE oldBmp = GDI32.INSTANCE.SelectObject(memDC, imageHandle );

			User32.INSTANCE.ReleaseDC(hWnd, clientDC);

			// �]����̈�
			final WinDef.RECT windowRect = new WinDef.RECT();
			User32.INSTANCE.GetWindowRect(hWnd, windowRect);

			// �]��
			final WinUser.BLENDFUNCTION bf = new WinUser.BLENDFUNCTION();
			bf.BlendOp = WinUser.AC_SRC_OVER;
			bf.BlendFlags = 0;
			bf.SourceConstantAlpha = (byte)alpha; // �Z�x��ݒ�
			bf.AlphaFormat = WinUser.AC_SRC_ALPHA;

			final WinUser.POINT lt = new WinUser.POINT();
			lt.x = windowRect.left;
			lt.y = windowRect.top;
			final WinUser.SIZE size = new WinUser.SIZE();
			size.cx = windowRect.right-windowRect.left;
			size.cy = windowRect.bottom-windowRect.top;
			final WinUser.POINT zero = new WinUser.POINT();
			User32.INSTANCE.UpdateLayeredWindow(
					hWnd, null,
					lt, size,
					memDC, zero, 0, bf, WinUser.ULW_ALPHA );

			// �r�b�g�}�b�v�͌��ɖ߂��Ă���
			GDI32.INSTANCE.SelectObject(memDC, oldBmp);

			GDI32.INSTANCE.DeleteDC(memDC);
		}

	}

	/**
	 * �\������摜.
	 */
	private WindowsNativeImage image;

	/**
	 * �\���Z�x. 0 = �܂������\�����Ȃ��A255 = ���S�ɕ\������.
	 */
	private int alpha = 255;

	@Override
	public String toString() {
		return "LayeredWindow[hashCode="+hashCode()+",bounds="+getBounds()+"]";
	}

	@Override
	public void paint(final Graphics g) {
		if (getImage() != null) {
			// JNI ���g�p���ă��l���摜��`�悷��.
			paint(getImage().getHandle(), getAlpha());
		}
	}

	private WindowsNativeImage getImage() {
		return this.image;
	}

	public void setImage(final NativeImage image) {
		this.image = (WindowsNativeImage)image;
	}

	public int getAlpha() {
		return this.alpha;
	}

	public void setAlpha(final int alpha) {
		this.alpha = alpha;
	}

	public void updateImage() {
		repaint();
	}

}
