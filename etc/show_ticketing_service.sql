
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

CREATE TABLE venue
(
    `id`        INT UNSIGNED    NOT NULL    AUTO_INCREMENT,
    `name`      VARCHAR(45)     NOT NULL,
    `address`   VARCHAR(100)    NOT NULL,
    `tel`       VARCHAR(20)     NOT NULL,
    `homepage`  VARCHAR(100)    NULL,
    PRIMARY KEY (id)
);

CREATE INDEX venue_Index ON venue(name);

CREATE TABLE venueHall(
   id INT UNSIGNED NOT NULL AUTO_INCREMENT,
   venueId INT UNSIGNED NOT NULL,
   name VARCHAR(40) NOT NULL,
   rowSeats INT(10) NOT NULL,
   columnSeats INT(10) NOT NULL,
   seatingCapacity INT(100) NOT NULL,
   PRIMARY KEY (id),
   FOREIGN KEY (venueId) REFERENCES venue(id)
   ON DELETE CASCADE
);

CREATE TABLE performance
(
    `id`            INT UNSIGNED    NOT NULL    AUTO_INCREMENT,
    `title`         VARCHAR(50)     NOT NULL,
    `imageFilePath` VARCHAR(255)    NULL,
    `detail`        VARCHAR(500)    NULL,
    `ageLimit`      INT             NULL,
    `showType`      INT(3)          NOT NULL,
    `venueId`       INT UNSIGNED    NOT NULL,
    `hallId`        INT UNSIGNED    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (venueId) REFERENCES venue(id),
    FOREIGN KEY (hallId) REFERENCES venueHall(id)
);

CREATE INDEX performance_Index ON performance(title);

CREATE TABLE performanceTime
(
    `id`             INT UNSIGNED    NOT NULL    AUTO_INCREMENT,
    `performanceId`  INT UNSIGNED    NOT NULL,
    `hallId`         INT UNSIGNED    NOT NULL,
    `startTime`      DATETIME        NOT NULL,
    `endTime`        DATETIME        NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (performanceId) REFERENCES performance(id) ON DELETE CASCADE,
    FOREIGN KEY (hallId)        REFERENCES venueHall(id)
);

CREATE INDEX performanceTime_Index ON performanceTime(startTime);
