
@tag
Feature: Eliminar medico

  @tag1
  Scenario Outline: Eliminar Medico Postman
    Given  Tengo de un medico dni "<dni>", especialidad "<especialidad>" 
    And creo el medico "<response>"
    And ClienteHttpCrearMedico
    When Envio peticion de eliminar medico dni "<dni>" , especlialidad "<especialidad>", response "<response>"
    Then Recibo una respuesta response "<response>"

    
Examples:
|dni       |           especialidad       |response   |
|97637789Y |     EspecialidadC            | OK        |
|05726690N |                              | Error     |
|          |     Podología                | Error     |
|05726690N |     Farmaceutica             | Error     |