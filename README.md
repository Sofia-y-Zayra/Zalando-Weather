

# Zalando Weather Stylist
# 1. Descripción del proyecto y propuesta de valor
Zalando Weather es una aplicación desarrollada en Java que genera recomendaciones de outfits personalizadas en función del clima y el género del usuario. El sistema combina información meteorológica obtenida desde la API OpenWeatherMap con productos extraídos mediante web scraping de la página web de Zalando.

El proyecto está dividido en varios módulos que se comunican mediante eventos utilizando ActiveMQ. Por un lado, se recopilan productos de ropa y datos meteorológicos; por otro, estos datos se almacenan y procesan para construir recomendaciones automáticas adaptadas a las condiciones climáticas.

La aplicación utiliza tecnologías como Selenium, SQLite, Javalin y ActiveMQ, además de seguir una arquitectura modular basada en el patrón MVC y un enfoque de Event Sourcing para gestionar y reconstruir la información del sistema.

El resultado final es una aplicación web capaz de sugerir combinaciones de ropa coherentes con el tiempo atmosférico de distintas ciudades de manera automática y dinámica.

La propuesta de valor de Zalando Weather surge de un problema cotidiano al que las personas se enfrentan cada día: decidir cómo vestirse según el clima. Muchas veces resulta difícil elegir ropa adecuada cuando las condiciones meteorológicas cambian o cuando no se sabe qué prendas combinan mejor entre sí.

El proyecto busca solucionar este problema mediante una aplicación capaz de generar automáticamente recomendaciones de outfits personalizadas utilizando información meteorológica en tiempo real y productos reales obtenidos desde Zalando. De esta forma, el usuario recibe sugerencias de ropa adaptadas tanto a la temperatura y las condiciones climáticas como al género seleccionado.

Además de aportar utilidad práctica al usuario, el sistema integra diferentes tecnologías actuales como scraping web, APIs meteorológicas, procesamiento de eventos y almacenamiento de datos, creando una solución automatizada, dinámica y escalable orientada al ámbito de la moda y la personalización digital.

# 2. Justificación de la elección de las fuentes
Se ha utilizado la API de OpenWeatherMap debido a que proporciona información meteorológica actualizada y accesible de forma sencilla mediante peticiones HTTP. Esta API permite obtener datos relevantes para el proyecto, como la temperatura o la descripción del clima de distintas ciudades en tiempo real.

Además, su integración con Java resulta relativamente simple gracias al formato JSON de las respuestas, lo que facilita el procesamiento y transformación de los datos dentro del sistema. Su uso ha permitido incorporar recomendaciones dinámicas de outfits adaptadas a las condiciones meteorológicas reales de cada usuario.

Se ha escogido Zalando como plataforma para realizar el scraping debido a que su estructura web resulta relativamente accesible y estable para la extracción de información mediante Selenium. Además, en comparación con otras plataformas de comercio electrónico, Zalando presenta menos restricciones iniciales frente a la automatización de navegación y permite acceder de forma sencilla a datos relevantes de los productos, como nombre, precio, categoría, color o imágenes.

Esto ha facilitado el desarrollo del sistema de recomendación, permitiendo trabajar con productos reales y actualizados sin necesidad de utilizar APIs privadas o sistemas más complejos de acceso a datos.

# 3. Estructura del datamart

El datamart del proyecto está implementado mediante una base de datos SQLite denominada 'datamart_final.db'. Su estructura está compuesta principalmente por dos tablas: 'weather_status', encargada de almacenar la información meteorológica de las ciudades (temperatura, descripción y fecha), y 'product_catalog', destinada a almacenar el catálogo de productos extraídos desde Zalando, incluyendo nombre, categoría, color, precio, imagen y enlace del producto.

Esta estructura permite centralizar la información necesaria para el sistema de recomendaciones, facilitando el acceso rápido a los datos utilizados para generar outfits personalizados según las condiciones climáticas.

# 4. Instrucciones para compilar/ejecutar cada modulo

> Requisitos previos

Para ejecutar correctamente el proyecto es necesario disponer de las siguientes herramientas y tecnologías instaladas:

- Java JDK 21 o superior.
- Apache Maven.
- IntelliJ IDEA (recomendado).
- ActiveMQ.
- Google Chrome.
- ChromeDriver compatible con la versión instalada de Google Chrome.
- Conexión a Internet para acceder a la API OpenWeatherMap y realizar el scraping sobre Zalando.

> Compilación del proyecto

1º Habría que descargar el proyecto desde GitHub con el siguiente comando:

git clone https://github.com/Sofia-y-Zayra/Zalando-Weather.git

2º Ejecución del sistema
- Iniciar ActiveMQ

