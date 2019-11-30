#
#@tag
#Feature: Registro Postman
#
#    @tag2
#     Scenario Outline: Quiero poder enviar peticiones web al servidor para registrarme
#    Given ClienteHttpRegistro
#     When Envío petición Post con todos los campos de registro DNI "<DNI>", Nombre "<Nombre>", Apellidos "<Apellidos>", Contraseña "<Contraseña>",NumeroSS "<NumeroSS>"
#     Then Recibo una respuesta Result "<Result>" DNI <"DNI">  de registro 
#         And Borro usuario "<DNI>" Result "<Result>"
#
#     Examples: 
#      | DNI       | Nombre | Apellidos         | Contraseña    |Result  |NumeroSS     |
#      | 04839982T |Antonio | Pulido Hernández  |Antonio123     |OK      |123456789012 |
#      | 1057267Z  |Antonio |    Pérez López    |PerezLopez123  |Error   |123456789012 |
#      | 10572676J |        | Pulido Hernández  |Jaimemanuel123 |Error   |123456789012 |      
#      | 10572676J |Jaime   |                   |Jaimemanuel123 |Error   |123456789012 |
#      |           |Jaime   | Manuel Pérez      |Jaimemanuel123 |Error   |123456789012 |
#      | 10572676  |Jaime   | Manuel Pérez      |Jaimemanuel123 |Error   |123456789012 |      
#      |15726773V  |Juan    | Prueba Prueba     | hola          |Error   |123456789012 |
#      |15726773V  |Juan    | Prueba Prueba     | holaaaaaaa    |Error   |123456789012 |
#      |15726773V  |Antonio | Pulido Hernández  | antonio123    |Error   |123456789012 |
#      |05726690S  |Antonio | Rodríguez         | Antonio123    |Error   |123456789012 |
#      |00572676E  |Antonio | Rodríguez         | ANTONIO123    |Error   |123456789012 |
#      | 04839982T |Antonio | Pulido Hernández  |Antonio123     |Error   |             |
#      | 04839982T |Antonio | Pulido Hernández  |Antonio123     |Error   |143          |
#      | 10572676J |        | Pulido Hernández  |Jaimemanuel123 |Error   |-123456789012 | 