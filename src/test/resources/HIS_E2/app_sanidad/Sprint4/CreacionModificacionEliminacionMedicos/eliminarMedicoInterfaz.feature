#@tag
#Feature: Eliminar medicos desde la interfaz
#
#  @tag1
#  Scenario Outline: Title of your scenario
#    Given  Tengo de un medico dni "<dni>", especialidad "<especialidad>" 
#    Given creo el medico "<response>"
#    Given Abroo Firefox y entro en la aplicacion citas
#   Then relleno los campos de eliminacion medico dni"<dni>" especialidad "<especialidad>", response "<response>"
#  Then el medico  ha sido borrado correctamente dni "<dni>", especialidad "<especialidad>", response "<response>" 
#    
#Examples:
#|dni       |           especialidad       |response   |
#|84675678M |     Podología                | OK        |
#|05726690N |                              | Error     |
#|          |     Podología                | Error     |
#|05726690N |     Farmaceutica             | Error     |