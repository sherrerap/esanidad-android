package HIS_E2.app_sanidad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**Clase controller de la página de gestor.
 * @author Miguel.
 */
@Controller
public class GestorSistemaController {

		/**Controlador mapeo de la página de gestor.
		 * @return Devuelve el html de la página de gestor.
		 */
		@RequestMapping(value = "/gestor", method = RequestMethod.GET)
		public String getGestor() {
			return "views/gestor.html";
		}
}
