package HIS_E2.app_sanidad.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PacienteMedico")
public class PacienteMedico {

	ObjectId _id;
	String dniPaciente;
	String dniMedico;
	String especialidad;

	public PacienteMedico(String dniPaciente, String dniMedico,
			String especialidad) {
		super();
		this.dniPaciente = dniPaciente;
		this.dniMedico = dniMedico;
		this.especialidad = especialidad;
	}

	public PacienteMedico() {
		// TODO Auto-generated constructor stub
	}

	public String getDniPaciente() {
		return dniPaciente;
	}
	public void setDniPaciente(String dniPaciente) {
		this.dniPaciente = dniPaciente;
	}
	public String getDniMedico() {
		return dniMedico;
	}
	public void setDniMedico(String dniMedico) {
		this.dniMedico = dniMedico;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
}
