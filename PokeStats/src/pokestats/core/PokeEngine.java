package pokestats.core;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import com.pokegoapi.api.PokemonGo;
import com.pokegoapi.api.inventory.Inventories;
import com.pokegoapi.api.inventory.PokeBank;
import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.auth.GoogleUserCredentialProvider;
import com.pokegoapi.auth.PtcCredentialProvider;

public class PokeEngine {
	private final long CONN_TIME_OUT_SEC = 60L;

	private String proxyURL = "";
	private int proxyPort = 0;

	private String user = "";
	private String password = "";

	private String googleToken = "";
	private String refreshToken = "";

	public PokeEngine(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public PokeEngine(String token) {
		this.googleToken = token;
	}

	public void setProxy(String url, int port) {
		this.proxyPort = port;
		this.proxyURL = url;
	}

	public void refreshToken(String newToken) {
		if (this.googleToken.equals(newToken) == false) {
			refreshToken = "";
			googleToken = newToken;
		}
		this.user = "";
		this.password = "";

	}

	public void refreshUserPassword(String nuser, String npassword) {
		if (user.equals(nuser) == false || password.equals(npassword) == false) {
			this.user = nuser;
			this.password = npassword;
		}
		googleToken = "";
		refreshToken = "";
	}

	public List<Pokemon> getPokemons() {
		List<Pokemon> pokemons = new ArrayList<Pokemon>();

		boolean useProxy = !proxyURL.isEmpty();
		try {
			OkHttpClient httpClient = null;
			if (useProxy) {
				httpClient = new OkHttpClient.Builder().connectTimeout(CONN_TIME_OUT_SEC, TimeUnit.SECONDS)
						.writeTimeout(60L, TimeUnit.SECONDS).readTimeout(CONN_TIME_OUT_SEC, TimeUnit.SECONDS)
						.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyURL, proxyPort))).build();
			} else {
				httpClient = new OkHttpClient.Builder().connectTimeout(CONN_TIME_OUT_SEC, TimeUnit.SECONDS)
						.writeTimeout(60L, TimeUnit.SECONDS).readTimeout(CONN_TIME_OUT_SEC, TimeUnit.SECONDS).build();
			}

			GoogleUserCredentialProvider provider = new GoogleUserCredentialProvider(httpClient);
			PokemonGo go = null;

			if (googleToken.isEmpty() == false) {// use google login
				provider.login(googleToken);
				if (refreshToken == null || refreshToken.isEmpty()) {
					go = new PokemonGo(provider, httpClient);
					refreshToken = provider.getRefreshToken();
				} else
					// we have a refresh token, let's use it
					go = new PokemonGo(new GoogleUserCredentialProvider(httpClient, refreshToken), httpClient);

			} else {// use pokemongo login
				go = new PokemonGo(new PtcCredentialProvider(httpClient, user, password), httpClient);
			}

			Inventories inv = go.getInventories();
			PokeBank pokeBank = inv.getPokebank();
			pokemons = pokeBank.getPokemons();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pokemons;
	}
}
