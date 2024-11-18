-- Datos iniciales para géneros
INSERT IGNORE INTO genres (id, name) VALUES
    (1, 'Ciencia Ficción'),
    (2, 'Fantasía'),
    (3, 'Romance'),
    (4, 'Terror'),
    (5, 'Misterio'),
    (6, 'Thriller'),
    (7, 'Biografía'),
    (8, 'Historia'),
    (9, 'Ciencia'),
    (10, 'Autoayuda'),
    (11, 'Literatura Clásica'),
    (12, 'Infantil'),
    (13, 'Juvenil'),
    (14, 'Poesía'),
    (15, 'Ensayo'),
    (16, 'Filosofía'),
    (17, 'Religión'),
    (18, 'Arte'),
    (19, 'Teatro'),
    (20, 'Aventuras');

    -- Insertar autores
    INSERT INTO authors (name, surnames, date_of_birth, nationality, biography) VALUES
    ('Juan', 'Pérez', '1975-06-15', 'Estadounidense', 'Un reconocido autor de novelas de misterio.'),
    ('María', 'García', '1980-03-22', 'Británica', 'Una prolífica escritora de ciencia ficción.'),
    ('Carlos', 'González', '1990-01-10', 'Mexicano', 'Conocido por sus cautivadoras novelas históricas.'),
    ('Sofía', 'López', '1985-09-17', 'Francesa', 'Escribe entretenidas comedias románticas.'),
    ('Hiroshi', 'Tanaka', '1978-11-30', 'Japonés', 'Experto en literatura sobre tecnología e inteligencia artificial.');

    -- Insertar editoriales
    INSERT INTO editorials (name, country, founded_year, logo) VALUES
    ('Editorial Planeta', 'España', 1949, NULL),
    ('Alfaguara', 'España', 1964, NULL),
    ('Seix Barral', 'España', 1911, NULL),
    ('Anagrama', 'España', 1969, NULL),
    ('Santillana', 'España', 1960, NULL);

    -- Insertar usuarios
    INSERT INTO users (name, surnames, email, username, password, profile_picture_url, user_type, account_disabled) VALUES
    ('Alicia', 'Martínez', 'alicia.martinez@ejemplo.com', 'alicia123', 'contrasena1', NULL, 'ADMIN', FALSE),
    ('Roberto', 'Fernández', 'roberto.fernandez@ejemplo.com', 'robertoF', 'contrasena2', NULL, 'USER', FALSE),
    ('Carlos', 'Ruiz', 'carlos.ruiz@ejemplo.com', 'carlosR', 'contrasena3', NULL, 'USER', FALSE),
    ('Diana', 'Blanco', 'diana.blanco@ejemplo.com', 'dianab', 'contrasena4', NULL, 'USER', FALSE),
    ('Eva', 'Negro', 'eva.negro@ejemplo.com', 'evaN', 'contrasena5', NULL, 'USER', TRUE);

    -- Insertar libros
    INSERT INTO books (isbn, title_es, title_en, title_ca, description, language, page_count, cover_url, publication_date, editorial_id, user_id) VALUES
    ('978-3-16-148410-0', 'El Misterio', NULL, NULL, 'Una intrigante novela de misterio.', 'ES', 320, NULL, '2020-05-15', 1, 1),
    ('978-1-4028-9462-6', 'La Ciencia Ficción', NULL, NULL, 'Explora el futuro de la humanidad.', 'ES', 250, NULL, '2019-03-22', 2, 2),
    ('978-0-19-852663-6', 'El Pasado', NULL, NULL, 'Aventuras históricas en tiempos antiguos.', 'ES', 400, NULL, '2018-07-10', 3, 3),
    ('978-4-06-521410-6', 'La Fantasía', NULL, NULL, 'Un mundo lleno de magia y maravillas.', 'ES', 370, NULL, '2021-11-17', 4, 4),
    ('978-0-330-40034-8', 'La Biografía', NULL, NULL, 'La inspiradora historia de una vida.', 'ES', 280, NULL, '2020-09-30', 5, 5);

    -- Relación libro-autor
    INSERT INTO book_author (book_id, author_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

    -- Relación libro-género
    INSERT INTO book_genre (book_id, genre_id) VALUES
    (1, 1),
    (2, 3),
    (3, 2),
    (4, 4),
    (5, 5);