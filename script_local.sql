create database telintelTestBD; 
use telintelTestBD;

CREATE USER 'telintel'@'localhost' IDENTIFIED BY 'tel intel .NET';

GRANT ALL PRIVILEGES ON 'telintelTestBD' . * TO 'telintel'@'localhost';


CREATE TABLE IF NOT EXISTS `product_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`));

 

CREATE TABLE IF NOT EXISTS `product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `unit_price` DECIMAL(10,3) NOT NULL,
  `product_type_id` INT(11) NOT NULL,
    PRIMARY KEY (`id`), 
    FOREIGN KEY (`product_type_id`)
    REFERENCES `product_type` (`id`));
    

CREATE TABLE IF NOT EXISTS `processFile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nameThread` VARCHAR(255) NOT NULL,
  `startDate` timestamp NULL,
  `endDate` timestamp  NULL,
  `status` VARCHAR(255) NOT NULL,
  `totalProducts` INT  NULL, 
    PRIMARY KEY (`id`)
  );
    


INSERT INTO `product_type` (`id`, `name`) VALUES (1, 'Food');
INSERT INTO `product_type` (`id`, `name`) VALUES (2, 'Technology');
INSERT INTO `product_type` (`id`, `name`) VALUES (3, 'Forniture');


