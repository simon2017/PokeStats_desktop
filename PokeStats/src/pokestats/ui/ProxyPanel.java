package pokestats.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

import net.miginfocom.swing.MigLayout;

public class ProxyPanel extends JPanel implements ActionListener {
	private JTextField proxyTxt;
	private JTextField portTxt;
	private JCheckBox useProxyChck;

	public ProxyPanel() {
		super(new MigLayout("wrap 2, fill", "[70%][30%]"));
		this.setOpaque(false);
	}

	public void loadComponentes() {
		useProxyChck = new JCheckBox("Usar proxy");
		useProxyChck.setSelected(true);
		useProxyChck.addActionListener(this);
		useProxyChck.setOpaque(false);

		proxyTxt = new JTextField("10.214.8.100");
		proxyTxt.setBorder(UtilUI.createBorder("Url", TitledBorder.CENTER));
		proxyTxt.setOpaque(false);


	    portTxt = new JTextField("8080");
	    portTxt.setBorder(UtilUI.createBorder("Port", TitledBorder.CENTER));
	    portTxt.setOpaque(false);

		this.add(useProxyChck, "growx, span 2");
		this.add(proxyTxt, "growx");
		this.add(portTxt, "growx");
	}

	public String getProxyUrl() {
		String url = "";
		if (useProxyChck.isSelected())
			url = proxyTxt.getText();
		return url;
	}

	public int getPort() {
		int port = 0;
		if (useProxyChck.isSelected())
			port = Integer.valueOf(portTxt.getText());
		return port;
	}

	public boolean useProxy() {
		return useProxyChck.isSelected();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == useProxyChck) {
			proxyTxt.setEnabled(useProxyChck.isSelected());
			portTxt.setEnabled(useProxyChck.isSelected());
		}

	}

}
