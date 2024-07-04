package Ecommerce.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ecommerce.DTO.ProductDTO;
import Ecommerce.Exeptions.ProductNotExistsException;
import Ecommerce.Model.Category;
import Ecommerce.Model.Product;
import Ecommerce.Repository.ProductRepo;

@Service
public class ProductService {
	@Autowired
	ProductRepo productRepo;
	
	 public void createProduct(ProductDTO productDto, Category category) {
	        Product product = new Product();
	        product.setDescription(productDto.getDescription());
	        product.setImageURL(productDto.getImageURL());
	        product.setProductName(productDto.getName());
	        product.setCategory(category);
	        product.setPrice(productDto.getPrice());
	        productRepo.save(product);
	    }

	    public ProductDTO getProductDto(Product product) {
	        ProductDTO productDto = new ProductDTO();
	        productDto.setDescription(product.getDescription());
	        productDto.setImageURL(product.getImageURL());
	        productDto.setName(product.getProductName());
	        productDto.setCategoryId(product.getCategory().getCategory_id());
	        productDto.setPrice(product.getPrice());
	        productDto.setId(product.getId());
	        return productDto;
	    }

	    public List<ProductDTO> getAllProducts() {
	        List<Product> allProducts = productRepo.findAll();

	        List<ProductDTO> productDtos = new ArrayList<>();
	        for(Product product: allProducts) {
	            productDtos.add(getProductDto(product));
	        }
	        return productDtos;
	    }

	    public void updateProduct(ProductDTO productDto, Integer productId) throws Exception {
	        Optional<Product> optionalProduct = productRepo.findById(productId);
	        if (!optionalProduct.isPresent()) {
	            throw new Exception("product not present");
	        }
	        Product product = optionalProduct.get();
	        product.setDescription(productDto.getDescription());
	        product.setImageURL(productDto.getImageURL());
	        product.setProductName(productDto.getName());
	        product.setPrice(productDto.getPrice());
	        productRepo.save(product);
	    }

	    public Product findById(int productId) throws ProductNotExistsException {
	        Optional<Product> optionalProduct = productRepo.findById(productId);
	        if (optionalProduct.isEmpty()) {
	            throw new ProductNotExistsException("product id is invalid: " + productId);
	        }
	        return optionalProduct.get();
	    }
}
