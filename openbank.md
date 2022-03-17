**MarvelApiApp**
Prueba técnica realizada para Openbank utilizando las siguientes tecnologías:
- Arquitectura MVVM con ViewBinding y DataBinding. Implementación de BindingAdapter para requisitos de la aplicación.
- Uso de StateFlow y Flow para la obtención y actualización de datos de la interfaz.
- MockK para los tests de los ViewModels, repositorios y más clases de la aplicación.
- Room como base de datos para cacheo.
- Hilt para la inyección de dependencias.
- Retrofit como gestor de las peticiones de red.
- Modulos independientes para los sistemas de network, database y la aplicación en sí.
- Picasso para la carga de imagenes.
- Extensiones de vista para facilitar la programación.

Las claves de la API de Marvel se encuentran en la carpeta conf del directorio raíz, en blanco por defecto.
