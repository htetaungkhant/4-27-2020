package external_classes;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Fonts {

	public static Font pyisuNormal18 = new Font("Pyidaungsu", Font.PLAIN, 18);
	public static Font pyisuNormal20 = new Font("Pyidaungsu", Font.PLAIN, 20);
	public static Font pyisuNormal40 = new Font("Pyidaungsu", Font.PLAIN, 40);

	public static Font pyisuBold20 = new Font("Pyidaungsu", Font.BOLD, 20);
	public static Font pyisuBold40 = new Font("Pyidaungsu", Font.BOLD, 40);

	public static Font customFont(){
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("F://JavaWorkspace/4-27-2020/src/Pyidaungsu-2.5.3_Regular.ttf"));

			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);

			font = font.deriveFont(12f);

			return font;
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
