package com.ujian.examback.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ujian.examback.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
