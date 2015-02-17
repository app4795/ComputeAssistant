package com.group_finity.mascot.win;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import com.group_finity.mascot.image.NativeImage;
import com.group_finity.mascot.win.jna.BITMAP;
import com.group_finity.mascot.win.jna.GDI32Ex;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;

/**
 * {@link WindowsTranslucentWindow} �Ɏg�p�\�ȃ��l���摜.
 *
 * {@link WindowsTranslucentWindow} �Ɏg�p�ł���̂� Windows �r�b�g�}�b�v�����Ȃ̂ŁA
 * ������ {@link BufferedImage} ���� Windows �r�b�g�}�b�v�Ƀs�N�Z�����R�s�[����.
 *
 */
class WindowsNativeImage implements NativeImage {

	/**
	 * Windows �r�b�g�}�b�v���쐬����.
	 * @param width �r�b�g�}�b�v�̉���.
	 * @param height �r�b�g�}�b�v�̍���.
	 * @return �쐬�����r�b�g�}�b�v�̃n���h��.
	 */
	private static WinDef.HBITMAP createNative(final int width, final int height) {

		final WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();
		bmi.bmiHeader.biSize = 40;
		bmi.bmiHeader.biWidth = width;
		bmi.bmiHeader.biHeight = height;
		bmi.bmiHeader.biPlanes = 1;
		bmi.bmiHeader.biBitCount = 32;

		final WinDef.HBITMAP hBitmap = GDI32.INSTANCE.CreateDIBSection(
				null, bmi, WinGDI.DIB_RGB_COLORS, null, null, 0 );

		return hBitmap;
	}

	/**
	 * {@link BufferedImage} �̓��e���r�b�g�}�b�v�ɔ��f������.
	 * @param nativeHandle �r�b�g�}�b�v�̃n���h��.
	 * @param rgb �摜��ARGB�l.
	 */
	private static void flushNative(final WinDef.HBITMAP nativeHandle, final int[] rgb) {

		final BITMAP bmp = new BITMAP();
		GDI32Ex.INSTANCE.GetObjectW(nativeHandle, bmp.size(), bmp);

		// �s�N�Z�����x���ŃR�s�[����.
		final int width = bmp.bmWidth;
		final int height = bmp.bmHeight;
		final int destPitch = ((bmp.bmWidth*bmp.bmBitsPixel)+31)/32*4;
		int destIndex = destPitch*(height-1);
		int srcIndex = 0;
		for( int y = 0; y < height; ++y )
		{
			for( int x = 0; x<width; ++x )
			{
				// UpdateLayeredWindow �� Photoshop �͑����������炵��
				// UpdateLayeredWindow ��RGB�l�� FFFFFF ���ƃ��l�𖳎����Ă��܂��o�O������A
				// Photoshop �̓��l��0�ȂƂ����RGB�l�� 0 �ɂ������������.

				bmp.bmBits.setInt(destIndex + x*4,
					(rgb[srcIndex]&0xFF000000)==0 ? 0 : rgb[srcIndex] );

				++srcIndex;
			}

			destIndex -= destPitch;
		}

	}

	/**
	 * Windows �r�b�g�}�b�v���J������.
	 * @param nativeHandle �r�b�g�}�b�v�̃n���h��.
	 */
	private static void freeNative(final WinDef.HBITMAP nativeHandle) {
		GDI32.INSTANCE.DeleteObject(nativeHandle);
	}

	/**
	 * Java �C���[�W�I�u�W�F�N�g.
	 */
	private final BufferedImage managedImage;

	/**
	 * Windows �r�b�g�}�b�v�n���h��.
	 */
	private final WinDef.HBITMAP nativeHandle;

	/**
	 * ARGB�l�̓]���Ɏg�p����o�b�t�@.
	 */
	private final int[] rgb;

	public WindowsNativeImage(final BufferedImage image) {
		this.managedImage = image;
		this.nativeHandle = createNative(this.getManagedImage().getWidth(), this.getManagedImage().getHeight());
		this.rgb = new int[this.getManagedImage().getWidth() * this.getManagedImage().getHeight()];
		update();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		freeNative(this.getNativeHandle());
	}

	/**
	 * �摜�ւ̕ύX�� Windows �r�b�g�}�b�v�ɔ��f������.
	 */
	public void update() {

		this.getManagedImage().getRGB(0, 0, this.getManagedImage().getWidth(), this.getManagedImage().getHeight(), this.getRgb(), 0,
				this.getManagedImage().getWidth());

		flushNative(this.getNativeHandle(), this.getRgb());

	}

	public void flush() {
		this.getManagedImage().flush();
	}

	public WinDef.HBITMAP getHandle() {
		return this.getNativeHandle();
	}

	public Graphics getGraphics() {
		return this.getManagedImage().createGraphics();
	}

	public int getHeight() {
		return this.getManagedImage().getHeight();
	}

	public int getWidth() {
		return this.getManagedImage().getWidth();
	}

	public int getHeight(final ImageObserver observer) {
		return this.getManagedImage().getHeight(observer);
	}

	public Object getProperty(final String name, final ImageObserver observer) {
		return this.getManagedImage().getProperty(name, observer);
	}

	public ImageProducer getSource() {
		return this.getManagedImage().getSource();
	}

	public int getWidth(final ImageObserver observer) {
		return this.getManagedImage().getWidth(observer);
	}

	private BufferedImage getManagedImage() {
		return this.managedImage;
	}

	private WinDef.HBITMAP getNativeHandle() {
		return this.nativeHandle;
	}

	private int[] getRgb() {
		return this.rgb;
	}

}
