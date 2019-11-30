#
#@tag
#Feature: Como usuario quiero modificar una cita
#    @tag2
#     Scenario Outline: Quiero poder enviar peticiones web al servidor para modificar cita
#     Given ClienteHttpPedirCita
#     When Envío petición Post con todos los campos de modificar cita dni-user "<dni-user>", especialidad "<especialidad>" , fecha "<fecha antigua>", fecha nueva "<nueva fecha>"
#     Then Recibo una respuesta de cita Result "<Response>" dni-user "<dni-user>", especialidad "<especialidad>" , fecha "<nueva fecha>"
#     And Borro la cita si ha sido insertada con exito "<dni-user>", especialidad "<especialidad>", fecha "<fecha>" Result "<Response>"
#    Examples: 
#	|dni-user     | especialidad   | fecha antigua   | nueva fecha      | Response |
#  |05726690N    | Cabecera       |10/12/2019 16:30 | 12/12/2019 17:30 | OK       |
#  |05726690     | Cabecera       |10/12/2019 16:30 | 21/12/2019 16:30 | Error    |
#	|05726691J    |                |10/12/2019 17:30 | 20/12/2019 17:30 | Error    |
#	|05726692Z    | Hola           | 10/12/2019 24:00| 19/12/2019 17:30 | Error    |
