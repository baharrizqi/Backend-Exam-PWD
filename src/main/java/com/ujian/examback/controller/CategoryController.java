package com.ujian.examback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ujian.examback.dao.CategoryRepo;
import com.ujian.examback.dao.MovieRepo;
import com.ujian.examback.entity.Category;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private MovieRepo movieRepo;
	
	@GetMapping
	public Iterable<Category> getCategory(){
		return categoryRepo.findAll();
	}
	
	@PostMapping
	public Category addCategory(@RequestBody Category category) {
		return categoryRepo.save(category);
	}
	
	// Delete Category (putusin semua hubungan dengan id itu)
	@DeleteMapping("/{categoryId}")
	public void deleteCategory(@PathVariable int categoryId) {
		Category findCategory = categoryRepo.findById(categoryId).get();
		
		findCategory.getMovies().forEach(movie -> {
			List<Category> movieCategory = movie.getCategories();
			movieCategory.remove(findCategory);
			movieRepo.save(movie);
		});
		
		findCategory.setMovies(null);
		
		categoryRepo.deleteById(categoryId);
	}
	
	// Edit Category
	@PutMapping("/edit")
	public Category editCategory(@RequestBody Category category) {
		Category findCategory = categoryRepo.findById(category.getId()).get();
		category.setMovies(findCategory.getMovies());
		return categoryRepo.save(category);
	}
}

