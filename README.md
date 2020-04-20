# TCC-PUC-Minas
Trabalho de Conclusão do Curso de Especialização em Arquitetura de Software Distribuído pela PUC Minas.

Este trabalho é uma POC (Prova de Conceito) aplicando o desenvolvimento de microserviços, automação de build,
testes automatizados e implantação automática de artefatos em ambiente de nuvem.

A arquitetura é composta de três microserviços, construídos com a plataforma Java e ecossistema Spring.

Nessa prova de conceito foram integradas API's para envio de SMS e E-mail, e integração real com o boker de IoT da Microsoft, o Azure IoT Hub, através do SDK Java fornecido pela própria Microsoft.

Eventos enviados por dispositivo IoT emulado (simulando um sensor de barragens) passam pelo broker e são recebidos por um microserviço de monitoramento o qual realiza o tratamento dos dados
e solicita ou não a um segundo microserviço o envio de altertas para pessoas afetadas relacionadas à barragem.