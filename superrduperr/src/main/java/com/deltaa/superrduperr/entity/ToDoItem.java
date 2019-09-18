package com.deltaa.superrduperr.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="todoitem")
public class ToDoItem {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="todolist_id")
	private int todolist_id;
	

	@Column(name="taskname")
	private String taskname;
	
	@Column(name="completed")
	private int completed;
	
	@Column(name="softdeleted")
	private int softdeleted;
	
	@Column(name="tag")
	private String tag;
	
	 @Temporal(value=TemporalType.TIMESTAMP)
	 @Column(name="reminder")
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
	 private Date reminder;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	

	public int getSoftdeleted() {
		return softdeleted;
	}

	public void setSoftdeleted(int softdeleted) {
		this.softdeleted = softdeleted;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getReminder() {
		return reminder;
	}

	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}
	
	public int getTodolist_id() {
		return todolist_id;
	}

	public void setTodolist_id(int todolist_id) {
		this.todolist_id = todolist_id;
	}
	
	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}


	
	
}
