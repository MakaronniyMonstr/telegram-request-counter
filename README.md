# telegram-request-counter

## Структура приложения

## Системные требования 

Java 17
PosgreSQL 15.1

## Запуск локального сервера БД

Для запуска может использоваться Docker образ:

````
docker-compose -f .\dev-container\src\main\resources\docker-compose.yml up
````

## Запуск локального сервера приложения

На данный момент не поддерживает автоматический запуск прилоежния в Docker контейнере.  
Возможожен запуск через терминал:

````\
mvn -pl request-counter-application -am package
java -jar .\request-counter-application\target\request-counter-application-1.0-SNAPSHOT.jar --bot_key=328265424:AAHy-VZ22RFGT-Pi_9jNOe7t7BPfJhZhqQY --default_delay=1000

````