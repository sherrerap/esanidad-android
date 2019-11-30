#
#@tag
#Feature: Pedir cita Interfaz Gestor
#
#  @tag1
#  Scenario Outline: Como gestor quiero pedir una cita
#    Given Una cita con todos los campos dni-user "<dni-user>" , especialidad "<especialidad>", fecha "<fecha>"
#    When pido la cita "<Response>"
#    Given Abroo Firefox y entro en la aplicacion cita
#    And Me autentico como gestor dni "<98276278S>" pwd "<Pedro123>"
#    When modifico una cita como gestor dni-user "<dni-user>", especialidad "<especialidad>", fecha "<fecha>", fecha_mod "<fecha_mod>"
#    Then Recibo una respuesta de gestor  cita "<Response>"
#    And Borro la cita si ha sido insertada con exito "<dni-user>", especialidad "<especialidad>", fecha "<fecha>", fecha_mod "<fecha>" Response "<Response>"
#
#    Examples: 
#      | dni-user   | especialidad   |  fecha              |  fecha_mod          |  Response  |
#      |05726690N   | Oncología      |20/12/2019 16:35:00  |20/12/2019 16:35:00  |   OK       |
#      |05726690N   | Cabecera       |20/12/2019 16:35:00  |                     |Error       |
#      |05726690N   | Cabecera       |20/12/2019 16:35:00  |         adsf        |Error       |