# CRUD app for users with books 
## *CRUD app*
## *Spring*
## *DB - PostgreSQL*
## *TestContainers*



Функция этого приложения хранить всех пользователей и все книги, которые находятся у пользвателей

В данном спринг модуле мы можем:

* Cоздать пользователя с привязанными к нему по userId книгами.

* Редактировать этого пользователя с книгами по id:
редактировать можно fullName, title, age у самого пользователя. Можно добавить новые книги в список пользвателя.

* Получить пользвателя и его книги по id.

* Удалить позвателя вместе с книгами.



**Инструкция:**

1. собрать приложение mvn clean install
2. проверить работу приложения возможно по этому ендпоинту: http://localhost:8091/app/actuator
3. посмотреть сваггер возможно тут: http://localhost:8091/app/swagger-ui/index.html

http://localhost:8091/app/api/v1/user/create

{
"userRequest": {
"fullName": "test",
"title": "reader",
"age": 33
},
"bookRequests": [
{
"title": "book name",
"author": "test author",
"pageCount": 222
},
{
"title": "book name test",
"author": "test author second",
"pageCount": 555
}
]
}

docker run --name postgres -d -p 15432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres:alpine
rqid requestId1010101