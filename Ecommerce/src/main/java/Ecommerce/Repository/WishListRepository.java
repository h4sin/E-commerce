package Ecommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Ecommerce.Model.User;
import Ecommerce.Model.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
	List<WishList> findAllByUserOrderByCreatedDateDesc(User user);
}
