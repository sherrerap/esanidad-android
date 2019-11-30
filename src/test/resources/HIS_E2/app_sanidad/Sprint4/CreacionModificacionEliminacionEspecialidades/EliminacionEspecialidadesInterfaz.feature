#
#
#
#@tag
#Feature: Eliminación de una especialidad
#
#  @tag1
#  Scenario Outline: Como administrador del sistema quiero poder eliminar citas (Vista)
#     Given Tengo nombre "<nombre>",duracion "<duracion>",hora inicio "<hora_inicio>",hora final "<hora_final>"
#  Given creo la especialidad "<OK>"
#   Given Abroo Firefox y entro en la aplicacion citas
#   And Entro en la vista del gestor dni"<768766579C>" contraseña "<password>"
#   When Presiono el boton eliminar especialidad y recibo respuesta "<response>"
#   Then la especialidad ha sido borrada correctamente nombre "<nombre>",duracion "<duracion>",hora inico "<hora_inicio>",hora final "<hora_final>",response "<response>"
#  
#  
#Examples:
#	|nombre         | duracion               |response             |
#	| Podología     |   15                   | OK                  |
#	|               |   20                   | Error               |
#	| 1234          |   20                   | Error               |
#	|E12            |   5                    | Error               |
#	|Cabecera       |   15                   | OK                  |
#	|Cabecera       |   A                    | Error               |
#	|Oncología      |   0                    | Error               |
#	|Podología      |                        | Error               |
#	|Oncología      |   -1                   | Error               |
	
