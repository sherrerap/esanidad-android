package HIS_E2.app_sanidad;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringRunner;

import HIS_E2.app_sanidad.controller.Manager;
import HIS_E2.app_sanidad.model.Cifrador;
import HIS_E2.app_sanidad.model.Especialidad;
import HIS_E2.app_sanidad.model.Medico;
import HIS_E2.app_sanidad.model.PacienteMedico;
import HIS_E2.app_sanidad.model.Usuario;
import HIS_E2.app_sanidad.repositories.UserRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
public class JunitTests2 {
	@Autowired
	UserRepository userRepo;
	@Test
	public void pruebas() {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String dniABuscar="";
		try {
			 dniABuscar= Cifrador.cifrar("76746672L");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Usuario user =userRepo.findByDni(dniABuscar);
		user.toString();
    }
	@Test
	public void pruebas1() {
		try {
			new TestContextManager(getClass()).prepareTestInstance(this);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Manager.get().crearEspecialidad("Pediatría", "1", "9:00", "14:00");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
		@Test
		public void pruebas2() {
			try {
				new TestContextManager(getClass()).prepareTestInstance(this);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {	
				Manager.get().crearMedicoPaciente("05726690N", "73672276X");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		
		
	}
	
	
	
}
