package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Message;



public interface MessageRepo extends JpaRepository<Message, Long>{

}
