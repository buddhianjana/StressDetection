package com.example.demo.Controller;

import java.security.Principal;
import java.util.List;

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

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Service.AdminService;
import com.example.demo.Service.UserService;

import jakarta.servlet.http.HttpSession;

import com.example.demo.Model.Book;
import com.example.demo.Model.Message;


@Controller
@CrossOrigin("*")
@RequestMapping("/admin/")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LogManager.getLogger(AdminController.class);

	
	@GetMapping("/")
	public String Index(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;
	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user = userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "Admin/index";
	}
	

    @GetMapping("/message")
    public String Message(Model model, Principal principal) {
        try {
            boolean isLoggedIn = principal != null;
            model.addAttribute("isLoggedIn", isLoggedIn);

            if (isLoggedIn) {
                String email = principal.getName();
                User user = userRepo.findByEmail(email);
                model.addAttribute("user", user);
            }
            List<Message> messages = adminService.getAllMessages();
            model.addAttribute("message", messages);

            // Log an INFO message for accessing the messages page
            logger.info("Accessed the messages page");

            return "Admin/messages";
        } catch (Exception e) {
            // Log the exception with an ERROR level
            logger.error("An error occurred while accessing the messages page", e);

            // Log a WARN message for the access failure
            logger.warn("Failed to access the messages page");

            return "redirect:/errorPage";
        }
    }
	
    @GetMapping("/manage")
    public String manageRole(Model model, Principal principal) {
        try {
            boolean isLoggedIn = principal != null;
            model.addAttribute("isLoggedIn", isLoggedIn);

            if (isLoggedIn) {
                String email = principal.getName();
                User user = userRepo.findByEmail(email);
                model.addAttribute("user", user);
            }
            model.addAttribute("user", adminService.getAllUsers());

            // Log an INFO message for accessing the manage role page
            logger.info("Accessed the manage role page");

            return "Admin/manage-role";
        } catch (Exception e) {
            // Log the exception with an ERROR level
            logger.error("An error occurred while accessing the manage role page", e);

            // Log a WARN message for the access failure
            logger.warn("Failed to access the manage role page");

            return "redirect:/errorPage";
        }
    }
	
    @GetMapping("/appointment")
    public String appointment(Model model, Principal principal) {
        try {
            boolean isLoggedIn = principal != null;
            model.addAttribute("isLoggedIn", isLoggedIn);

            if (isLoggedIn) {
                String email = principal.getName();
                User user = userRepo.findByEmail(email);
                model.addAttribute("user", user);
            }

            List<Book> appointmentsBooks = adminService.getAllAppointments();
            model.addAttribute("appointment", appointmentsBooks);

            // Log an INFO message for accessing the appointment page
            logger.info("Accessed the appointment page");

            return "Admin/appointments";
        } catch (Exception e) {
            // Log the exception with an ERROR level
            logger.error("An error occurred while accessing the appointment page", e);

            // Log a WARN message for the access failure
            logger.warn("Failed to access the appointment page");

            return "redirect:/errorPage";
        }
    }
	
	@GetMapping("/addusers")
	public String addUsers(Model model, Principal principal) {
		boolean isLoggedIn = principal != null;
	    model.addAttribute("isLoggedIn", isLoggedIn);

	    if (isLoggedIn) {
	        String email = principal.getName();
	        User user = userRepo.findByEmail(email);
	        model.addAttribute("user", user);
	    }
		return "Admin/addusers";
	}
	
	
	  @PostMapping("/saveUser")
	    public String saveUser(@ModelAttribute User user, HttpSession session) {
	        try {
	            User existingUser = userRepo.findByEmail(user.getEmail());
	            User savedUser = userService.saveUser(user);

	            // Log an INFO message for a successful user registration
	            logger.info("User registered with email: " + user.getEmail());

	            session.setAttribute("msg", "Registration successful. Please sign in.");
	            session.removeAttribute("msgError");

	            return "redirect:/admin/manage";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred during user registration", e);

	            // Log a WARN message for the registration failure
	            logger.warn("Failed to register a user with email: " + user.getEmail());

	            return "redirect:/errorPage";
	        }
	    }
	
	  @GetMapping("/deleteUser/{id}")
	    public String deleteUser(@PathVariable Long id) {
	        try {
	            adminService.deleteUserByID(id);

	            // Log an INFO message for successfully deleting a user
	            logger.info("Deleted user with ID: " + id);

	            return "redirect:/admin/manage";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while deleting a user with ID: " + id, e);

	            // Log a WARN message for the delete failure
	            logger.warn("Failed to delete a user with ID: " + id);

	            return "redirect:/errorPage";
	        }
	    }
	
	  @GetMapping("/editUsers")
	    public String editUsers(Model model, Principal principal) {
	        try {
	            boolean isLoggedIn = principal != null;
	            model.addAttribute("isLoggedIn", isLoggedIn);

	            if (isLoggedIn) {
	                String email = principal.getName();
	                User user = userRepo.findByEmail(email);
	                model.addAttribute("user", user);
	            }

	            // Log an INFO message for accessing the edit users page
	            logger.info("Accessed the edit users page");

	            return "Admin/edituser";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while accessing the edit users page", e);

	            // Log a WARN message for the access failure
	            logger.warn("Failed to access the edit users page");

	            return "redirect:/errorPage";
	        }
	    }
	
	
	  @GetMapping("/editUsers/{id}")
	    public String editProducts(@PathVariable Long id, Model model) {
	        try {
	            User user = adminService.getUserByID(id);
	            model.addAttribute("user", user);

	            // Log an INFO message for editing a user with a specific ID
	            logger.info("Editing user with ID: " + id);

	            return "Admin/edituser";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while editing the user with ID: " + id, e);

	            // Log a WARN message for the edit failure
	            logger.warn("Failed to edit user with ID: " + id);

	            return "redirect:/errorPage";
	        }
	    }

	  @PostMapping("/editUsers/{id}")
	    public String updateAdmin(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {
	        try {
	            User existingUser = adminService.getUserByID(id);
	            existingUser.setId(id);
	            existingUser.setFirstName(user.getFirstName());
	            existingUser.setLastName(user.getLastName());
	            existingUser.setEmail(user.getEmail());
	            existingUser.setRole(user.getRole());
	            existingUser.setPassword(user.getPassword());

	            adminService.updateUser(existingUser);

	            // Log an INFO message for successfully updating a user
	            logger.info("Updated user with ID: " + id);

	            return "redirect:/admin/manage";
	        } catch (Exception e) {
	            // Log the exception with an ERROR level
	            logger.error("An error occurred while updating the user with ID: " + id, e);

	            // Log a WARN message for the update failure
	            logger.warn("Failed to update user with ID: " + id);

	            return "redirect:/errorPage";
	        }
	    }
	
	
	
}
