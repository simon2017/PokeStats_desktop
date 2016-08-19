package pokestats.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import com.pokegoapi.auth.GoogleUserCredentialProvider;

public class AccountPanel extends JPanel implements ChangeListener, ActionListener {
	private JCheckBox useGoogle = null;
	private JButton getGoogleTokenBttn = null;
	private JTextField userTxt = null;
	private JPasswordField passwordTxt = null;
	private JTextField googleTokenTxt = null;
	private JPanel googlePanel = null;
	private JPanel authPanel = null;

	public AccountPanel() {
		super(new MigLayout("wrap 2,fill, insets 5 5 5 5","[center][center]"));
		this.setBorder(UtilUI.createBorder("Usuario", TitledBorder.CENTER));
		this.setOpaque(false);
	}

	public void loadGoogle() {
		googlePanel = new JPanel(new MigLayout("wrap 2,fill, insets 5 5 5 5","[center]"));
		googlePanel.setBorder(UtilUI.createBorder("Google", TitledBorder.CENTER));
		googlePanel.setOpaque(false);

		getGoogleTokenBttn = new JButton("Obtener Token");
		getGoogleTokenBttn.setOpaque(false);

		googleTokenTxt = new JTextField();
		googleTokenTxt.setBorder(UtilUI.createBorder("Token", TitledBorder.CENTER));
		googleTokenTxt.setOpaque(false);

		googlePanel.add(new JLabel("Primero generar Token"), "growx");
		googlePanel.add(getGoogleTokenBttn, "grow");
		googlePanel.add(googleTokenTxt, "span 2 , growx");

		getGoogleTokenBttn.addActionListener(this);

	}

	public void loadAuthenticate() {
		authPanel = new JPanel(new MigLayout("wrap 2,fill, insets 5 5 5 5", "[center]"));
		authPanel.setBorder(UtilUI.createBorder("Club Pokemon", TitledBorder.CENTER));
		authPanel.setOpaque(false);

		userTxt = new JTextField();
		userTxt.setBorder(UtilUI.createBorder("Usuario", TitledBorder.CENTER));
		userTxt.setOpaque(false);

		passwordTxt = new JPasswordField();
		passwordTxt.setBorder(UtilUI.createBorder("Clave", TitledBorder.CENTER));
		passwordTxt.setOpaque(false);

		authPanel.add(userTxt, "growx");
		authPanel.add(passwordTxt, "growx");

	}

	public void loadComponents() {

		useGoogle = new JCheckBox("Usar login Google");
		useGoogle.setOpaque(false);

		loadGoogle();
		loadAuthenticate();

		useGoogle.addActionListener(this);

		{// by default use google login
			useGoogle.setSelected(true);
			passwordTxt.setEnabled(false);
			userTxt.setEnabled(false);
		}

		this.add(useGoogle, "al left,span 2");
		this.add(googlePanel, "span 2, growx");
		this.add(authPanel, "span 2,growx");
	}

	public boolean isGoogleChoosed() {
		return useGoogle != null ? useGoogle.isSelected() : false;
	}

	public String getUser() {
		return this.userTxt.getText();
	}

	public String getPassword() {
		char[] psswd = passwordTxt.getPassword();
		return new String(psswd);
	}

	public String getGoogleToken() {
		return this.googleTokenTxt.getText();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(useGoogle)) {
			boolean googleChoosed = isGoogleChoosed();
			getGoogleTokenBttn.setEnabled(googleChoosed);
			googleTokenTxt.setEnabled(googleChoosed);

			passwordTxt.setEnabled(!googleChoosed);
			userTxt.setEnabled(!googleChoosed);

			this.invalidate();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(useGoogle)) {
			boolean googleChoosed = useGoogle.isSelected();
			getGoogleTokenBttn.setEnabled(googleChoosed);
			googleTokenTxt.setEnabled(googleChoosed);

			passwordTxt.setEnabled(!googleChoosed);
			userTxt.setEnabled(!googleChoosed);

			this.invalidate();
		}

		if (e.getSource().equals(getGoogleTokenBttn)) {
			String command = String.format("%s \"%s\"", "start chrome", GoogleUserCredentialProvider.LOGIN_URL);
			try {
				Runtime.getRuntime().exec("cmd /c" + command);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
