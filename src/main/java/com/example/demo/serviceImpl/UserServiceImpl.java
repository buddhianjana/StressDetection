package com.example.demo.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.example.demo.Model.Book;
import com.example.demo.Model.DoctorDetails;
import com.example.demo.Model.Message;
import com.example.demo.Model.User;
import com.example.demo.Repository.BookRepo;
import com.example.demo.Repository.DoctorDetailsRepo;
import com.example.demo.Repository.MessageRepo;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MessageRepo messageRepo;
	
	@Autowired
	private DoctorDetailsRepo doctorDetailsRepo;
	
	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void removeSessionMessage() {
		HttpSession session= ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg");
		session.removeAttribute("msgError");
	}

	@Override
	public User saveUser(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user.setRole("ROLE_USER");
		User newUser= userRepo.save(user);
		return newUser;
	}

	@Override
	public Message saveMessages(Message message) {
		Message newMessage = messageRepo.save(message);
		return newMessage;
	}

	@Override
	public List<DoctorDetails> getAllDoctors() {
		return doctorDetailsRepo.findAll();
	}

	@Override
	public DoctorDetails getDoctorDetailsByID(Long id) {
		return doctorDetailsRepo.findById(id).get();
	}

	@Override
	public Book saveBook(Book book) {
		Book book2 = bookRepo.save(book);
		return book2;
	}



}
