package HIS_E2.app_sanidad.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@Document(collection = "cita")
public class Cita {

	ObjectId _id;
	@JsonDeserialize(using = DateHandler.class)
	Date fecha;
	String dniMedico;
	String dniPaciente;
	String especialidad;

	public Cita(Date fecha, String dniMedico,
			String dniPaciente, String especialidad) {
		super();
		this.fecha = fecha;
		this.dniMedico = dniMedico;
		this.dniPaciente = dniPaciente;
		this.especialidad = especialidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDniMedico() {
		return dniMedico;
	}

	public void setDniMedico(String dniMedico) {
		this.dniMedico = dniMedico;
	}

	public String getDniPaciente() {
		return dniPaciente;
	}

	public void setDniPaciente(String dniPaciente) {
		this.dniPaciente = dniPaciente;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	@Override
	public String toString() {
		return "Cita [Fecha=" + fecha.toString()
		+ ", dniMedico=" + dniMedico + ", dniPaciente="
				+ dniPaciente + ", especialidad ="
		+ especialidad + "]";
	}

}