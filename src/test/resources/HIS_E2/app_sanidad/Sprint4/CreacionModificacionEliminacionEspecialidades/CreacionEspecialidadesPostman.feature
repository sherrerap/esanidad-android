
@tag
Feature: Creación de una especialidad POSTMAN

  @tag1
  Scenario Outline: Como administrador quiero crear una especialidad (Web)
    Given ClienteHttpEspecialidad
    When Envio peticion crear especialidad nombre "<nombre>",duracion "<duracion>",hora inico "<hora_inicio>",hora final "<hora_final>",response "<response>"
    Then Recibo una respuesta  nombre "<nombre>",duracion "<duracion>",hora inico "<hora_inicio>",hora final "<hora_final>",response "<response>"
    And La especialidad ha sido guardada nombre "<nombre>",duracion "<duracion>",hora inico "<hora_inicio>",hora final "<hora_final>",response "<response>"
    And borro la especialidad nombre "<nombre>",duracion "<duracion>",hora inico "<hora_inicio>",hora final "<hora_final>",response "<response>"
    
Examples:
	|nombre           | duracion     |hora_inicio| hora_final       |response             |
	|especialidadTestA|   15         |  9:00     | 14:00            | OK                  |
	|                 |   20         |  9:00     | 14:00            | Error               |
	| 1234            |   20         |  9:00     |14:00             | Error               |
	|E12              |   5          |  9:00     | 14:00            | Error               |
	|especialidadTestB|   15         |  9:00     |14:00             | OK                  |
	|Cabecera         |    A         |  9:00     |14:00             | Error               |
	|Oncología        |   0          |  9:00     |14:00             | Error               |
	|especialidadTestA|              |  9:00     |14:00             | Error               |
	|Oncología        |    -1        |  9:00     |14:00             | Error               |