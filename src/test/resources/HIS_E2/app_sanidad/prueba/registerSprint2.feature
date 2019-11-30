#
# 
#@tag
#Feature: Registro
#  @tag1
#   Scenario Outline: Como usuario quiero registrarme a la aplicación
#     Given Abro Firefox y entro en la aplicacion
#     When Relleno los campos DNI "<DNI>", Nombre "<Nombre>", Apellidos "<Apellidos>", Contraseña "<Contraseña>", Repetir_contraseña "<Repetir_contraseña>", NumeroSS "<NumeroSS>"
#     Then Recibo respuesta "<Result>"
#     And Borro usuario "<DNI>" Result "<Result>"
# 
#     Examples: 
#      | DNI       | Nombre | Apellidos         | Contraseña    |Result  |Repetir_contraseña|NumeroSS    |
#      |76876673R  |Antonio | Pulido Hernández  |Antonio123     |OK      |Antonio123        |123456789012 |
#      | 1057267Z  |Antonio |    Pérez López    |PerezLopez123  |Error   |PerezLopez123     |123456789012 |
#      | 10572676J |        | Pulido Hernández  |Jaimemanuel123 |Error   |Jaimemanuel123    |123456789012 | 
#      | 10572676J |Jaime   |                   |Jaimemanuel123 |Error   |Jaimemanuel123    |123456789012 |
#      |           |Jaime   | Manuel Pérez      |Jaimemanuel123 |Error   |Jaimemanuel123    |123456789012 |
#      | 10572676  |Jaime   | Manuel Pérez      |Jaimemanuel123 |Error   |Jaimemanuel123    |123456789012 |
#      |15726773V  |Juan    | Prueba Prueba     | hola          |Error   |hola              |123456789012 |
#      |15726773V  |Juan    | Prueba Prueba     | holaaaaaaa    |Error   |holaaaaaaa        |123456789012 |
#      |15726773V  |Antonio | Pulido Hernández  | antonio123    |Error   |antonio123        |123456789012 |
#      |00572676E  | Juan   | Prueba Prueba     | Jaimemanuel123|Error   |Jaimemanuel123    |123456789012 |
#      |05726690N  |Antonio | Rodríguez         | Antonio123    |Error   |Antonio123        |123456789012 |
#      |05726690S  |Antonio | Rodríguez         | Antonio123    |Error   |Antonio123        |123456789012 |
#      |00572676E  |Antonio | Rodríguez         | ANTONIO123    |Error   |ANTONIO123        |123456789012 |
#      |00572676E  |Antonio | Pulido Hernández  |Antonio123     |Error   |antonio123        |123456789012 |
#      | 00572676E |Antonio | Pulido Hernández  |Antonio123     |Error   |Antonio123        |             |
#      | 00572676E |Antonio | Pulido Hernández  |Antonio123     |OK      |Antonio123        |12345678     |