package Ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Ecommerce.Model.Category;
@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
