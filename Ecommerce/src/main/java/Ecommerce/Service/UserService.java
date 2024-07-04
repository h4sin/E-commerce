package Ecommerce.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ecommerce.DTO.ReponseDTO;
import Ecommerce.DTO.User.SignInDTO;
import Ecommerce.DTO.User.SignInReponseDTO;
import Ecommerce.DTO.User.SignUpDTO;
import Ecommerce.Exeptions.AuthenticationFailException;
import Ecommerce.Exeptions.CustomException;
import Ecommerce.Model.AuthenticationToken;
import Ecommerce.Model.User;
import Ecommerce.Repository.UserRepo;
import jakarta.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	TokenService tokenService;

	@Transactional
	public ReponseDTO signUp(SignUpDTO signUpDTO) {
		if (Objects.nonNull(userRepo.findByEmail(signUpDTO.getEmail()))) {
			throw new CustomException("user already present");
		}
		if (Objects.nonNull(userRepo.findByEmail(signUpDTO.getEmail()))) {
			throw new CustomException("user already present");
		}

		String encryptedPassword = signUpDTO.getPassword();

		try {
			encryptedPassword = hashPassword(signUpDTO.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		User user = new User(signUpDTO.getFirstName(), signUpDTO.getLastName(), signUpDTO.getEmail(),
				encryptedPassword);

		userRepo.save(user);

		final AuthenticationToken authenticationToken = new AuthenticationToken(user);

		tokenService.saveConfirmationToken(authenticationToken);
		ReponseDTO reponseDTO = new ReponseDTO("success", "user created succesfully");
		return reponseDTO;
	}

	private String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return hash;
	}

	public SignInReponseDTO signIn(SignInDTO signInDto) {

		User user = userRepo.findByEmail(signInDto.getEmail());

		if (Objects.isNull(user)) {
			throw new AuthenticationFailException("user is not valid");
		}

		try {
			if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
				throw new AuthenticationFailException("wrong password");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		AuthenticationToken token = tokenService.getToken(user);

		if (Objects.isNull(token)) {
			throw new CustomException("token is not present");
		}

		return new SignInReponseDTO("sucess", token.getToken());

	}
}
