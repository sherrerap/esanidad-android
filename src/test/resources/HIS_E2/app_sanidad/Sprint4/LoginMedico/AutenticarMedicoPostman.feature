
@tag
Feature: Autenticar Medico Postman

    
  @tag2
  Scenario Outline: Como sanitario quiero poder autenticarme (WEB)
  Given ClienteHttpAutenticarMedico
  When Envio peticion autenticar "<dni>" contrasenia "<contrasenia>" response "<response>"
  Then Recibo una respuesta autenticar "<dni>" contrasenia "<contrasenia>" response "<response>"
  
  
 Examples:
 |dni           | contrasenia     |  response |
 | 65278762R    | Albert123       | OK        |
 | 97637789Y    | Carlos123       | Error     |
 | 65278762     | Albert123       | Error     |
 | 97637789S    | Carlos123       | Error     |
 |              | Carlos123       | Error     |  
 


