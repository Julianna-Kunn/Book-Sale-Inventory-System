-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: signup_schema
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `user_log`
--

DROP TABLE IF EXISTS `user_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_log` (
  `userid` varchar(255) NOT NULL,
  `user_type` varchar(255) NOT NULL,
  `date_time` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_log`
--

LOCK TABLES `user_log` WRITE;
/*!40000 ALTER TABLE `user_log` DISABLE KEYS */;
INSERT INTO `user_log` VALUES ('C-1001','Customer','03-05-2024 11:51:09 PM'),('C-1001','Customer','03-05-2024 11:51:50 PM'),('C-1001','Customer','05-05-2024 11:01:37 AM'),('C-1001','Customer','06-05-2024 07:58:20 PM'),('C-1001','Customer','06-05-2024 07:58:34 PM'),('C-1001','Customer','06-05-2024 08:01:39 PM'),('C-1001','Customer','06-05-2024 08:23:14 PM'),('C-1001','Customer','06-05-2024 08:28:39 PM'),('C-1001','Customer','06-05-2024 08:30:43 PM'),('A-1001','Admin','06-05-2024 09:58:48 PM'),('A-1001','Admin','06-05-2024 09:59:28 PM'),('A-1001','Admin','06-05-2024 10:04:18 PM'),('A-1001','Admin','06-05-2024 10:17:33 PM'),('A-1001','Admin','06-05-2024 10:18:55 PM'),('A-1001','Admin','06-05-2024 10:27:42 PM'),('A-1001','Admin','06-05-2024 10:45:57 PM'),('A-1001','Admin','06-05-2024 11:01:34 PM'),('A-1001','Admin','06-05-2024 11:41:15 PM'),('A-1001','Admin','07-05-2024 12:06:26 AM'),('A-1001','Admin','07-05-2024 12:12:59 AM'),('A-1001','Admin','07-05-2024 12:15:32 AM'),('A-1001','Admin','07-05-2024 12:16:59 AM'),('A-1001','Admin','07-05-2024 12:18:11 AM'),('A-1001','Admin','07-05-2024 12:24:28 AM'),('A-1001','Admin','07-05-2024 12:27:10 AM'),('A-1001','Admin','07-05-2024 10:24:02 AM'),('A-1001','Admin','07-05-2024 10:25:54 AM'),('A-1001','Admin','07-05-2024 10:27:21 AM'),('A-1001','Admin','07-05-2024 10:28:33 AM'),('C-1001','Customer','07-05-2024 10:33:25 AM'),('A-1001','Admin','07-05-2024 10:34:06 AM'),('A-1001','Admin','07-05-2024 10:36:25 AM'),('A-1001','Admin','07-05-2024 10:38:25 AM'),('A-1001','Admin','07-05-2024 10:41:26 AM'),('A-1001','Admin','07-05-2024 10:43:54 AM'),('A-1001','Admin','07-05-2024 10:47:45 AM'),('A-1001','Admin','07-05-2024 10:50:32 AM'),('A-1001','Admin','08-05-2024 11:41:58 AM'),('A-1001','Admin','08-05-2024 12:21:25 PM'),('A-1001','Admin','08-05-2024 12:23:19 PM'),('A-1001','Admin','08-05-2024 12:27:07 PM'),('A-1001','Admin','08-05-2024 01:51:56 PM'),('A-1001','Admin','08-05-2024 01:53:18 PM'),('A-1001','Admin','08-05-2024 01:55:06 PM'),('A-1001','Admin','08-05-2024 01:56:00 PM'),('A-1001','Admin','08-05-2024 01:57:37 PM'),('A-1001','Admin','08-05-2024 03:17:12 PM'),('A-1001','Admin','08-05-2024 09:28:10 PM'),('A-1001','Admin','08-05-2024 09:35:11 PM'),('A-1001','Admin','08-05-2024 09:42:23 PM'),('A-1001','Admin','09-05-2024 09:35:41 PM'),('A-1001','Admin','10-05-2024 07:24:11 PM'),('A-1001','Admin','10-05-2024 08:39:00 PM'),('A-1001','Admin','10-05-2024 08:39:14 PM'),('A-1001','Admin','11-05-2024 01:19:02 PM'),('A-1001','Admin','11-05-2024 01:22:03 PM'),('A-1001','Admin','11-05-2024 01:32:35 PM'),('A-1001','Admin','11-05-2024 01:34:15 PM'),('A-1001','Admin','11-05-2024 01:35:19 PM'),('A-1001','Admin','11-05-2024 01:35:50 PM'),('A-1001','Admin','13-05-2024 10:21:02 PM'),('C-1001','Customer','13-05-2024 10:22:40 PM'),('A-1001','Admin','13-05-2024 10:22:52 PM'),('A-1001','Admin','13-05-2024 10:23:46 PM'),('A-1001','Admin','14-05-2024 08:04:10 AM'),('A-1001','Admin','14-05-2024 08:05:14 AM'),('A-1001','Admin','14-05-2024 08:20:22 AM'),('A-1001','Admin','14-05-2024 08:22:30 AM'),('A-1001','Admin','14-05-2024 08:28:19 AM'),('A-1001','Admin','14-05-2024 08:30:01 AM'),('A-1001','Admin','14-05-2024 08:31:23 AM'),('A-1001','Admin','14-05-2024 08:35:59 AM'),('A-1001','Admin','14-05-2024 10:06:17 AM'),('A-1001','Admin','14-05-2024 10:07:35 AM'),('A-1001','Admin','14-05-2024 10:55:41 AM'),('A-1001','Admin','14-05-2024 11:06:26 AM'),('A-1001','Admin','14-05-2024 11:26:26 AM'),('A-1001','Admin','14-05-2024 11:28:48 AM'),('A-1001','Admin','14-05-2024 01:36:39 PM'),('A-1001','Admin','14-05-2024 02:03:56 PM'),('A-1001','Admin','14-05-2024 02:29:45 PM'),('C-3001','Customer','14-05-2024 02:54:37 PM'),('A-1','Admin','14-05-2024 02:55:12 PM'),('A-1001','Admin','14-05-2024 02:59:09 PM'),('A-1001','Admin','14-05-2024 03:08:45 PM'),('A-1001','Admin','15-05-2024 08:18:19 PM'),('A-1001','Admin','15-05-2024 08:37:14 PM'),('A-1001','Admin','15-05-2024 08:51:04 PM'),('A-1001','Admin','15-05-2024 08:54:20 PM'),('A-1001','Admin','15-05-2024 11:51:06 PM'),('A-1001','Admin','15-05-2024 11:52:11 PM'),('A-1001','Admin','16-05-2024 12:05:28 AM'),('A-0000','Admin','16-05-2024 12:06:37 AM'),('A-1001','Admin','16-05-2024 12:07:07 AM'),('A-1001','Admin','16-05-2024 12:11:23 AM'),('A-1001','Admin','16-05-2024 12:13:56 AM'),('C-3001','Customer','16-05-2024 12:14:28 AM'),('A-1001','Admin','16-05-2024 12:36:00 AM'),('A-1001','Admin','16-05-2024 07:45:03 PM'),('C-3001','Customer','16-05-2024 07:49:30 PM'),('C-3001','Customer','16-05-2024 07:54:19 PM'),('C-3001','Customer','16-05-2024 07:58:06 PM'),('C-3001','Customer','16-05-2024 08:10:41 PM'),('C-3001','Customer','16-05-2024 08:17:49 PM'),('C-3001','Customer','16-05-2024 08:19:27 PM'),('C-3001','Customer','16-05-2024 08:20:31 PM'),('A-1001','Admin','17-05-2024 01:20:32 AM'),('A-1001','Admin','17-05-2024 10:19:29 AM'),('A-1001','Admin','19-05-2024 08:57:39 PM');
/*!40000 ALTER TABLE `user_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-20 14:14:28
