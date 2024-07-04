package Ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Ecommerce.Model.AuthenticationToken;
import Ecommerce.Model.User;
@Repository
public interface TokenRepo extends JpaRepository<AuthenticationToken, Integer> {

	AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);
}
