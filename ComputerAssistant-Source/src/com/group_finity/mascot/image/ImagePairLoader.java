package com.group_finity.mascot.image;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;



/**
 *�@�摜�y�A��ǂݍ���.
 */
public class ImagePairLoader {

	/**
	 * �摜�y�A��ǂݍ���.
	 *
	 * �������摜��ǂݍ���ŁA�E�����摜��������������.
	 *
	 * @param name �ǂݍ��݂����������摜.
	 * @param center �摜�̒������W.
	 * @return �ǂݍ��񂾉摜�y�A.
	 */
	public static ImagePair load(final String name, final Point center) throws IOException {

		// flip �ł͔������ɂȂ�Ȃ��摜������炵���̂�
		// shime1.png �ɑ΂��� shime1-r.png �𔽓]�摜�Ƃ��Ďg�p����悤�ɂ��ĉ���B
		String rightName = name.replaceAll("\\.[a-zA-Z]+$", "-r$0");

		final BufferedImage leftImage = ImageIO.read(ImagePairLoader.class.getResource(name));


		final BufferedImage rightImage;
		if ( ImagePairLoader.class.getResource(rightName)==null ) {
			rightImage = flip(leftImage);
		} else {
			rightImage = ImageIO.read(ImagePairLoader.class.getResource(rightName));
		}

		return new ImagePair(new MascotImage(leftImage, center), new MascotImage(rightImage, new Point(rightImage
				.getWidth()
				- center.x, center.y)));
	}

	/**
	 * �摜�����E���]������.
	 * @param src ���E���]�������摜
	 * @return�@���E���]����
	 */
	private static BufferedImage flip(final BufferedImage src) {

		final BufferedImage copy = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);

		for (int y = 0; y < src.getHeight(); ++y) {
			for (int x = 0; x < src.getWidth(); ++x) {
				copy.setRGB(copy.getWidth() - x - 1, y, src.getRGB(x, y));
			}
		}
		return copy;
	}

}
