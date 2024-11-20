# TPI - Trabajo Práctico Integrador

Este proyecto forma parte del Trabajo Práctico Integrador (TPI) desarrollado durante el año 2024, correspondiente a las asignaturas **Laboratorio de Computación IV**, **Programación IV** y **Metodología de Sistemas** de la carrera **Tecnicatura Universitaria en Programación** en la **Universidad Tecnológica Nacional - Facultad Regional Córdoba**.

<div align="center"> 
  <h1>👤 Gestión de Usuarios 👤</h1>
</div>

## ✍ Descripción

El microservicio de **Gestión de Usuarios** permite administrar los usuarios de la aplicación y sus roles. Brinda a los administradores la capacidad de gestionar los datos de los usuarios finales, como información personal y roles asignados. Utilizado por la matoria de los demas microservicios siendo una parte esencial en el proyecto.

Este sistema incluye funcionalidades para:

- Consultar la lista completa de usuarios del sistema, asignar o actualizar roles, y gestionar detalles de información personal y dar de baja usuarios.
- Consultar información necesaria para realizar dashboards o graficos en el front-end.
- Consultas personalizadas requeridas para otros grupos o para nuestro mismo grupo en microservicios como OwnersPlots.
- Generar accesos utilizando el microservicio de Accesos.
- Generar contactos de un usuario en el microservicio de Contacos.
- Generar notificación de bienvenida utilizando el microservicio de Notificaciones.
- Generar recuperación de contraseña por medio de un email utilizando el microservicio de Notificaciones.
- Proteger información de login y generar token utilizando JWT.


### Funcionalidades principales:

1. **Alta de usuarios**

2. **Consulta de usuarios:** 

3. **Actualización de información:** 

4. **Filtrado avanzado:**
   
5. **Baja logica de usuarios:**+ 
   

## 📥 Instalación

### Requisitos previos

- Java 17
- Maven
- Docker desktop
- Spring Boot
- Eureka Server

### Pasos de instalación

1. Clonar este repositorio en tu máquina local.
2. Configurar las propiedades necesarias en el archivo `application.properties` o `application.yml`.
3. Ejecutar el comando `mvn spring-boot:run` para iniciar la aplicación.

### Opcional: Docker

1. Abrir docker desktop.
2. Clonar el repositorio en tu máquina local.
3. Abrir una consola en la dirección donde se encuentra el docker-compose.yml
4. Ejecutar el comando `docker compose -up --build` para realizar el buildeo del contenedor.

---

## 🌐 Endpoints Principales

### **Usuarios**

- **GET** `/users/getall`  
  Retorna una lista de todos los usuarios activos registrados en el sistema.

- **GET** `/usersgetById/{userId}`  
  Permite consultar la información detallada de un usuario específico.

- **POST** `/users/post`  
  Crea un nuevo usuario en el sistema.

- **PUT** `users/put/{userId}`  
  Actualiza la información de un usuario existente, como nombre, teléfono o roles.

- **DELETE** `/delete/{userId}/{userIdUpdate}`  
  Elimina un usuario del sistema.

---

### **Roles**

- **GET** `/roles`  
  Retorna una lista de todos los roles disponibles en el sistema.

- **POST** `/roles`  
  Pemrite el alta de un nuevo rol.

---

## Integrantes

<div align="center">

| Nombre                      |
| --------------------------- |
| Bertello Valentino          |
| Cifuentes Pilar             |
| Lara, Ulises                |
| Lopez, Camila Antonella     |
| Moro, Benjamín              |
| Ruiz, Facundo Nicolás       |
|  Sánchez, Juan Manuel       |

</div>
