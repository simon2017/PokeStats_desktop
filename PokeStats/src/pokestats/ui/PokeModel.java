package pokestats.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import pokestats.core.PokeDetail;

public class PokeModel extends AbstractTableModel {
	private static final long serialVersionUID = 8161132662117018692L;
	private static final String[] columnNames = { "Nombre", "Perf.", "CP", "Ataque", "Defensa", "Estamina", "Nivel" };
	private List<PokeDetail> list;

	public PokeModel() {
		list = new ArrayList<PokeDetail>();
	}

	public void addObject(PokeDetail detail) {
		list.add(detail);
		this.fireTableDataChanged();
	}

	public void loadList(List<PokeDetail> details) {
		list = null;
		list = details;
		this.fireTableDataChanged();
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// { "Nombre", "IV", "CP", "Ataque", "Defensa", "Estamina", "Nivel" }

		switch (columnIndex) {
		case 0:
			return list.get(rowIndex).getNombre();
		case 1:
			return round(list.get(rowIndex).getPerfecto(),2);
		case 2:
			return list.get(rowIndex).getCp();
		case 3:
			return list.get(rowIndex).getAtaque();
		case 4:
			return list.get(rowIndex).getDefensa();
		case 5:
			return list.get(rowIndex).getEstamina();
		case 6:
			return list.get(rowIndex).getNivel();
		}
		return null;
	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
