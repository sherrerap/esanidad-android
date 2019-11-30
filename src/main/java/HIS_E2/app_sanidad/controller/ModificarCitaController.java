package HIS_E2.app_sanidad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**Clase controller de la página de citas.
 * @author Juan Luis
 */
@Controller
public class ModificarCitaController {

	/**Controlador mapeo de la página de citas.
	 * @return Devuelve el html de la página de citas.
	 */
	@RequestMapping(value = "/modificarCita", method = RequestMethod.GET)
	public String getModificarCita() {
		return "views/modificarCita.html";
	}
}
