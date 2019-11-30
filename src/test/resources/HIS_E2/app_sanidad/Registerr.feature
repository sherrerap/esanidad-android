
@tag
Feature: Registro web postman
     @tag1
  Scenario Outline: Comoo usuario puedo enviar peticion de registro
    Given Cliente http
    When Envío petición Post con todos los campos
    Then Recibo una respuesta satisfactoria
     
     
     
#     Examples:                      		

#|nombre  |apellidos        |DNI            |numeroSS         |password  |repetir_password         |		

#|User1   |password1        |05726690N		   |12345678910      |1111      |1111                     |

#|User2   |password2        |05726690N		   |12345678910      |1111      |1111                     |		

#|User3   |password3        |05726690N		   |12345678910      |1111      |1111                     |