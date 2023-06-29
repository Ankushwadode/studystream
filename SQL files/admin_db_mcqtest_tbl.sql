-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: admin_db
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `mcqtest_tbl`
--

DROP TABLE IF EXISTS `mcqtest_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mcqtest_tbl` (
  `q_id` int NOT NULL AUTO_INCREMENT,
  `ans` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mrk` varchar(45) DEFAULT NULL,
  `op1` varchar(255) DEFAULT NULL,
  `op2` varchar(255) DEFAULT NULL,
  `op3` varchar(255) DEFAULT NULL,
  `op4` varchar(255) DEFAULT NULL,
  `op5` varchar(255) DEFAULT NULL,
  `que` varchar(500) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`q_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mcqtest_tbl`
--

LOCK TABLES `mcqtest_tbl` WRITE;
/*!40000 ALTER TABLE `mcqtest_tbl` DISABLE KEYS */;
INSERT INTO `mcqtest_tbl` VALUES (11,'A pointer must point to a data item on the heap (free store).','chandrakant@123','10','A pointer to an int and a pointer to a double are of the same size.','A pointer must point to a data item on the heap (free store).','A pointer can be reassigned to point to another data item.','','','Which of the following statement is not true?','C Programming'),(12,'All of the above','chandrakant@123','10','Random access iterator','Bi-directional iterator','Forward iterator','All of the above','','What kinds of iterators can be used with vectors?','C Programming'),(13,'This statement is not true.','chandrakant@123','10','This statement is not true.','This statement is definately not true.','This statement could be true. ','This statement is definately true.','','Which of the following statement is not true?','C Programming');
/*!40000 ALTER TABLE `mcqtest_tbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-26 23:11:20
