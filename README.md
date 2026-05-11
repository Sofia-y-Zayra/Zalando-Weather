#Zalando Weather Staylist

Integrantes: Sofia Godoy y Zayra Peña.

### Propuesta de valor 
**Zalando Weather Stylist** es una herramienta inteligente que resuelve el dilema diario de "¿Qué me pongo?". 
Al integrar datos meteorológicos en tiempo real (OpenWeather) con el catálogo de moda de Zalando, la aplicación ofrece recomendaciones personalizadas de outfits basadas en la temperatura y el estado del cielo de tu ciudad actual.

- **Eficiencia**: Ahorra tiempo al usuario ofreciendole un outfit adecuado al clima de la ciudad en la que se encuentre evitando así que este tenga que pensar en ello.
- **Precisión**: Diferencia entre hombre y mujer para ofrecer resultados relevantes.
- **Interfaz**: Selección de ciudades cerrado (19 ciudadades) para evitar que el usuario busque una ciudad de la cuál no disponemos y así evitar errores de búsqueda.

## Arquitectura del Sistema

El proyecto sigue una estructura basada en *microservicios/módulos* para garantizar la escalabilidad y limpieza del código:

1. **Feeders**: "ZalandoScaper" : extrae información de productos, precios e imágenes de la web de Zalando, "WeatherApiConsumer": obtiene datos climáticos de OpenWeatherMap API.
2. **Event-Store-Builder**: Procesa los datos y los almacena en el  "Event Store" (ficheros .events) y posteriormente en el "Data Mart" (SQLite) para su consulta rápida.
3. **Business-Unit**:
   Api rest construida con Javalin.
   Interfaz web dinámica que consume el DataMart y muestra las recomendaciones.

## Tecnologías y Patrones

- **Lenguaje:** Java 17+
- **Framework Web:** Javalin (Ligero y eficiente para APIs REST).
- **Base de Datos:** SQLite (Datamart optimizado).
- **APIs:** OpenWeatherMap API.
- **Patrones de Diseño:**
    - **Repository Pattern:** Para gestionar el acceso a los datos climáticos y de productos.
    - **Dependency Injection:** Para inicializar los servicios de recomendación.
    - **MVC (Model-View-Controller):** Separación clara entre la lógica de negocio y la interfaz HTML.


## Instrucciones de Ejecución

### Requisitos Previos
- Tener instalado **Java JDK 17** o superior.
- **Maven** para la gestión de dependencias.
- Una conexión a internet activa para la carga de imágenes (Base64/URLs).

### Pasos para arrancar el proyecto:
1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/Sofia-y-Zayra/Zalando-Weather.git](https://github.com/Sofia-y-Zayra/Zalando-Weather.git)
    ```
2.  **Ejecutar los Feeders:** Ejecuta la clase `Main` del módulo de ingesta para poblar la base de datos.
3.  **Lanzar la Aplicación:** Ejecuta la clase `Main` dentro del módulo `RecommendationAPI`.
4.  **Acceso Web:** Abre tu navegador y ve a:
    ```
    http://localhost:8080/recommend
    ```



##  Ciudades Disponibles
La aplicación está configurada para funcionar en las siguientes 19 ciudades:
*Madrid, Barcelona, Valencia, Sevilla, Bilbao, Zaragoza, Malaga, Murcia, Palma, Alicante, Valladolid, Vigo, Gijon, Oviedo, Granada, Cordoba, Santander, Pamplona y Salamanca.*


##  Justificación del Datamart
Se ha elegido **SQLite** como motor de base de datos para el Datamart debido a su portabilidad y rapidez para aplicaciones de escritorio/ciencia de datos. La estructura de tablas permite cruzar instantáneamente la temperatura actual con los rangos de idoneidad de las prendas de Zalando.
