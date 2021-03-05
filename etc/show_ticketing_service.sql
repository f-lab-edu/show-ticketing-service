
CREATE TABLE `user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `userId` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `name` varchar(15) NOT NULL,
  `phoneNum` varchar(13) NOT NULL,
  `email` varchar(40) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `userType`  INT(3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;