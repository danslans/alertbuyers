# Proyecto AlertBuyers

## Descripción

El proyecto AlertBuyers es una API encargada de notificar a los compradores sobre el retraso en la entrega de sus pedidos por el estado del clima. A continuación se detallan los escenarios principales y alternativos del funcionamiento del sistema.

## NOTA:
Tener en cuenta que esta api tiene conexiones reales a plataformas de envio de correos Mailtrap y a la API del clima api.weatherapi.com en caso de querer probar algun escenario, como ver las notificaciones, de ante mano el API de clima debio haber
notificado el estado del clima con los codigo que estan en la prueba (1186, 1189, 1192, 1195).

Es posible cambiar o agregar mas codigos para la prueba. en el archivo: 
```
alertbuyers\src\main\resources\data.sql
```


## Escenario Principal

### Notificación de Retraso de Entrega

**Pre-condiciones:** Los datos del comprador no deben ser nulos o vacíos.

1. **Solicitud de Notificación:** El actor Meli solicita alertar retraso al comprador.
2. **Validación del Clima:** El sistema valida el estado del clima en la página weatherapi.
3. **Identificación del Código de Notificación:** El sistema identifica el código de notificación.
4. **Envío de Notificación:** El sistema decide si enviar o no la notificación al comprador.
5. **Respuesta del Sistema:** El sistema retorna una respuesta con el estado del clima para la ubicación, la descripción del pronóstico y si se envía o no la notificación.

## Escenario Alternativo

2. **API de Clima No Disponible:**
    1. La API para la consulta del estado del clima está caída o no existe.
    2. El sistema arroja un mensaje de error indicando que no es posible realizar la operación.
   


4. **API de Notificación No Disponible:**
    1. La API de notificación está caída o no existe.
    2. El sistema muestra la respuesta correctamente, pero con el campo de envío de notificación en `false`.

----------------------------------------------------------------
## Endpoints
Puedes ejecutar los endpoint con el archivo. 
```
/alertbuyers/postman_collection.json
```


### `POST /alertBuyer`
`https://alertbuyers-e6f2014622ac.herokuapp.com/alertBuyer`
`http://localhost:8080/alertBuyer`

**Descripción:** Envía una alerta de retraso al comprador.

**Request Body:**

```json
{
   "email": "email@gmail.com",
   "location": {
      "latitude": "6.1543519",
      "longitude": "-75.6076758"
   }
}
```
**Campos de la Solicitud**

| Campo     | Tipo     | Descripción                                      | Obligatorio  |
|-----------|----------|--------------------------------------------------|--------------|
| `email`   | `string` | Dirección de correo electrónico del comprador.   | true         |
| `location`| `object` | Objeto que contiene la ubicación del comprador.  | true         |
| `latitude`| `string` | Latitud de la ubicación.                         | true         | 
| `longitude`| `string` | Longitud de la ubicación.                       | true         |



**Response:**
```json
{
   "message": "",
   "forecast_code": 1063,
   "forecast_description": "Lluvia  moderada a intervalos",
   "buyer_notification": false
}
```

**Campos de la Respuesta**

| Campo                 | Tipo      | Descripción                                                                          |
|-----------------------|-----------|--------------------------------------------------------------------------------------|
| `message`             | `string`  | Mensaje informativo sobre la operación.  En caso que haya fallado en la notificacion |
| `forecast_code`       | `integer` | Código del pronóstico del clima.                                                     |
| `forecast_description`| `string`  | Descripción del pronóstico del clima.                                                |
| `buyer_notification`  | `boolean` | Indica si la notificación al comprador fue enviada (`true`) o no (`false`).          |


**Código de Respuesta:**

200 OK: Procesado correctamente.
400 Bad Request: Datos del comprador nulos o vacíos.
500 Internal Server Error: Error interno en el servidor.

### `GET /notifications`
`https://alertbuyers-e6f2014622ac.herokuapp.com/notifications`
`http://localhost:8080/notifications`

**Descripción:**  Obtiene las notificaciones enviadas a un comprador basado en su email.

**Request Param:**

`email`: Email del comprador (string). (no puede ser vacio)

**Response:** Lista de los envios para el Email
```json
[
    {
        "date": "2024-07-14T11:56:22.579737",
        "idEmail": "0053e280-4202-11ef-0040-f16f3d897ba2",
        "location": "6.1543519,-75.6076758",
        "weatherForecast": "Lluvia  moderada a intervalos"
    }
]
```

**Campos de la Respuesta**

| Campo            | Tipo      | Descripción                                                    |
|------------------|-----------|----------------------------------------------------------------|
| `date`           | `string`  | Fecha y hora de la notificación (formato ISO 8601).            |
| `idEmail`        | `string`  | Identificador único de la notificación por correo electrónico. |
| `location`       | `string`  | Coordenadas de la ubicación en formato "latitud,longitud".     |
| `weatherForecast`| `string`  | Descripción del pronóstico del clima.                          |


**Código de Respuesta:**

200 OK: Procesado correctamente.
400 Bad Request: Parámetro de email no proporcionado.
500 Internal Server Error: Error interno en el servidor.

------------------------------------------------------------------
## Tabla de errores:

| Código de Error | Descripción del Error                           | Clase                      |
|-----------------|-------------------------------------------------|----------------------------|
| 001             | Error with the API                              | WeatherException           |
| 002             | Weather not found                               | WeatherException           |
| 003             | Error sending email using MailtrapSender        | NotificationEmailException |




## Ejecución del Proyecto

### Caracteristicas

- Java 17
- Spring Boot
- Gradle
- Base de datos H2
- Arquitectura: Layered Architecture
- patrones de diseño sobresalientes: Strategy, adapters


### Comandos para Levantar el Proyecto Localmente

1. **Clonar el Repositorio:**
   ```bash
   git clone https://github.com/usuario/alertbuyers.git
   cd alertbuyers
   ```
   
2. **Compilar el Proyecto:**
   ```bash
   ./gradlew build
   ```
   
3. **Ejecutar el Proyecto:**
   ```bash
   ./gradlew bootRun
   ```

## Ejecutar las pruebas y generar el reporte de cobertura de código
Para ejecutar las pruebas y generar el reporte de cobertura de código:
   ```bash
    ./gradlew test jacocoTestReport
    cd alertbuyers/build/jacocoHtml/index.html
   ```
