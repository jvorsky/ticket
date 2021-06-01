# ticket

Java 1.8

##### ДЗ 1. DatabaseJourneyService

Настройки подключения к бд в application.properties

Скрипт создания таблицы journey в create.sql.

Бин для коннекта к БД: DataSource.

Бин для поиска в БД: DbJourneyService.

Настройка бинов в RootConfig.


##### ДЗ 2. Spring + Hibernate

Добавлен hibernate. 

При старте системы проект устанавливает с бд сразу 30 соединений, максимальное число соединений 150. 
Максимальное время выполнения запроса - не больше 5 минут.

Настройки подключения к бд в database.properties

##### ДЗ 3. Hibernate relations

Реализована взаимосвязь сущностей journey, stop, vehicle и seat_info. 

Таблица seat_info используется для хранения информации о свободных местах 
в транспортном средстве в разрезе конкретного маршрута.
Поля таблицы:
~~~~
id
vehicle_id - ссылка на транспортное средство
journey_id - ссылка на маршрут
free_seats - кол-во свободных мест
~~~~

В рамках репозитория и сервисного слоя реализована возможность 
создания и редактирования всех спроектированных сущностей.

##### ДЗ 4. Remove relation

Реализовано:
    
    - удаление информации по транспортному средству
    - удаление самого транспортного средства
    - удаление остановки
    - удаление маршрута

##### ДЗ 5. Получение сущностей

Для уменьшения дублирования кода ввел тип запроса

~~~~
public enum QueryType {
    HQL, NATIVE, NAMED, CRITERIA, STORED_PROCEDURE
}
~~~~
и создал абстрактный класс содержащий все варианты получения сущностей 
плюс метод для получения данных в зависимости от типа запроса.

~~~~
AbstractTransactionalService

public Collection<E> findAll(QueryType queryType)
~~~~

##### ДЗ 6. Paging and sorting

Сделано постраничное отображение сущностей findAllJourneys, findAllVehicles, findAllStops с возможностью сортировки по полям: id, name, active.

Реализован метод получения списка транспортных средств с наименьшим количеством свободных мест.

Реализован метод получения списка транспортных средств с наибольшим количеством свободных мест.

##### ДЗ 7. Spring Data Jpa

Общий метод поиска всех сущностей с пагинацией, сортировкой и динамической фильтрации через Specification:

AbstractTransactionalService.findAll(QueryContext queryCntx)

        System.out.println("\nПоиск journey с сортировкой по дате отправления и фильтром по полю StationFrom");
        System.out.println(ticketClient.findAllJourneys(0, 5, JourneyEntity_.DATE_FROM, false, "StationFrom", "Odessa"));

        System.out.println("\nПоиск journey с сортировкой по Id у которых есть активное транспортное средство");
        System.out.println(ticketClient.findAllJourneys(0, 2, JourneyEntity_.ID, true, "ActiveVehicle", null));

        System.out.println("\nПоиск vehicle с сортировкой по Id и фильтром по дате создания");
        System.out.println(ticketClient.findAllVehicles(0, 10, AbstractEntity_.ID, true, "CreateDate", "2021-04-27"));

        System.out.println("\nПоиск stop с сортировкой по дате создания и фильтром по имени остановки");
        System.out.println(ticketClient.findAllStops(0, 4, StopEntity_.CREATE_DATE, false, "Name", "Подольск"));
