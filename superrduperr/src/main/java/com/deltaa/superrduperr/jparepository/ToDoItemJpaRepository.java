package com.deltaa.superrduperr.jparepository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.deltaa.superrduperr.entity.ToDoItem;

@Repository
public interface ToDoItemJpaRepository extends JpaRepository<ToDoItem, Integer> {

	
	@Query("SELECT item FROM ToDoItem item  WHERE item.todolist_id =:toDoListId")
    List<ToDoItem> findAllToDoItemsById(@Param("toDoListId") int toDoListId);
		 

	}

