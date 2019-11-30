
@tag
Feature: Pedir cita Interfaz Gestor

  @tag1
  Scenario Outline: Como gestor quiero pedir una cita
    Given Abroo Firefox y entro en la aplicacion cita
    When Me autentico como gestor dni "71723156W" pwd "GestorCitas123"
    Then Pido una cita como gestor dni-user "<dni-user>", especialidad "<especialidad>", fecha "<fecha>"


    Examples: 
      | dni-user   | especialidad   |  fecha              |  Response  |
      |05726690N   | Pediatría      |20/12/2019 16:35:00  |   OK       |
#      |05726690    | Cabecera       |10/12/2019 16:30:00  | Error      |
#      |05726690N   |                |10/12/2019 16:30:00  | Error      |
#      |05726690N   | Hola           |10/12/2019 16:30:00  | Error      |
#      |05726690N   | Cabecera       |                     | Error      |