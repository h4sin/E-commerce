package Ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Ecommerce.Model.Product;
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
