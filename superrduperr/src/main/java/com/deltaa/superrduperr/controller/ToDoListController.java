package com.deltaa.superrduperr.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deltaa.superrduperr.entity.ToDoList;
import com.deltaa.superrduperr.handleexception.ToDoListCustomError;
import com.deltaa.superrduperr.handleexception.ToDoListCustomException;
import com.deltaa.superrduperr.jparepository.ToDoItemJpaRepository;
import com.deltaa.superrduperr.jparepository.ToDoListJpaRepository;


@RestController
@RequestMapping("/")
public class ToDoListController {
	
		@Autowired
		private ToDoListJpaRepository todolistRepository;
		
		@GetMapping("/todolists")
	    public List<ToDoList> getAllToDoLists() {
	      return todolistRepository.findAll();
	    }
	    
	    @GetMapping("/todolists/{id}")
	    public ToDoList getToDoListByID(@PathVariable int id) {
	      Optional<ToDoList> optToDoList = todolistRepository.findById(id);
	      
	      if(optToDoList.isPresent()) {
	        return optToDoList.get();
	      }else {
	        throw new ToDoListCustomException("ToDoList not found with id " + id);
	      }
	      	
	    }
	    
	    @PostMapping("/todolists")
	    public ToDoList createToDoList(@Valid @RequestBody ToDoList toDoList) {
	        return todolistRepository.save(toDoList);
	    }
    
   
  }
