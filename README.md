# TCC-PUC-Minas
Trabalho de Conclusão do Curso de Especialização em Arquitetura de Software Distribuído pela PUC Minas.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Azure](https://img.shields.io/badge/azure-%230072C6.svg?style=for-the-badge&logo=microsoftazure&logoColor=white)
![Twilio](https://img.shields.io/badge/Twilio-F22F46?style=for-the-badge&logo=Twilio logoColor=white)
![Eclipse](https://img.shields.io/badge/Eclipse-FE7A16.svg?style=for-the-badge&logo=Eclipse&logoColor=white)

Este trabalho é uma POC (Prova de Conceito) aplicando o desenvolvimento de microserviços, automação de build,
testes automatizados e implantação automática de artefatos em ambiente de nuvem.

A arquitetura é composta de três microserviços, construídos com a plataforma Java e ecossistema Spring.

Nessa prova de conceito foram integradas API's para envio de SMS e E-mail, e integração real com o boker de IoT da Microsoft, o Azure IoT Hub, através do SDK Java fornecido pela própria Microsoft.

Eventos enviados por dispositivo IoT emulado (simulando um sensor de barragens) passam pelo broker e são recebidos por um microserviço de monitoramento o qual realiza o tratamento dos dados
e solicita ou não a um segundo microserviço o envio de altertas para pessoas afetadas relacionadas à barragem.
