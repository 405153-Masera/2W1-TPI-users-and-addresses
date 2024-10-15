# TPI

Este proyecto es parte del Trabajo Práctico Integrador (TPI) realizado durante el año 2024, de las asignaturas Laboratorio de Computación IV, Programación IV y Metodología de Sistemas de la carrera "Tecnicatura Universitaria en Programación" de la "Universidad Tecnológica Nacional - Facultad Regional Córdoba".

GRUPO 6: Usuarios (Microservicio de Users)

- Disponemos de una Fake Api para simular las consultas que sean necesarias para los demas grupos , Link: https://my-json-server.typicode.com/405786MoroBenjamin/users-responses

- Proponemos un diagrama que representa como se relacionan los demas microservicios con el actual "Users"

![image](https://github.com/user-attachments/assets/73004043-cb12-4b31-b8e7-c0b4d00100df)

- El anterior diagrama ofrece una representación visual de la arquitectura del sistema, enfocándose en las relaciones y dependencias entre los microservicios. Cada componente en el
diagrama corresponde a un microservicio, y se representa mediante:

![image](https://github.com/user-attachments/assets/6f69e8d1-834b-482c-8155-0735db6f3930)

-Se observa como a Users la consumen varios microservicios como Acces (Acceso) , Complaints (Denuncias), Employees (Empleados) , SuppliersInventory (Proveedores e Inventario) y OwnersPlots (Propietarios y Parcelas) , ya que estas áreas requieren consumir el microservicio para gestionar información que se proporciona , a su vez UserAdresses consume Contacts (Contactos) , esto es así ya que necesitamos saber el contacto de los usuarios de nuestro sistema pero el microservicio de contactos está manejado por otra área , lo que implica que debamos consumir ese microservicio en algún momento. Consumimos el microservicio de Contactos utilizando RestTemplate , todos los archivos relacionados se encuentran dentro de la carpeta restTemplate

![image](https://github.com/user-attachments/assets/adff768b-4d5d-4b05-ad2c-a77f665e7ba3)

-El script de la base de datos de nuestro microservicio se encuentra dentro de la carpeta /docs/db de este mismo repositorio

<img width="2080" alt="Base de datos" src="https://github.com/user-attachments/assets/90f5b93b-efde-4939-8d28-58a76b3245f3">





