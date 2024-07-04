package Ecommerce.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import Ecommerce.Common.ApiResponse;
import Ecommerce.DTO.ProductDTO;
import Ecommerce.Model.Category;
import Ecommerce.Model.Product;
import Ecommerce.Repository.CategoryRepo;
import Ecommerce.Service.CategoryService;
import Ecommerce.Service.ProductService;

public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	CategoryRepo categoryRepo;
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse>createProduct(@RequestBody ProductDTO productDTO){
		 Optional<Category> optionalCategory = categoryRepo.findById(productDTO.getCategoryId());
         if (!optionalCategory.isPresent()) {
             return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exists"), HttpStatus.BAD_REQUEST);
         }
         productService.createProduct(productDTO, optionalCategory.get());
         return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been added"), HttpStatus.CREATED);
	}
	
	 @GetMapping("/")
	    public ResponseEntity<List<ProductDTO>> getProducts() {
	        List<ProductDTO> products = productService.getAllProducts();
	        return new ResponseEntity<>(products, HttpStatus.OK);
	    }
	 
	 @PostMapping("/update/{productId}")
	    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") int productId, @RequestBody ProductDTO productDto) throws Exception {
	        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
	        if (!optionalCategory.isPresent()) {
	            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exists"), HttpStatus.BAD_REQUEST);
	        }
	        productService.updateProduct(productDto, productId);
	        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been updated"), HttpStatus.OK);
	    }

}
