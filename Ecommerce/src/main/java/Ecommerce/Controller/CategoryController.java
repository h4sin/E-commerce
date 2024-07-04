package Ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import Ecommerce.Common.ApiResponse;
import Ecommerce.Model.Category;
import Ecommerce.Service.CategoryService;
@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	 @PostMapping("/create")
	    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
	        categoryService.createCategory(category);
	        return new ResponseEntity<>(new ApiResponse(true, "a new category created"), HttpStatus.CREATED);
	    }

	    @GetMapping("/")
	    public List<Category> listCategory() {
	        return categoryService.listCategory();
	    }

	    @PostMapping("/update/{categoryId}")
	    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category ) {
	        System.out.println("category id " + categoryId);
	        if (!categoryService.findById(categoryId)) {
	            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exists"), HttpStatus.NOT_FOUND);
	        }
	        categoryService.editCategory(categoryId, category);
	        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "category has been updated"), HttpStatus.OK);
	    }
}
