# Biblioteca - Sistema de Gestión de Libros

Un sistema para gestionar libros, autores, géneros y usuarios.

## 🚀 Características
- Gestión de libros, autores, géneros, editoriales y usuarios.
- Relación entre libros y autores, géneros y editoriales.
- Autenticación y manejo de usuarios.

## 🛠️ Requisitos Previos

- **Java**: Versión 17 o superior.
- **Maven**: Para gestionar dependencias.
- **MySQL**: Para la base de datos.

## ⚙️ Configuración

1. Clona el repositorio:
   ```bash
   git clone https://github.com/DannyHdez/Biblioteca.git
   cd biblioteca

2. Inserta datos de ejemplo con el archivo data.sql:
    ```bash
   mysql -u usuario -p < src/main/resources/data.sql

3. Configura las credenciales de la base de datos en el archivo application.properties:
    ```properties
   spring.datasource.url=jdbc:mysql://localhost:8080/biblioteca
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   
4. Ejecuta la aplicación:
    ```bash
   mvn spring-boot:run