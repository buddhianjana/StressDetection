package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Book;
import com.example.demo.Model.Message;
import com.example.demo.Model.User;

public interface AdminService {

	List<User> getAllUsers();

	List<Message> getAllMessages();

	void deleteUserByID(Long id);

	User getUserByID(Long id);

	User updateUser(User user);

	List<Book> getAllAppointments();

}
