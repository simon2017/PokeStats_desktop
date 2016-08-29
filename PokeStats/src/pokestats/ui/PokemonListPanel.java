package pokestats.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;
import pokestats.core.PokeDetail;

import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.util.PokeNames;

public class PokemonListPanel extends JPanel {

	private static final long serialVersionUID = -86304150506768383L;
	private PokeModel model;

	public PokemonListPanel() {
		super(new BorderLayout());
		this.setBorder(UtilUI.createBorder("Lista de Pokemon", TitledBorder.CENTER));
		this.setOpaque(false);

		model = new PokeModel();
		JTable table = new JTable(model);
		table.setDefaultRenderer(Double.class, new PokeCellRenderer());
		TableRowSorter<PokeModel> sorter = new TableRowSorter<PokeModel>(model);
		table.setRowSorter(sorter);
		table.setOpaque(false);

		JScrollPane pane = new JScrollPane(table);
		pane.setOpaque(false);

		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(pane, BorderLayout.CENTER);
	}

	private List<PokeDetail> cleanList(List<Pokemon> pokemons) {

		List<PokeDetail> cleaned = new ArrayList<PokeDetail>();

		for (Pokemon pokemon : pokemons) {
			String name = pokemon.getNickname();
			if (name == null || name.isEmpty())
				name = PokeNames.getDisplayName(pokemon.getPokemonId().getNumber(), Locale.ENGLISH);

			PokeDetail pokeDetail = new PokeDetail();
			pokeDetail.setNombre(name);
			pokeDetail.setPerfecto(Double.valueOf(pokemon.getIvRatio() * 100.0D));
			pokeDetail.setCp(String.valueOf(pokemon.getCp()));
			pokeDetail.setAtaque(String.valueOf(pokemon.getIndividualAttack()));
			pokeDetail.setDefensa(String.valueOf(pokemon.getIndividualDefense()));
			pokeDetail.setEstamina(String.valueOf(pokemon.getIndividualStamina()));
			pokeDetail.setNivel(String.valueOf(pokemon.getLevel()));
			cleaned.add(pokeDetail);
		}

		return cleaned;
	}

	public void loadPokemons(List<Pokemon> pokemons) {
		List<PokeDetail> cleaned = cleanList(pokemons);
		model.loadList(cleaned);
	}

}
