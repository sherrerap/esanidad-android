
@tag
Feature: Eliminar Una relación paciente-medico

  @tag1
  Scenario Outline: Como administrador del sistema quiero poder eliminar citas (Manager)
      Given Tengo dni-user "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
    Given creo la relacion "<Response>"
		Given la relacion ha sido guardada dni-user "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
   When borro la relacion dni-user "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
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