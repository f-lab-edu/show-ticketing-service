
CREATE TABLE `user` (
  `id` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `name` varchar(15) NOT NULL,
  `phoneNum` varchar(13) NOT NULL,
  `email` varchar(40) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
