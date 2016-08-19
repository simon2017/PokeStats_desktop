package pokestats.ui;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class UtilUI {

	public UtilUI() {
	}
	
	/**
	 * 
	 * @param title
	 * @param titleJustif
	 * @return
	 */
	public static TitledBorder createBorder(String title, int titleJustif) {
		TitledBorder centerBorder = BorderFactory.createTitledBorder(title);
		centerBorder.setTitleJustification(titleJustif);
		return centerBorder;
	}

}
