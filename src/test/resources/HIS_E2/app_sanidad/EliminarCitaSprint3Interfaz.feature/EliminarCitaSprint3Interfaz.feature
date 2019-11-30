
#@tag
#Feature: Eliminar cita interfaz
#  @tag1  
#  Scenario Outline: Como usuario quiero eliminar una cita
#    Given Abro Firefox y entro en la aplicacion citas
#    And Me autentico como un usuario 
#    When Elimino una cita dni-user "<dni-user>", especialidad "<especialidad>", fecha "<fecha>"
#    Then Recibo una respuesta de cita eliminada "<Result>"
#    And Inserto la cita si ha sido borrada con exito "<dni-user>", especialidad "<especialidad>", fecha "<fecha>"
#    Examples: 
#      | dni-user   | especialidad   |  fecha              |  Response  |
#      |05726690N   | Cabezera       |  10/12/2019 16:30   |   OK       |
#      |05726690    | Cabezera       |  10/12/2019 16:30   | Error      |
#      |05726690N   |                |  10/12/2019 17:30   | Error      |
#      |05726690N   | Hola           |  10/12/2019 18:30   | Error      |
#      |05726690N   | Oncología      |  10/12/2019 24:00   | Error      |
#      |05726690N   | Cabezera       |                     | Error      |
#      |05726690N   | Oncología      |  10/12/2017 18:30   | Error      |
#      |0572669N    | Oncología      | 10/12/2019 18:30    | Error      |
#      |            | Cabezera       | 10/12/2019 16:30    | Error      |
#      |05726690S   | Cabezera       | 10/12/2019 16:30    | Error      | 
#      |05726690N   | Cabezera       |  10/12/2019 17:30   | Error      |
