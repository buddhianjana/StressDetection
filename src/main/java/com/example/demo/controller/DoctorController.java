package com.example.demo.Controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.example.demo.Repository.DoctorDetailsRepo;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Service.DoctorDetiailsService;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin("*")
@RequestMapping("/doctor/")
public class DoctorController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserService userService;
	
	@Autowired
	private DoctorDetailsRepo doctorDetailsRepo;
	
	@Autowired
	private BookRepo bookRepo;
	
	@Autowired
	private DoctorDetiailsService doctorDetailsService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger logger = LogManager.getLogger(DoctorController.class);
	

	@GetMapping("/")
	public String Index(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;
		boolean isSaved = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);
		model.addAttribute("isSaved", isSaved);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		if (isSaved) {
			String email = principal.getName();
			DoctorDetails details = doctorDetailsRepo.findByEmail(email);
			model.addAttribute("doctor", details);
		}
		return "Doctor/index";
	}

	 @GetMapping("/add")
	    public String AddDetails(Model model, Principal principal) {
	        try {
	            boolean isLoggedIn = principal != null;
	            boolean isSaved = principal != null;

	            model.addAttribute("isLoggedIn", isLoggedIn);
	            model.addAttribute("isSaved", isSaved);

	            if (isLoggedIn) {
	                String email = principal.getName();
	                User user = userRepo.findByEmail(email);
	                model.addAttribute("user", user);
	            }
	            if (isSaved) {
	                String email = principal.getName();
	                DoctorDetails details = doctorDetailsRepo.findByEmail(email);
	                model.addAttribute("doctor", details);
	            }

	            // Log an INFO message for accessing the AddDetails page
	            logger.info("Accessed the AddDetails page");

	            return "Doctor/add-details";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while accessing the AddDetails page", e);

	            // Log a WARN message for the access failure
	            logger.warn("Failed to access the AddDetails page");

	            return "redirect:/errorPage";
	        }
	    }

	 @GetMapping("/appointment")
	    public String Appointment(Model model, Principal principal) {
	        try {
	            boolean isLoggedIn = principal != null;
	            boolean isSaved = principal != null;

	            model.addAttribute("isLoggedIn", isLoggedIn);
	            model.addAttribute("isSaved", isSaved);

	            if (isLoggedIn) {
	                String email = principal.getName();
	                User user = userRepo.findByEmail(email);
	                model.addAttribute("user", user);
	            }
	            if (isSaved) {
	                String email = principal.getName();
	                DoctorDetails details = doctorDetailsRepo.findByEmail(email);
	                model.addAttribute("doctor", details);

	                // Fetch the appointments for the logged-in doctor
	                List<Book> doctorAppointments = bookRepo.findByDname(details.getFirstName());
	                model.addAttribute("doctorAppointments", doctorAppointments);

	                // Log an INFO message for accessing the Appointment page
	                logger.info("Accessed the Appointment page");

	                return "Doctor/appointments";
	            }
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while accessing the Appointment page", e);

	            // Log a WARN message for the access failure
	            logger.warn("Failed to access the Appointment page");
	        }

	        return "redirect:/errorPage";
	    }
	
	

	    @GetMapping("/appointments/approve/{id}")
	    public String editProducts(@PathVariable Long id, Model model, Principal principal) {
	        try {
	            boolean isLoggedIn = principal != null;
	            boolean isSaved = principal != null;

	            model.addAttribute("isLoggedIn", isLoggedIn);
	            model.addAttribute("isSaved", isSaved);

	            if (isLoggedIn) {
	                String email = principal.getName();
	                User user = userRepo.findByEmail(email);
	                model.addAttribute("user", user);
	            }
	            if (isSaved) {
	                String email = principal.getName();
	                DoctorDetails details = doctorDetailsRepo.findByEmail(email);
	                model.addAttribute("doctor", details);
	            }

	            Book doctorAppointments = doctorDetailsService.getappointmentByID(id);
	            model.addAttribute("doctorAppointments", doctorAppointments);

	            // Log an INFO message for accessing the "approve" page
	            logger.info("Accessed the 'approve' page for appointment with ID: " + id);

	            return "Doctor/approve";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while accessing the 'approve' page for appointment with ID: " + id, e);

	            // Log a WARN message for the access failure
	            logger.warn("Failed to access the 'approve' page for appointment with ID: " + id);

	            return "redirect:/errorPage";
	        }
	    }

	    @PostMapping("/appointments/approve/{id}")
	    public String updateAdmin(@PathVariable Long id, @ModelAttribute("user") Book book, Model model) {
	        try {
	            Book approve = doctorDetailsService.getappointmentByID(id);
	            approve.setId(id);
	            approve.setUname(book.getUname());
	            approve.setUemail(book.getUemail());
	            approve.setUmobile(book.getUmobile());
	            approve.setGender(book.getGender());
	            approve.setDname(book.getDname());
	            approve.setDemail(book.getDemail());
	            approve.setDmobile(book.getDmobile());
	            approve.setRemark(book.getRemark());
	            approve.setStatus("Approved");

	            bookRepo.save(approve);

	            // Send a welcome email to the user
	            SimpleMailMessage mailMessage = new SimpleMailMessage();
	            mailMessage.setSubject("Doctor Approved Your Appointment Successfully!");
	            mailMessage.setTo(book.getUemail());
	            mailMessage.setFrom("docshedofficial@gmail.com");

	            String emailContent = "Dear " + book.getUname() + ",\n\n"
	                    + "We are pleased to inform you that your appointment has been approved.\n\n"
	                    + "If you have any questions or need to make changes, please don't hesitate to contact us.\n\n"
	                    + "Thank you for choosing DOCSHED.\n\n"
	                    + "Sincerely,\n"
	                    + "The DOCSHED Team";

	            mailMessage.setText(emailContent);

	            javaMailSender.send(mailMessage);

	            // Log an INFO message for the successful appointment approval
	            logger.info("Appointment with ID: " + id + " has been successfully approved");

	            return "redirect:/doctor/appointment";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while approving the appointment with ID: " + id, e);

	            // Log a WARN message for the approval failure
	            logger.warn("Failed to approve the appointment with ID: " + id);

	            return "redirect:/errorPage";
	        }
	    }
	
	
	
	    @GetMapping("/appointments/decline/{id}")
	    public String declineApprovement(@PathVariable Long id, Model model, Principal principal) {
	        try {
	            boolean isLoggedIn = principal != null;
	            boolean isSaved = principal != null;

	            model.addAttribute("isLoggedIn", isLoggedIn);
	            model.addAttribute("isSaved", isSaved);

	            if (isLoggedIn) {
	                String email = principal.getName();
	                User user = userRepo.findByEmail(email);
	                model.addAttribute("user", user);
	            }
	            if (isSaved) {
	                String email = principal.getName();
	                DoctorDetails details = doctorDetailsRepo.findByEmail(email);
	                model.addAttribute("doctor", details);
	            }

	            Book doctorAppointments = doctorDetailsService.getappointmentByID(id);
	            model.addAttribute("doctorAppointments", doctorAppointments);

	            // Log an INFO message for accessing the "decline" page
	            logger.info("Accessed the 'decline' page for appointment with ID: " + id);

	            return "Doctor/decline";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while accessing the 'decline' page for appointment with ID: " + id, e);

	            // Log a WARN message for the access failure
	            logger.warn("Failed to access the 'decline' page for appointment with ID: " + id);

	            return "redirect:/errorPage";
	        }
	    }

	    @PostMapping("/appointments/decline/{id}")
	    public String declineAppointment(@PathVariable Long id, @ModelAttribute("user") Book book, Model model) {
	        try {
	            Book approve = doctorDetailsService.getappointmentByID(id);
	            approve.setId(id);
	            approve.setUname(book.getUname());
	            approve.setUemail(book.getUemail());
	            approve.setUmobile(book.getUmobile());
	            approve.setGender(book.getGender());
	            approve.setDname(book.getDname());
	            approve.setDemail(book.getDemail());
	            approve.setDmobile(book.getDmobile());
	            approve.setRemark(book.getRemark());
	            approve.setStatus("Declined");

	            bookRepo.save(approve);

	            // Send a decline email to the user
	            SimpleMailMessage mailMessage = new SimpleMailMessage();
	            mailMessage.setSubject("Doctor Declined Your Appointment");
	            mailMessage.setTo(book.getUemail());
	            mailMessage.setFrom("docshedofficial@gmail.com");

	            String emailContent = "Dear " + book.getUname() + ",\n\n" +
	                    "We regret to inform you that your appointment has been declined.\n\n" +
	                    "Here were the details:\n" +
	                    "Doctor Name: " + book.getDname() + "\n" +
	                    "Doctor Email: " + book.getDemail() + "\n" +
	                    "If you have any questions or would like to reschedule, please don't hesitate to contact us.\n\n" +
	                    "We apologize for any inconvenience, and we hope to assist you with your healthcare needs in the future.\n\n" +
	                    "Sincerely,\n" +
	                    "The DOCSHED Team";

	            mailMessage.setText(emailContent);

	            javaMailSender.send(mailMessage);

	            // Log an INFO message for declining the appointment
	            logger.info("Appointment with ID: " + id + " has been declined.");

	            return "redirect:/doctor/appointment";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while declining the appointment with ID: " + id, e);

	            // Log a WARN message for the decline failure
	            logger.warn("Failed to decline the appointment with ID: " + id);

	            return "redirect:/errorPage";
	        }
	    }

	
	    @PostMapping("/saveDetails")
	    public String saveDetails(@ModelAttribute DoctorDetails doctorDetails, HttpSession session, Model model) {
	        try {
	            DoctorDetails savedDetails = doctorDetailsService.saveDetails(doctorDetails);

	            // Log an INFO message for saving doctor details
	            logger.info("Doctor details have been successfully saved.");

	            return "redirect:/doctor/";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while saving doctor details", e);

	            // Log a WARN message for the save failure
	            logger.warn("Failed to save doctor details");

	            return "redirect:/errorPage";
	        }
	    }

}
