-- MariaDB dump 10.19  Distrib 10.11.6-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: game
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

USE game;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `item` varchar(255) NOT NULL,
  `descr_item` varchar(255) NOT NULL,
  `scene_id` int NOT NULL,
  PRIMARY KEY (`item_id`),
  KEY `scene_id` (`scene_id`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`scene_id`) REFERENCES `scenes` (`scene_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES
(1,'dinheiro','Que é good e \'nóis\' não have...',1),
(2,'papeis','Pelo menos é uma grana boa...',1),
(3,'monza','O último carro de verdade feito pela GM.',2),
(4,'uno','Um ótimo carro, mas não para ir pro PY.',2),
(5,'slayer','Ótimo, mas pra viajar?',3),
(6,'pantera','Melhor banda de todos os tempos.',3),
(7,'radio','Instalei um RoadStar.',3),
(8,'ciudad_del_este','Aquela ponte é muito vigiada rsrs...',4),
(9,'pedro_juan_caballero','Fronteira seca... hummm tenho uma idéia!.',4),
(10,'whisky','Não foi atrás de Red Label que vc veio aqui...',5),
(11,'derby','Enche o carro de maços e maços de derby cabrón!',5),
(12,'me','51, uma boa idéia.',6),
(13,'leite','Leite das crianças.',6),
(14,'moddess','Pra quem não sabe, absorvente.',6);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scenes`
--

DROP TABLE IF EXISTS `scenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scenes` (
  `scene_id` int NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `title` varchar(255) NOT NULL,
  `target` int DEFAULT NULL,
  `right_command` varchar(255) NOT NULL,
  `inventory` varchar(255) NOT NULL,
  PRIMARY KEY (`scene_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scenes`
--

LOCK TABLES `scenes` WRITE;
/*!40000 ALTER TABLE `scenes` DISABLE KEYS */;
INSERT INTO `scenes` VALUES
(0,'help = Ajuda com os comandos do jogo.\nuse = usar um item,\nget = adicionar item ao inventário,\ncheck = verificar a descrição do item,\nsave = salvar o jogo.\n','help',1,'help',' '),
(1,'Em um dia comum de trabalho, você encontra documentos que expõem uma trama criminosa envolvendo seus colegas. Chocado, você decide não se envolver, mas sabe que está em perigo por ter descoberto demais. A tensão no escritório aumenta, e pouco depois, você é convocado para uma reunião de emergência. Lá, armam contra você, te acusando injustamente. Apesar de suas tentativas de se defender, você é demitido. O DINHEIRO do acerto está na mesa junto dos PAPEIS da demissão.','O escritório da Firma!',2,'get dinheiro',''),
(2,'Após a demissão, você passa a noite refletindo sobre o que fazer. Uma hora o dinheiro do acerto vai acabar, você decide investir em um carro usado para poder iniciar uma nova fase da sua vida. No dia seguinte, você entra na OLX e encontra dois carros que se encaixam no seu orçamento: um MONZA e um UNO. Cada carro tem suas vantagens e desvantagens, e a escolha certa pode fazer a diferença na sua jornada.','Demitido! Você foi de Gupy! E agora zé?',3,'get monza','dinheiro'),
(3,'Já no carro, cuidadosamente preparado. Você precisa se decidir de algo muito importante, a trilha sonora! Pega dois CDs para ouvir no RADIO do carro, SLAYER e PANTERA, qual ouvir nessa jornada?','Partiu Paraguay',4,'use pantera with radio','dinheiro, monza'),
(4,'Com o carro preparado, você parte rumo ao Paraguai. As estradas são longas e esburacas, mas a adrenalina e a determinação te mantém focado. Durante a viagem, você escuta Pantera no radio e se concentra em seus objetivos. Ao chegar à fronteira, você pega seu MAPA e precisa decidir seu destino, CIUDAD_DEL_ESTE ou PEDRO_JUAN_CABALLERO','As estradas no Brasil são uma m3Rd@!',5,'use pedro_juan_caballero','dinheiro, monza'),
(5,'Em Pedro Juan Caballero, você se infiltra no mercado clandestino. As opções são variadas: WHISKY, CIGARRO, e outros itens de alto valor estão disponíveis. Cada decisão que você toma aqui é crucial; qualquer erro pode arruinar sua viagem de volta ao Brasil. Com o carro carregado, você precisa escolher como proceder para garantir um lucro sem atrair atenção indesejada.','Dinheiro na mão!',6,'get derby','dinheiro, monza'),
(6,'Final da história, agora é só continuar fazendo dinheiro pra comprar o MÉ, LEITE das crianças e o MODDESS da muié.','Nunca é um final!',1,'use mé','dinheiro, monza, derby');
/*!40000 ALTER TABLE `scenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `current_scene` int DEFAULT '1',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name` (`name`),
  KEY `current_scene` (`current_scene`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`current_scene`) REFERENCES `scenes` (`scene_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES
(0,'0','***',0),
(1,'root','senha123',1),
(2,'user','user',3),
(5,'1','***',1),
(6,'2','***',2),
(7,'3','***',3),
(8,'4','***',4),
(9,'5','***',5),
(10,'6','***',6);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-08 19:35:16
