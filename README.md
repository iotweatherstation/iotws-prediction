Proyecto IoT Weather Station
Universidad EAFIT
Seminario de Ingenieria de Sistemas
2017

Autor: Edwin Montoya - emontoya@eafit.edu.co

Proyecto en Java, con Eclipse.

## ejecutarlo:

$ java Prediction emontoya

## Descripción de métodos de la clase "Prediction.java" para realizar la predicción.

1. public void getTempHumidByUser(String username)

se conecta al servidor, y descarga las ultimas 24 horas de muestras para el usuario username

2. public void saveTempHumidByUser(String username)

se conecta al servidor, y descarga las ultimas 24 horas de muestras para el usuario username y lo salva en un archivo local llamado username.csv (esta rutina es opcional, realmente no se requiere para la predicción)

3. public void loadSample(String line)

carga una muestra en el arreglo en memoria: samples[i]

4. public void sendPrediction() 

envia al servidor una predicción de temp y humid para un idhome dado

5. public void getPrediction() 

trae del servidor la última predicción para el idhome dado.

6. public void calcPrediccion()

Pendiente de implementar el cálculo de la predicción futura de temp y humid basada en serie de tiempo por promedio móvil