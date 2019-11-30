package HIS_E2.app_sanidad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**Clase controller de la página de medico.
 * @author Miguel.
 */
@Controller
public class MedicoController {

	/**Controlador mapeo de la página de medico.
	 * @return Devuelve el html de la página de medico.
	 */
	@RequestMapping(value = "/medico", method = RequestMethod.GET)
	public String getMedico() {
		return "views/medico.html";
	}
}
