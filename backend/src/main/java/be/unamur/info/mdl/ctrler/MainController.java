package be.unamur.info.mdl.ctrler;


import be.unamur.info.mdl.dto.CredentialDTO;
import be.unamur.info.mdl.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import be.unamur.info.mdl.dal.entity.User;
import be.unamur.info.mdl.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController    // This means that this class is a Controller
@RequestMapping(path="/api") // This means URL's start with /demo (after Application path)
public class MainController {
	@Autowired // This means to get the bean called userService
	           // Which is auto-generated by Spring, we will use it to handle the data
	private UserService userService;

	
	private Object sendJSON(Object result, int status) {
		return null;
	}
	
	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		if(name == "" || email == "") {
			return "Invalid Params Data";
		}

		User newUser = new User(name,email);
		// userService.addUser(newUser);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Map<Long, User> getAllUsers() {
		// This returns a JSON or XML with the users
		// Map<Long, User> users = new HashMap<Long, User>();
		// for (User user : userService.findAll()) {
		// 	users.put(user.id(), user);
		// }
		// return users;
		return null;
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	public @ResponseBody String login(@RequestBody CredentialDTO userDTO){
		String result;
		if(userService.login(userDTO))	result = String.format("{LOGIN SUCCESS: %b}",true);
		else result = String.format("{LOGIN SUCCESS: %b}",false);
		return result;
	}
	
}
