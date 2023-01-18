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

На данный момент не поддерживает автоматический запуск приложения в Docker контейнере.  
Возможожен запуск через терминал:

````\
mvn -pl request-counter-application -am package
java -jar .\request-counter-application\target\request-counter-application-1.0-SNAPSHOT.jar --bot_key=key --default_delay=1000

````

## Пример работы
### Пример 1
Пользователь не зарегестрирован и делает попытку ввод echo message
![img.png](images/echo_message_unknown_user.png)

### Пример 2
Неизвестный системе пользователь выполняет регистрацию
![img.png](images/register_unknown_user.png)

### Пример 3
Уже зарегестрированный пользователь выполняет регистрацию
![img.png](images/register_known_user.png)

### Пример 4
Зарегестрированный или незарегестрированный пользователь отправляет неизвествую команду
![img.png](images/unknown_command.png)

### Пример 5
Зарегестрированный пользователь отправляет echo message
![img.png](images/echo_message_known_user.png)