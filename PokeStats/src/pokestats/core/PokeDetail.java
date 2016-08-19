package pokestats.core;

public class PokeDetail {
	private String nombre;
	private double perfecto;
	private String cp;
	private String ataque;
	private String defensa;
	private String estamina;
	private String nivel;

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPerfecto() {
		return this.perfecto;
	}

	public void setPerfecto(double perfecto) {
		this.perfecto = perfecto;
	}

	public String getCp() {
		return this.cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getAtaque() {
		return this.ataque;
	}

	public void setAtaque(String ataque) {
		this.ataque = ataque;
	}

	public String getDefensa() {
		return this.defensa;
	}

	public void setDefensa(String defensa) {
		this.defensa = defensa;
	}

	public String getEstamina() {
		return this.estamina;
	}

	public void setEstamina(String estamina) {
		this.estamina = estamina;
	}

	public String getNivel() {
		return this.nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

}
