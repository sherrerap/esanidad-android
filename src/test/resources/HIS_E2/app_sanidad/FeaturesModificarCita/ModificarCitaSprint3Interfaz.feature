#
#@tag
#Feature: Modificar cita Interfaz
#
#  @tag1
#  Scenario Outline: Como usuario quiero modificar una cita
#    Given Abro Firefox y entro en la aplicacion citas
#    And Me autentico como un usuario
#    And  pido la cita "<Response>" 
#    When Modifico una cita dni-user "<dni-user>", especialidad "<especialidad>", fecha "<fecha>",nueva fecha "<nueva fecha>",
#    Then Recibo una respuesta de modificación cita "<Result>"
#     And Borro la cita si ha sido insertada con exito "<dni-user>", especialidad "<especialidad>", fecha "<fecha>" Result "<Response>"
#
#    Examples: 
#	|dni-user     | especialidad   | fecha antigua   | nueva fecha      | Response |
#	|05726690N    | Cabecera       |10/12/2019 16:30 | 12/12/2019 17:30 | OK       |
#	|05726690     | Cabecera       |10/12/2019 16:30 | 21/12/2019 16:30 | Error    |
#	|05726691J    |                |10/12/2019 17:30 | 20/12/2019 17:30 | Error    |
#	|05726692Z    | Hola           | 10/12/2019 24:00| 19/12/2019 17:30 | Error    |