CREATE DATABASE  IF NOT EXISTS `tccscapucminasbackendapimonitoramentobarragens` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tccscapucminasbackendapimonitoramentobarragens`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tccscapucminasbackendapimonitoramentobarragens
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `afetado`
--

DROP TABLE IF EXISTS `afetado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `afetado` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `email` varchar(130) NOT NULL,
  `telefone_principal` varchar(16) NOT NULL,
  `telefone_reserva` varchar(16) NOT NULL,
  `barragem_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_afetado_barragem_id` (`barragem_id`),
  CONSTRAINT `fk_afetado_barragem_id` FOREIGN KEY (`barragem_id`) REFERENCES `barragem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afetado`
--

LOCK TABLES `afetado` WRITE;
/*!40000 ALTER TABLE `afetado` DISABLE KEYS */;
INSERT INTO `afetado` VALUES (1,'Afetado 01','maildev@mail.com','+5599999999999','+5599999999999',6),(2,'Afetado 02','maildev@mail.com','+5599999999999','+5599999999999',6);
/*!40000 ALTER TABLE `afetado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `barragem`
--

DROP TABLE IF EXISTS `barragem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `barragem` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  `capacidade_metros_cubicos` decimal(21,2) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `grau_risco` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barragem`
--

LOCK TABLES `barragem` WRITE;
/*!40000 ALTER TABLE `barragem` DISABLE KEYS */;
INSERT INTO `barragem` VALUES (1,'Barragem Mina Engenho',46621.00,15231,74024,'ALTO'),(2,'Barragem II Mina Engenho',44599.00,61992,38776,'ALTO'),(3,'Barragem B1/B4',34264.00,58502,47486,'BAIXO'),(4,'Barragem de Água do Igarapé Bahia',70038.00,47468,36973,'ALTO'),(5,'Barragem de Rejeitos',30034.00,60684,61352,'BAIXO'),(6,'Norte/Laranjeiras',47142.00,67704,80903,'MEDIO'),(7,'BACIA DE REJEITOS 14/15',95813.00,7740,62150,'MEDIO'),(8,'Barragem B1-Auxiliar - Mina Tico-Tico',14569.00,94944,81939,'BAIXO'),(9,'Barragem de Água do Igarapé Bahia',9976.00,18856,17317,'MEDIO'),(10,'Barragem do Mirante I e II',33372.00,78971,78701,'MEDIO');
/*!40000 ALTER TABLE `barragem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2020-04-10 15:26:49',1,'EXECUTED','8:9bf75aca02e1c4157cc1268a96e34b65','createTable tableName=jhi_persistent_audit_event; createTable tableName=jhi_persistent_audit_evt_data; addPrimaryKey tableName=jhi_persistent_audit_evt_data; createIndex indexName=idx_persistent_audit_event, tableName=jhi_persistent_audit_event; c...','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180500-1','jhipster','config/liquibase/changelog/20200409180500_added_entity_Barragem.xml','2020-04-10 15:26:49',2,'EXECUTED','8:b0503ea9b9e6bb2812a37361c72fcc9b','createTable tableName=barragem','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180500-1-relations','jhipster','config/liquibase/changelog/20200409180500_added_entity_Barragem.xml','2020-04-10 15:26:49',3,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','empty','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180500-1-data','jhipster','config/liquibase/changelog/20200409180500_added_entity_Barragem.xml','2020-04-10 15:26:49',4,'EXECUTED','8:2d75f40ade516e65ee95162394795ac2','loadData tableName=barragem','',NULL,'3.8.7','faker',NULL,'6532408365'),('20200409180600-1','jhipster','config/liquibase/changelog/20200409180600_added_entity_Sensor.xml','2020-04-10 15:26:49',5,'EXECUTED','8:11be12a4270c8aeaa90c357df25fe8ee','createTable tableName=sensor','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180600-1-relations','jhipster','config/liquibase/changelog/20200409180600_added_entity_Sensor.xml','2020-04-10 15:26:49',6,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','empty','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180600-1-data','jhipster','config/liquibase/changelog/20200409180600_added_entity_Sensor.xml','2020-04-10 15:26:49',7,'EXECUTED','8:b360bb3e1cb18ffc38485fccb424ebf3','loadData tableName=sensor','',NULL,'3.8.7','faker',NULL,'6532408365'),('20200409180700-1','jhipster','config/liquibase/changelog/20200409180700_added_entity_EventoMedicao.xml','2020-04-10 15:26:49',8,'EXECUTED','8:94ea8c97423fd83cdf7195f2e73465d6','createTable tableName=evento_medicao; dropDefaultValue columnName=data, tableName=evento_medicao','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180700-1-relations','jhipster','config/liquibase/changelog/20200409180700_added_entity_EventoMedicao.xml','2020-04-10 15:26:49',9,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','empty','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180700-1-data','jhipster','config/liquibase/changelog/20200409180700_added_entity_EventoMedicao.xml','2020-04-10 15:26:49',10,'EXECUTED','8:b1f63c3256e9b99e46b540e72c4c4078','loadData tableName=evento_medicao','',NULL,'3.8.7','faker',NULL,'6532408365'),('20200409180800-1','jhipster','config/liquibase/changelog/20200409180800_added_entity_Incidente.xml','2020-04-10 15:26:49',11,'EXECUTED','8:96b80f80d04d4250dad185fff71d9cff','createTable tableName=incidente; dropDefaultValue columnName=data, tableName=incidente','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180800-1-relations','jhipster','config/liquibase/changelog/20200409180800_added_entity_Incidente.xml','2020-04-10 15:26:49',12,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','empty','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180800-1-data','jhipster','config/liquibase/changelog/20200409180800_added_entity_Incidente.xml','2020-04-10 15:26:49',13,'EXECUTED','8:a22736a34fc06ca182d72357bdeca001','loadData tableName=incidente','',NULL,'3.8.7','faker',NULL,'6532408365'),('20200409180900-1','jhipster','config/liquibase/changelog/20200409180900_added_entity_Afetado.xml','2020-04-10 15:26:50',14,'EXECUTED','8:15365825ea04ec4d4a9fd0abcda6f474','createTable tableName=afetado','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180900-1-relations','jhipster','config/liquibase/changelog/20200409180900_added_entity_Afetado.xml','2020-04-10 15:26:50',15,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','empty','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180900-1-data','jhipster','config/liquibase/changelog/20200409180900_added_entity_Afetado.xml','2020-04-10 15:26:50',16,'EXECUTED','8:0cd2bedc0a78e4e207a2dec8ef17279a','loadData tableName=afetado','',NULL,'3.8.7','faker',NULL,'6532408365'),('20200409180600-1','jhipster','config/liquibase/changelog/20200409180600_added_entity_EventoMedicaoClassificacaoAlerta.xml','2020-04-10 15:26:50',17,'EXECUTED','8:2333a7022c489fb7f01cbc25fefb64b5','createTable tableName=evento_medicao_classificacao_alerta','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180600-1-relations','jhipster','config/liquibase/changelog/20200409180600_added_entity_EventoMedicaoClassificacaoAlerta.xml','2020-04-10 15:26:50',18,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','empty','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180600-1-data','jhipster','config/liquibase/changelog/20200409180600_added_entity_EventoMedicaoClassificacaoAlerta.xml','2020-04-10 15:26:50',19,'EXECUTED','8:cd8153243047b791c114b15a549fc20d','loadData tableName=evento_medicao_classificacao_alerta','',NULL,'3.8.7','faker',NULL,'6532408365'),('20200409180700-1','jhipster','config/liquibase/changelog/20200409180700_added_entity_IncidenteResultadoProcessamento.xml','2020-04-10 15:26:50',20,'EXECUTED','8:c31587f14695fbaad530e31b8f3ffa0f','createTable tableName=incidente_resultado_processamento; dropDefaultValue columnName=data, tableName=incidente_resultado_processamento','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180700-1-relations','jhipster','config/liquibase/changelog/20200409180700_added_entity_IncidenteResultadoProcessamento.xml','2020-04-10 15:26:50',21,'EXECUTED','8:d41d8cd98f00b204e9800998ecf8427e','empty','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180700-1-data','jhipster','config/liquibase/changelog/20200409180700_added_entity_IncidenteResultadoProcessamento.xml','2020-04-10 15:26:50',22,'EXECUTED','8:4b9a264b1cda5443241cb462c722d8ed','loadData tableName=incidente_resultado_processamento','',NULL,'3.8.7','faker',NULL,'6532408365'),('20200409180600-2','jhipster','config/liquibase/changelog/20200409180600_added_entity_constraints_Sensor.xml','2020-04-10 15:26:51',23,'EXECUTED','8:92e4925920d0975aaeaf0ecf468e8490','addForeignKeyConstraint baseTableName=sensor, constraintName=fk_sensor_barragem_id, referencedTableName=barragem','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180800-2','jhipster','config/liquibase/changelog/20200409180800_added_entity_constraints_Incidente.xml','2020-04-10 15:26:51',24,'EXECUTED','8:0e07e1bf189ea7d08e4bb7a9ea9803ae','addForeignKeyConstraint baseTableName=incidente, constraintName=fk_incidente_barragem_id, referencedTableName=barragem','',NULL,'3.8.7',NULL,NULL,'6532408365'),('20200409180900-2','jhipster','config/liquibase/changelog/20200409180900_added_entity_constraints_Afetado.xml','2020-04-10 15:26:51',25,'EXECUTED','8:7310de9f092ddb2e07f6f616cc4055fb','addForeignKeyConstraint baseTableName=afetado, constraintName=fk_afetado_barragem_id, referencedTableName=barragem','',NULL,'3.8.7',NULL,NULL,'6532408365');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento_medicao`
--

DROP TABLE IF EXISTS `evento_medicao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento_medicao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `identificador` varchar(36) NOT NULL,
  `sensor_identificador` varchar(36) NOT NULL,
  `tipo` varchar(255) NOT NULL,
  `data` datetime NOT NULL,
  `intensidade` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_evento_medicao_identificador` (`identificador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento_medicao`
--

LOCK TABLES `evento_medicao` WRITE;
/*!40000 ALTER TABLE `evento_medicao` DISABLE KEYS */;
/*!40000 ALTER TABLE `evento_medicao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento_medicao_classificacao_alerta`
--

DROP TABLE IF EXISTS `evento_medicao_classificacao_alerta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento_medicao_classificacao_alerta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) NOT NULL,
  `intensidade` int NOT NULL,
  `disparar_alertas` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento_medicao_classificacao_alerta`
--

LOCK TABLES `evento_medicao_classificacao_alerta` WRITE;
/*!40000 ALTER TABLE `evento_medicao_classificacao_alerta` DISABLE KEYS */;
INSERT INTO `evento_medicao_classificacao_alerta` VALUES (1,'GENERICO',40,_binary ''),(2,'PRESSAO',40,_binary ''),(3,'TREMORES',40,_binary ''),(4,'TREMORES',60,_binary ''),(5,'NIVEL_ARMAZENAMENTO',40,_binary ''),(6,'NIVEL_ARMAZENAMENTO',60,_binary ''),(7,'NIVEL_ARMAZENAMENTO',80,_binary ''),(8,'NIVEL_ARMAZENAMENTO',100,_binary ''),(9,'NIVEL_ARMAZENAMENTO',150,_binary ''),(10,'NIVEL_ARMAZENAMENTO',90,_binary '\0');
/*!40000 ALTER TABLE `evento_medicao_classificacao_alerta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidente`
--

DROP TABLE IF EXISTS `incidente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `identificador` varchar(36) NOT NULL,
  `data` datetime NOT NULL,
  `classificacao` int NOT NULL,
  `barragem_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_incidente_barragem_id` (`barragem_id`),
  CONSTRAINT `fk_incidente_barragem_id` FOREIGN KEY (`barragem_id`) REFERENCES `barragem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidente`
--

LOCK TABLES `incidente` WRITE;
/*!40000 ALTER TABLE `incidente` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidente_resultado_processamento`
--

DROP TABLE IF EXISTS `incidente_resultado_processamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidente_resultado_processamento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `incidente_identificador` varchar(36) NOT NULL,
  `incidente_classificacao` int NOT NULL,
  `sucesso` bit(1) NOT NULL,
  `data` datetime NOT NULL,
  `mensagem` varchar(10000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidente_resultado_processamento`
--

LOCK TABLES `incidente_resultado_processamento` WRITE;
/*!40000 ALTER TABLE `incidente_resultado_processamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidente_resultado_processamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_persistent_audit_event`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint NOT NULL AUTO_INCREMENT,
  `principal` varchar(50) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_persistent_audit_event`
--

LOCK TABLES `jhi_persistent_audit_event` WRITE;
/*!40000 ALTER TABLE `jhi_persistent_audit_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `jhi_persistent_audit_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_persistent_audit_evt_data`
--

DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`),
  CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_persistent_audit_evt_data`
--

LOCK TABLES `jhi_persistent_audit_evt_data` WRITE;
/*!40000 ALTER TABLE `jhi_persistent_audit_evt_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `jhi_persistent_audit_evt_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor`
--

DROP TABLE IF EXISTS `sensor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `identificador` varchar(36) NOT NULL,
  `nome` varchar(60) NOT NULL,
  `tipo` varchar(255) NOT NULL,
  `observacoes` varchar(255) DEFAULT NULL,
  `barragem_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_sensor_identificador` (`identificador`),
  KEY `fk_sensor_barragem_id` (`barragem_id`),
  CONSTRAINT `fk_sensor_barragem_id` FOREIGN KEY (`barragem_id`) REFERENCES `barragem` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` VALUES (1,'bec517b7-ee25-4466-8bd9-f9462cf879f0','Sensor Analógicodo Nível de Pressão 01','PRESSAO','Sensor Analógicodo Nível de Pressão 01 - Alimentação Passivo (Piezômetro)',1),(2,'e6c9cb82-99f9-4bea-9c73-b7c1a98c9b6c','Sensor Digital do Nível de Armazenamento Rejeitos 01','NIVEL_ARMAZENAMENTO','Sensor Digital do Nível de Armazenamento Rejeitos 01 - Alimentação Ativo (Piezômetro elétrico)',2),(3,'020deb12-6c33-4198-936b-25c6431f0703','Sensor Digital de tremores/Entorno Barragem 01','TREMORES','Sensor Digital de tremores/Entorno Barragem 01 - Alimentação Ativo (Sonda inclinométrica)',3),(4,'d8318bbc-19a1-428f-9fba-c6cdd70c52b7','Sensor Analógico de tremores/Entorno Barragem 02','TREMORES','Sensor Analógico de tremores/Entorno Barragem 02 - Alimentação Passivo (Sonda inclinométrica)',4),(5,'575048d1-ec28-4c66-90b8-fd7f3709ce0b','Sensor Analógico de medição Genérico 01','GENERICO','Sensor Analógico de medição Genérico 01 - Alimentação Ativo (Piezômetro pneumático)',5),(6,'241d60e0-1bc6-4dbd-a462-a9c2aed7dbf0','Sensor Digital do Nível de Armazenamento Rejeitos 02','NIVEL_ARMAZENAMENTO','Sensor Digital do Nível de Armazenamento Rejeitos 02 - Alimentação Ativo ((Piezômetro elétrico))',6),(7,'f3ba7b02-85c5-4963-8e53-fa7f488a53c6','Sensor Digital de medição Genérico 02','GENERICO','Sensor Digital de medição Genérico 02 - Alimentação Ativo (Célula de pressão)',7),(8,'c5e087db-de23-4d76-84cf-bfb15bfce608','Sensor Digital de medição Genérico 03','GENERICO','Sensor Digital de medição Genérico 03 - Alimentação Ativo (Medidor de vazão)',8),(9,'4eb7e55b-b2a1-41fd-8e5d-4b6d85c0f210','Sensor Analógico de medição Genérico 04','GENERICO','Sensor Analógico de medição Genérico 04 - Alimentação Passivo (Marco de referência com teodolito)',9),(10,'8dd03afc-7622-4a4c-9832-a221e310f390','Sensor Digital de tremores/Entorno Barragem 03','TREMORES','Sensor Digital de tremores/Entorno Barragem 03 - Alimentação Passivo (Marco topográfico)',10);
/*!40000 ALTER TABLE `sensor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'tccscapucminasbackendapimonitoramentobarragens'
--

--
-- Dumping routines for database 'tccscapucminasbackendapimonitoramentobarragens'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-10 11:29:29
