#@tag
#Feature: Pedir cita
#
#  @tag1
#  Scenario Outline: Como usuario quiero que el servidor recoja una petición de cita
#    Given Una cita con todos los campos dni-user "<dni-user>" , especialidad "<especialidad>", fecha "<fecha>"
#    When pido la cita "<Response>"
#    Then Se guarda correctamente la cita dni-user "<dni-user>" , especialidad "<especialidad>", fecha "<fecha>" Result "<Response>"
#   # And Borro la cita si ha sido insertada con exito "<dni-user>", especialidad "<especialidad>", fecha "<fecha>" Result "<Response>"
#
#    Examples: 
#      | dni-user   | especialidad   |  fecha              |  Response  |
#      |05726690N   | Oncología      |20/12/2019 16:35:00  |   OK       |
#      |05726690    | Cabecera       |10/12/2019 16:30:00  | Error      |
#      |05726690N   |                |10/12/2019 16:30:00  | Error      |
#      |05726690N   | Hola           |10/12/2019 16:30:00  | Error      |
#      |05726690N   | Cabecera       |                     | Error      |
#      |05726690N   | Oncología      |10/12/2019 16:30:00  | Error      |
#      |0572669N    | Oncología      |10/12/2019 16:30:00  | Error      |
#      |            | Cabecera       |10/12/2019 16:30:00  | Error      |
#      |05726690N   | Oncología      |10/12/2019 16:30:00  | Error      |  
#      |05726691J   | Oncología      |20/12/2019 16:30:00  | OK         |
#      |05726690N   | Podología      |20/12/2019 18:35:00  | Error      |
#      |05726692Z   | Oncología      |20/12/2019 18:35:00  | Error      |

 