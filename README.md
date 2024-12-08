
# LiterAlura - Catalogo de Libros

Hola! mi nombre es Sebastian Gutierrez y como parte de la formacion Java de Alura Latam hemos desarrollado un proyecto sobre el consumo de API y almacenado de datos.


## Uso de Gutendex API

#### Ejemplo de request
```http
  GET https://gutendex.com/books
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `books` | `string` | Muesta todos los libros |

Los datos vendran en el siguiente formato
```http
  {
    "count": <number>,
    "next": <string or null>,
    "previous": <string or null>,
    "results": <array of Books>
  }
```
#### Ejemplo busqueda de autores vivos entre años

```http
  GET https://gutendex.com/books?author_year_start=1800&author_year_end=1899
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `books`      | `string` | Muesta todos los libros |
| `author_year_start`      | `string` | Año de inicio |
| `author_year_end`      | `string` | Año de termino |


La busqueda devolvera libros con autores vivos en el siglo XIX.


#### Instalacion

Puedes descargar este proyecto y usarlo localmente desde el siguiente enlace
```http
  https://github.com/SebastianIsmaelG/LiterAlura.git
```
O utilizando la consola de comandos
```http
  git clone git@github.com:SebastianIsmaelG/LiterAlura.git
```