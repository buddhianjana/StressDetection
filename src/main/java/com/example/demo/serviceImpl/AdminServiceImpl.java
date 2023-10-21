package com.example.demo.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Book;
import com.example.demo.Model.Message;
import com.example.demo.Model.User;
import com.example.demo.Repository.BookRepo;
import com.example.demo.Repository.MessageRepo;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private BookRepo bookRepo;

	
	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public List<Message> getAllMessages() {
		return messageRepo.findAll();
	}

	@Override
	public void deleteUserByID(Long id) {
		userRepo.deleteById(id);
	}

	@Override
	public User getUserByID(Long id) {
		return userRepo.findById(id).get();
	}

	@Override
	public User updateUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public List<Book> getAllAppointments() {
		return bookRepo.findAll();
	}

}
