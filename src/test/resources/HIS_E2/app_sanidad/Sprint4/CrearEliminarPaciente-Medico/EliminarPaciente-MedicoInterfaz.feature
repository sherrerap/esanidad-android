#
#@tag
#Feature: Eliminar relacion  paciente medico
#
#  @tag1
#  Scenario Outline: Como administrador quiero crear una relacion medico paciente (Vista)
#   Given Tengo dni-user "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
#   When creo la relacion "OK"
#  Given Abroo Firefox y entro en la aplicacion
#  And Entro en la vista del administrador dni "768766579C" contraseña "password"
#  When presiono boton eliminar relacion medico paciente
#  Then elimino relacion medico paciente dni-user "<dni-user>" "<dni-medico>"
#  Then borro la relacion dni-user "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
#
#Examples:
#    | dni-user   | dni-medico  |  Response  |
#    |97637789Y   |65278762R    |    OK      |
#    |            |65278762R    | Error      |
#    |97637789Y   |             | Error      |
#    |            |             | Error      |
#    |05726690    |65278762R    | Error      |
#    |97637789Y   |05707785     | Error      |
#    |057266904N  |65278762R    | Error      |
#    |97637789Y   |057077852J   | Error      |