En caso de no tenerlo podemos descargarnoslo aqui: https://activemq.apache.org/components/classic/download/

Antes de ejecutar los módulos es necesario iniciar el broker de ActiveMQ en la consola de nuestro ordenador:
cd (y la ruta de donde tengamos el ActiveMQ descargado)\apache-activemq-6.2.4\bin
activemq start

- Ejecutar los feeders

A continuación, deben ejecutarse las clases Main correspondientes a Weather_Project y Zalando.

Estos módulos se encargan de obtener información meteorológica y productos desde Zalando, publicando posteriormente los eventos generados en ActiveMQ

- Ejecutar EventStoreBuilder

Posteriormente, debe ejecutarse la clase Main del modulo EventStoreBuilder

Este módulo escucha los eventos generados y los almacena en el Event Store.  

- Ejecutar Recommendation

  Finalmente, debe ejecutarse la clase Main del modulo Recommendation.

  Este módulo reconstruye el datamart y lanza la aplicación web de recomendaciones.

3º Acceso a la aplicación

Una vez iniciado el sistema, la aplicación estará disponible desde el navegador en:               
http://localhost:8080/recommend

# 5. Ejemplos de uso

La aplicación permite generar recomendaciones de outfits mediante peticiones HTTP realizadas sobre el módulo Recommendation. El usuario debe indicar la ciudad y el género para obtener una combinación de prendas adaptada a las condiciones meteorológicas.

- Ejemplo 1. Recomendación para Madrid

http://localhost:8080/recommend?city=Madrid&gender=MUJER

Genera una recomendación de outfit femenino basada en la predicción meteorológica de Madrid.

- Ejemplo 2. Recomendación para Barcelona

  http://localhost:8080/recommend?city=Barcelona&gender=HOMBRE

Genera una recomendación de outfit masculino utilizando la información meteorológica obtenida para Barcelona.

- Parametros utilizados

  ciudad -> ciudad utilizada para consultar el clima

  gender -> género utilizado para filtrar las prendas (MUJER o HOMBRE)

A partir de estos parámetros, el sistema consulta la información meteorológica almacenada en el datamart, selecciona productos compatibles del catálogo y genera automáticamente un outfit adaptado a las condiciones climáticas de la ciudad seleccionada.

# 6. Arquitectura del sistema

El proyecto Zalando Weather se ha desarrollado siguiendo una arquitectura modular basada en eventos. Cada módulo del sistema tiene una responsabilidad específica y se comunica con el resto mediante ActiveMQ utilizando un modelo publisher/subscriber.

La arquitectura general del sistema está compuesta por cuatro módulos principales:

- Zalando: encargado de realizar scraping de productos desde Zalando.
- Weather_Project: encargado de obtener información meteorológica desde OpenWeatherMap.
- EventStoreBuilder: responsable del almacenamiento persistente de eventos.
- Recommendation: encargado de reconstruir el datamart y generar recomendaciones de outfits.

La comunicación entre módulos se realiza mediante eventos JSON publicados y consumidos a través de ActiveMQ.


<img width="262" height="771" alt="Captura de pantalla 2026-05-20 002626" src="https://github.com/user-attachments/assets/e69d25b5-88cf-4cc3-a744-a93cef6080f0" />

# 7. Arquitectura de la aplicación

El proyecto utiliza una arquitectura basada en el patrón MVC (Model-View-Controller), aplicada de forma modular dentro de los distintos componentes del sistema.

Además, la aplicación sigue un enfoque basado en Event Sourcing, donde los eventos generados por los distintos módulos son almacenados y posteriormente utilizados para reconstruir el estado del sistema.

> Modulo Zalando

El módulo Zalando implementa principalmente las capas Model y Controller.
- Model
  
  Incluye las clases:

  Product -> Representa una prenda de ropa                                                          

  ZalandoEvent -> Representa eventos de productos

  Estas clases representan la estructura de los productos y eventos generados durante el scraping

- Controller
  
Incluye las clases:

ZalandoScraper -> Coordina todo el scraping

ProductParser ->  Extrae información del HTML

EventPublisher  -> Publica eventos en ActiveMQ

Además de las clases perteneciente a utils:

Category -> Es un Enum 

CategoryUtils -> Clasifica prendas por categoría

Color -> Es un Enum

ColorUtils -> Detecta colores desde texto

ImageUtils ->  Convierte imágenes a Base64

Estas clases gestionan la extracción de productos desde Zalando y la publicación de eventos.

<img width="1536" height="1024" alt="DiagramaClaseZalando" src="https://github.com/user-attachments/assets/05fe45df-2b9f-4718-865a-93b5784ee2ac" />


