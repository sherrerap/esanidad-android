package HIS_E2.app_sanidad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**Clase controller de la página de modificar especialidad.
 * @author Miguel.
 */
@Controller
public class ModificarEspecialidadController {

	/**Controlador mapeo de la página de modificar especialidad.
	 * @return Devuelve el html de la página de modificar especialidad.
	 */
	@RequestMapping(value = "/modificarEspecialidad", method = RequestMethod.GET)
	public String getModificarEspecialidad() {
		return "views/modificarEspecialidad.html";
	}
}