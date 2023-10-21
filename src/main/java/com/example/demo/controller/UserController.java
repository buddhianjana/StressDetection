package com.example.demo.Controller;

import java.security.Principal;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.Model.Book;
import com.example.demo.Model.DoctorDetails;
import com.example.demo.Model.User;
import com.example.demo.Repository.BookRepo;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Service.UserService;

@Controller
@CrossOrigin("*")
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BookRepo bookRepo;
	
	private static final Logger logger = LogManager.getLogger(UserController.class);

	
	 @GetMapping("/conform/{id}")
	    public String Conform(@PathVariable Long id, Model model, Principal principal) {
	        try {
	            boolean isLoggedIn = principal != null;

	            model.addAttribute("isLoggedIn", isLoggedIn);

	            if (isLoggedIn) {
	                String email = principal.getName();
	                User user = userRepo.findByEmail(email);
	                model.addAttribute("user", user);
	            }

	            DoctorDetails doctorDetails = userService.getDoctorDetailsByID(id);
	            model.addAttribute("doctorDetails", doctorDetails);

	            // Log an INFO message for successful retrieval of doctor details
	            logger.info("Doctor details retrieved successfully for booking confirmation: " + doctorDetails.toString());

	            return "confirm-booking";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while confirming the booking", e);

	            // Log a WARN message for the booking confirmation failure
	            logger.warn("Booking confirmation failed");

	            return "redirect:/errorPage";
	        }
	    }
	
	 @PostMapping("/conform/{id}")
	    public String saveBookingDetails(@ModelAttribute Book book, @PathVariable Long id, Model model) {
	        try {
	            DoctorDetails doctorDetails = userService.getDoctorDetailsByID(id);
	            book.setDname(doctorDetails.getFirstName());
	            book.setDemail(doctorDetails.getEmail());
	            book.setDmobile(doctorDetails.getMobile());

	            Book savedBooking = bookRepo.save(book);

	            // Log an INFO message for successful booking details saving
	            logger.info("Booking details saved successfully for doctor: " + doctorDetails.getFirstName());

	            return "redirect:/doctors";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while saving booking details", e);

	            // Log a WARN message for the booking details saving failure
	            logger.warn("Booking details saving failed");

	            return "redirect:/errorPage";
	        }
	    }
}
