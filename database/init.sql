CREATE DATABASE IF NOT EXISTS ToDo;
USE ToDo;

CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE tasks (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       end_date DATE,
                       status VARCHAR(50),
                       attributepath VARCHAR(255)
);