package training.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import training.demo.model.Location;
import training.demo.model.User;
import training.demo.security.JwtTokenUtil;
import training.demo.service.UserJpaService;
 
@RestController
@RequestMapping(value = "/api/user",
		produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
	
	private String tokenHeader = "Authorization";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	UserJpaService userService;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
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

	@RequestMapping(
			value = "/register",
			method = RequestMethod.POST)
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		if(userService.addUser(user)){
			return new ResponseEntity<User>(HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
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
