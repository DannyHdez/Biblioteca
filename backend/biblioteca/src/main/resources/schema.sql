-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE biblioteca;

-- Tabla de géneros
CREATE TABLE IF NOT EXISTS genres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Tabla de autores
CREATE TABLE IF NOT EXISTS authors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surnames VARCHAR(255) NOT NULL,
    date_of_birth DATE DEFAULT NULL,
    nationality VARCHAR(255) DEFAULT NULL,
    biography TEXT DEFAULT NULL
);

-- Tabla de editoriales
CREATE TABLE IF NOT EXISTS editorials (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    country VARCHAR(255) DEFAULT NULL,
    founded_year YEAR DEFAULT NULL,
    logo VARCHAR(255) DEFAULT NULL
);

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surnames VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_picture_url VARCHAR(255) DEFAULT NULL,
    registration_date DATE DEFAULT CURDATE(),
    deactivation_date DATE DEFAULT NULL,
    user_type VARCHAR(255) NOT NULL,
    account_disabled BOOLEAN DEFAULT FALSE
);

-- Tabla de libros
CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    title_es VARCHAR(255) NOT NULL,
    title_en VARCHAR(255) DEFAULT NULL,
    title_ca VARCHAR(255) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    language VARCHAR(10) DEFAULT 'ES',
    page_count INT DEFAULT NULL,
    cover_url VARCHAR(255) DEFAULT NULL,
    publication_date DATE DEFAULT NULL,
    editorial_id INT DEFAULT NULL,
    user_id INT NOT NULL,
    INDEX (editorial_id),
    INDEX (user_id),
    FOREIGN KEY (editorial_id) REFERENCES editorials (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Tabla de relación libro-autor
CREATE TABLE IF NOT EXISTS book_author (
    book_id INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    INDEX (book_id),
    INDEX (author_id),
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (author_id) REFERENCES authors (id)
);

-- Tabla de relación libro-género
CREATE TABLE IF NOT EXISTS book_genre (
    book_id INT NOT NULL,
    genre_id INT NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    INDEX (book_id),
    INDEX (genre_id),
    FOREIGN KEY (book_id) REFERENCES books (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);
