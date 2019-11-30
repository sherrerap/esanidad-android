@tag
Feature: Crear Médico Postman

  @tag1
  Scenario Outline: Como administrador quiero crear un medcio (Web)
    Given ClienteHttpCrearMedico
    When Envio peticion crear medico dni "<dni>",especialidad "<especialidad>", response "<response>"
    Then Recibo una respuesta response "<response>"
    Then el medico ha sido guardado correctamente dni "<dni>", especialidad "<especialidad>", response "<response>"
    Then borro el medico dni "<dni>", especialidad "<especialidad>", response "<response>"






    
Examples:
|dni       |           especialidad       |response   |
|97637789Y |     EspecialidadC            | OK        |
|05726690N |                              | Error     |
|          |     Podología                | Error     |
|05726690N |     Farmaceutica             | Error     |