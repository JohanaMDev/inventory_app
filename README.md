# 📦 Inventory Master Pro - Backend Java
### *Arquitectura escalable para la gestión de stock y finanzas corporativas.*

Este sistema de backend es una solución integral diseñada para gestionar lógicas complejas de inventario y ventas, priorizando la integridad de los datos y una arquitectura de servicios desacoplada. Desarrollado con **Java 17** y **Spring Boot**, el proyecto aplica patrones de diseño avanzados para resolver problemas reales de negocio.

---

## 🚀 Arquitectura y Lógica de Negocio
El sistema implementa una **jerarquía de servicios** estratégica para delegar responsabilidades y validaciones de forma eficiente:  
**Venta -> Empleado -> Puesto**

* **Desacoplamiento Fiscal:** Separación estricta entre la entidad `Empleado` y su `InformacionLaboral` para una normalización óptima y gestión de costos impositivos.
* **Validaciones en Cascada:** La lógica garantiza que una operación solo se procese si el perfil cumple con los requisitos y permisos del puesto asignado.
* **Integridad Transaccional:** Uso de `@Transactional` en todas las inyecciones a la base de datos para asegurar el rollback automático ante fallos, manteniendo la consistencia del sistema.
* **Seguridad Granular:** Implementación de **Spring Security** con `@PreAuthorize` para restringir acciones sensibles según el rol del usuario (Admin/Vendedor).

## 🛠 Stack Tecnológico
* **Lenguaje:** Java 17.
* **Framework:** Spring Boot 3.x (Spring Data JPA, Spring Security).
* **Persistencia:** PostgreSQL (Gestionada con dBeaver).
* **Seguridad:** JWT (JSON Web Tokens) para autenticación y flujo de autorización.
* **Versionado de BD:** Flyway para migraciones de esquema profesionales.
* **Herramientas:** IntelliJ IDEA, Postman para validación de API.

## ⚙️ Funcionalidades Clave
* **Auth Flow Integration:** Sistema de login y registro protegido con validación de datos rigurosa.
* **User Tax Management:** Módulo dedicado para el manejo de impuestos, salarios y pagos a proveedores.
* **Manejo Global de Excepciones:** Respuestas estandarizadas en JSON que informan el origen exacto del error sin comprometer la integridad del sistema.
* **Normalización de Datos:** Diseño relacional optimizado para evitar redundancias en información laboral y fiscal.

## 📊 Modelo de Datos (ERD)
La arquitectura de la base de datos refleja un diseño orientado a la escalabilidad y el desacoplamiento de lógicas de negocio.

![Database Schema](./assets/postgres%20-%20inventory_db%20-%20public.png)


## 🛠 Instalación y Ejecución
1.  **Clonar el repositorio:** `git clone https://github.com/JohanaMDev/inventory_app.git`
2.  **Configurar Base de Datos:** Establecer credenciales en `src/main/resources/application.properties`.
3.  **Buildear:** Ejecutar `mvn clean install` para descargar dependencias y compilar.
4.  **Ejecutar:** Correr la aplicación desde el IDE o vía terminal.

---
*Desarrollado con enfoque en escalabilidad, mentalidad "Get things done" y cumpliendo con el roadmap profesional de marzo 2026.*

---

## 📈 Roadmap & Próximos Pasos (Q2 2026)
Como parte de mi plan de especialización en **Java Expert**, el proyecto se encuentra en una fase de evolución constante con los siguientes objetivos para el próximo trimestre:

1.  **Cloud Readiness:** Migración de la persistencia local a **AWS RDS** y despliegue de contenedores mediante **Docker**.
2.  **Microservicios:** Desacoplamiento del módulo de `User Tax Management` en un microservicio independiente para mejorar la escalabilidad horizontal.
3.  **Testing Avanzado:** Implementación de pruebas unitarias y de integración con **JUnit 5** y **Mockito** para alcanzar un 80% de cobertura de código.
4.  **Documentación Dinámica:** Integración de **Swagger/OpenAPI** para facilitar la integración con el frontend y equipos externos.

---
*“La disciplina es el puente entre las metas y los logros.” - Enfoque 5:00 AM para la excelencia técnica.*

---

---

> **Last Update:** 02 de marzo, 2026  
> **Estado actual:** Finalizando lógica de seguridad (Spring Security + JWT) e implementando la integración del flujo de autenticación en el Frontend.
