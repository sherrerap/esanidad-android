package HIS_E2.app_sanidad.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "especialidad")
public class Especialidad {

	ObjectId _id;
	String nombreEspecialidad;
	int duracionCita;
	Date horaInicio;
	Date horaFin;

	public Especialidad(String nombreEspecialidad,
			int duracionCita, Date horaInicio, Date horaFin) {
		super();
		this.nombreEspecialidad = nombreEspecialidad;
		this.duracionCita = duracionCita;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public String getNombreEspecialidad() {
		return nombreEspecialidad;
	}

	public void setNombreEspecialidad(String nombreEspecialidad) {
		this.nombreEspecialidad = nombreEspecialidad;
	}

	public int getDuracionCita() {
		return duracionCita;
	}

	public void setDuracionCita(int duracionCita) {
		this.duracionCita = duracionCita;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	@Override
	public String toString() {
		return "Especialidad [nombreEspecialidad=" + nombreEspecialidad
				+ ", duracionCita=" + duracionCita + "]";
	}
}
