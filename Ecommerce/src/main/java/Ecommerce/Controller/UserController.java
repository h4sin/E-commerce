package Ecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ecommerce.DTO.ReponseDTO;
import Ecommerce.DTO.User.SignInDTO;
import Ecommerce.DTO.User.SignInReponseDTO;
import Ecommerce.DTO.User.SignUpDTO;
import Ecommerce.Service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public ReponseDTO signUp(@RequestBody SignUpDTO signUpDTO) {
		return userService.signUp(signUpDTO);
	}

	public SignInReponseDTO signIN(@RequestBody SignInDTO signInDTO) {
		return userService.signIn(signInDTO);
	}
}
