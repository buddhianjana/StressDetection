package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Book;

public interface BookRepo extends JpaRepository<Book, Long>{
	
	 List<Book> findByDname(String dname);

}
