
@tag
Feature: Eliminar una Especialidad Manager

  @tag1
  Scenario Outline: Como administrador del sistema quiero poder eliminar Especialidad (Manager)
    Given Tengo nombre "<nombre>",duracion "<duracion>",hora inicio "<hora_inicio>",hora final "<hora_final>"
    Given creo la especialidad "<response>"
		Given La especialidad ha sido guardada nombre "<nombre>",duracion "<duracion>",hora inico "<hora_inicio>",hora final "<hora_final>",response "<response>"
    When borro la especialidad nombre "<nombre>",duracion "<duracion>",hora inico "<hora_inicio>",hora final "<hora_final>",response "<response>"
    Then la especialidad ha sido borrada correctamente nombre "<nombre>",duracion "<duracion>",hora inico "<hora_inicio>",hora final "<hora_final>",response "<response>"
    
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