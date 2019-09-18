package com.deltaa.superrduperr.jparepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deltaa.superrduperr.entity.ToDoList;

@Repository
public interface ToDoListJpaRepository extends JpaRepository<ToDoList, Integer> {

}

