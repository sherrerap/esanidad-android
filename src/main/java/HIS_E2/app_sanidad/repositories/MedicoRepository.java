package HIS_E2.app_sanidad.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import HIS_E2.app_sanidad.model.Medico;

@Repository
public interface MedicoRepository extends MongoRepository<Medico, String> {

	@Query(value = "{ 'dni' : ?0 }")
	Medico findByDni(String dni);

}