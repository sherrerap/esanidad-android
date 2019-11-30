package HIS_E2.app_sanidad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**Clase controller de la página de especialidad.
 * @author Miguel.
 */
@Controller
public class EspecialidadController {

		/**Controlador mapeo de la página de especialidad.
		 * @return Devuelve el html de la página de especialidad.
		 */
		@RequestMapping(value = "/especialidad", method = RequestMethod.GET)
		public String getEspecialidad() {
			return "views/especialidad.html";
		}
}