###### Docker  
  
`docker run -p 8080:8080 int28h/scheduler`  
  
  
  
###### API  
  
+ `/showEmails`  
Возвращает строку с email'ами из БД, разделенными пробелами.  
  
**Параметры**: не требуются.  
**Пример запроса:** `http://localhost:8080/showEmails`  
**Пример ответа:** `test1@protonmail.com test2@yandex.ru`  
  
  
+ `/addEmail`  
Добавляет email в БД.  
  
**Параметры:**
*email:* адрес электронной почты, который требуется добавить в базу. Строка.  
**Пример запроса:** `http://localhost:8080/addEmail?email=test3@mail.ru`  
**Пример ответа:** `Email test3@mail.ru was added to the database.`  
  
  
+ /deleteEmail`  
Удаляет email из БД.  
  
**Параметры:**
*email:* адрес электронной почты, который требуется удалить из базы. Строка.  
**Пример запроса:** `http://localhost:8080/deleteEmail?email=test3@mail.ru`  
**Пример ответа:** `Email test3@mail.ru was deleted from the database.`  
  
  
+ `/sendMail`  
Отправляет письмо на указанный email c прописанным в [контроллере](src/main/java/com/int28h/controller/MailController.java) текстом.  
  
**Параметры**:  
*email:* адрес электронной почты, на который будет отправлено письмо.  
**Пример запроса:** `http://localhost:8080/sendMail?email=test3@mail.ru`  
**Пример ответа:** `Mail to test3@mail.ru  was sent.`  