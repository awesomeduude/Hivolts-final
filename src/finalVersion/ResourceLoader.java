package finalVersion;

import java.io.InputStream;

//Deals with loading the images onto the Jframe

final public class ResourceLoader {
	/**
	 * finds a specified image and returns it.
	 * @param path is the path to the image which will be loaded
	 * @return returns the image
	 */
	public static InputStream load(String path) {
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		

		return input;
	}
}
