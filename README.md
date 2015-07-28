# ZipSync
Programa para Sincronizar archivos entre dos carpetas que contienen empaquetados ZIP con el mismo nombre

## Forma de uso:

Asegurese de llenar el archivo "config.txt" con expresiones regulares que reconozcan los archivos incluidos en los empaquetados zip tanto de origen como de destino.

1. Seleccione en el primero cajón la carpeta de los archivos que desea modificar
2. Seleccione la carpeta donde se encuentran los archivos con los que se realizará la copia a los que serán modificados

Así mismo, al abrir el programa, deberá seleccionar la carpeta donde se encuentren los empaquetados .ZIP que serán reemplazados... el programa respetará la estructura de carpetas que tiene designada.

## Opciones
* Podrá realizar BackUP de los archivos antes de moddificarlos seleccionando la opción "Realizar copia de archivos en carpeta eFixer", lo cual hará que todos los archivos se copien en una carpeta con este nombre además de agregar hora y fecha del momento en que se ejecutó la acción. Tambien cabe mencionar que los cambios se realizan sobre esta carpeta, dejando intacta la carpeta original.
* Podrá, basado en una expresión regular, seleccionar que archivos se desean modificar (Si esta opción no es usada, todos los archivos serán modificados)
