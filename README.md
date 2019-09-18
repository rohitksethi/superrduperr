## SuperrDuperr

##Technologies

- Java 8
- Maven 3.6.0
- Spring Boot 2.1.8
- H2 Database
- Hibernate
- Tomcat 9.0
- Postman

## Usage 

- Running the Application (jar file is in target directory)
`java -jar SuperrDuperr-0.0.1-SNAPSHOT.jar`

- Application will run on `http://localhost:8080`
- Running unit test cases (From project folder) `mvn test`

## Rest API

- ToDoList ->

@GetMapping("/todolists") – get all todolist
@GetMapping("/todolists/{id}") – get todolist by Id
@PostMapping("/todolists")- post a todolist


- ToDoItem ->

@GetMapping("/todolists/{toDoListId}/todoitems") – get all todoitem by todolist Id
@PostMapping("/todolists/{toDoListId}/todoitems") – post todoitem by todolist Id.
