package HIS_E2.app_sanidad.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "paciente")
public class Paciente extends Usuario {
	String numeroSS;


	public Paciente(String dni, String nombre,
			String apellidos, String contrs,
			String numeroSS) throws Exception {
		super(dni, nombre, apellidos, contrs);
		this.numeroSS = Cifrador.cifrar(numeroSS);
	}

	public String getNumeroSS() {
		return numeroSS;
	}

	public void setNumeroSS(String numeroSS) {
		this.numeroSS = numeroSS;
	}

	@Override
	public String toString() {
		return "Paciente [numeroSS=" + numeroSS
				+ ", dni=" + dni + ", nombre="
				+ nombre + ", apellidos=" + apellidos
				+ ", contrs=" + contrs + ", centroSalud="
				+ centroSalud + "]";
	}
}
