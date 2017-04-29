Proyecto IoT Weather Station
Universidad EAFIT
Seminario de Ingenieria de Sistemas
2017

Autor: Edwin Montoya - emontoya@eafit.edu.co

Proyecto en Java, con Eclipse.

Se crearán varias clases para realizar la predicción.

1. getData() -> se conecta al servidor, y descarga las ultimas 24 horas de muestras por usuario.

$ java getData anietog1
$

en el directorio local, queda un archivo anietog1.csv para ser posteriormente procesado.

2. Prediccion() -> pendiente de desarrollar

dejará el resultado de la predicción en un archivo: ej: anietog1.txt
de la forma:

anietog1, predtemp, predhumid, timestamp

3. SendPrediction() -> pendiente de desarrollar

enviará al servidor los datos de predicción para el usuario.

4. processAll() -> pendiente de implementar

en secuencia invocara: getData() -> Prediction() -> sendPrediction()


