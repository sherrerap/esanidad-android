@tag
Feature: Eliminar Médico

  @tag2
  Scenario Outline: Eliminar medico Manager
    Given Tengo de un medico dni "<dni>", especialidad "<especialidad>"
    Given creo el medico "<response>"
    When el medico ha sido guardado correctamente dni "<dni>", especialidad "<especialidad>", response "<response>"
    Then borro el medico dni "<dni>", especialidad "<especialidad>", response "<response>"
    

    
Examples:
|dni       |           especialidad       |response   |
|97637789Y |     EspecialidadC            | OK        |
|05726690N |                              | Error     |
|          |     Podología                | Error     |
|05726690N |     Farmaceutica             | Error     |