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
-- Table `kino_arena`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`cities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `postcode` VARCHAR(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `city_id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
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
  `birth_date` DATE NOT NULL,
  `city_id` INT NOT NULL,
  `gender` VARCHAR(10) NOT NULL,
  `phone_number` VARCHAR(14) NOT NULL,
  `role_name` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `city_idx` (`city_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `city`
    FOREIGN KEY (`city_id`)
    REFERENCES `kino_arena`.`cities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
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
    REFERENCES `kino_arena`.`cities` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kino_arena`.`halls`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`halls` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cinema_id` INT NOT NULL,
  `type_id` INT NOT NULL,
  `rows` INT NOT NULL,
  `columns` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_halls_cinemas1_idx` (`cinema_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_halls_cinemas1`
    FOREIGN KEY (`cinema_id`)
    REFERENCES `kino_arena`.`cinemas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kino_arena`.`categories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(3) NOT NULL,
  `min_age` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kino_arena`.`genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`genres` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `genre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kino_arena`.`movies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`movies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` VARCHAR(1500) NOT NULL,
  `duration` INT NOT NULL,
  `premiere` DATE NOT NULL,
  `director` VARCHAR(45) NULL,
  `cast` VARCHAR(1500) NULL,
  `category_id` INT NOT NULL,
  `genre_id` INT NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_movies_categories1_idx` (`category_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  INDEX `fk_movies_genres1_idx` (`genre_id` ASC) VISIBLE,
  CONSTRAINT `fk_movies_categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `kino_arena`.`categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_movies_genres1`
    FOREIGN KEY (`genre_id`)
    REFERENCES `kino_arena`.`genres` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kino_arena`.`projections`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`projections` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `start_time` TIME NOT NULL,
  `date` DATE NOT NULL,
  `hall_id` INT NOT NULL,
  `movie_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_projections_halls1_idx` (`hall_id` ASC) VISIBLE,
  INDEX `fk_projections_movies1_idx` (`movie_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_projections_halls1`
    FOREIGN KEY (`hall_id`)
    REFERENCES `kino_arena`.`halls` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_projections_movies1`
    FOREIGN KEY (`movie_id`)
    REFERENCES `kino_arena`.`movies` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `kino_arena`.`tickets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kino_arena`.`tickets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `is_regular` TINYINT NOT NULL DEFAULT 1,
  `projection_id` INT NOT NULL,
  `row_number` INT NOT NULL,
  `col_number` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `discount` DOUBLE NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `projection_id_idx` (`projection_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `kino_arena`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `projection_id`
    FOREIGN KEY (`projection_id`)
    REFERENCES `kino_arena`.`projections` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


ALTER TABLE `kino_arena`.`halls` 
CHANGE COLUMN `rows` `hall_rows` INT NOT NULL ,
CHANGE COLUMN `columns` `hall_columns` INT NOT NULL ;

ALTER TABLE `kino_arena`.`users` 
ADD COLUMN `profile_image_url` VARCHAR(300) NULL AFTER `role_name`;