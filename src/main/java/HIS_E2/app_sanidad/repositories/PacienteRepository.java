package HIS_E2.app_sanidad.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import HIS_E2.app_sanidad.model.Paciente;

@Repository
public interface PacienteRepository extends MongoRepository<Paciente, String> {

}
