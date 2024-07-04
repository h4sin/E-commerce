package Ecommerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ecommerce.Model.Category;
import Ecommerce.Repository.CategoryRepo;

@Service
public class CategoryService {
	@Autowired
	CategoryRepo categoryRepo;
	
	 public void createCategory(Category category) {
	        categoryRepo.save(category);
	    }

	    public List<Category> listCategory() {
	        return categoryRepo.findAll();
	    }

	    public void editCategory(int categoryId, Category updateCategory) {
	        Category category = categoryRepo.findById(categoryId).get();
	        category.setCategoryName(updateCategory.getCategoryName());
	        category.setDescription(updateCategory.getDescription());
	        category.setImageURL(updateCategory.getImageURL());
	        categoryRepo.save(category);
	    }

	    public boolean findById(int categoryId) {
	        return categoryRepo.findById(categoryId).isPresent();
	    }
}
