package pokestats.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;
import pokestats.core.PokeEngine;

import com.pokegoapi.api.pokemon.Pokemon;

public class MainUi extends JPanel implements ActionListener {

	public static void main(String args[]) {
		// try {
		// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		// } catch (Exception e) {
		// }
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				loadUI();
			}
		});

	}

	public MainUi() {
		super(new MigLayout("wrap 1,fill, insets 0 0 0 0", "[center]"));
	}

	public static void loadUI() {

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("PokeStats - Desktop");
		frame.setPreferredSize(new Dimension(500, 800));

		JPanel setPanel = new JPanel(new BorderLayout());
		setPanel.setOpaque(false);

		MainUi contentPane = new MainUi();
		contentPane.setBackground(Color.WHITE);

		frame.setContentPane(contentPane);

		{
			proxyPanel = new ProxyPanel();
			proxyPanel.loadComponentes();

			contentPane.add(proxyPanel, "growx");
		}
		{
			actPanel = new AccountPanel();
			actPanel.loadComponents();

			contentPane.add(actPanel, "growx");
		}

		listButton = new JButton("List 'Em ALL");
		listButton.addActionListener(contentPane);
		listButton.setOpaque(false);
		contentPane.add(listButton, "growx");

		{
			listPanel = new PokemonListPanel();
			contentPane.add(listPanel, "growx");
		}

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private static JButton listButton;
	private static AccountPanel actPanel;
	private PokeEngine engine = null;
	private static PokemonListPanel listPanel = null;
	private static ProxyPanel proxyPanel;

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(listButton)) {
			if (actPanel.isGoogleChoosed()) {
				String token = actPanel.getGoogleToken();
				if (engine == null)
					engine = new PokeEngine(token);
				else
					engine.refreshToken(token);

			} else {

				String user = actPanel.getUser();
				String password = actPanel.getPassword();
				if (engine == null)
					engine = new PokeEngine(user, password);
				else
					engine.refreshUserPassword(user, password);
			}
			if (proxyPanel.useProxy()) {
				String proxy = proxyPanel.getProxyUrl();
				int port = proxyPanel.getPort();
				engine.setProxy(proxy, port);

			}
			List<Pokemon> pokemons = engine.getPokemons();
			listPanel.loadPokemons(pokemons);
		}
	}
}
