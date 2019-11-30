
@tag
Feature: Como administrador quiero ver la lista de especialidades

  @tag1
  Scenario: Como administrador quiero visualizar la lista de especialdiades
   Given Tengo nombre "Podología",duracion "15",hora inicio "9:00",hora final "13:00"
    Given creo la especialidad "OK"
    Given usuario admin "23456225C", contrasenia "hrW@4e1%gKac@&ipLtsY"
    When Pido la lista de especialidades "23456225C"
    Then Recibo una respuesta lista de especialidades "OK"
    Then borro la especialidad nombre "Podología",duracion "15",hora inico "9:00",hora final "13:00",response "OK"
    

    
  @Tag2
  Scenario: Quiero poder recibir la lista de especialidades
   Given Tengo nombre "Podología",duracion "15",hora inicio "9:00",hora final "13:00"
    Given creo la especialidad "OK"
  Given ClienteHttpEspecialidad
  When Envio peticion recibir lista especialidades dni_admin "23456225C"
  Then Recibo Respuesta lista especialidades "OK"
      Then borro la especialidad nombre "Podología",duracion "15",hora inico "9:00",hora final "13:00",response "OK"