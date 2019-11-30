#@tag
#Feature: Autenticacion de medico WEB
#
#  @tag1
#  Scenario Outline: Como medico quiero autenticarme
#    Given un un medico "<dni>" contrasenia "<contrasenia>"
#    When envio peticion autenticar "<dni>" contrasenia "<contrasenia>" response "<Response>"
#    Then recibo una respuesta autenticar "<dni>" contrasenia "<contrasenia>" response "<Response>"
#
#    Examples: 
#    | dni               | contrasenia     |Response  |
#    | 98276278S         | Pedro123        | OK       |
#    | 05726691          | hola            | Error    |
#    | 98276278N         |                 | Error    |
#    | 97637789Y         | Carlos12        | Error    |
#    | 98276278N         |                 | Error    |
#    | 98276278S         | Pedro123        | OK       |
