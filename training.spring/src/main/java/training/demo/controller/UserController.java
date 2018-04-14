package training.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import training.demo.model.User;
import training.demo.security.JwtTokenUtil;
import training.demo.service.UserJpaService;
 
@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {
	
	private String tokenHeader = "Authorization";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	UserJpaService userService;
	
	@RequestMapping(value = "user", method = RequestMethod.GET)
    public User getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Date date = jwtTokenUtil.getIssuedAtDateFromToken(token);
        System.out.println(date);
        User user = userService.loadUserByUsername(username);
        return user;
    }
	
	@RequestMapping(path = "/unprotected", method = RequestMethod.GET)
	public ResponseEntity<?> getUnprotectedGreeting() {
        return ResponseEntity.ok("Greetings from unprotected method!");
    }
	
	
	
	@RequestMapping(path = "/protected", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getProtectedGreeting() {
        return ResponseEntity.ok("Greetings from admin protected method!");
    }

	@RequestMapping(value = "/add_user")
	@ResponseBody
	public String addUser() {
		User user = new User();
		user.setUsername("firstTest");
		user.setPassword("lastTest");
		
		userService.addUser(user);
		
		return "User added.";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "find_all_users")
	@ResponseBody
	public String findAllUsers() {
		ObjectMapper mapper = new ObjectMapper();
		List<User> userList = userService.findAllUsers();
		List<User> userList1 = new ArrayList<User>();
		for(User user : userList) {
			if(!user.getUsername().equals("firstTest")) {
				userList1.add(user);
			}
		}
		
		String usersJson = "";
		try {
			usersJson = mapper.writeValueAsString(userList1);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return usersJson;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "userinfo")
	public String getUser(@RequestParam("id") int id, Model m) {
		
		User user = userService.findUserById(id);
		
		if(user == null) {
			System.out.println("User not found.");
			return null;
		}else {
			m.addAttribute("id", user.getUserId());
			m.addAttribute("firstname", user.getUsername());
			m.addAttribute("lastname", user.getPassword());
			
			//name of the view
			return "userinfo";
		}
	
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "get-user-info")
	@ResponseBody
	public String getUserInfo(@RequestParam("id") int id, Model m) {
		ObjectMapper mapper = new ObjectMapper();
		
		User user = userService.findUserById(id);
		
		String userJson = "";
		try {
			userJson = mapper.writeValueAsString(user);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return userJson;
		
	}
	
}