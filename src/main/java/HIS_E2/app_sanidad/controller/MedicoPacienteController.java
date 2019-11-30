package HIS_E2.app_sanidad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**Clase controller de la página de medico-paciente.
 * @author Miguel.
 */
@Controller
public class MedicoPacienteController {

	/**Controlador mapeo de la página de medico-paciente.
	 * @return Devuelve el html de la página de medico-paciente.
	 */
	@RequestMapping(value = "/medicoPaciente", method = RequestMethod.GET)
	public String getMedicoPaciente() {
		return "views/medicoPaciente.html";
	}
}
