package com.springboot.web.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.web.model.Todo;
 

public interface TodoRepository extends JpaRepository<Todo,Integer>{

	List<Todo> findByUser(String user);
}
