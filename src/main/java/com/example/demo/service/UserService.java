package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Book;
import com.example.demo.Model.DoctorDetails;
import com.example.demo.Model.Message;
import com.example.demo.Model.User;

public interface UserService {
	public User saveUser(User user);

	public void removeSessionMessage();

	public Message saveMessages(Message message);

	List<DoctorDetails> getAllDoctors();

	DoctorDetails getDoctorDetailsByID(Long id);
	
	public Book saveBook(Book book);
}
