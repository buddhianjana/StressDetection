package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Model.Book;
import com.example.demo.Model.DoctorDetails;
import com.example.demo.Repository.BookRepo;
import com.example.demo.Repository.DoctorDetailsRepo;
import com.example.demo.Repository.UserRepo;

@SpringBootTest
public class DoctorUnitTest {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	DoctorDetailsRepo doctorDetailsRepo;
	
	@Autowired
	BookRepo bookRepo;
	
	@Test
	public void testDoctorDetailsSave() {
		DoctorDetails doctorDetails = new DoctorDetails();
		doctorDetails.setId(2L);
		doctorDetails.setFirstName("Name");
		doctorDetails.setEmail("Doctor@gmail.com");
		doctorDetails.setMobile("0714547852");
		doctorDetails.setSpecificArea("Stress");
		doctorDetailsRepo.save(doctorDetails);
		assertNotNull(doctorDetailsRepo.findById(2L).get());
	}

	@Test
	public void testReadAllDetailst() {
		List<DoctorDetails> list1 = doctorDetailsRepo.findAll();
		assertThat(list1).size().isGreaterThan(0);
	}
	
	@Test
	public void testBookSave() {
		Book book = new Book();
		book.setId(2L);
		book.setUname("Name");
		book.setUemail("Doctor@gmail.com");
		book.setUmobile("0714547852");
		book.setGender("Male");
		book.setDname("Perera");
		book.setDemail("Perera@gmail.com");
		book.setDmobile("07741452369");
		book.setRemark("-");
		book.setStatus("Approved");
		bookRepo.save(book);
		assertNotNull(bookRepo.findById(2L).get());
	}
	

	@Test
	public void testReadAllBook() {
		List<Book> list1 = bookRepo.findAll();
		assertThat(list1).size().isGreaterThan(0);
	}
}
