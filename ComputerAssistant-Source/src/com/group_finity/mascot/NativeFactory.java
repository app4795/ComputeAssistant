package com.group_finity.mascot;

import java.awt.image.BufferedImage;

import com.group_finity.mascot.environment.Environment;
import com.group_finity.mascot.image.NativeImage;
import com.group_finity.mascot.image.TranslucentWindow;
import com.sun.jna.Platform;

/**
 * �l�C�e�B�u���ւ̃A�N�Z�X��񋟂���.
 * {@link #getInstance()} �͎��s���ɂ���� Windows �p���邢�͔ėp�̃T�u�N���X�̃C���X�^���X��Ԃ�.
 * @author Yuki
 */
public abstract class NativeFactory {

	private static final NativeFactory instance;

	/**
	 * �T�u�N���X�̃C���X�^���X���쐬���Ă���.
	 */
	static {
		final String basePackage = NativeFactory.class.getName().substring(0, NativeFactory.class.getName().lastIndexOf('.'));

		final String subPackage = Platform.isWindows() ? "win" : "generic";

		try {
			final Class<? extends NativeFactory> impl = (Class<? extends NativeFactory>)Class.forName(basePackage+"."+subPackage+".NativeFactoryImpl");

			instance = impl.newInstance();

		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (final InstantiationException e) {
			throw new RuntimeException(e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ���s���ɉ������T�u�N���X�̃C���X�^���X���擾����.
	 * @return
	 */
	public static NativeFactory getInstance() {
		return instance;
	}

	/**
	 * ���I�u�W�F�N�g���擾����.
	 * @return ���I�u�W�F�N�g.
	 */
	public abstract Environment getEnvironment();

	/**
	 * �w�肳�ꂽ BufferedImage �ɑΉ�����l�C�e�B�u�ȉ摜���擾����.
	 * ���̉摜�� {@link TranslucentWindow} �̃}�X�L���O�Ɏg�p�ł���.
	 * @param src
	 * @return
	 */
	public abstract NativeImage newNativeImage(BufferedImage src);

	/**
	 * �������\�����\�ȃE�B���h�E���쐬����.
	 * @return
	 */
	public abstract TranslucentWindow newTransparentWindow();
}
