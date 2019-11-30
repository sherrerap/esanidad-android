package HIS_E2.app_sanidad.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import HIS_E2.app_sanidad.model.Cita;

@Repository
public interface CitaRepository extends MongoRepository<Cita, String> {

	@Query(value = "{ 'dniMedico' : ?0}")
	List<Cita> findByDniMedico(String dniMedico);

	@Query(value = "{ 'dniPaciente' : ?0}")
	List<Cita> findByDniPaciente(String dniPaciente);

	@Query(value = "{ 'dniPaciente' : ?0, 'dniMedico' : ?1, 'fecha' : ?2}")
	List<Cita> deleteCustomCita(String dniPaciente,
			String dniMedico, Date fecha);

	@Query(value = "{ 'dniPaciente' : ?0, 'dniMedico' : ?1, 'fecha' : ?2}")
	boolean existCustomCita(String dniPaciente,
			String dniMedico, Date fecha);
}