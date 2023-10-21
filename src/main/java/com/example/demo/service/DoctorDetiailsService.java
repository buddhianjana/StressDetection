package com.example.demo.Service;

import java.util.List;

import com.example.demo.Model.Book;
import com.example.demo.Model.DoctorDetails;


public interface DoctorDetiailsService {

	public DoctorDetails saveDetails(DoctorDetails doctorDetails);
	
	 List<DoctorDetails> getAllDetails();
	 
	 Book getappointmentByID(Long id);
}
