package pokestats.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class PokeCellRenderer extends DefaultTableCellRenderer {

	public PokeCellRenderer() {
		super();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 1) {
			double perfecto = Double.parseDouble(value.toString());
			component.setBackground(Color.WHITE);
			if (perfecto >= 80d && perfecto < 90d) {
				component.setBackground(Color.YELLOW);
			}
			if (perfecto >= 90d) {
				component.setBackground(Color.GREEN);
			}

		}
		return component;

	}

}
