# PROGRAMA 1

Hacer un programa en java que realice la búsqueda o reemplazo de palabras en un fichero. El programa dispondrá de un menú con las siguientes opciones:

1. Introducir el término de búsqueda

2. Introducir la ruta del archivo en el que se realizará la búsqueda

3. Mostrar el número de ocurrencias del término en el archivo

4. Realizar el reemplazo de la palabra por otra que se solicitará. El reemplazo
 se realizará en un nuevo archivo cuyo nombre será el mismo que el original
 pero con el prefijo NUEVO_

Por otro lado, el programa podrá recibir como argumentos del programa la siguiente información (correspondiendo el orden de los argumentos al siguiente listado):

* cadena búsqueda
* ruta del archivo
* término de reemplazo 

En caso de recibir argumentos, el programa se comportará de diferente manera, siendo esta la siguiente:

* Si el programa recibe la cadena de búsqueda y la ruta del archivo, imprimirá por pantalla la siguiente información:
    - Número de ocurrencias de la cadena en el archivo

* Si el programa recibe los 3 parámetros imprimirá por pantalla:
    - línea 1: ruta del archivo con el texto reemplazado 
    - línea 2: número de reemplazos realizados

En ambos casos se finalizará el programa con System.exit(0) en caso de no haber ningún error, o system.exit(CODIGO_ERROR) en el caso de error, donde CODIGO_ERROR corresponda a un código numérico propio para identificar el fallo
