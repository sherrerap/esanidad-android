
@tag
Feature: Autenticar medico Manager

  @tag2
  Scenario Outline: Me autentico como medico (MANAGER)
    Given un un medico "<dni>" contrasenia "<contrasenia>"
    When me autentico "<response>"
    Then recibo la especialidad de medico "<response>"

 Examples:
 |dni           | contrasenia     |  response |
 | 65278762R    | Albert123       | OK        |
 | 97637789Y    | Carlos123       | Error     |
 | 65278762     | Albert123       | Error     |
 | 97637789S    | Carlos123       | Error     |
 |              | Carlos123       | Error     |  
