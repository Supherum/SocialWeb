# InstaFake

Esta aplicación intenta modelar el comportamiento de una pequeña red social
haciendo uso de los modelos como Usuarios y las Publicaciones de estos. Para
poder probar la aplicación se dejará en el repositorio una colección de Postman

Como idea general podría ser la siguiente:


![](https://cdn.discordapp.com/attachments/409448284080570368/944198233213767690/Publicacion.png)


## Peticiones a la API


| Registro | | |
| ------------- | ------------- | ------------- |
| POST | Registrar usuario  |  localhost:8080/register/usuario |
| POST | Logear un usuario  | localhost:8080/login  |
| GET | Mis datos | localhost:8080/me |



| Publicaciones | | |
| ------------- | ------------- | ------------- |  
|POST| Nueva publicación  |  localhost:8080/publicacion/ |
| POST | Agregar un recurso a una publicación  | localhost:8080/publicacion/resource/add/{{IdPublicacion}} |
|PUT| Editar publicación | localhost:8080/publicacion/{{IdPublicacion}} |
| DELETE | Borrar una publicación | localhost:8080/publicacion/{{IdPublicacion}} |
| GET | Ver una publicacion | http://localhost:8080/publicacion/{{IdPublicacion}} |
| GET | Ver publicaciones públicas | http://localhost:8080/publicacion/public |
| GET | Ver las publicaciones de un Usuario | http://localhost:8080/publicacion/usuario/{{nickName}} |
| GET | Ver mis publicaciones | http://localhost:8080/publicacion/mia |


| Solicitudes | | |
| ------------- | ------------- | ------------- |  
| POST | Solicitar seguir a un Usuario | http://localhost:8080/usuario/seguir/{{nickName}} |
| POST | Aceptar seguir a un usuario | http://localhost:8080/usuario/seguir/{{nickName}} | 
| DELETE | Denegar un seguimiento de ususario | http://localhost:8080/usuario/rechazar/{{usuarioId}} |
| GET | Ver las solicitudes pendientes | localhost:8080/usuario/seguidores |

| Usuario | | |
| ------------- | ------------- | ------------- |  
| GET | Ver el perfil de un Usuario | localhost:8080/usuario/{{usuarioId}} |
| PUT | Actualizar mi perfil | localhost:8080/usuario/me |
| POST | Favorito a una Publicacion | localhost:8080/usuario/like/{{idPublicacion}} |


Para poder acceder a las peticiones es necesaio estar registrado, excepto como es 
obvio en el login y en el registro propiamente.



### Para probar postman hay que seleccionar los archivos de prueba que están adjuntos en el directorio de files. Hay 4 archivos, selecciona el que guste.
