-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema kino_arena
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema kino_arena
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kino_arena` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `kino_arena` ;

-- -----------------------------------------------------
-- Table `kino_arena`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(3) NOT NULL,
  `min_age` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kino_arena`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`cities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `postcode` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `city_id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kino_arena`.`cinemas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`cinemas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `city_id` INT NOT NULL,
  `phone_number` VARCHAR(14) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cinema_id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `city_id_idx` (`city_id` ASC) VISIBLE,
  CONSTRAINT `city_id`
    FOREIGN KEY (`city_id`)
    REFERENCES `kino_arena`.`cities` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kino_arena`.`genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`genres` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `genre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kino_arena`.`halls`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`halls` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cinema_id` INT NOT NULL,
  `type_id` INT NOT NULL,
  `hall_rows` INT NOT NULL,
  `hall_columns` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_halls_cinemas1_idx` (`cinema_id` ASC) VISIBLE,
  CONSTRAINT `fk_halls_cinemas1`
    FOREIGN KEY (`cinema_id`)
    REFERENCES `kino_arena`.`cinemas` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kino_arena`.`movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`movies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` VARCHAR(1500) NOT NULL,
  `duration` INT NOT NULL,
  `premiere` DATE NOT NULL,
  `director` VARCHAR(45) NULL DEFAULT NULL,
  `cast` VARCHAR(1500) NULL DEFAULT NULL,
  `category_id` INT NOT NULL,
  `genre_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_movies_categories1_idx` (`category_id` ASC) VISIBLE,
  INDEX `fk_movies_genres1_idx` (`genre_id` ASC) VISIBLE,
  CONSTRAINT `fk_movies_categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `kino_arena`.`categories` (`id`),
  CONSTRAINT `fk_movies_genres1`
    FOREIGN KEY (`genre_id`)
    REFERENCES `kino_arena`.`genres` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kino_arena`.`projections`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`projections` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start_time` TIME NOT NULL,
  `date` DATE NOT NULL,
  `hall_id` INT NOT NULL,
  `movie_id` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_projections_halls1_idx` (`hall_id` ASC) VISIBLE,
  INDEX `fk_projections_movies1_idx` (`movie_id` ASC) VISIBLE,
  CONSTRAINT `fk_projections_halls1`
    FOREIGN KEY (`hall_id`)
    REFERENCES `kino_arena`.`halls` (`id`),
  CONSTRAINT `fk_projections_movies1`
    FOREIGN KEY (`movie_id`)
    REFERENCES `kino_arena`.`movies` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kino_arena`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `birth_date` DATETIME NOT NULL,
  `city_id` INT NOT NULL,
  `gender` VARCHAR(10) NOT NULL,
  `phone_number` VARCHAR(14) NOT NULL,
  `role_name` VARCHAR(15) NOT NULL,
  `profile_image_url` VARCHAR(300) NULL DEFAULT NULL,
  `confirmatron_token` VARCHAR(300) NULL DEFAULT NULL,
  `enable` TINYINT NULL DEFAULT '0',
  `date_time_registration` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `city_idx` (`city_id` ASC) VISIBLE,
  CONSTRAINT `city`
    FOREIGN KEY (`city_id`)
    REFERENCES `kino_arena`.`cities` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 69
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `kino_arena`.`tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`tickets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `projection_id` INT NOT NULL,
  `r_number` INT NOT NULL,
  `c_number` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `projection_id_idx` (`projection_id` ASC) VISIBLE,
  CONSTRAINT `projection_id`
    FOREIGN KEY (`projection_id`)
    REFERENCES `kino_arena`.`projections` (`id`),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `kino_arena`.`users` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
