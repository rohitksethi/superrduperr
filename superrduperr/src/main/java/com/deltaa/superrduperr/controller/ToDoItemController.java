package com.deltaa.superrduperr.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deltaa.superrduperr.entity.ToDoItem;
import com.deltaa.superrduperr.entity.ToDoList;
import com.deltaa.superrduperr.handleexception.ToDoListCustomException;
import com.deltaa.superrduperr.jparepository.ToDoItemJpaRepository;
import com.deltaa.superrduperr.jparepository.ToDoListJpaRepository;


@RestController
@RequestMapping("/")
public class ToDoItemController {
		
		@Autowired
		private ToDoItemJpaRepository todoitemRepository;
		
		@Autowired
		private ToDoListJpaRepository todolistRepository;
	
	    @GetMapping("/todolists/{toDoListId}/todoitems")
	    public List<ToDoItem> getToDoItemByToDoListId(@PathVariable(value="toDoListId") int toDoListId) {
	      
	        if(!todolistRepository.existsById(toDoListId)) {
	            throw new ToDoListCustomException("TodoList not found!");
	        }
	        return todoitemRepository.findAllToDoItemsById(toDoListId);
	     }
	    
	    @PostMapping("/todolists/{toDoListId}/todoitems")
	    public ToDoItem addToDoItem(@PathVariable(value="toDoListId") int toDoListId,
                @Valid @RequestBody ToDoItem toDoItem) {
					
					Optional<ToDoList> optToDoList = todolistRepository.findById(toDoListId);
					
					if(optToDoList.isPresent()) {
					
					toDoItem.setTodolist_id(optToDoList.get().getId());
					
					return todoitemRepository.save(toDoItem) ;
					}else {
					 throw new ToDoListCustomException("ToDoList not found with id " + toDoListId);
					}

	    	}
}
