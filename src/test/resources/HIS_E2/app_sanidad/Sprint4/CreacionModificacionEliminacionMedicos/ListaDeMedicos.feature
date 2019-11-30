
@tag
Feature: Lista de médicos

  @tag1
  Scenario: Quiero visualizr lista de DNIs Médicos (servidor)
    When Pido lista de DNIs manager 
    Then recibo una lista de DNIs "<OK>"


@tag2
Scenario: Quiero visualizar lista de DNIs de médicos
	Given ClienteHttpCrearMedico
	When pido lista de médicos web
	Then Recibo una respuesta response "<OK>"