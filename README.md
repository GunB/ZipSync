# ZipPacker
Programa para agregar y reemplazar archivos en empaquetados Zip

## Forma de uso:

Agregue todos los archivos que desea cambiar en la carpeta "change_files" (Los nombres no son relevantes)
Dirigase al archivo de texto config.txt donde debe ser llenado de la siguiente forma:

1. Dirección del archivo que va a ser modificado o agregado
2. Nombre del archivo dentro de la carpeta "change_files" que reemplazará esl archivo descrito en la anterior linea

Así mismo, al abrir el programa, deberá seleccionar la carpeta donde se encuentren los empaquetados .ZIP que serán reemplazados... el programa respetará la estructura de carpetas que tiene designada.

## Opciones
* Podrá realizar BackUP de los archivos antes de moddificarlos seleccionando la opción "Realizar copia de archivos en carpeta eFixer", lo cual hará que todos los archivos se copien en una carpeta con este nombre además de agregar hora y fecha del momento en que se ejecutó la acción. Tambien cabe mencionar que los cambios se realizan sobre esta carpeta, dejando intacta la carpeta original.
* Podrá, basado en una expresión regular, seleccionar que archivos se desean modificar (Si esta opción no es suada, todos los archivos serán modificados)
