package HIS_E2.app_sanidad.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import HIS_E2.app_sanidad.model.Usuario;

@Repository
public interface UserRepository extends MongoRepository<Usuario, String> {

	@Query("{ 'dni' : ?0 }")
	Usuario findByDni(String dni);
}