> Modulo Weather_Project

El módulo Weather_Project se encarga de obtener y transformar información meteorológica.
- Model

Incluye las clase:

Weather ->  Representa información meteorológica

WeatherEvents ->  Representa eventos climáticos

- Controller

  Incluye las clases:

  WeatherController -> Coordina flujo meteorológico

  WeatherTransformer ->  Convierte JSON a Weather

  WheatherApiConsumer ->  Consume OpenWeather

  EventPublisher  ->  Publica eventos en ActiveMQ


<img width="1672" height="941" alt="DiagramaClasesWeather_Project" src="https://github.com/user-attachments/assets/722a294e-1edf-4e0b-894a-e5d2c162836d" />

> Modulo EventStoreBuilder

El módulo EventStoreBuilder implementa la persistencia de eventos siguiendo un enfoque de Event Sourcing. Incluye las clases: 

EventConsumer ->  Consume eventos desde ActiveMQ

EventStorageService -> Gestiona almacenamiento lógico

EventFileManager ->  Persiste eventos físicamente

Su objetivo es almacenar los eventos generados por el sistema dentro del Event Store.

<img width="898" height="330" alt="Captura de pantalla 2026-05-20 022557" src="https://github.com/user-attachments/assets/6e8a242f-95fd-4dbf-979b-33861d27a29c" />


> Modulo Recommendation

El módulo Recommendation constituye el núcleo funcional de la aplicación y es el único que incorpora las tres capas del patrón MVC

- Model

  Incluye las clases:

  ColorMatching  ->  Gestiona compatibilidad cromática

  Outfit -> Representa outfits completos

  Product  -> Representa productos

- Controller

  Aquí encontramos las clases:

  ActiveMQConfig -> centraliza toda la configuración necesaria para la conexión y comunicación con ActiveMQ

  EventFileReader ->  Lee eventos almacenados desde el Event Store

  EventHandler -> Procesa eventos y actualiza datos

  EventStoreLoader -> Reconstruye el datamart

  EventSubscriber -> Consume eventos en tiempo real

  OutfitBuilder ->  Construye outfits

  RecommendationAPI ->  Gestiona endpoints y peticiones

  RecommendationService ->  Genera recomendaciones

  Además dentro del controller encontramos también la persistencia con las siguientes clases:

  DatamartStore -> Gestiona el datamart

  ProductRepository ->  Gestiona productos SQLite

  WeatherRepository ->  Gestiona clima SQLite

  También aqui encontramos las distintas utlidades como lo son:

  ColorParser ->  Normaliza colores

  ColorType -> Es un Enum

  WeatherParser ->  Clasifica clima

  WeatherType  -> Es un Enum

- View

  En esta capa encontramos la clase:

  RecommendationPageBuilder ->  Construye dinámicamente el HTML

<img width="1536" height="1024" alt="DDiagramaClaseRecommendation" src="https://github.com/user-attachments/assets/d63d2f08-7f7d-4fb7-b8fa-96522824201d" />

# 8. Principios y patrones de diseño aplicados en cada módulo.

- Modulo Zalando

El módulo Zalando utiliza una arquitectura MVC parcial, separando las clases modelo (Product, ZalandoEvent) de las clases encargadas del scraping y publicación de eventos (ZalandoScraper, ProductParser, EventPublisher).

Además, se aplica el patrón Publisher/Subscriber mediante ActiveMQ para publicar eventos de productos de forma desacoplada.

- Modulo Weather_Project

El módulo Weather_Project sigue también una arquitectura MVC parcial. Las clases Weather y WeatherEvent representan el modelo, mientras que WeatherController, WeatherTransformer y WheatherApiConsumer gestionan la lógica de negocio.

Se emplea el patrón Publisher/Subscriber para publicar eventos meteorológicos a través de ActiveMQ.

- Modulo EventStoreBuilder

El módulo EventStoreBuilder implementa el patrón Event Sourcing, almacenando los eventos generados por el sistema en archivos .events.

Además, aplica el principio Separation of Concerns, separando: el consumo de eventos (EventConsumer), la lógica de almacenamiento (EventStorageService), y la gestión de archivos (EventFileManager).

- Modulo Recommendation

El módulo Recommendation utiliza una arquitectura MVC completa:

Modelo: Product, Outfit ...
Vista: RecommendationPageBuilder.
Controlador: RecommendationAPI, RecommendationService, OutfitBuilder...

También se aplican: el patrón Repository (ProductRepository, WeatherRepository), Event Sourcing (EventStoreLoader, EventSubscriber, EventHandler), y el patrón Builder mediante OutfitBuilder para generar outfits dinámicamente.












