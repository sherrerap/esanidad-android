package HIS_E2.app_sanidad.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuario")
public class Usuario {

	@Indexed(unique=true)
	String dni;
	String nombre;
	String apellidos;
	String contrs;
	int centroSalud;

	public Usuario(String dni, String nombre, String apellidos, String contrs) throws Exception {
		super();
		this.dni = Cifrador.cifrar(dni);
		this.nombre = Cifrador.cifrar(nombre);
		this.apellidos = Cifrador.cifrar(apellidos);
		this.contrs = Cifrador.cifrarHash(contrs);
	}
	public Usuario() {

	}
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getContrs(){
		return contrs;
	}

	public void setContrs(String contrs) {
		this.contrs = contrs;
	}

	public int getCentroSalud() {
		return centroSalud;
	}

	public void setCentroSalud(int centroSalud) {
		this.centroSalud = centroSalud;
	}

	@Override
	public String toString() {
		return "Usuario [dni=" + dni + ", nombre=" + nombre
				+ ", apellidos=" + apellidos
				+ ", contrs=" + contrs
				+ ", centroSalud=" + centroSalud + "]";
	}
}
