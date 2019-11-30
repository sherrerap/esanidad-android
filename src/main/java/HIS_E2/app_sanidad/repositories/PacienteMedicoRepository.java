package HIS_E2.app_sanidad.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import HIS_E2.app_sanidad.model.PacienteMedico;

@Repository
public interface PacienteMedicoRepository
	extends MongoRepository<PacienteMedico, String> {

	@Query(value = "{ 'dniPaciente' : ?0, 'especialidad' : ?1}")
	PacienteMedico findCustomMedico(String dniPaciente,
			String especialidad);

	@Query(value = "{ 'dniMedico' : ?0}")
	List<PacienteMedico> findCustomDniMedico(String dniMedico);
	
	@Query(value = "{ 'dniPaciente' : ?0}")
	List<PacienteMedico> findCustomDniPaciente(String dniPaciente);
}
