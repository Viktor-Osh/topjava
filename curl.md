Curl requests for MealRestController

Get meal by id:
curl --location 'http://localhost:8080/topjava/rest/profile/meals/100008'

Get all user meals:
curl --location 'http://localhost:8080/topjava/rest/profile/meals'

Update meal with id:
curl --location --request PUT 'http://localhost:8080/topjava/rest/profile/meals/100008' \
--header 'Content-Type: application/json' \
--data '{
"id": 100008,
"dateTime": "2020-01-31T13:00:00",
"description": "ОбедTest",
"calories": 1000,
"user": null
}'

Delete meal with id:
curl --location --request DELETE 'http://localhost:8080/topjava/rest/profile/meals/100008'

Create new meal:
curl --location 'http://localhost:8080/topjava/rest/profile/meals/' \
--header 'Content-Type: application/json' \
--data '{
"dateTime": "2023-01-31T10:00:00",
"description": "ЗавтракTest",
"calories": 500,
"user": null
}'

Get meals filtered by date and time (startDate and startTime - inclusive; endDate and endTime - exclusive)
curl --location 'http://localhost:8080/topjava/rest/profile/meals/filter?startDate=&endDate=2020-01-30&startTime=13%3A00&endTime='
