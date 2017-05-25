import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class Save {
	
	public void saveGame(String matrixString) {

		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream("save.properties");

			// set the properties value
			prop.setProperty("save1", matrixString);

			// save properties to root folder
			prop.store(output, null);

		} catch (IOException io) {
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}

		}
	}
	
	public String matrixToString(Map m) {
		
		int i = 0, j = 0, mapX = m.getMapSize().get(0), mapY = m.getMapSize().get(1);
		StringBuilder string = new StringBuilder();
		
		
		for (i = 0; i < mapY; i++) {
			for (j = 0; j < mapX; j++) {
				string.append(Integer.toString(m.getLocations()[i][j]));
			}
		}
		return string.toString();
	}
}
