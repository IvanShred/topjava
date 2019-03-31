curl http://localhost:8080/topjava/rest/meals - Получение списка еды  
curl http://localhost:8080/topjava/rest/meals/100002 - Получение еды по идентификатору  
curl -X DELETE http://localhost:8080/topjava/rest/meals/100002 - Удаление еды  
curl -X POST -H "Content-type: application/json" -d '{"id" : null, "dateTime" : "2015-06-01T18:00:00", "description" : "Созданный ужин", "calories" : 300}' http://localhost:8080/topjava/rest/meals - Добавление еды  
curl -X PUT -H "Content-type: application/json" -d '{"id" : 100002, "dateTime" : "2015-05-30T10:00:00", "description" : "Обновленный завтрак", "calories" : 200}' http://localhost:8080/topjava/rest/meals/100002 - Редактирование еды  
curl http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-30&endDate=2015-05-30 - Фильтрация еды по датам

curl http://localhost:8080/topjava/rest/admin/users - Получение списка пользователей  
curl http://localhost:8080/topjava/rest/admin/users/100000 - Получение пользователя по идентификатору  
curl -X POST -H "Content-type: application/json" -d '{"name": "New2", "email": "new2@yandex.ru", "password": "passwordNew", "roles": ["ROLE_USER"]}' 'http://localhost:8080/topjava/rest/admin/users' - Добавление пользователя  
curl -XPUT -H "Content-type: application/json" -d '{"name" : "User", "email" : "changedMail@yandex.ru", "password" : "password", "enabled" : true, "roles" : ["ROLE_USER"]}' 'http://localhost:8080/topjava/rest/admin/users/100000' - Редактирование пользователя  
curl -X DELETE http://localhost:8080/topjava/rest/admin/users/100000 - Удаление пользователя  
curl http://localhost:8080/topjava/rest/admin/users?by=user@yandex.ru - Получение пользователя по email