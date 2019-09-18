drop table if exists todolist;
drop table if exists todoitem;

CREATE TABLE todolist (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

CREATE TABLE todoitem (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  todolist_id int NOT NULL,
  taskname VARCHAR(250) NOT NULL,
  softdeleted int default 0,
  completed int default 0,
  tag VARCHAR(250) default NULL,
  reminder TIMESTAMP default NULL
 );
 
 insert into todolist (name) values ('list1');
 insert into todoitem (todolist_id,taskname) values (1,'task1');
 insert into todoitem (todolist_id,taskname) values (1,'task2');