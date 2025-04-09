-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: t1_ps
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `plant`
--

DROP TABLE IF EXISTS `plant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plant` (
  `idplant` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `specie` varchar(45) NOT NULL,
  `carnivorous` tinyint NOT NULL,
  `img` longtext,
  PRIMARY KEY (`idplant`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plant`
--

LOCK TABLES `plant` WRITE;
/*!40000 ALTER TABLE `plant` DISABLE KEYS */;
INSERT INTO `plant` VALUES (2,'Brad','TREE','S1',0,'https://magazinuldebrazi.ro/wp-content/uploads/2023/10/BSE-2-scaled-1-600x1284.jpg'),(4,'Cin alb','FLOWERING','S2',0,'https://bulbshop.ro/4576-large_default/crini-blue-pink.jpg'),(6,'Mar','FRUIT_BEARING','S1',0,'https://www.zdravan.ro/sites/default/files/images/products/2014/07/pom-idared.jpg'),(11,'Pinguicula','CARNIVOROUS','S3',1,'https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Pinguicula_moranensis.jpg/1200px-Pinguicula_moranensis.jpg'),(12,'Dionaea','CARNIVOROUS','S3',1,'https://www.flowertime.ro/images/products/1687958288dionea-carnivora.jpg'),(13,'Trandafir','FLOWERING','S2',0,'https://www.sieberz.ro/media/image/9d/5e/39/ZoLwomjww56Foc_600x600.jpg'),(14,'Portocal','FRUIT_BEARING','S1',0,'https://static4.libertatea.ro/wp-content/uploads/2021/04/portocale-sevilla-profimedia.jpg'),(15,'Ghiocel','FLOWERING','S1',0,'https://upload.wikimedia.org/wikipedia/commons/b/b8/Poculiform_snowdrop_%28Galanthus_plicatus%29.jpg'),(16,'Mac rosu','FLOWERING','S3',0,'https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Papaver_rhoeas_LC0050.jpg/1200px-Papaver_rhoeas_LC0050.jpg');
/*!40000 ALTER TABLE `plant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-25  8:11:52
