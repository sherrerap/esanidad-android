@tag
Feature: Eliminar una relacion paciente Medico Postman

  @tag1
  Scenario Outline: Como administrador del sistema quiero poder eliminar relaciones paciente medico (Web)
     Given Tengo dni-user "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
    And creo la relacion "<Response>"
    Given ClienteHttpMedicoPaciente
    When Envio peticion eliminar relacion dni-user "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
     Then Recibo una respuesta  dni-user "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
    Then la relacion ha sido borrada "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
Examples:
    | dni-user   | dni-medico  |  Response  |
    |97637789Y   |65278762R    |    OK      |
    |            |65278762R    | Error      |
    |97637789Y   |             | Error      |
    |            |             | Error      |
    |05726690    |65278762R    | Error      |
    |97637789Y   |05707785     | Error      |
    |057266904N  |65278762R    | Error      |
    |97637789Y   |057077852J   | Error      |