package com.deltaa.superrduperr.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="todolist")
public class ToDoList {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="todolist_id")
	private List<ToDoItem> todoItems;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ToDoItem> getTodoItems() {
		return todoItems;
	}

	@Override
	public String toString() {
		return "ToDoList [id=" + id + ", name=" + name + ", todoItems=" + todoItems + "]";
	}

	public void setTodoItems(List<ToDoItem> todoItems) {
		this.todoItems = todoItems;
	}
	
}
