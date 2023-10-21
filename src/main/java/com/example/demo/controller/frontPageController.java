package com.example.demo.Controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.Model.DoctorDetails;
import com.example.demo.Model.Message;
import com.example.demo.Model.User;
import com.example.demo.Repository.MessageRepo;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin("*")
public class frontPageController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MessageRepo messageRepo;
	
	private static final Logger logger = LogManager.getLogger(frontPageController.class);

	@GetMapping("/")
	public String Index(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "index";
	}

	@GetMapping("/doctors")
	public String Doctors(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		List<DoctorDetails> doctorDetails = userService.getAllDoctors();
		model.addAttribute("doctor", doctorDetails);
		return "Doctors";
	}
	
	
	
	
	
	
	
	@GetMapping("/doctorDetails/{id}")
    public String doctorList(@PathVariable Long id, Model model, Principal principal) {
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

            // Log an INFO message for successfully loading doctor details
            logger.info("Loaded doctor details for ID: " + id);

            return "doctor-info";
        } catch (Exception e) {
            // Log the exception with an ERROR level
            logger.error("An error occurred while loading doctor details for ID: " + id, e);

            // Log a WARN message for the load failure
            logger.warn("Failed to load doctor details for ID: " + id);

            return "redirect:/errorPage";
        }
    }
	
	
	
	
	
	

	@GetMapping("/about")
	public String About(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		
		return "about";
	}

	@GetMapping("/contact")
	public String Contact(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;

		model.addAttribute("isLoggedIn", isLoggedIn);

		if (isLoggedIn) {
			String email = principal.getName();
			User user = userRepo.findByEmail(email);
			model.addAttribute("user", user);
		}
		return "contact";
	}

	@GetMapping("/login")
	public String Login(Model model) {
		return "login";
	}

	@GetMapping("/signup")
	public String Signup(Model model, Principal principal) {
		return "signup";
	}

	  @GetMapping("/doctorInfo")
	    public String DoctorInfo(Model model, Principal principal) {
	        try {
	            boolean isLoggedIn = principal != null;

	            model.addAttribute("isLoggedIn", isLoggedIn);

	            if (isLoggedIn) {
	                String email = principal.getName();
	                User user = userRepo.findByEmail(email);
	                model.addAttribute("user", user);
	            }

	            // Log an INFO message for accessing the DoctorInfo page
	            logger.info("Accessed the DoctorInfo page");

	            return "doctor-info";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while accessing the DoctorInfo page", e);

	            // Log a WARN message for the access failure
	            logger.warn("Failed to access the DoctorInfo page");

	            return "redirect:/errorPage";
	        }
	    }

	  @PostMapping("/saveUser")
	    public String saveUser(@ModelAttribute User user, HttpSession session) {
		  try {
				User existingUser = userRepo.findByEmail(user.getEmail());

				if (existingUser != null) {
					session.setAttribute("msgError", "Email address already exists. Please use a different email.");
					session.removeAttribute("msg");
					// Log an INFO message
					logger.info("Registration failed for user with email: " + user.getEmail() + " - Email already exists");
					return "redirect:/signup";
				} else {
					User savedUser = userService.saveUser(user);

					if (savedUser != null) {
						session.setAttribute("msg", "Registration successful. Please sign in.");
						session.removeAttribute("msgError");

						// Log an INFO message for successful registration
						logger.info("User registered successfully with email: " + user.getEmail());

						return "redirect:/signup";
					} else {
						session.setAttribute("msgError", "Something went wrong on the server.");
						session.removeAttribute("msg");
						// Log a WARN message for registration failure
						logger.warn("Registration failed for user with email: " + user.getEmail());
						return "redirect:/signup";
					}
				}
			} catch (Exception e) {
				// Log the exception with an ERROR level
				logger.error("An error occurred while processing the registration request for user with email: "
						+ user.getEmail(), e);

				return "redirect:/errorPage";
			}
		}
	
	  @PostMapping("/sendMessage")
	    public String saveMessage(@ModelAttribute Message message, HttpSession session) {
	        try {
	            Message sentMessage = messageRepo.save(message);

	            // Log an INFO message for successful message submission
	            logger.info("Message sent successfully: " + message.toString());

	            return "redirect:/contact";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while sending the message", e);

	            // Log a WARN message for the message sending failure
	            logger.warn("Message sending failed");

	            return "redirect:/errorPage";
	        }
	    }

}
