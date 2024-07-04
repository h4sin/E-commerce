package Ecommerce.Service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import Ecommerce.Exeptions.AuthenticationFailException;
import Ecommerce.Model.AuthenticationToken;
import Ecommerce.Model.User;
import Ecommerce.Repository.TokenRepo;

@Service
public class TokenService {
	 @Autowired
	    TokenRepo tokenRepo;

	    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
	        tokenRepo.save(authenticationToken);
	    }

	    public AuthenticationToken getToken(User user) {
	        return tokenRepo.findByUser(user);
	    }
	    
	    public User getUser(String token) {
	        final AuthenticationToken authenticationToken = tokenRepo.findByToken(token);
	        if(Objects.isNull(authenticationToken)) {
	            return null;
	        }
	        return authenticationToken.getUser();
	    }

	    public void authenticate(String token) throws AuthenticationFailException {
	        if(Objects.isNull(token)) {
	            throw new AuthenticationFailException("token not present");
	        }
	        if(Objects.isNull(getUser(token))) {
	            throw new AuthenticationFailException("token not valid");
	        }
	    }
}
