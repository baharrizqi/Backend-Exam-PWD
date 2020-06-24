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
import com.ujian.examback.entity.Movie;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieRepo movieRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@GetMapping
	public Iterable<Movie> getMovie(){
		return movieRepo.findAll();
	}
	
	@PostMapping
	public Movie addMovie(@RequestBody Movie movie) {
		return movieRepo.save(movie);
	}
	
	// Add Category ke Movie
	@PostMapping("/{movieId}/category/{categoryId}")
	public Movie AddCategoriesToMovies(@PathVariable int movieId,@PathVariable int categoryId) {
		Movie findMovie = movieRepo.findById(movieId).get();
		
		Category findCategory = categoryRepo.findById(categoryId).get();
		
		findMovie.getCategories().add(findCategory);
		
		return movieRepo.save(findMovie);
	}
	
	// Delete Movie dan Category
	@DeleteMapping("/delete/{id}")
	public void deleteMovieById(@PathVariable int id) {
		Movie findMovie = movieRepo.findById(id).get();
		
		findMovie.getCategories().forEach(category -> {
			List<Movie> categoryMovie = category.getMovies();
			categoryMovie.remove(findMovie);
			categoryRepo.save(category);
		});
		findMovie.setCategories(null);
		movieRepo.deleteById(id);
	}
	
	// Putusin Category di Movie (Tidak delete Category/putusin relasi category)
	@DeleteMapping("/delete/{movieId}/category/{categoryId}")
	public Movie deleteCategoryinMovie(@PathVariable int movieId,@PathVariable int categoryId) {
		Movie findMovie = movieRepo.findById(movieId).get();
		Category findCategory = categoryRepo.findById(categoryId).get();
		
		findMovie.getCategories().remove(findCategory);
		
		return movieRepo.save(findMovie);
	}
	
	// Edit Movie Tapi Categories null
//	@PutMapping
//	public Movie editMovie(@RequestBody Movie movie) {
//		Movie findMovie = movieRepo.findById(movie.getId()).get();
//		
//		if(findMovie.toString() == "Optional.empty") {
//			throw new RuntimeException("Product with id "+ movie.getId()+ " does not exist");
//		}
//
//		return movieRepo.save(movie);
//	}
	
	// Edit Movie tanpa hilang categories
	@PutMapping("/{movieId}")
	public Movie editMovie(@RequestBody Movie movie,@PathVariable int movieId) {
		Movie findMovie = movieRepo.findById(movieId).get();
		movie.setId(movieId);
		movie.setCategories(findMovie.getCategories());
		return movieRepo.save(movie);
	}
	
}
