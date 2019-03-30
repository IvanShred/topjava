curl http://localhost:8080/topjava/rest/meals
curl http://localhost:8080/topjava/rest/meals/100002
curl -XDELETE http://localhost:8080/topjava/rest/meals/100002
curl -XPOST -H "Content-type: application/json" -d '{"id" : null, "dateTime" : "2015-06-01T18:00:00", "description" : "Созданный ужин", "calories" : 300}' http://localhost:8080/topjava/rest/meals
curl -XPUT -H "Content-type: application/json" -d '{"id" : 100002, "dateTime" : "2015-05-30T10:00:00", "description" : "Обновленный завтрак", "calories" : 200}' http://localhost:8080/topjava/rest/meals/100002
curl http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-30&endDate=2015-05-30